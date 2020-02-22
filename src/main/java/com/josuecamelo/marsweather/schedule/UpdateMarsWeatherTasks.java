package com.josuecamelo.marsweather.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.josuecamelo.marsweather.deserialization.InSightWeatherDataDeserializer;
import com.josuecamelo.marsweather.deserialization.SensorDataDeserializer;
import com.josuecamelo.marsweather.deserialization.SolDataDeserializer;
import com.josuecamelo.marsweather.deserialization.compass.CompassDataDeserializer;
import com.josuecamelo.marsweather.deserialization.compass.CompassDataPointDeserializer;
import com.josuecamelo.marsweather.deserialization.validity.CheckDeserializer;
import com.josuecamelo.marsweather.deserialization.validity.ValidityCheckDeserializer;
import com.josuecamelo.marsweather.deserialization.validity.ValidityChecksDeserializer;
import com.josuecamelo.marsweather.models.InSightWeatherData;
import com.josuecamelo.marsweather.models.SensorData;
import com.josuecamelo.marsweather.models.SolData;
import com.josuecamelo.marsweather.models.Weather;
import com.josuecamelo.marsweather.models.compass.CompassData;
import com.josuecamelo.marsweather.models.compass.CompassDataPoint;
import com.josuecamelo.marsweather.models.validity.Check;
import com.josuecamelo.marsweather.models.validity.ValidityCheck;
import com.josuecamelo.marsweather.models.validity.ValidityChecks;
import com.josuecamelo.marsweather.services.WeatherService;

@Component
public class UpdateMarsWeatherTasks {
	private static final Logger logger = LoggerFactory.getLogger(UpdateMarsWeatherTasks.class);

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private SolData solData;
	
	private Weather weather;
	@Autowired
	private WeatherService service;
			
	@Scheduled(cron = "* 0 * * * ?") // De Hora em Hora
	//@Scheduled(cron = "0/5 * * * * ?")
	public void scheduleTaskWithCronExpression() {		
		logger.info("Cron Task To Update Mars Weather :: Execution Time - {}",
				dateTimeFormatter.format(LocalDateTime.now()));

		try {
			Collection<SolData> l = this.makeRequest().getSolData().values();
						
			for (Iterator<SolData> iterator = l.iterator(); iterator.hasNext();) {
				solData = iterator.next();
				
				weather = service.findBySol(solData.getKey());
								
				if(weather == null) {
					weather = new Weather();
					weather.setId(Long.valueOf(solData.getKey()));
					weather.setSol(solData.getKey());
					weather.setAv(solData.getAtmosphericTemperature().getSampleAverage());
					weather.setMin(solData.getAtmosphericTemperature().getSampleMinimum());
					weather.setMax(solData.getAtmosphericTemperature().getSampleMaximum());		
					
					service.save(weather);
				} else {
					weather.setAv(solData.getAtmosphericTemperature().getSampleAverage());
					weather.setMin(solData.getAtmosphericTemperature().getSampleMinimum());
					weather.setMax(solData.getAtmosphericTemperature().getSampleMaximum());		
					service.update(weather.getId(),weather);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String parseHTTPResponse(HttpURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	private InSightWeatherData makeRequest() throws Exception {
		String uri = "https://api.nasa.gov/insight_weather/?api_key=6ekPObnQDx5dRasKJAxWqni6QM9RoscnozHuGkID&feedtype=json&ver=1.0";
		URL url = new URL(uri);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String result = parseHTTPResponse(con);

			GsonBuilder builder = new GsonBuilder();

			builder.registerTypeAdapter(SensorData.class, new SensorDataDeserializer());
			builder.registerTypeAdapter(CompassDataPoint.class, new CompassDataPointDeserializer());
			builder.registerTypeAdapter(CompassData.class, new CompassDataDeserializer());
			builder.registerTypeAdapter(Check.class, new CheckDeserializer());
			builder.registerTypeAdapter(ValidityCheck.class, new ValidityCheckDeserializer());
			builder.registerTypeAdapter(ValidityChecks.class, new ValidityChecksDeserializer());
			builder.registerTypeAdapter(SolData.class, new SolDataDeserializer());
			builder.registerTypeAdapter(InSightWeatherData.class, new InSightWeatherDataDeserializer());

			Gson gson = builder.create();
			return gson.fromJson(result, InSightWeatherData.class);
		}
		throw new Exception("HTTP response code " + responseCode);
	}

}