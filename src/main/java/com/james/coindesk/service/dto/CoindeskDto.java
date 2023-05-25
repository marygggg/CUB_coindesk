package com.james.coindesk.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CoindeskDto {

    private String updated;

    private List<BpiDto> bpiList;

    @Data
    public static class BpiDto {
        private String code;

        private String name;

        private BigDecimal rate_float;
    }
}
