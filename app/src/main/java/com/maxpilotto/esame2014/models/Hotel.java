package com.maxpilotto.esame2014.models;

import android.database.Cursor;

import com.maxpilotto.esame2014.persitence.tables.HotelTable;

public class Hotel {
    private Integer id;
    private String name;
    private String city;
    private Integer rating;
    private Double price;

    public Hotel(Cursor cursor) {
        this(
                cursor.getInt(cursor.getColumnIndex(HotelTable._ID)),
                cursor.getString(cursor.getColumnIndex(HotelTable.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(HotelTable.COLUMN_CITY)),
                cursor.getInt(cursor.getColumnIndex(HotelTable.COLUMN_RATING)),
                cursor.getDouble(cursor.getColumnIndex(HotelTable.COLUMN_PRICE))
        );
    }

    public Hotel(Integer id, String name, String city, Integer rating, Double price) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.rating = rating;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}


