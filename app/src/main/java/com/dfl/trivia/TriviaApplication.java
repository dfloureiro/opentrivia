package com.dfl.trivia;

import com.dfl.trivia.crashreports.CrashReports;

/**
 * Created by Loureiro on 14/11/2017.
 */

public class TriviaApplication extends android.app.Application {

  private static CrashReports crashReports;

  @Override public void onCreate() {
    super.onCreate();
    crashReports = new CrashReports(this);
  }

  public static CrashReports getCrashReports() {
    return crashReports;
  }
}
