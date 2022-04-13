package com.juliansanchez.tenpochallenge.sum;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SumResponse {
    private BigDecimal result;
}
