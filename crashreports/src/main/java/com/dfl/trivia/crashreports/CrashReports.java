package com.dfl.trivia.crashreports;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by diogoloureiro on 21/11/2017.
 */

public class CrashReports {

  public CrashReports(Context context) {
    Fabric.with(context, new Crashlytics());
  }
}
