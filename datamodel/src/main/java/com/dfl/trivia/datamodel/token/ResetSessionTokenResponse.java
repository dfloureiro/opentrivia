package com.dfl.trivia.datamodel.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetSessionTokenResponse {

  @SerializedName("response_code") @Expose private Integer responseCode;
  @SerializedName("token") @Expose private String token;

  public Integer getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(Integer responseCode) {
    this.responseCode = responseCode;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}