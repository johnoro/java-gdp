package com.lambdaschool.gdp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Country {
  private @Id @GeneratedValue Long id;
  private String name;
  private long gdp;

  public Country() {}

  public Country(String name, long gdp) {
    this.name = name;
    this.gdp = gdp;
  }
}
