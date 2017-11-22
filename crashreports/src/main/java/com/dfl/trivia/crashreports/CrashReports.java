package com.dfl.trivia.crashreports;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;

/**
 * Created by diogoloureiro on 21/11/2017.
 */

public class CrashReports {

  public CrashReports(Context context) {
    Crashlytics crashlyticsKit = new Crashlytics.Builder().core(
        new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG)
            .build())
        .build();
    Fabric.with(context, crashlyticsKit);
  }

  public void logException(Throwable throwable) {
    Crashlytics.logException(throwable);
  }
}
