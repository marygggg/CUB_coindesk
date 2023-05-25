package com.james.coindesk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.coindesk.repository.CurrencyNameRepository;
import com.james.coindesk.repository.entity.CurrencyNameEntity;
import com.james.coindesk.service.dto.CoindeskClientDto;
import com.james.coindesk.service.dto.CoindeskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CoindeskClientService.class)
@ExtendWith(MockitoExtension.class)
class CoindeskClientServiceTest {

    @Autowired
    private CoindeskClientService coindeskClientService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private CurrencyNameRepository currencyNameRepository;
    @Test
    void callCoindeskApi() throws JsonProcessingException {
        String responseString = "{\"time\":{\"updated\":\"May 24, 2023 06:08:00 UTC\",\"updatedISO\":\"2023-05-24T06:08:00+00:00\",\"updateduk\":\"May 24, 2023 at 07:08 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"26,733.6671\",\"description\":\"United States Dollar\",\"rate_float\":26733.6671},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"22,338.4384\",\"description\":\"British Pound Sterling\",\"rate_float\":22338.4384},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"26,042.4949\",\"description\":\"Euro\",\"rate_float\":26042.4949}}}";
        CoindeskClientDto responseDto = new ObjectMapper().readValue(responseString, CoindeskClientDto.class);
        ResponseEntity<CoindeskClientDto> responseEntity = ResponseEntity.ok(responseDto);
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(), ArgumentMatchers.<Class<CoindeskClientDto>>any()))
                .thenReturn(responseEntity);

        CoindeskClientDto result = coindeskClientService.callCoindeskApi();
        assertNotNull(result);
        assertEquals(responseString, new ObjectMapper().writeValueAsString(result));
    }

    @Test
    void transformCoindeskData() throws JsonProcessingException {
        Mockito.when(currencyNameRepository.findById(Mockito.eq("USD")))
                .thenReturn(Optional.of(new CurrencyNameEntity("USD", "美元")));
        Mockito.when(currencyNameRepository.findById(Mockito.eq("GBP")))
                .thenReturn(Optional.of(new CurrencyNameEntity("GBP", "英鎊")));
        Mockito.when(currencyNameRepository.findById(Mockito.eq("EUR")))
                .thenReturn(Optional.of(new CurrencyNameEntity("EUR", "歐元")));

        String responseString = "{\"time\":{\"updated\":\"May 24, 2023 06:08:00 UTC\",\"updatedISO\":\"2023-05-24T06:08:00+00:00\",\"updateduk\":\"May 24, 2023 at 07:08 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"26,733.6671\",\"description\":\"United States Dollar\",\"rate_float\":26733.6671},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"22,338.4384\",\"description\":\"British Pound Sterling\",\"rate_float\":22338.4384},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"26,042.4949\",\"description\":\"Euro\",\"rate_float\":26042.4949}}}";
        CoindeskClientDto responseDto = new ObjectMapper().readValue(responseString, CoindeskClientDto.class);
        CoindeskDto result = coindeskClientService.transformCoindeskData(responseDto);
        assertNotNull(result);
        assertEquals(result.getBpiList().size(), 3);
        assertEquals("2023/05/24 14:08:00", result.getUpdated());
        assertEquals("美元", result.getBpiList().get(0).getName());
        assertEquals("英鎊", result.getBpiList().get(1).getName());
        assertEquals("歐元", result.getBpiList().get(2).getName());
    }
}