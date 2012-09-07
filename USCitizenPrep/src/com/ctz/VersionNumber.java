package com.ctz;

import android.app.Application;

public class VersionNumber extends Application {
  private static String appVersion = "";

  public static String getAppVersion() {
    return appVersion;
  }

  public static void setAppVersion(String version) {
    appVersion = version;
  }
}
