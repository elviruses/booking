package com.elvir.backend.controller;

import com.elvir.backend.model.dto.ClientDto;
import com.elvir.backend.model.request.ClientInfo;
import com.elvir.backend.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/client")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ClientInfo clientInfo) {
        clientService.create(clientInfo);
    }

    @PatchMapping("/client/{id}")
    public void update(@PathVariable("id") UUID uuid, @RequestBody ClientInfo clientInfo) {
        clientService.update(uuid, clientInfo);
    }

    @DeleteMapping("/client/{id}")
    public void delete(@PathVariable("id") UUID uuid) {
        clientService.delete(uuid);
    }

    @GetMapping("/client/{id}")
    public ClientDto get(@PathVariable("id") UUID uuid) {
        return clientService.get(uuid);
    }

    @GetMapping("/client/list")
    public List<ClientDto> getList() {
        return clientService.getList();
    }

    @PostMapping("/client/{phone}/verify")
    public void sendCode(@PathVariable("phone") Long phone) {
        clientService.sendCode(phone);
    }
}
