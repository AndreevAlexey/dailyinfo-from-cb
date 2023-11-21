package com.example.demo.service;


import com.example.demodailyinfocb.model.KeyRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.AppUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KeyRateService extends CbRateService<KeyRate>{

    public KeyRateService(@Value("${services.key-rate.name:KeyRate}") String name,
                          @Value("${services.key-rate.method:KeyRateXML}") String method,
                          @Value("${services.key-rate.path://KeyRate/KR}") String path) {
        super(name, method, path);
    }

    @Override
    protected List<KeyRate> getItemsFromNodes(NodeList nodes) {
        List<KeyRate> items = new ArrayList<>();
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                NodeList rateNodes = nodes.item(i).getChildNodes();
                KeyRate item = new KeyRate();
                for (int j = 0; j < rateNodes.getLength(); j++) {
                    Node child = rateNodes.item(j);
                    switch (child.getNodeName()) {
                        case "DT" -> item.setDate(LocalDateTime.parse(child.getTextContent(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                        case "Rate" -> item.setRate(Double.parseDouble(child.getTextContent()));
                    }
                }
                items.add(item);
            }
        }
        return items;
    }

    public List<KeyRate> getKeyRatesFromCb(LocalDate begDate, LocalDate endDate) {

        if (!AppUtil.checkDatePeriod(begDate, endDate))
            return Collections.emptyList();

        return
            getItemsSoapRequest(begDate, endDate);
    }

}
