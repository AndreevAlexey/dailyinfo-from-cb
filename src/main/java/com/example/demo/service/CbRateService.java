package com.example.demo.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.NodeList;
import com.example.demo.util.AppUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class CbRateService<T> extends CbDailyInfoService<T>{

    protected final String name;
    protected final String method;
    protected final String path;

    public String getRequestBodyPattern() {
        return
                """
                <?xml version="1.0" encoding="utf-8"?>
                <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                  <soap:Body>
                    <methodName xmlns="http://web.cbr.ru/">
                      <fromDate>begDate</fromDate>
                      <ToDate>endDate</ToDate>
                    </methodName>
                  </soap:Body>
                </soap:Envelope>""";
    }

    public String getRequestBody(LocalDate begDate, LocalDate endDate) {
        if (!AppUtil.checkDatePeriod(begDate, endDate))
            return null;

        if (!AppUtil.checkString(method))
            return null;

        String requestXml = getRequestBodyPattern();
        requestXml = requestXml.replace("methodName", method);
        requestXml = requestXml.replace("begDate", begDate.format(DateTimeFormatter.ISO_DATE));
        requestXml = requestXml.replace("endDate", endDate.format(DateTimeFormatter.ISO_DATE));
        return
                requestXml;
    }

    protected List<T> getItemsSoapRequest(LocalDate begDate, LocalDate endDate) {
        return
                getItemsFromNodes(getResponseNodes(begDate, endDate));
    }

    protected NodeList getResponseNodes(LocalDate begDate, LocalDate endDate) {
        return
                getResponse(getRequestBody(begDate, endDate), path);
    }

}

