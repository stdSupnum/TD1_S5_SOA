package com.example.servermonitoringsoap.soap;

import com.example.servermonitoringsoap.exception.BusinessException;
import com.example.servermonitoringsoap.exception.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;

import java.util.Properties;

@Configuration
public class SoapExceptionResolverConfig {

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        SoapFaultMappingExceptionResolver resolver = new SoapFaultMappingExceptionResolver();

        SoapFaultDefinition defaultFault = new SoapFaultDefinition();
        defaultFault.setFaultCode(SoapFaultDefinition.SERVER);
        resolver.setDefaultFault(defaultFault);

        Properties mappings = new Properties();
        mappings.setProperty(NotFoundException.class.getName(), SoapFaultDefinition.CLIENT.toString());
        mappings.setProperty(BusinessException.class.getName(), SoapFaultDefinition.CLIENT.toString());

        resolver.setExceptionMappings(mappings);
        resolver.setOrder(1);
        return resolver;
    }
}
