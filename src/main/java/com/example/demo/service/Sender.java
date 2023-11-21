package com.example.demo.service;


import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class Sender {

    private static final String TEXT_XML = "text/xml; charset=utf-8";

    public Document sendPostRequest(String urlStr, String body) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", TEXT_XML);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

            wr.writeBytes(body);
            wr.flush();
            wr.close();

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return
                    documentBuilder.parse(connection.getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return
                null;
    }

    public NodeList getNodesFromDocument(Document document, String path) {
        try {
            XPathFactory pathFactory = XPathFactory.newInstance();
            XPath xPath = pathFactory.newXPath();
            XPathExpression expr = xPath.compile(path);
            return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
