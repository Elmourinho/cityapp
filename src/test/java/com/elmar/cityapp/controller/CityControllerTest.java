package com.elmar.cityapp.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.elmar.city.config.SecurityConfig;
import com.elmar.city.model.dto.CityDto;
import com.elmar.city.model.dto.ResultDto;
import com.elmar.city.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
@Import(SecurityConfig.class)
class CityControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CityService cityService;

  @Test
  void whenFindAll_thenJsonWithAllCitiesShouldBeReturned() throws Exception {
    int pageNo = 0;
    int pageSize = 20;
    String nameLike = "test";

    CityDto cityDto = new CityDto("Test City", "photo");

    List<CityDto> cities = Collections.singletonList(cityDto);

    ResultDto resultDto = new ResultDto();
    resultDto.setCities(cities);
    resultDto.setTotalElements(1);
    resultDto.setTotalPages(1);

    given(cityService.findAll(pageNo, pageSize, nameLike)).willReturn(resultDto);

    MvcResult mvcResult = mockMvc.perform(get("/cities")
                                              .param("pageNo", String.valueOf(pageNo))
                                              .param("pageSize", String.valueOf(pageSize))
                                              .param("nameLike", nameLike))
                                 .andExpect(status().isOk())
                                 .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();

    verify(cityService).findAll(pageNo, pageSize, nameLike);

    ResultDto responseDto = new ObjectMapper().readValue(responseBody, ResultDto.class);
    assertEquals(resultDto.getCities().size(), responseDto.getCities().size());
    assertEquals(resultDto.getCities().get(0).getName(), responseDto.getCities().get(0).getName());
    assertEquals(resultDto.getCities().get(0).getPhoto(), responseDto.getCities().get(0).getPhoto());
    assertEquals(resultDto.getTotalElements(), responseDto.getTotalElements());
    assertEquals(resultDto.getTotalPages(), responseDto.getTotalPages());

  }


  @Test
  void whenUpdateUnauthorized_unauthorizedStatusShouldBeReturned() throws Exception {
    CityDto cityDto = new CityDto("New York", "url1");
    given(cityService.update(1L, cityDto)).willReturn(cityDto);

    String cityDtoJson = "{\"name\":\"New York\",\"photoUrl\":\"url1\"}";

    mockMvc.perform(put("/cities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cityDtoJson))
           .andExpect(status().isUnauthorized());
  }

}