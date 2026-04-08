package com.example.test_geli.constants;

public enum Code {
  success("000000"),
  error("050392"),
  internal("053392"),
  unauthorization("053312"),
  forbideen("053112");

  private final String code;

  Code(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
