package com.openbootcamp.springsecurityjwt.dto;

/**
 * Data Transfer Object
 * Los datos no se persisten en la BD, simplemente sirven para almacenar información y devolverla al frontend (Cliente)
 */

public class CountDTO {

    private Long count;
    private String message;

    public CountDTO(){}

    public CountDTO(Long count){ this.count = count; }

    public Long getCount() { return count; }

    public void setCount(Long count){ this.count = count; }

    public String getMessage(){ return message; }

    public void setMessage(String message){ this.message = message; }

}
