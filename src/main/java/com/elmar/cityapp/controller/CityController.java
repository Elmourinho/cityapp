package com.elmar.cityapp.controller;

import com.elmar.cityapp.model.dto.CityDto;
import com.elmar.cityapp.model.dto.ResultDto;
import com.elmar.cityapp.service.CityService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cities")
public class CityController {

  private final CityService cityService;

  @GetMapping
  public ResponseEntity<ResultDto> findAll2(@RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                            @RequestParam(value = "nameLike", required = false) String nameLike) {
    log.info("Find all cities");
    return new ResponseEntity<>(cityService.findAll(pageNo, pageSize, nameLike), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public CityDto update(@PathVariable Long id, @RequestBody @Valid CityDto cityDto) {
    log.info("Update city by id {}", id);
    return cityService.update(id, cityDto);
  }


}
