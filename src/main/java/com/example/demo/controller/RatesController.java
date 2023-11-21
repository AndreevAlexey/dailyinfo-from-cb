package com.example.demo.controller;


import com.example.demodailyinfocb.model.KeyRate;
import com.example.demodailyinfocb.service.KeyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("data/importer")
@RequiredArgsConstructor
public class RatesController {

    private final KeyRateService keyRateService;

    @ResponseBody
    @GetMapping("keyRate/period")
    public List<KeyRate> getKeyRateByPeriod(@RequestParam("begin") LocalDate begin, @RequestParam("end") LocalDate end) {
        return
                keyRateService.getKeyRatesFromCb(begin, end);
    }

}
