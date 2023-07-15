package com.redfrog.trafficsm.service.exception;

import lombok.Getter;
import lombok.Setter;

public class GetFieldDuringSetException extends RuntimeException {

  public GetFieldDuringSetException() {
    super();
    //_____________________________________
  }

  public GetFieldDuringSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    //_____________________________________
  }

  public GetFieldDuringSetException(String message, Throwable cause) {
    super(message, cause);
    //_____________________________________
  }

  public GetFieldDuringSetException(String message) {
    super(message);
    //_____________________________________
  }

  public GetFieldDuringSetException(Throwable cause) {
    super(cause);
    //_____________________________________
  }

  //_________
  
  @Getter
  @Setter
  private Object theStaleFieldTryingToGet;
  
}
