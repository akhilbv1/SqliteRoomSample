package com.swayam.sqliteroomsample;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 Created by akhil on 6/3/18.
 */

public class UserDetailsDeserializer implements JsonDeserializer<AllUserDetailsKeyModel> {
  /*
    bebebejunskjd:{
      "email": "akhilbv1@gmail.com",
          "mobileNum": "8341770556",
          "password": "1234567",
          "username": "akhil"}*/
  @Override public AllUserDetailsKeyModel deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {

    final JsonObject jsonObject = json.getAsJsonObject();

    Gson gson = new Gson();

    Type AllUserDetailsResponseModel =
        new TypeToken<HashMap<String, AllUserDetailsResponseModel>>(){}.getType();

    HashMap<String, AllUserDetailsResponseModel> user =
        gson.fromJson(jsonObject, AllUserDetailsResponseModel);
    AllUserDetailsKeyModel result = new AllUserDetailsKeyModel();
    result.setResult(user);
    return result;
  }


}
