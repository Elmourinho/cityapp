package com.elmar.cityapp.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {

  private List<CityDto> cities;
  private int totalPages;
  private long totalElements;
}
