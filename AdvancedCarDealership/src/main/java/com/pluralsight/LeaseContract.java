package com.pluralsight;

public class LeaseContract extends Contract{
    
    private double endingValue;
    private double leaseFee;
    
    
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double totalPrice, double monthlyPayment, double endingValue, double leaseFee) {
        super(date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
        this.endingValue = endingValue;
        this.leaseFee = leaseFee;
        
    }
    
    public double getEndingValue() {
        return endingValue;
    }
    
    public void setEndingValue(double endingValue) {
        this.endingValue = endingValue;
    }
    
    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }
    
    public double getExpectedEndingValue(){
        if(this.endingValue == 0) {
            this.endingValue = getVehicleSold().getPrice() / 2;
        }
        return this.endingValue;
    }
    
    public double getLeaseFee(){
        if(this.leaseFee == 0){
            this.leaseFee = (getVehicleSold().getPrice() * .07) + getVehicleSold().getPrice();
        }
        return this.leaseFee;
    }
    
    @Override
    public double getTotalPrice(Vehicle vehicleSold) {
        return (this.getMonthlyPayment(vehicleSold)* 36) + this.getExpectedEndingValue();
    }
    
    
    @Override
    public double getMonthlyPayment(Vehicle vehicleSold) {
        double principalAmount = getVehicleSold().getPrice();
        double interestRate = 0.04 /12;
        int loanLength = 36;
        
        return principalAmount * (interestRate * Math.pow(1 + interestRate, 12 * loanLength)
                / (Math.pow(1 + interestRate, 12 * loanLength) - 1));
        
    }
}