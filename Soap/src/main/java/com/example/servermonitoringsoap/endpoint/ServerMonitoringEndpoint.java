package com.example.servermonitoringsoap.endpoint;

import com.example.servermonitoringsoap.model.Server;
import com.example.servermonitoringsoap.service.ServerService;
import com.example.servermonitoringsoap.*;
//import org.springframework.ws.server.endpoint.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.example.servermonitoringsoap.soap.SoapConfig;

import java.util.List;

@Endpoint
public class ServerMonitoringEndpoint {

    private final ServerService service;

    public ServerMonitoringEndpoint(ServerService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = SoapConfig.NAMESPACE, localPart = "ListServersRequest")
    @ResponsePayload
    public ListServersResponse list(@RequestPayload ListServersRequest request) {
        List<Server> servers = service.listServers();
        ListServersResponse res = new ListServersResponse();
        for (Server s : servers) res.getServers().add(toDto(s));
        return res;
    }

    private ServerDto toDto(Server s) {
        ServerDto dto = new ServerDto();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setStatus(s.isStatus());
        return dto;
    }

    @PayloadRoot(namespace = SoapConfig.NAMESPACE, localPart = "CreateServerRequest")
    @ResponsePayload
    public CreateServerResponse create(@RequestPayload CreateServerRequest request) {
        Server created = service.createServer(request.getName());
        CreateServerResponse res = new CreateServerResponse();
        res.setServer(toDto(created));
        return res;
    }



    @PayloadRoot(namespace = SoapConfig.NAMESPACE, localPart = "RenameServerRequest")
    @ResponsePayload
    public RenameServerResponse rename(@RequestPayload RenameServerRequest request) {
        Server updated = service.renameServer(request.getId(), request.getNewName());
        RenameServerResponse res = new RenameServerResponse();
        res.setServer(toDto(updated));
        return res;
    }

    @PayloadRoot(namespace = SoapConfig.NAMESPACE, localPart = "GetStatusRequest")
    @ResponsePayload
    public GetStatusResponse status(@RequestPayload GetStatusRequest request) {
        boolean status = service.getStatus(request.getId());
        GetStatusResponse res = new GetStatusResponse();
        res.setStatus(status);
        return res;
    }

    @PayloadRoot(namespace = SoapConfig.NAMESPACE, localPart = "StartServerRequest")
    @ResponsePayload
    public StartServerResponse start(@RequestPayload StartServerRequest request) {
        Server updated = service.startServer(request.getId());
        StartServerResponse res = new StartServerResponse();
        res.setServer(toDto(updated));
        return res;
    }

    @PayloadRoot(namespace = SoapConfig.NAMESPACE, localPart = "StopServerRequest")
    @ResponsePayload
    public StopServerResponse stop(@RequestPayload StopServerRequest request) {
        Server updated = service.stopServer(request.getId());
        StopServerResponse res = new StopServerResponse();
        res.setServer(toDto(updated));
        return res;
    }

    @PayloadRoot(namespace = SoapConfig.NAMESPACE, localPart = "DeleteServerRequest")
    @ResponsePayload
    public DeleteServerResponse delete(@RequestPayload DeleteServerRequest request) {
        service.deleteServer(request.getId());
        DeleteServerResponse res = new DeleteServerResponse();
        res.setMessage("Deleted successfully");
        return res;
    }
}
