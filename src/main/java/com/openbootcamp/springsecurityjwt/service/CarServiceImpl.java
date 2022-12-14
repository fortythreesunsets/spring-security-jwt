package com.openbootcamp.springsecurityjwt.service;

import com.openbootcamp.springsecurityjwt.entities.Car;
import com.openbootcamp.springsecurityjwt.repositories.CarRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private static final Integer MIN_DOORS = 3;
    private final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
    private CarRepository carRepository;
    public CarServiceImpl(CarRepository carRepository) { this.carRepository = carRepository; }

    /**
     * Recupera todos los coches de la BD
     * @return
     */
    @Override
    public List<Car> findAll() {
        log.info("Executing findAll Cars");
        return this.carRepository.findAll();
    }

    /**
     * Recupera un coche de la BD por medio de su ID
     * @param id identificador único de coche
     * @return
     */
    @Override
    public Optional<Car> findById(Long id) {
        log.info("Executing findById");
        return this.carRepository.findById(id);
    }

    /**
     * Encontrar coches por el número de puertas ingresado
     * @param doors número de puertas a buscar
     * @return
     */
    @Override
    public List<Car> findByDoors(Integer doors) {
        log.info("Searching cars by number of doors");
        if (doors < MIN_DOORS) {
            log.warn("Trying to search less than allowed number of doors");
            return new ArrayList<>();
        }
        return this.carRepository.findByDoors(doors);
    }

    /**
     * Contar el total de coches en la BD
     * @return
     */
    @Override
    public Long count() {
        log.info("Get total number of cars");
        return this.carRepository.count();
    }

    /**
     * Guarda un coche nuevo o modificado en la BD
     * @param car
     * @return
     */
    @Override
    public Car save(Car car) {
        log.info("Creating/Updating car");
        // pre
        if (!this.validateCar(car))
            return null;

        // actions
        // find template from db
        Car carDB = this.carRepository.save(car);

        // post
        // send notification
        // this.notificationService(NotificationType.CREATION, car);
        return carDB;
    }

    /**
     * Valida que el coche se cree no tenga atributos vacíos
     * ni menos de las puertas establecidas
     * @param car objeto car
     * @return
     */
    private boolean validateCar(Car car) {
        // Null car validation
        if (car == null) {
            log.warn("Trying to create null car");
            return false;
        }
        // Number of doors validation
        if (car.getDoors() == null || car.getDoors() < MIN_DOORS) {
            log.warn("Trying to create a car with invalid number of doors");
            return false;
        }
        return true;
    }

    /**
     * Elimina un coche de la BD por su ID
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        log.info("Deleting car by id");
        if (id == null || id < 0 || id == 0) {
            log.warn("Trying to delete a car with a wrong id");
            return;
        }
        try {
            this.carRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error trying to delete a car by id {}", id, e);
        }

    }

    /**
     * Elimina todos los coches de la BD
     */
    @Override
    public void deleteAll() {
        log.info("Deleting all cars");
        this.carRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<Car> cars) {
        log.info("Deleting car by Id");
        if (CollectionUtils.isEmpty(cars)) {
            log.warn("Trying to delete an empty or null Car List");
            return;
        }
        this.carRepository.deleteAll(cars);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        log.info("Deleting car by Id");
        if (CollectionUtils.isEmpty(ids)) {
            log.warn("Trying to delete an empty or null Car List");
            return;
        }
        this.carRepository.deleteAllById(ids);
    }

    @Override
    public List<Car> findByManufacturerAndModel(String manufacturer, String model) {
        if (!StringUtils.hasLength(manufacturer) || !StringUtils.hasLength(model))
            return new ArrayList<>();

        return this.carRepository.findByManufacturerAndModel(manufacturer, model);
    }

    @Override
    public List<Car> findByDoorsGreaterThanEqual(Integer doors) {
        if (doors == null || doors < 0)
            return new ArrayList<>();

        return this.carRepository.findByDoorsGreaterThanEqual(doors);
    }

    @Override
    public List<Car> findByModelContaining(String model) {
        return this.carRepository.findByModelContaining(model);
    }

    @Override
    public List<Car> findByYearIn(List<Integer> years) {
        return this.carRepository.findByYearIn(years);
    }

    @Override
    public List<Car> findByYearBetween(Integer startYear, Integer endYear) {
        return this.carRepository.findByYearBetween(startYear, endYear);
    }

    @Override
    public List<Car> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate) {
        return this.carRepository.findByReleaseDateBetween(startDate, endDate);
    }

    @Override
    public List<Car> findByAvailableTrue() {
        return this.carRepository.findByAvailableTrue();
    }

    @Override
    public Long deleteAllByAvailableFalse() {
        return this.carRepository.deleteAllByAvailableFalse();
    }
}
