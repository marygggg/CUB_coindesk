package com.james.coindesk.service;

import com.james.coindesk.repository.CurrencyNameRepository;
import com.james.coindesk.repository.entity.CurrencyNameEntity;
import com.james.coindesk.service.dto.CoindeskClientDto;
import com.james.coindesk.service.dto.CoindeskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoindeskClientService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyNameRepository currencyNameRepository;

    @Transactional
    public CoindeskClientDto callCoindeskApi() {
        ResponseEntity<CoindeskClientDto> responseEntity;

        // call api
        String uri = "https://api.coindesk.com/v1/bpi/currentprice.json";
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "Bearer API-KEY");
        HttpEntity<Object> requestEntity = new HttpEntity<>(header);
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, CoindeskClientDto.class);

        // get response body
        CoindeskClientDto responseBody = responseEntity.getBody();

        return responseBody;
    }

    public CoindeskDto transformCoindeskData(CoindeskClientDto coindeskClientDto) {
        CoindeskDto coindeskDto = new CoindeskDto();
        List<CoindeskDto.BpiDto> bpiList = new ArrayList<>();

        // time
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(coindeskClientDto.getTime().getUpdatedISO(), DateTimeFormatter.ISO_DATE_TIME);
        coindeskDto.setUpdated(zonedDateTime.withZoneSameInstant(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        // bpiList
        coindeskClientDto.getBpi().values().forEach(value -> {
            CoindeskDto.BpiDto bpiDto = new CoindeskDto.BpiDto();

            Optional<CurrencyNameEntity> entity = currencyNameRepository.findById(value.getCode());
            bpiDto.setCode(value.getCode());
            bpiDto.setName(entity.isPresent() ? entity.get().getName() : "");
            bpiDto.setRate_float(value.getRate_float());
            bpiList.add(bpiDto);
        });
        coindeskDto.setBpiList(bpiList);

        return coindeskDto;
    }
}
