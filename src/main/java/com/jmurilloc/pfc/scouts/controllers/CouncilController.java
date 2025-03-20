package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.entities.dto.CouncilDto;
import com.jmurilloc.pfc.scouts.services.CouncilService;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/councils")
public class CouncilController {
    private CouncilService service;

    @Autowired
    public void setService(CouncilService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SCOUTER','COORDI')")
    @GetMapping("/{id}")
    public ResponseEntity<Council> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','SCOUTER','COORDI')")
    @GetMapping("/councilsByFecha")
    public ResponseEntity<Object> getCouncilsByFecha(@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        List<Council> councils = service.findByInitialDate(fecha);
        List<CouncilDto> councilDtos = councils.stream().map(BuildDto::buildCouncilDto).collect(Collectors.toSet()).stream().toList();
        return ResponseEntity.ok(councilDtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SCOUTER','COORDI')")
    @PostMapping
    public ResponseEntity<Object> createCouncil(@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date fechaInicio,
                                                 @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date fechaFin,
                                                 @RequestParam("puntosConsejo") MultipartFile puntosConsejo,
                                                 @RequestParam("puntosConsejoAnterior") MultipartFile puntosConsejoAnterior) throws IOException {
        // Convert MultipartFile to Byte[]
        Byte[] puntosConsejoBytes = convertToByteArray(puntosConsejo.getBytes());
        Byte[] puntosConsejoAnteriorBytes = convertToByteArray(puntosConsejoAnterior.getBytes());

        Council council = new Council();
        council.setFechaInicio(fechaInicio);
        council.setFechaFin(fechaFin);
        council.setPuntosConsejo(puntosConsejoBytes);
        council.setPuntosConsejoAnterior(puntosConsejoAnteriorBytes);

        Council savedCouncil = service.save(council);

        return ResponseEntity.ok(BuildDto.buildCouncilDto(savedCouncil));

    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCouncil(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.ok().body("Borrado con exito");
    }
    private Byte[] convertToByteArray(byte[] bytes) {
        Byte[] byteObjects = new Byte[bytes.length];
        int i = 0;
        for (byte b : bytes) {
            byteObjects[i++] = b; // Autoboxing
        }
        return byteObjects;
    }

}
