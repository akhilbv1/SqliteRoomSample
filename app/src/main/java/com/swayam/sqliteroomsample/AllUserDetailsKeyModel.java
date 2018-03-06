package com.swayam.sqliteroomsample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Created by akhil on 5/3/18.
 */

public class AllUserDetailsKeyModel {

  private Map<String, AllUserDetailsResponseModel> result;

  public Map<String, AllUserDetailsResponseModel> getResult() {
    return result;
  }

  public void setResult(Map<String, AllUserDetailsResponseModel> result) {
    this.result = result;
  }

}
