package com.pluralsight;

public abstract class Contract {
    private String Date;
    private String customerName;
    private String customerEmail;
    private Vehicle vehicleSold;
    private double totalPrice;
    private double monthlyPayment;
    
    public Contract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double totalPrice
            , double monthlyPayment) {
        Date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
        this.totalPrice = totalPrice;
        this.monthlyPayment = monthlyPayment;
    }
    
    public String getDate() {
        return Date;
    }
    
    public void setDate(String date) {
        Date = date;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public Vehicle getVehicleSold() {
        return vehicleSold;
    }
    
    public void setVehicleSold(Vehicle vehicleSold) {
        this.vehicleSold = vehicleSold;
    }
    
    public abstract double getTotalPrice(Vehicle vehicleSold);
    
    public abstract double getMonthlyPayment(Vehicle vehicleSold);
}