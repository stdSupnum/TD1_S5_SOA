package com.example.servermonitoringsoap.service;

import com.example.servermonitoringsoap.exception.BusinessException;
import com.example.servermonitoringsoap.exception.NotFoundException;
import com.example.servermonitoringsoap.model.Server;
import com.example.servermonitoringsoap.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServerService {

    private final ServerRepository repo;

    public ServerService(ServerRepository repo) {
        this.repo = repo;
    }

    public Server createServer(String name) {
        if (name == null || name.isBlank()) {
            throw new BusinessException("Server name is required");
        }
        repo.findByName(name).ifPresent(s -> {
            throw new BusinessException("Server name already exists");
        });
        return repo.save(new Server(name, false));
    }

    @Transactional(readOnly = true)
    public List<Server> listServers() {
        return repo.findAll();
    }

    public Server renameServer(Long id, String newName) {
        Server s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Server not found: " + id));

        if (newName == null || newName.isBlank()) {
            throw new BusinessException("New name is required");
        }
        repo.findByName(newName).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new BusinessException("New name already used by another server");
            }
        });

        s.setName(newName);
        return repo.save(s);
    }

    @Transactional(readOnly = true)
    public boolean getStatus(Long id) {
        Server s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Server not found: " + id));
        return s.isStatus();
    }

    public Server startServer(Long id) {
        Server s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Server not found: " + id));
        s.setStatus(true);
        return repo.save(s);
    }

    public Server stopServer(Long id) {
        Server s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Server not found: " + id));
        s.setStatus(false);
        return repo.save(s);
    }

    public void deleteServer(Long id) {
        Server s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Server not found: " + id));

        if (s.isStatus()) {
            throw new BusinessException("Cannot delete a running server");
        }
        repo.delete(s);
    }
}
