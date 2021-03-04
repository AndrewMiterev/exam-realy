package com.ralli.exam.service;

import com.ralli.exam.DTO.AssigmentsInDTO;

import java.util.List;

public interface DataService {
    List<AssigmentsInDTO> getList();
    List<AssigmentsInDTO> filteredList(Integer minPrice, Integer maxPrice, Integer minBad, Integer maxBad, Integer minBath, Integer maxBath);
}
