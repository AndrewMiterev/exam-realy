package com.ralli.exam.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.ralli.exam.DTO.AssigmentsInDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataServiceImpl implements DataService {

    private List<AssigmentsInDTO> list;

    @Value("${source.data.url}")
    private String url;

    @PostConstruct
    void firstLoad() {
        list = getList();
    }

    @Scheduled(fixedDelayString = "${scheduler-data-updater-delay-in-milliseconds:600000}")
    private void scheduler() {
        log.debug("Scheduler for update started");
        list = getList();
    }

    private static List<AssigmentsInDTO> extractData(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() != HttpStatus.OK)
            return null;

        InputStreamReader reader = new InputStreamReader(response.getBody());
//                            ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
//                            ms.setType(AssigmentsInDTO.class);
        CsvToBean<AssigmentsInDTO> csvToBean = new CsvToBeanBuilder<AssigmentsInDTO>(reader)
                .withType(AssigmentsInDTO.class)
                .withSkipLines(1)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
//                                    .withMappingStrategy(ms)
                .build();
//                            Iterator<AssigmentsInDTO> myUserIterator = csvToBean.iterator();
//                            while (myUserIterator.hasNext()) {
//                                AssigmentsInDTO myA = myUserIterator.next();
//                                log.info(myA.toString());
//                            }
        return new ArrayList<>(csvToBean.parse());
    }

    public List<AssigmentsInDTO> getList() {
        RestTemplate restTemplate = new RestTemplate();
        log.debug("call to 3-thd service started");
        final ResponseExtractor<List<AssigmentsInDTO>> extractor = DataServiceImpl::extractData;
        List<AssigmentsInDTO> response = restTemplate.execute(UriComponentsBuilder.fromHttpUrl(url).toUriString(),
                HttpMethod.GET, null, extractor);
        log.debug("call to 3-thd service finished");
        Objects.requireNonNull(response).forEach(a -> log.debug(a.toString()));
        return response;
    }

    @Override
    public List<AssigmentsInDTO> filteredList(Integer minPrice, Integer maxPrice, Integer minBad, Integer maxBad, Integer minBath, Integer maxBath) {
        log.debug(String.format("Filter minPrice %s maxPrice %s minBad %s maxBad %s minBath %s maxBath %s",minPrice, maxPrice,  minBad, maxBad , minBath , maxBath));
        return list.stream()
                .filter(d -> minPrice == null || d.getPrice() >= minPrice)
                .filter(d -> maxPrice == null || d.getPrice() <= maxPrice)
                .filter(d -> minBad == null || d.getBedrooms() >= minBad)
                .filter(d -> maxBad == null || d.getBedrooms() <= maxBad)
                .filter(d -> minBath == null || d.getBedrooms() >= minBath)
                .filter(d -> maxBath == null || d.getBedrooms() >= maxBath)
                .collect(Collectors.toList());
    }
}