package com.ralli.exam.controllers;

import com.ralli.exam.DTO.*;
import com.ralli.exam.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ListingsController {
    private final DataService service;

    @GetMapping("listings")
    public GeoDTO getListings(
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "min_bad", required = false) Integer minBad,
            @RequestParam(name = "max_bad", required = false) Integer maxBad,
            @RequestParam(name = "min_bath", required = false) Integer minBath,
            @RequestParam(name = "max_bath", required = false) Integer maxBath
    ) {

        List<AssigmentsInDTO> dtoList = service.filteredList(minPrice, maxPrice, minBad, maxBad, minBath, maxBath);
        List<FeaturesDTO> featuresDTO = dtoList.stream().map(a -> {
            return FeaturesDTO.builder()
                    .type("Feature")
                    .geometry(GeometryDTO.builder()
                            .type("Point")
                            .coordinates(new Double[]{a.getLng(), a.getLat()})
                            .build())
                    .properties(PropertiesDTO.builder()
                            .id(a.getId())
                            .price(a.getPrice())
                            .street(a.getStreet())
                            .bedrooms(a.getBedrooms())
                            .bathrooms(a.getBathrooms())
                            .sq_ft(a.getSqFt())
                            .build())
                    .build();
        }).collect(Collectors.toList());
        return GeoDTO.builder()
                .type("FeatureCollection")
                .features(featuresDTO)
                .build();
    }
}