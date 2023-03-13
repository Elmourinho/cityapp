package com.elmar.cityapp.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CityDto {

  private Long id;

  @NotBlank
  @NonNull
  private String name;

  @NotBlank
  @NonNull
  private String photo;
}
