package com.juliansanchez.tenpochallenge.sum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SumRequest {

    @NotNull
    private BigDecimal firstNumber;

    @NotNull
    private BigDecimal secondNumber;
}
