package com.redfrog.trafficsm.exception;

import lombok.Getter;

public class AlreadyExistedException extends Exception {

  @Getter
  private Object objAlreadyExisted;

  public AlreadyExistedException(Object objAlreadyExisted) {
    super();
    this.objAlreadyExisted = objAlreadyExisted;
  }

  public AlreadyExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    //_____________________________________
  }

  public AlreadyExistedException(String message, Throwable cause) {
    super(message, cause);
    //_____________________________________
  }

  public AlreadyExistedException(String message) {
    super(message);
    //_____________________________________
  }

  public AlreadyExistedException(Throwable cause) {
    super(cause);
    //_____________________________________
  }

}
