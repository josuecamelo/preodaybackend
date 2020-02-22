package com.josuecamelo.marsweather.deserialization.validity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.josuecamelo.marsweather.deserialization.Deserializer;
import com.josuecamelo.marsweather.models.validity.ValidityCheck;
import com.josuecamelo.marsweather.models.validity.ValidityChecks;

public class ValidityChecksDeserializer implements JsonDeserializer<ValidityChecks> {

    @Override
    public ValidityChecks deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        int hoursRequired = object.get("sol_hours_required").getAsInt();

        List<Integer> solsChecked = new ArrayList<Integer>();
        for (JsonElement element : object.get("sols_checked").getAsJsonArray()) {
            solsChecked.add(element.getAsInt());
        }

        object.remove("sol_hours_required");
        object.remove("sols_checked");

        Map<Integer, ValidityCheck> checks = new HashMap<Integer, ValidityCheck>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            int key = Integer.parseInt(entry.getKey());
            JsonObject value = entry.getValue().getAsJsonObject();
            value.addProperty("key", key);

            ValidityCheck check = Deserializer.gson.fromJson(value, ValidityCheck.class);
            checks.put(key, check);
        }

        ValidityChecks validityChecks = new ValidityChecks(hoursRequired, solsChecked, checks);
        return validityChecks;
    }

}
