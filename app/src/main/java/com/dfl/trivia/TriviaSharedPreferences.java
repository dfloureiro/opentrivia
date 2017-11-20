package com.dfl.trivia;

import android.content.SharedPreferences;

/**
 * Created by diogoloureiro on 20/11/2017.
 */

public class TriviaSharedPreferences {

  private final static String SESSION_TOKEN_KEY = "SESSION_TOKEN_KEY";

  private SharedPreferences sharedPreferences;

  public TriviaSharedPreferences(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public void saveSessionToken(String sessionToken) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(SESSION_TOKEN_KEY, sessionToken);
    editor.apply();
  }

  public String getSessionToken() {
    return sharedPreferences.getString(SESSION_TOKEN_KEY, "");
  }
}
