package com.ralli.exam.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GeoDTO {
    String type;
    List<FeaturesDTO> features;
}

