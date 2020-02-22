package com.josuecamelo.marsweather.deserialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.josuecamelo.marsweather.deserialization.compass.CompassDataDeserializer;
import com.josuecamelo.marsweather.deserialization.compass.CompassDataPointDeserializer;
import com.josuecamelo.marsweather.deserialization.validity.CheckDeserializer;
import com.josuecamelo.marsweather.deserialization.validity.ValidityCheckDeserializer;
import com.josuecamelo.marsweather.deserialization.validity.ValidityChecksDeserializer;
import com.josuecamelo.marsweather.models.InSightWeatherData;
import com.josuecamelo.marsweather.models.SensorData;
import com.josuecamelo.marsweather.models.SolData;
import com.josuecamelo.marsweather.models.compass.CompassData;
import com.josuecamelo.marsweather.models.compass.CompassDataPoint;
import com.josuecamelo.marsweather.models.validity.Check;
import com.josuecamelo.marsweather.models.validity.ValidityCheck;
import com.josuecamelo.marsweather.models.validity.ValidityChecks;

public class Deserializer {

    public static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        
        builder.registerTypeAdapter(SensorData.class, new SensorDataDeserializer());
        builder.registerTypeAdapter(CompassDataPoint.class, new CompassDataPointDeserializer());
        builder.registerTypeAdapter(CompassData.class, new CompassDataDeserializer());
        builder.registerTypeAdapter(Check.class, new CheckDeserializer());
        builder.registerTypeAdapter(ValidityCheck.class, new ValidityCheckDeserializer());
        builder.registerTypeAdapter(ValidityChecks.class, new ValidityChecksDeserializer());
        builder.registerTypeAdapter(SolData.class, new SolDataDeserializer());
        builder.registerTypeAdapter(InSightWeatherData.class, new InSightWeatherDataDeserializer());

        gson = builder.create();
    }

}