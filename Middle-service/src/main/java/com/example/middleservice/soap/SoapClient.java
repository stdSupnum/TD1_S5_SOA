package com.example.middleservice.soap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class SoapClient {

    private final WebServiceTemplate webServiceTemplate;
    private final String soapEndpoint;

    public SoapClient(WebServiceTemplate webServiceTemplate,
                      @Value("${soap.endpoint}") String soapEndpoint) {
        this.webServiceTemplate = webServiceTemplate;
        this.soapEndpoint = soapEndpoint;
    }

    public Object call(Object request) {
        return webServiceTemplate.marshalSendAndReceive(soapEndpoint, request);
    }
}
