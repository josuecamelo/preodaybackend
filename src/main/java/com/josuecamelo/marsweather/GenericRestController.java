package com.josuecamelo.marsweather;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.josuecamelo.marsweather.services.BaseService;

public class GenericRestController<T> {
	
	@Autowired
	private BaseService<T> baseService;
	
	public GenericRestController(BaseService<T> baseService) {
		this.baseService = baseService;
	}
		
	@PostMapping("create")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public T create(@RequestBody T t) {
		return this.baseService.create(t);
	}
	
	/*@GetMapping
	@ResponseBody
	public Page<T> findAll(Pageable pageable) {
		return this.baseService.findAll(pageable);
	}*/
	
	@GetMapping
	@ResponseBody
	public List<T> findAll() {
		return this.baseService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<T> findById(@PathVariable("id") Long id) {
		return this.baseService.findById(id);
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeById(@PathVariable("id") Long id) {
		this.baseService.deleteById(id);
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public Optional<T> updateById(@PathVariable("id") Long id, @RequestBody T t) {
		return this.baseService.update(id, t);
	}

	public BaseService<T> getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService<T> baseService) {
		this.baseService = baseService;
	}
}
