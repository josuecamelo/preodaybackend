package com.josuecamelo.marsweather.deserialization.compass;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.josuecamelo.marsweather.models.compass.CompassData;
import com.josuecamelo.marsweather.models.compass.CompassDataPoint;

public class CompassDataDeserializer implements JsonDeserializer<CompassData> {

    private Gson gson;

	@Override
    public CompassData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        gson = null;
              
        
        JsonObject mostCommonObj = object.get("most_common").getAsJsonObject();
        mostCommonObj.addProperty("key", 0);
        CompassDataPoint mostCommon = gson.fromJson(mostCommonObj, CompassDataPoint.class);

        object.remove("most_common");
        Map<Integer, CompassDataPoint> points = new HashMap<Integer, CompassDataPoint>();

        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            int key = Integer.parseInt(entry.getKey());
            JsonObject value = entry.getValue().getAsJsonObject();
            value.addProperty("key", key);

            CompassDataPoint point = gson.fromJson(value, CompassDataPoint.class);
            points.put(key, point);
        }
        
        CompassData sensorData = new CompassData(points, mostCommon);
        return sensorData;
    }

}
