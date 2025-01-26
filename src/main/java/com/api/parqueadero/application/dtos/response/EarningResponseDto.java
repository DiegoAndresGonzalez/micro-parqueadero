package com.api.parqueadero.application.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarningResponseDto {
    private Integer dailyEarnings;
    private Integer weeklyEarnings;
    private Integer monthlyEarnings;
    private Integer yearlyEarnings;
}
