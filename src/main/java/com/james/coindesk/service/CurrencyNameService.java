package com.james.coindesk.service;

import com.james.coindesk.repository.entity.CurrencyNameEntity;
import com.james.coindesk.repository.CurrencyNameRepository;
import com.james.coindesk.service.dto.CurrencyNameDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CurrencyNameService {

    @Autowired
    private CurrencyNameRepository currencyNameRepository;

    @Transactional
    public List<CurrencyNameDto> findAll() {
        List<CurrencyNameDto> dtoList = new ArrayList<>();

        List<CurrencyNameEntity> data = currencyNameRepository.findAll();
        data.forEach(entity -> {
            CurrencyNameDto dto = new CurrencyNameDto();
            dto.setCode(entity.getCode());
            dto.setName(entity.getName());
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Transactional
    public boolean insert(CurrencyNameDto currencyNameDto) {
        if (!currencyNameRepository.existsById(currencyNameDto.getCode())) {
            CurrencyNameEntity entity= new CurrencyNameEntity();
            entity.setCode(currencyNameDto.getCode());
            entity.setName(currencyNameDto.getName());
            currencyNameRepository.save(entity);
            return true;
        }

        return false;
    }

    @Transactional
    public CurrencyNameDto update(String id, CurrencyNameDto currencyNameDto) {
        Optional<CurrencyNameEntity> entityOptional = currencyNameRepository.findById(id);
        if (entityOptional.isPresent()) {
            entityOptional.get().setCode(currencyNameDto.getCode());
            entityOptional.get().setName(currencyNameDto.getName());
            CurrencyNameEntity resEntity = currencyNameRepository.save(entityOptional.get());

            return new CurrencyNameDto(resEntity.getCode(), resEntity.getName());
        }

        return null;
    }

    @Transactional
    public void delete(String id) {
        currencyNameRepository.deleteById(id);
    }
}
