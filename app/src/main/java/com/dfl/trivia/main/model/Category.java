package com.dfl.trivia.main.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Loureiro on 15/11/2017.
 */

@Parcel public class Category {

  private int id;
  private String name;

  @ParcelConstructor public Category(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
