package com.james.coindesk.service;

import com.james.coindesk.repository.CurrencyNameRepository;
import com.james.coindesk.repository.entity.CurrencyNameEntity;
import com.james.coindesk.service.dto.CurrencyNameDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CurrencyNameService.class)
@ExtendWith(MockitoExtension.class)
class CurrencyNameServiceTest {
    @Autowired
    private CurrencyNameService currencyNameService;

    @MockBean
    private CurrencyNameRepository currencyNameRepository;

    @Test
    void findAll() {
        List<CurrencyNameEntity> entityList = new ArrayList<>();
        entityList.add(new CurrencyNameEntity("USD", "美元"));
        entityList.add(new CurrencyNameEntity("GBP", "英鎊"));
        entityList.add(new CurrencyNameEntity("EUR", "歐元"));
        Mockito.when(currencyNameRepository.findAll()).thenReturn(entityList);

        List<CurrencyNameDto> dtoList = currencyNameService.findAll();
        assertNotNull(dtoList);
        assertEquals("USD", dtoList.get(0).getCode());
        assertEquals("美元", dtoList.get(0).getName());
        assertEquals("GBP", dtoList.get(1).getCode());
        assertEquals("英鎊", dtoList.get(1).getName());
        assertEquals("EUR", dtoList.get(2).getCode());
        assertEquals("歐元", dtoList.get(2).getName());
    }

    @Test
    void insert() {
        Mockito.when(currencyNameRepository.save(Mockito.any(CurrencyNameEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        CurrencyNameDto dto = new CurrencyNameDto("USD", "美金");
        boolean success = currencyNameService.insert(dto);
        assertTrue(success);
    }

    @Test
    void update() {
        Optional<CurrencyNameEntity> entity = Optional.of(new CurrencyNameEntity("USD", "美元"));
        Mockito.when(currencyNameRepository.findById(Mockito.eq("USD")))
                .thenReturn(entity);
        Mockito.when(currencyNameRepository.save(Mockito.any(CurrencyNameEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        CurrencyNameDto dto = new CurrencyNameDto("USD", "美元");
        CurrencyNameDto result = currencyNameService.update("USD", dto);
        assertNotNull(result);
        assertEquals("USD", result.getCode());
        assertEquals("美元", result.getName());
    }

    @Test
    void delete() {
        Mockito.doNothing().when(currencyNameRepository).deleteById(Mockito.anyString());

        currencyNameService.delete("USD");
        Mockito.verify(currencyNameRepository, Mockito.times(1)).deleteById(Mockito.anyString());
    }
}