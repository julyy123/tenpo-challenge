package com.juliansanchez.tenpochallenge.sum.history;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * I think it would be better to store the request history in a no-sql database
 * and retrieve it with elastic search or something like that
 */

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sum_history")
public class SumHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    @Column(name = "first_number")
    private BigDecimal firstNumber;

    @NonNull
    @Column(name = "second_number")
    private BigDecimal secondNumber;

    @NonNull
    @Column(name = "result")
    private BigDecimal result;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

}
