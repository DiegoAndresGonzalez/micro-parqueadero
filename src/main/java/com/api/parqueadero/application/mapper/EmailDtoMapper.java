package com.api.parqueadero.application.mapper;

import com.api.parqueadero.application.dtos.request.EmailRequestDto;
import com.api.parqueadero.domain.model.EmailModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EmailDtoMapper {

    EmailModel toEmailModel(EmailRequestDto emailRequestDto);
    EmailRequestDto toEmailRequestDto(EmailModel emailModel);

}
