package com.juliansanchez.tenpochallenge.sum;


import com.juliansanchez.tenpochallenge.security.user.UserService;
import com.juliansanchez.tenpochallenge.sum.history.SumHistory;
import com.juliansanchez.tenpochallenge.sum.history.SumHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SumServiceTest {

    private static final String DUMMY_USERNAME = "dummy";

    @Mock
    private UserService userService;

    @Mock
    private SumHistoryRepository sumHistoryRepository;

    @InjectMocks
    private SumService sumService;

    @BeforeEach
    void setup() {
        when(userService.getLoggedUsername())
                .thenReturn(DUMMY_USERNAME);
    }

    @Test
    public void whenSumRequestIsValidShouldSaveAndReturnSumResponse() {
        SumRequest sumRequest = new SumRequest(BigDecimal.ONE, BigDecimal.TEN);
        SumResponse expectedSumResponse = new SumResponse(BigDecimal.valueOf(11));

        SumResponse sumResponse = sumService.sum(sumRequest);

        verify(sumHistoryRepository, times(1)).save(any());
        assertThat(sumResponse).isEqualTo(expectedSumResponse);
    }

    @Test
    public void whenSumRequestIsValidButSumHistoryRepositoryFailsShouldThrowException() {
        SumRequest sumRequest = new SumRequest(BigDecimal.ONE, BigDecimal.TEN);

        doThrow(mock(DataAccessException.class)).when(sumHistoryRepository).save(any());

        assertThrows(DataAccessException.class, () -> {
            sumService.sum(sumRequest);
        });
    }

    @Test
    public void whenGetSumHistoryPagesShouldReturnSumHistoriesWithGivenPageRequest() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<SumHistory> expectedSumHistories = new PageImpl(List.of(new SumHistory(), new SumHistory()));

        when(sumHistoryRepository.findSumHistoryByUsername(DUMMY_USERNAME, pageRequest))
                .thenReturn(expectedSumHistories);

        Page<SumHistory> sumHistories = sumService.getSumHistoryPages(pageRequest);

        assertThat(sumHistories.getSize())
                .isEqualTo(expectedSumHistories.getSize());
        assertThat(sumHistories.getContent())
                .isEqualTo(expectedSumHistories.getContent());
    }

    @Test
    public void whenGetSumHistoryPagesSumHistoryRepositoryFailesShouldThrowException() {
        PageRequest pageRequest = PageRequest.of(0, 2);

        doThrow(mock(DataAccessException.class)).when(sumHistoryRepository).findSumHistoryByUsername(any(), any());

        assertThrows(DataAccessException.class, () -> {
            sumService.getSumHistoryPages(pageRequest);
        });
    }
}
