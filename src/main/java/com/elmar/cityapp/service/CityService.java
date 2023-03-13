package com.elmar.cityapp.service;


import com.elmar.cityapp.errors.Errors;
import com.elmar.cityapp.exception.CityException;
import com.elmar.cityapp.model.City;
import com.elmar.cityapp.model.dto.CityDto;
import com.elmar.cityapp.model.dto.ResultDto;
import com.elmar.cityapp.repository.CityRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {

  private final CityRepository cityRepository;
  private final ModelMapper modelMapper;

  public ResultDto findAll(int pageNo, int pageSize, String nameLike) {
    Pageable paging = PageRequest.of(pageNo, pageSize);

    ResultDto resultDto = new ResultDto();

    Page<City> cityPage = cityRepository.findByNameContainingOrAll(nameLike, paging);
    List<CityDto> cities = cityPage.stream()
                                   .map(f -> modelMapper.map(f, CityDto.class))
                                   .collect(Collectors.toList());

    resultDto.setTotalPages(cityPage.getTotalPages());
    resultDto.setTotalElements(cityPage.getTotalElements());
    resultDto.setCities(cities);

    return resultDto;

  }

  public CityDto update(Long id, CityDto dto) {
    cityRepository.findById(id)
                  .orElseThrow(() -> new CityException(Errors.CITY_NOT_FOUND, Map.of("id", id)));
    dto.setId(id);
    City city = cityRepository.save(modelMapper.map(dto, City.class));
    return modelMapper.map(city, CityDto.class);
  }


}
