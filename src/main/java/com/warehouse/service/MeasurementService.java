package com.warehouse.service;

import com.warehouse.entity.Measurement;
import com.warehouse.payload.Result;
import com.warehouse.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public ResponseEntity<Result> addMeasurementService(Measurement measurement){

        if(measurementRepository.existsByname(measurement.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Measurement  saved before with this name", false));

        }
        measurementRepository.save(measurement);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Measurement saved successfully", true));

    }

    public ResponseEntity<?> getMeasurementById(Long id) {
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Measurement not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<Measurement>> getAll() {
        return ResponseEntity.ok(measurementRepository.findAll());

    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Measurement not found with this ID:" + id,false));

        measurementRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Measurement deleted successfully",false));
    }

    public ResponseEntity<Result> updateMeasurement(Measurement measurement, Long id) {
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Measurement not found with this ID:" + id,false));

        if(measurementRepository.existsByname(measurement.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Measurement  saved before with this name", false));

        Measurement measurement1 = byId.get();
        measurement1.setName(measurement.getName());
        measurementRepository.save(measurement1);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Measurement edited successfully", true));

    }
}
