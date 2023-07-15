package com.redfrog.trafficsm.exception;

public class NullClassMethodAccessException extends RuntimeException {

  public NullClassMethodAccessException() {
    super();
    //_____________________________________
  }

  public NullClassMethodAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    //_____________________________________
  }

  public NullClassMethodAccessException(String message, Throwable cause) {
    super(message, cause);
    //_____________________________________
  }

  public NullClassMethodAccessException(String message) {
    super(message);
    //_____________________________________
  }

  public NullClassMethodAccessException(Throwable cause) {
    super(cause);
    //_____________________________________
  }

}
