package com.elmar.cityapp.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.elmar.city.errors.Errors;
import com.elmar.city.exception.CityException;
import com.elmar.city.model.City;
import com.elmar.city.model.dto.CityDto;
import com.elmar.city.model.dto.ResultDto;
import com.elmar.city.repository.CityRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

  @Mock
  private CityRepository cityRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private CityService cityService;

  @Test
  void whenFindAll_thenAllCitiesShouldBeReturned(){
    City city1 = new City("New York", "photo1");
    City city2 = new City("San Francisco", "photo2");
    List<City> cities = Arrays.asList(city1, city2);

    CityDto cityDto = new CityDto("New York", "photo1");

    Page<City> page = new PageImpl<>(cities);
    Pageable paging = PageRequest.of(0, 10);

    when(cityRepository.findByNameContainingOrAll("York", paging)).thenReturn(page);
    when(modelMapper.map(city1, CityDto.class)).thenReturn(cityDto);

    ResultDto resultDto = cityService.findAll(0, 10, "York");
    assertEquals(2, resultDto.getTotalElements());
    assertEquals(1, resultDto.getTotalPages());
    assertEquals("New York", resultDto.getCities().get(0).getName());
  }

  @Test
  void whenUpdate_thenUpdateShouldBeDoneProperly() {
    Long id = 1L;
    CityDto dto = new CityDto();
    dto.setId(id);
    City city = new City();
    when(cityRepository.findById(id)).thenReturn(Optional.of(city));
    when(cityRepository.save(city)).thenReturn(city);
    when(modelMapper.map(dto, City.class)).thenReturn(city);
    when(modelMapper.map(city, CityDto.class)).thenReturn(dto);

    CityDto result = cityService.update(id, dto);

    assertEquals(id, result.getId());
    verify(cityRepository).findById(id);
    verify(cityRepository).save(city);
    verify(modelMapper).map(dto, City.class);
    verify(modelMapper).map(city, CityDto.class);
  }

  @Test
  void whenUpdateNotFound_thenExceptionShouldBeReceived() {
    Long id = 1L;
    CityDto dto = new CityDto();
    when(cityRepository.findById(id)).thenReturn(Optional.empty());

    CityException exception = assertThrows(CityException.class, () -> cityService.update(id, dto));
    assertEquals(Errors.CITY_NOT_FOUND, exception.getErrorResponse());

  }

}