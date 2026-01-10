package com.example.middleservice.controller;

import com.example.middleservice.dto.ServerDto;
import com.example.middleserviceservice.service.ServerRestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    private final ServerRestService service;

    public ServerController(ServerRestService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServerDto create(@RequestBody Map<String, String> body) {
        return service.create(body.get("name"));
    }

    @GetMapping
    public List<ServerDto> list() {
        return service.list();
    }

    @PutMapping("/{id}/rename")
    public ServerDto rename(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.rename(id, body.get("newName"));
    }

    @GetMapping("/{id}/status")
    public Map<String, Object> status(@PathVariable Long id) {
        return Map.of("id", id, "status", service.status(id));
    }

    @PutMapping("/{id}/start")
    public ServerDto start(@PathVariable Long id) {
        return service.start(id);
    }

    @PutMapping("/{id}/stop")
    public ServerDto stop(@PathVariable Long id) {
        return service.stop(id);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        service.delete(id);
        return Map.of("message", "Deleted successfully");
    }
}
