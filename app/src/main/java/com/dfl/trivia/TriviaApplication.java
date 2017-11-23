package com.dfl.trivia;

import com.dfl.trivia.crashreports.CrashReports;

/**
 * Created by Loureiro on 14/11/2017.
 *
 * Application level
 */

public class TriviaApplication extends android.app.Application {

  @Override public void onCreate() {
    super.onCreate();
    new CrashReports(this);
  }
}
