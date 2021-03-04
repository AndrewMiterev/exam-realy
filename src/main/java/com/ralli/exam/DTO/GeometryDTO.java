package com.ralli.exam.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.lang.reflect.Array;

@Getter
@Setter
@Builder
public class GeometryDTO {
    String type;
    Double[] coordinates = new Double[2];
}
