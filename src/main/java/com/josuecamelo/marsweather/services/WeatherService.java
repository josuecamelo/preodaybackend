package com.josuecamelo.marsweather.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.josuecamelo.marsweather.models.Weather;
import com.josuecamelo.marsweather.repositories.WeatherRepository;

@Service
public class WeatherService implements BaseService<Weather> {
	@Autowired
	private WeatherRepository repository;
	
	public WeatherService(WeatherRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public List<Weather> findAll() {
		return this.repository.findAll();
	}

	@Override
	public Weather create(Weather weather) {
		return this.repository.save(weather);
	}

	@Override
	public Optional<Weather> findById(long id) {
		return this.repository.findById(id);
	}

	@Override
	public Weather save(Weather weather) {
		return this.repository.save(weather);
	}

	@Override
	public void delete(Weather t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResponseEntity<?> deleteById(long id) {
		 return this.repository.findById(id)
		           .map(record -> {
		               this.repository.deleteById(id);
		               return ResponseEntity.ok().build();
		           }).orElse(ResponseEntity.notFound().build());
	}

	@Override
	public Optional<Weather> update(long id, Weather w) {
		return this.findById(id).map(record -> {
			record.setId(w.getId());
			record.setSol(w.getSol());
			record.setMin(w.getMin());
			record.setMax(w.getMax());
			record.setAv(w.getAv());
			
			Weather updated = this.save(record);
			
			return updated;
		});
	}
	
	public Weather findBySol(Integer sol) {
		return this.repository.findBySol(sol);
	}
}
