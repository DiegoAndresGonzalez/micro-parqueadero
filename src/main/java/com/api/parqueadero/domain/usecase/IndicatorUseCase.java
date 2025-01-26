package com.api.parqueadero.domain.usecase;

import com.api.parqueadero.domain.exceptions.BadUserRequestException;
import com.api.parqueadero.domain.model.EarningModel;
import com.api.parqueadero.domain.model.ParkingModel;
import com.api.parqueadero.domain.model.RegistryModel;
import com.api.parqueadero.domain.model.UserModel;
import com.api.parqueadero.domain.ports.in.IndicatorServicePort;
import com.api.parqueadero.domain.ports.out.AdminPersistencePort;
import com.api.parqueadero.domain.ports.out.AssociatePersistencePort;
import com.api.parqueadero.domain.ports.out.IndicatorPersistencePort;
import com.api.parqueadero.domain.utils.Constants;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@RequiredArgsConstructor
public class IndicatorUseCase implements IndicatorServicePort {

    private final IndicatorPersistencePort persistencePort;
    private final AssociatePersistencePort associatePersistencePort;
    private final AdminPersistencePort adminPersistencePort;

    @Override
    public List<RegistryModel> getTop10Vehicles(Long parkingId) {
        return persistencePort.getTop10Vehicles(parkingId);
    }

    @Override
    public List<RegistryModel> getFirstTimeVehicle(Long parkingId) {
        return persistencePort.getFirstTimeVehicles(parkingId);
    }

    @Override
    public EarningModel getEarnings(Long parkingId) {

        ParkingModel actualParking = associatePersistencePort.getParkingById(parkingId);

        if(actualParking == null) {
            throw new BadUserRequestException(Constants.PARKING_NOT_FOUND);
        }

        if (Boolean.FALSE.equals(associatePersistencePort.checkIfParkingIsAssociated
                (parkingId, associatePersistencePort.getLoggedAssociateId()))){
            throw new BadUserRequestException(Constants.NOT_ASSIGNED);
        }


        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = now.with(LocalTime.MAX);

        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(LocalTime.MAX);

        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        LocalDateTime startOfYear = now.with(TemporalAdjusters.firstDayOfYear()).toLocalDate().atStartOfDay();
        LocalDateTime endOfYear = now.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);

        // Calcular ganancias para hoy, esta semana, este mes y este año
        Integer dailyEarnings = calculateEarnings(parkingId, startOfDay, endOfDay, actualParking.getCostPerHour());
        Integer weeklyEarnings = calculateEarnings(parkingId, startOfWeek, endOfWeek, actualParking.getCostPerHour());
        Integer monthlyEarnings = calculateEarnings(parkingId, startOfMonth, endOfMonth, actualParking.getCostPerHour());
        Integer yearlyEarnings = calculateEarnings(parkingId, startOfYear, endOfYear, actualParking.getCostPerHour());

