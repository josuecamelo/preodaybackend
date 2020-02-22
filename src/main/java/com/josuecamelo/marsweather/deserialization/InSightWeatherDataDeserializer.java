package com.josuecamelo.marsweather.deserialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.josuecamelo.marsweather.models.InSightWeatherData;
import com.josuecamelo.marsweather.models.SolData;
import com.josuecamelo.marsweather.models.validity.ValidityChecks;

public class InSightWeatherDataDeserializer implements JsonDeserializer<InSightWeatherData> {

    @Override
    public InSightWeatherData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        ArrayList<Integer> solKeys = new ArrayList<>();
        for (JsonElement element : object.get("sol_keys").getAsJsonArray()) {
            solKeys.add(element.getAsInt());
        }

        ValidityChecks validityChecks = Deserializer.gson.fromJson(object.get("validity_checks"), ValidityChecks.class);

        object.remove("sol_keys");
        object.remove("validity_checks");

        Map<Integer, SolData> solData = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            int key = Integer.parseInt(entry.getKey());
            JsonObject value = entry.getValue().getAsJsonObject();
            value.addProperty("key", key);
            solData.put(key, Deserializer.gson.fromJson(value, SolData.class));
        }

        InSightWeatherData inSightWeatherData = new InSightWeatherData(solData, solKeys, validityChecks);
        return inSightWeatherData;
    }

}
