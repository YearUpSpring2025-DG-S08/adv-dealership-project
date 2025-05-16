package com.pluralsight;

@SuppressWarnings("ALL")
public class Vehicle {
    // instance variables
    private int vin;
    private int year;
    private String make;
    private String model;
    private String type;
    private String color;
    private double mileage;
    private double price;

    // constructor
    public Vehicle(int vin, int year, String make, String model, String type, String color, double mileage,
                   double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.type = type;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
    }

    // getters & setters
    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.make = model;
    }
    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // display methods
    @Override
    public String toString() {
        String data = String.format("%d|%s|%s|%s|%s|%s|%.0f|%.0f"
                , vin, year, make, model, type, color, mileage, price);
        return data;
    }
    
    public String toFormattedRow() {
        return String.format("%-8d | %-6d | %-12s | %-14s | %-12s | %-10s | %10s | %12s", 
                vin,
                year,
                make,
                model,
                type,
                color,
        String.format("%,d", (int) mileage),
        String.format("$%,.2f", price));
    }
}