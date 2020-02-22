package com.josuecamelo.marsweather;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josuecamelo.marsweather.models.Weather;
import com.josuecamelo.marsweather.services.BaseService;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
public class WeatherController extends GenericRestController<Weather> {

	public WeatherController(BaseService<Weather> baseService) {
		super(baseService);
		// TODO Auto-generated constructor stub
	}
}
