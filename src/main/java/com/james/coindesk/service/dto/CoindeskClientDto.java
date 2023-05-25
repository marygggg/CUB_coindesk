package com.james.coindesk.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CoindeskClientDto {

    private TimeDto time;

    private String disclaimer;

    private String chartName;

    private Map<String, BpiDto> bpi;

    @Data
    public static class TimeDto {
        private String updated;
        private String updatedISO;
        private String updateduk;
    }

    @Data
    public static class BpiDto {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        private BigDecimal rate_float;
    }
}
