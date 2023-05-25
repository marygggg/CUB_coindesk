package com.james.coindesk.controller;

import com.james.coindesk.service.CurrencyNameService;
import com.james.coindesk.service.dto.CurrencyNameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/${version}/currency/names")
public class CurrencyNameController {

    @Autowired
    private CurrencyNameService currencyNameService;

    @GetMapping("")
    public ResponseEntity<List<CurrencyNameDto>> findAll() {
        return new ResponseEntity<>(currencyNameService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> insert(@RequestBody CurrencyNameDto currencyNameDto) {
        return ResponseEntity.ok(currencyNameService.insert(currencyNameDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyNameDto> update(@PathVariable(value = "id") String id,
                                              @RequestBody CurrencyNameDto currencyNameDto) {
        return ResponseEntity.ok(currencyNameService.update(id, currencyNameDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
        currencyNameService.delete(id);
        return ResponseEntity.ok(true);
    }
}
