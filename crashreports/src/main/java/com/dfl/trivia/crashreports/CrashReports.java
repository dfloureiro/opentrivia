package com.dfl.trivia.crashreports;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;

/**
 * Created by diogoloureiro on 21/11/2017.
 *
 * init and interact with crash reporting libs
 */

public class CrashReports {

  /**
   * init crash reports
   *
   * @param context activity context
   */
  public CrashReports(Context context) {
    Crashlytics crashlyticsKit = new Crashlytics.Builder().core(
        new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG)
            .build())
        .build();
    Fabric.with(context, crashlyticsKit);
  }

  /**
   * log exception
   *
   * @param throwable exception to log
   */
  public void logException(Throwable throwable) {
    Crashlytics.logException(throwable);
  }
}
