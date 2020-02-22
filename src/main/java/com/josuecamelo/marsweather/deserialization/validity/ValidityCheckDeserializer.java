package com.josuecamelo.marsweather.deserialization.validity;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.josuecamelo.marsweather.deserialization.Deserializer;
import com.josuecamelo.marsweather.models.validity.Check;
import com.josuecamelo.marsweather.models.validity.ValidityCheck;

public class ValidityCheckDeserializer implements JsonDeserializer<ValidityCheck> {

    @Override
    public ValidityCheck deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        int key = object.get("key").getAsInt();
        Check atmosphericTemperature = Deserializer.gson.fromJson(object.get("AT"), Check.class);
        Check atmosphericPressure = Deserializer.gson.fromJson(object.get("PRE"), Check.class);
        Check horizontalWindSpeed = Deserializer.gson.fromJson(object.get("HWS"), Check.class);
        Check windDirection = Deserializer.gson.fromJson(object.get("WD"), Check.class);

        ValidityCheck validityCheck = new ValidityCheck(key, atmosphericTemperature, atmosphericPressure, horizontalWindSpeed, windDirection);
        return validityCheck;
    }

}