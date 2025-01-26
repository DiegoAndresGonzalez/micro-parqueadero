package com.api.parqueadero.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EarningModel {

    private Integer dailyEarnings;
    private Integer weeklyEarnings;
    private Integer monthlyEarnings;
    private Integer yearlyEarnings;

}
