package com.example.servermonitoringsoap.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.core.io.ClassPathResource;

@EnableWs
@Configuration
public class SoapConfig extends WsConfigurerAdapter {

    public static final String NAMESPACE = "http://example.com/servermonitoring";

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "serverMonitoring")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema serverSchema) {
        DefaultWsdl11Definition wsdlDefinition = new DefaultWsdl11Definition();
        wsdlDefinition.setPortTypeName("ServerMonitoringPort");
        wsdlDefinition.setLocationUri("/ws");
        wsdlDefinition.setTargetNamespace(NAMESPACE);
        wsdlDefinition.setSchema(serverSchema);
        return wsdlDefinition;
    }

    @Bean
    public XsdSchema serverMonitoringSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/server-monitoring.xsd"));
    }
}
