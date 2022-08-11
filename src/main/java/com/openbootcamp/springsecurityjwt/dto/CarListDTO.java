package com.openbootcamp.springsecurityjwt.dto;

import com.openbootcamp.springsecurityjwt.entities.Car;

import java.util.List;

public class CarListDTO {

    private List<Car> cars;

    public CarListDTO(){}

    public List<Car> getCars(){ return cars; }

    public void setCars(List<Car> cars) { this.cars = cars; }
}
