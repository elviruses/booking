package com.elvir.backend.controller;

import com.elvir.backend.model.dto.ServicingDto;
import com.elvir.backend.model.request.ServicingInfo;
import com.elvir.backend.service.service.ServicingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ServicingController {

    private final ServicingService servicingService;

    @PostMapping("/service")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ServicingInfo servicingInfo) {
        servicingService.create(servicingInfo);
    }

    @PatchMapping("/service/{id}")
    public void update(@PathVariable("id") UUID uuid, @RequestBody ServicingInfo servicingInfo) {
        servicingService.update(uuid, servicingInfo);
    }

    @DeleteMapping("/service/{id}")
    public void delete(@PathVariable("id") UUID uuid) {
        servicingService.delete(uuid);
    }

    @GetMapping("/service/{id}")
    public ServicingDto get(@PathVariable("id") UUID uuid) {
        return servicingService.get(uuid);
    }

    @GetMapping("/service/list")
    public List<ServicingDto> getList() {
        return servicingService.getList();
    }
}
