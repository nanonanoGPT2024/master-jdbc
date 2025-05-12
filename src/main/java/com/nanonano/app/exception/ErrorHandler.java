package com.nanonano.app.exception;

public class ErrorHandler extends RuntimeException {
   private final int status;

   public ErrorHandler(int status, String message) {
      super(message);
      this.status = status;
   }

   public int getStatus() {
      return status;
   }
}
