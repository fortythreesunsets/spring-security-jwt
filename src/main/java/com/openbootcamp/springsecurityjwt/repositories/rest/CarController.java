package com.openbootcamp.springsecurityjwt.repositories.rest;

import com.openbootcamp.springsecurityjwt.dto.CarListDTO;
import com.openbootcamp.springsecurityjwt.dto.CountDTO;
import com.openbootcamp.springsecurityjwt.dto.MessageDTO;
import com.openbootcamp.springsecurityjwt.entities.Car;
import com.openbootcamp.springsecurityjwt.service.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CarController {

    private final Logger log = LoggerFactory.getLogger(CarController.class);

    // Dependencia
    private CarService carService;

    // Spring inyecta la dependencia
    public CarController(CarService carService) {
        this.carService = carService;
    }

    // ===================== SPRING CRUD METHODS =====================
    /**
     * http://localhost:8080/api/cars/1
     */
    @GetMapping("/cars/{id}")
    @ApiOperation("Buscar coche por Id")
    public ResponseEntity<Car> findById(@ApiParam("Clave primaria car") @PathVariable Long id) {
        log.info("REST request to find one car");
        Optional<Car> carOptional = this.carService.findById(id);

        if (carOptional.isPresent())
            return ResponseEntity.ok(carOptional.get());
        return ResponseEntity.notFound().build();
    }

    /**
     * http://localhost:8080/api/cars
     */
    @GetMapping("/cars")
    public List<Car> findAll() {
        log.info("REST request to find all cars");
        return this.carService.findAll();
    }

    // Create a car
    @PostMapping("/cars")
    public ResponseEntity<Car> create(@RequestBody Car car) {
        log.info("REST request to create a new car");

        if (car.getId() != null) {
            log.warn("Trying to create a new car with an existing Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.carService.save(car));
    }

    // Update a car
    @PutMapping("/cars")
    public ResponseEntity<Car> update(@RequestBody Car car) {
        log.info("REST request to update an existing car");

        if (car.getId() == null) {
            log.warn("Trying to update an existing car without Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.carService.save(car));
    }

    // Delete a car
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Car> delete(@PathVariable Long id) {
        log.info("REST request to delete an existing car");
        this.carService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Delete all cars
    @DeleteMapping("/cars")
    public ResponseEntity<Car> deleteAll() {
        log.info("REST request to delete all cars");
        this.carService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * http://localhost:8080/api/cars/count
     */
    @GetMapping("/cars/count")
    public ResponseEntity<CountDTO> count() {
        log.info("REST request to count all cars");
        Long count = this.carService.count();
        CountDTO dto = new CountDTO(count);
        dto.setMessage(":)");
        return ResponseEntity.ok(dto);
    }

    /**
     * http://localhost:8080/api/cars/hello
     */
    @GetMapping("/cars/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }

    /**
     * http://localhost:8080/api/cars/hello2
     */
    @GetMapping("/cars/hello2")
    public ResponseEntity<MessageDTO> hello2() {
        return ResponseEntity.ok(new MessageDTO("Hello"));
    }

    /**
     * http://localhost:8080/api/cars/deletemany
     */
    @DeleteMapping("/cars/deletemany")
    public ResponseEntity<Car> deleteMany(@RequestBody CarListDTO carListDTO) {
        this.carService.deleteAll(carListDTO.getCars());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cars/deletemany/{ids}")
    public ResponseEntity<Car> deleteMany(@PathVariable List<Long> ids) {
        this.carService.deleteAllById(ids);
        return ResponseEntity.noContent().build();
    }

    // ===================== CUSTOM CRUD METHODS =====================

    @GetMapping("/cars/manufacturer/{manufacturer}/model/{model}")
    @ApiOperation("Buscar coches filtrando por fabricante y modelo")
    public List<Car> findByManufacturerAndModel(@PathVariable String manufacturer, @PathVariable String model) {
        return this.carService.findByManufacturerAndModel(manufacturer, model);
    }

    @GetMapping("/cars/doors/{doors}")
    @ApiOperation("Buscar coches filtrando por número de puertas")
    public List<Car> findByDoors(@PathVariable Integer doors) {
        log.info("REST request to finds cars by number of doors");
        return this.carService.findByDoors(doors);
    }

    @GetMapping("/cars/doors-gte/{doors}")
    @ApiOperation("Buscar coches filtrando por número de puertas mayor o igual a un número dado")
    public List<Car> findByDoorsGreaterThanEqual(@PathVariable Integer doors) {
        return this.carService.findByDoorsGreaterThanEqual(doors);
    }

}
