package com.josuecamelo.marsweather.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public interface BaseService<T> {
	//Page<T> findAll(Pageable pageable);
	List<T> findAll();
	T create(T t);
	T save(T t);
	Optional<T> update(long id, T t);
	Optional<T> findById(long id);
	void delete(T t);
	ResponseEntity<?> deleteById(long id);
}