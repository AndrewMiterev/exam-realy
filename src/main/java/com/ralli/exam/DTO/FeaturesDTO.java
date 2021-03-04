package com.ralli.exam.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeaturesDTO {
    String type;
    GeometryDTO geometry;
    PropertiesDTO properties;
}
