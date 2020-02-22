package com.josuecamelo.marsweather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.josuecamelo.marsweather.models.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
	  public Weather findBySol(Integer sol);
}
