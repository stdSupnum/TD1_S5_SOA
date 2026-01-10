package com.supnum.td1.services;

import com.supnum.td1.daos.ServerRepository;
import com.supnum.td1.entities.Server;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    public Server create(Server s) {
        s.setStatus(false);
        return serverRepository.save(s);
    }


    public List<Server> findAll() {
        return serverRepository.findAll();
    }


    public Server rename(Long id, String newName) {
        Server s = serverRepository.findById(id).orElseThrow();
        s.setName(newName);
        return serverRepository.save(s);
    }


    public boolean getStatus(Long id) {
        return serverRepository.findById(id).orElseThrow().isStatus();
    }


    public Server start(Long id) {
        Server s = serverRepository.findById(id).orElseThrow();
        s.setStatus(true);
        return serverRepository.save(s);
    }


    public Server stop(Long id) {
        Server s = serverRepository.findById(id).orElseThrow();
        s.setStatus(false);
        return serverRepository.save(s);
    }


    public void delete(Long id) {
        Server s = serverRepository.findById(id).orElseThrow();
        if (s.isStatus()) {
            throw new RuntimeException("Impossible de supprimer un serveur actif.");
        }
        serverRepository.delete(s);
    }
}
