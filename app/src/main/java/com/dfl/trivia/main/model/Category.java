package com.dfl.trivia.main.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Loureiro on 15/11/2017.
 *
 * Parcelable category
 */

@Parcel public class Category {

  int id;
  String name;

  @ParcelConstructor public Category(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
