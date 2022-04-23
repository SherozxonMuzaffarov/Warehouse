package com.warehouse.service;

import com.warehouse.entity.Clients;
import com.warehouse.payload.ClientDto;
import com.warehouse.payload.Result;
import com.warehouse.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ResponseEntity<Result> addClient(ClientDto clientDto) {

        boolean byPhoneNumber = clientRepository.existsByPhoneNumber(clientDto.getPhoneNumber());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Client  saved before with this  phone", false));

        Clients clients = new Clients();
        clients.setName(clientDto.getName());
        clients.setPhoneNumber(clientDto.getPhoneNumber());
        clientRepository.save(clients);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Client saved successfully", true));
    }

    public ResponseEntity<?> getClientById(Long id) {
        Optional<Clients> byId = clientRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Client not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());

    }

    public ResponseEntity<List<Clients>> getAll() {
        return  ResponseEntity.ok(clientRepository.findAll());

    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Clients> byId = clientRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Client not found with this id: " + id, false));
        clientRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Client deleted Successfully", true));
    }

    public ResponseEntity<Result> updateClient(Long id, ClientDto clientDto) {
        Optional<Clients> byId = clientRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Client not found with this ID:" + id,false));

        boolean exists = clientRepository.existsByPhoneNumber(clientDto.getPhoneNumber());
        if (exists)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Client  saved before with this phone number", false));

        Clients clients = byId.get();
        clients.setName(clientDto.getName());
        clients.setPhoneNumber(clientDto.getPhoneNumber());
        clients.setActive(true);
        clientRepository.save(clients);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Client edited successfully", true));
    }
}
