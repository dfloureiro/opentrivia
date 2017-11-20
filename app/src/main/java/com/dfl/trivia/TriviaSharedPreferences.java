package com.dfl.trivia;

import android.content.SharedPreferences;

/**
 * Created by diogoloureiro on 20/11/2017.
 */

public class TriviaSharedPreferences {

  private SharedPreferences sharedPreferences;

  public TriviaSharedPreferences(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public void saveSessionToken(String sessionToken) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("token", sessionToken);
    editor.apply();
  }

  public String getSessionToken() {
    return sharedPreferences.getString("token", "");
  }
}
