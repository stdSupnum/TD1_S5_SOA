package com.supnum.td1.controllers;

import com.supnum.td1.dtos.RenameRequest;
import com.supnum.td1.entities.Server;
import com.supnum.td1.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    @Autowired
    private ServerService serviceService;

    @PostMapping
    public Server create(@RequestBody Server server){
        return serviceService.create(server);
    }

    @GetMapping
    public List<Server> all(){
        return serviceService.findAll();
    }

    @PutMapping("/{id}/rename")
    public Server rename(@PathVariable Long id, @RequestBody RenameRequest req) {
        return serviceService.rename(id, req.getNewName());
    }

    @GetMapping("/{id}/status")
    public boolean status(@PathVariable Long id){
        return serviceService.getStatus(id);
    }

    @PutMapping("/{id}/start")
    public Server start(@PathVariable Long id){
        return serviceService.start(id);
    }

    @PutMapping("/{id}/stop")
    public Server stop(@PathVariable Long id){
        return serviceService.stop(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        serviceService.delete(id);
    }
}
