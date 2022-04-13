package com.juliansanchez.tenpochallenge.sum.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SumHistoryRepository extends JpaRepository<SumHistory, Integer> {

    Page<SumHistory> findSumHistoryByUsername(String username, Pageable pageable);
}
