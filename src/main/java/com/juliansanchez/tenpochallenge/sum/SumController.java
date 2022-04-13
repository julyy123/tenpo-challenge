package com.juliansanchez.tenpochallenge.sum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Sum controller
 */
@RestController
public class SumController {

    private SumService sumService;

    @Autowired
    public SumController(final SumService sumService) {
        this.sumService = sumService;
    }

    /**
     * Sum given numbers
     *
     * @param sumRequest {@link SumRequest}
     * @return result    {@link ResponseEntity}
     */
    @PostMapping("/sum")
    public ResponseEntity sum(@Valid @RequestBody SumRequest sumRequest) {
        return ResponseEntity.ok(sumService.sum(sumRequest));
    }

    /**
     * Gets sum history pages by username
     *
     * @param pageable            {@link Pageable}
     * @return Page of SumHistory {@link ResponseEntity}
     */
    @GetMapping("/sum/history")
    public ResponseEntity getSumHistory(Pageable pageable) {
        return ResponseEntity.ok(sumService.getSumHistoryPages(pageable));
    }
}