        return new EarningModel(dailyEarnings, weeklyEarnings, monthlyEarnings, yearlyEarnings);

    }

    private Integer calculateEarnings(Long parkingId, LocalDateTime startDate, LocalDateTime endDate, Integer costPerHour) {
        List<RegistryModel> registries = persistencePort.calculateEarningsByParkingId(parkingId);
        return getInteger(startDate, endDate, costPerHour, registries);
    }

    @Override
    public List<UserModel> associateTop() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(LocalTime.MAX);

        // Obtener los registros dentro del rango de fechas
        List<RegistryModel> registries = persistencePort.calculateEarningsByEntryBetween(startOfWeek, endOfWeek);
        Map<Long, Set<String>> uniquePlatesPerUser = new HashMap<>();

        // Recorrer los registros y contar los vehículos únicos por usuario
        for (RegistryModel registry : registries) {
            Long userId = registry.getUserModel().getId();

            // Agregar la placa al conjunto de placas únicas para el usuario
            Set<String> uniquePlates = uniquePlatesPerUser.getOrDefault(userId, new HashSet<>());
            uniquePlates.add(registry.getPlate());
            uniquePlatesPerUser.put(userId, uniquePlates);
        }

        // Asociar la cantidad de vehículos únicos con cada usuario, excluyendo los inexistentes
        return uniquePlatesPerUser.entrySet().stream()
                .map(entry -> {
                    UserModel user = adminPersistencePort.findUserById(entry.getKey());
                    if (user != null) { // Verificar si el usuario existe
                        user.setVehicleAmount(entry.getValue().size()); // Setear la cantidad de vehículos únicos
                        return user;
                    }
                    return null; // Si no existe, retornamos null
                })
                .filter(Objects::nonNull) // Excluir los usuarios inexistentes
                .sorted((u1, u2) -> u2.getVehicleAmount().compareTo(u1.getVehicleAmount())) // Ordenar por vehículos únicos
                .limit(3) // Obtener los top 3 usuarios
                .toList();
    }

    @Override
    public List<ParkingModel> getTop3ParkingEarning() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX);

        // Obtener todos los registros dentro del rango de la semana
        List<RegistryModel> registries = persistencePort.calculateEarningsByEntryBetween(startOfWeek, endOfWeek);

        // Mapa para almacenar las ganancias por parqueadero
        Map<Long, Integer> earningsPerParking = new HashMap<>();

        for (RegistryModel registry : registries) {
            Long parkingId = registry.getParkingModel().getId();

            Integer earnings = calculateEarningsForRegistry(registry);
            earningsPerParking.put(parkingId, earningsPerParking.getOrDefault(parkingId, 0) + earnings);
        }

        // Ordenar y retornar los top 3 parqueaderos, excluyendo los inexistentes
        return earningsPerParking.entrySet().stream()
                .map(entry -> {
                    ParkingModel parking = adminPersistencePort.findParkingById(entry.getKey());
                    if (parking != null) { // Verificar si el parqueadero existe
                        parking.setTotalAmount(entry.getValue()); // Setear las ganancias
                        return parking;
                    }
                    return null; // Si no existe, retornamos null
                })
                .filter(Objects::nonNull) // Excluir los parqueaderos inexistentes
                .sorted((p1, p2) -> p2.getTotalAmount().compareTo(p1.getTotalAmount())) // Ordenar por ganancias
                .limit(3) // Obtener los top 3 parqueaderos
                .toList();
    }

    private Integer calculateEarningsForRegistry(RegistryModel registry) {
        Integer costPerHour = registry.getParkingModel().getCostPerHour();
        LocalDateTime entry = registry.getEntry();
        LocalDateTime departure = registry.getDeparture() != null ? registry.getDeparture() : LocalDateTime.now();

        // Asegurarnos de no calcular más allá del rango del registro
        long durationInMinutes = Duration.between(entry, departure).toMinutes();

        if (durationInMinutes <= 60) {
            return costPerHour; // Costo por la primera hora
        } else {
            long remainingMinutes = durationInMinutes - 60;
            double costPerMinute = costPerHour / 60.0;
            return (int) (costPerHour + Math.ceil(remainingMinutes * costPerMinute));
        }
    }

    private Integer getInteger(LocalDateTime startDate, LocalDateTime endDate, Integer costPerHour, List<RegistryModel> registries) {
        int totalEarnings = 0;
        double costPerMinute = costPerHour / 60.0;

        for (RegistryModel registry : registries) {
            LocalDateTime entry = registry.getEntry();
            LocalDateTime departure = registry.getDeparture() != null ? registry.getDeparture() : LocalDateTime.now();

            if (entry.isBefore(startDate)) {
                entry = startDate;
            }
            if (departure.isAfter(endDate)) {
                departure = endDate;
            }

            while (!entry.toLocalDate().isAfter(departure.toLocalDate())) {
                LocalDateTime startOfCurrentDay = entry.toLocalDate().atStartOfDay();
                LocalDateTime endOfCurrentDay = entry.toLocalDate().atTime(LocalTime.MAX);

                LocalDateTime effectiveStart = entry.isAfter(startOfCurrentDay) ? entry : startOfCurrentDay;
                LocalDateTime effectiveEnd = departure.isBefore(endOfCurrentDay) ? departure : endOfCurrentDay;

                long durationInMinutes = Duration.between(effectiveStart, effectiveEnd).toMinutes();

                if (durationInMinutes <= 60) {
                    totalEarnings += costPerHour;
                } else {
                    long remainingMinutes = durationInMinutes - 60;
                    totalEarnings += (int) (costPerHour + Math.ceil(remainingMinutes * costPerMinute));
                }

                entry = startOfCurrentDay.plusDays(1);
            }
        }

        return totalEarnings;
    }


}
