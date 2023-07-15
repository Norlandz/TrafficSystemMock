package com.redfrog.trafficsm.exception;

public class DuplicatedIdException extends RuntimeException {

  public DuplicatedIdException() {
    super();
    //_____________________________________
  }

  public DuplicatedIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    //_____________________________________
  }

  public DuplicatedIdException(String message, Throwable cause) {
    super(message, cause);
    //_____________________________________
  }

  public DuplicatedIdException(String message) {
    super(message);
    //_____________________________________
  }

  public DuplicatedIdException(Throwable cause) {
    super(cause);
    //_____________________________________
  }

}
