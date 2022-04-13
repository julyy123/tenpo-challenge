package com.juliansanchez.tenpochallenge.sum;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SumControllerTest {

    private static final String SUM_POST_URL = "/sum";
    private static final String SUM_POST_HISTORY = "/sum/history";
    private static final String DUMMY_JSON = "{\"dummy\": \"dummy\"}";

    @Mock
    private SumService sumService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {

        when(sumService.sum(any()))
                .thenReturn(new SumResponse(BigDecimal.valueOf(11)));
    }

    @Test
    @WithMockUser
    public void whenSumRequestIsValidShouldSumTwoNumbers() throws Exception {
        SumRequest sumRequest = new SumRequest(BigDecimal.TEN, BigDecimal.ONE);

        RequestBuilder request = prepareRequest(SUM_POST_URL, objectMapper.writeValueAsString(sumRequest));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(11));
    }

    @Test
    @WithMockUser
    public void whenSumRequestIsEmptyShouldReturnBadRequest() throws Exception {
        RequestBuilder request = prepareRequest(SUM_POST_URL, "{}");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void whenSumRequestIsInvalidShouldReturnBadRequest() throws Exception {
        RequestBuilder request = prepareRequest(SUM_POST_URL, DUMMY_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    private RequestBuilder prepareRequest(final String url, final String sumRequest) {
        return MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(sumRequest);
    }
}
