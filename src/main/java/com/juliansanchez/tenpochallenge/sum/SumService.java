package com.juliansanchez.tenpochallenge.sum;

import com.juliansanchez.tenpochallenge.security.user.UserService;
import com.juliansanchez.tenpochallenge.sum.history.SumHistory;
import com.juliansanchez.tenpochallenge.sum.history.SumHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Sum service class
 */
@Service
public class SumService {

    private UserService userService;
    private SumHistoryRepository sumHistoryRepository;

    @Autowired
    public SumService(final SumHistoryRepository sumHistoryRepository,
                      final UserService userService) {
        this.sumHistoryRepository = sumHistoryRepository;
        this.userService = userService;
    }

    /**
     * Sum given numbers and save into sumHistoryRepository
     *
     * @param sumRequest         {@link SumRequest}
     * @return result of the sum {@link SumResponse}
     */
    public SumResponse sum(final SumRequest sumRequest) {
        BigDecimal result = sumRequest.getFirstNumber().add(sumRequest.getSecondNumber());

        saveSumHistory(sumRequest, result);

        return new SumResponse(result);
    }

    /**
     * Gets sum history pages by username
     * @param pageable              {@link Pageable}
     * @return Page of SumHistories {@link Page}
     */
    public Page<SumHistory> getSumHistoryPages(Pageable pageable) {
        String username = userService.getLoggedUsername();

        return sumHistoryRepository.findSumHistoryByUsername(username, pageable);
    }

    /**
     * Saves SumHistory for given sumRequest and result
     *
     * @param sumRequest {@link SumRequest}
     * @param result     {@link SumResponse}
     */
    private void saveSumHistory(final SumRequest sumRequest, final BigDecimal result) {
        String username = userService.getLoggedUsername();

        SumHistory sumHistory = new SumHistory(username, sumRequest.getFirstNumber(), sumRequest.getSecondNumber(), result);

        sumHistoryRepository.save(sumHistory);
    }

}
