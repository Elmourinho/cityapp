package com.elmar.cityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class CityappApplication {

  public static void main(String[] args) {
    SpringApplication.run(CityappApplication.class, args);
  }

}
