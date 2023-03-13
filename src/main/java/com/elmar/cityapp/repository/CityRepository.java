package com.elmar.cityapp.repository;


import com.elmar.cityapp.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

  Page<City> findByNameContainingIgnoreCase(String name, Pageable pageable);

  default Page<City> findByNameContainingOrAll(String nameLike, Pageable pageable) {
    if (nameLike == null || nameLike.isEmpty()) {
      return findAll(pageable);
    } else {
      return findByNameContainingIgnoreCase(nameLike, pageable);
    }
  }
}
