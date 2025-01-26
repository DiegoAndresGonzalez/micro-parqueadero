package com.api.parqueadero.domain.utils;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String ASSOCIATE_ROLE = "SOCIO";
    public static final String USER_ALREADY_EXISTS = "Ya existe un usuario creado con este correo";
    public static final String EMPTY_PASSWORD = "La contraseña no puede estar vacia";
    public static final String EMPTY_EMAIL = "El correo no puede estar vacio";
    public static final String EMPTY_NAME = "El nombre no puede estar vacio";
    public static final String PARKING_NOT_FOUND = "El parqueadero ingresado no existe";
    public static final String VEHICLE_NOT_FOUND = "El vehiculo no se encuentra en el parqueadero ingresado";
    public static final String PARKING_ALREADY_EXISTS = "El nombre del parqueadero ingresado ya existe";
    public static final String USER_NOT_FOUND = "El usuario ingresado no existe";
    public static final String USER_ALREADY_ASSOCIATED = "La asociación ya existe entre el usuario y el parqueadero";
    public static final String NOT_ASSIGNED_NOT_FOUND = "Parqueadero no encontrado o el socio no lo tiene asignado.";
    public static final String NOT_ASSIGNED = "El parqueadero existe pero no se encuentra asignado al socio.";
    public static final String ALREADY_REGISTERED = "No se puede Registrar Ingreso, ya tienes este vehiculo registrado.";
    public static final String ALREADY_PARKED = "No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero.";
    public static final int NUMBER_ONE = 1;
    public static final String NOT_AUTHORIZED_PARKING = "El usuario no está autorizado para registrar la salida de este vehiculo.";
    public static final String NONE_ASSIGNED = "El usuario no tiene ningun parqueadero asignado";
    public static final String NOT_PARKED =  "No se puede Registrar Salida, no existe la placa en el parqueadero";
    public static final String EMPTY_LOCATION = "La ubicación del parqueadero no puede estar vacía";
    public static final String EMPTY_MAX_VEHICLE_AMOUNT = "La cantidad máxima de vehiculos permitidos no puede estar vacia";
    public static final String EMPTY_COST = "El costo por hora no puede estar vacio.";
    public static final String PLATE_REGEX = "^[A-Za-z0-9]{6}$";
    public static final String PARKING_FULL = "El parqueadero esta lleno";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final String EMPTY_REGISTRY = "La placa o el ID del estacionamiento no pueden estar vacios";
    public static final String INVALID_PLATE = "La placa ingresada no cumple con el formato adecuado";
    public static final String EMPTY_USER_UPDATE = "Debe proporcionar al menos un dato para actualizar (nombre, correo o contraseña)";
    public static final String WRONG_EMAIL = "El correo electrónico proporcionado no tiene un formato válido";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String INVALID_TOKEN = "La sesión ha expirado, debes logearte denuevo";
    public static final String NOT_PERMITTED = "No tienes los permisos adecuados para realizar esta acción";
}

