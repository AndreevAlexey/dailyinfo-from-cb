package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import util.AppUtil;

import java.util.List;

public abstract class CbDailyInfoService<T> {

    @Value("${urls.cbr:https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx}")
    public String url;

    @Autowired
    private Sender sender;

    public Document sendPostRequest(String body) {
        return
                (AppUtil.checkString(body)) ? sender.sendPostRequest(url, body) : null;
    }

    public NodeList getDocumentNodeList(Document document, String path) {
        return sender.getNodesFromDocument(document, path);
    }

    public NodeList getResponse(String body, String path) {
        return sender.getNodesFromDocument(sendPostRequest(body), path);
    }

    protected abstract List<T> getItemsFromNodes(NodeList nodes);


}
