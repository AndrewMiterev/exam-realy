package com.ralli.exam.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PropertiesDTO {
    Integer id;
    Double price;
    String street;
    Integer bedrooms;
    Integer bathrooms;
    Double sq_ft;
}
