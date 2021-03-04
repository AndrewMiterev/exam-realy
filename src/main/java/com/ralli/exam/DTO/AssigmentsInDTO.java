package com.ralli.exam.DTO;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssigmentsInDTO {
    @CsvBindByPosition(position = 0)
    Integer id;
    @CsvBindByPosition(position = 1)
    String street;
    @CsvBindByPosition(position = 2)
    String status;
    @CsvBindByPosition(position = 3)
    Double price;
    @CsvBindByPosition(position = 4)
    Integer bedrooms;
    @CsvBindByPosition(position = 5)
    Integer bathrooms;
    @CsvBindByPosition(position = 6)
    Double sqFt;
    @CsvBindByPosition(position = 7)
    Double lat;
    @CsvBindByPosition(position = 8)
    Double lng;
}
