package com.redfrog.trafficsm.util;

import java.io.Serializable;
import java.time.Instant;

public class SystemMetric {

  //______________________________________________________
  public static final Instant appBootTimeInstant = Instant.now();
  public static final String appBootTime = TimeUtil.time2strnano(appBootTimeInstant);

}