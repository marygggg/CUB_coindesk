package com.james.coindesk.controller;

import com.james.coindesk.service.CoindeskClientService;
import com.james.coindesk.service.dto.CoindeskClientDto;
import com.james.coindesk.service.dto.CoindeskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${version}/coindesk")
public class CoindeskController {

    @Autowired
    private CoindeskClientService coindeskClientService;

    @GetMapping("")
    public ResponseEntity<CoindeskClientDto> callCoindeskApi() {
        return ResponseEntity.ok(coindeskClientService.callCoindeskApi());
    }

    @GetMapping("/transformations")
    public ResponseEntity<CoindeskDto> callCoindeskApiAndTransform() {
        CoindeskClientDto dto = coindeskClientService.callCoindeskApi();
        return ResponseEntity.ok(coindeskClientService.transformCoindeskData(dto));
    }
}
