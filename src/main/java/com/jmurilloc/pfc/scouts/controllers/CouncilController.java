package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.exceptions.CouncilNotFound;
import com.jmurilloc.pfc.scouts.services.CouncilService;
import com.jmurilloc.pfc.scouts.util.MessageError;
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
        Optional<Council> optionalCouncil = service.findById(id);
        if (optionalCouncil.isEmpty()) throw new CouncilNotFound(MessageError.COUNCIL_NOT_FOUND.getValue());
        Council council = optionalCouncil.orElseThrow();
        return ResponseEntity.ok(council);
    }
    @PreAuthorize("hasAnyRole('ADMIN','SCOUTER','COORDI')")
    @GetMapping("/councilsByFecha")
    public ResponseEntity<List<Council>> getCouncilsByFecha(@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        List<Council> councils = service.findByInitialDate(fecha);
        if (councils.isEmpty()) throw new CouncilNotFound(MessageError.COUNCIL_NOT_FOUND.getValue());
        return ResponseEntity.ok(councils);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SCOUTER','COORDI')")
    @PostMapping
    public ResponseEntity<Council> createCouncil(@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date fechaInicio,
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
        return ResponseEntity.ok(savedCouncil);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCouncil(@PathVariable Long id){
        Optional<Council> optionalCouncil = service.findById(id);
        if (optionalCouncil.isEmpty()) throw new CouncilNotFound(MessageError.COUNCIL_NOT_FOUND.getValue());
        service.deleteById(id);
        return ResponseEntity.status(201).body("Se ha borrado correctamente");
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
