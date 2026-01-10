package com.example.middleservice.service;

import com.example.middleservice.dto.ServerDto;
import com.example.middleservice.soap.SoapClient;
import com.example.middleservice.soap.gen.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerRestService {

    private final SoapClient client;

    public ServerRestService(SoapClient client) {
        this.client = client;
    }

    private ServerDto toRestDto(com.example.middleservicesoap.gen.ServerDto s) {
        ServerDto dto = new ServerDto();
        dto.id = s.getId();
        dto.name = s.getName();
        dto.status = s.isStatus();
        return dto;
    }

    public ServerDto create(String name) {
        CreateServerRequest req = new CreateServerRequest();
        req.setName(name);
        CreateServerResponse res = (CreateServerResponse) client.call(req);
        return toRestDto(res.getServer());
    }

    public List<ServerDto> list() {
        ListServersRequest req = new ListServersRequest();
        ListServersResponse res = (ListServersResponse) client.call(req);
        return res.getServers().stream().map(this::toRestDto).toList();
    }

    public ServerDto start(Long id) {
        StartServerRequest req = new StartServerRequest();
        req.setId(id);
        StartServerResponse res = (StartServerResponse) client.call(req);
        return toRestDto(res.getServer());
    }

    public ServerDto stop(Long id) {
        StopServerRequest req = new StopServerRequest();
        req.setId(id);
        StopServerResponse res = (StopServerResponse) client.call(req);
        return toRestDto(res.getServer());
    }

    public ServerDto rename(Long id, String newName) {
        RenameServerRequest req = new RenameServerRequest();
        req.setId(id);
        req.setNewName(newName);
        RenameServerResponse res = (RenameServerResponse) client.call(req);
        return toRestDto(res.getServer());
    }

    public boolean status(Long id) {
        GetStatusRequest req = new GetStatusRequest();
        req.setId(id);
        GetStatusResponse res = (GetStatusResponse) client.call(req);
        return res.isStatus();
    }

    public void delete(Long id) {
        DeleteServerRequest req = new DeleteServerRequest();
        req.setId(id);
        client.call(req);
    }
}
