package com.dfl.trivia;

import android.content.SharedPreferences;

/**
 * Created by diogoloureiro on 20/11/2017.
 *
 * Trivia shared preferences, handles the token saving and retrieving
 */

public class TriviaSharedPreferences {

  private final static String SESSION_TOKEN_KEY = "SESSION_TOKEN_KEY";

  private final SharedPreferences sharedPreferences;

  public TriviaSharedPreferences(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  /**
   * save session token on shared preferences
   *
   * @param sessionToken token to save
   */
  public void saveSessionToken(String sessionToken) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(SESSION_TOKEN_KEY, sessionToken);
    editor.apply();
  }

  /**
   * retrive session token from the shared preferences
   *
   * @return last saved token. if none, returns empty string
   */
  public String getSessionToken() {
    return sharedPreferences.getString(SESSION_TOKEN_KEY, "");
  }
}
