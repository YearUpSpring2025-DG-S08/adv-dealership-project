package com.pluralsight;

@SuppressWarnings("ALL")
public class SalesContract extends Contract{
    private double salesTax; // total price * .5
    private double recordingFee; // 100
    private double processingFee;
    private boolean finance;
    
    
    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double salesTax, double recordingFee, double processingFee, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.finance = finance;
    }
    
    public double getSalesTax() {
        if(this.salesTax == 0){
            this.salesTax = this.getVehicleSold().getPrice() * 0.05;
        }
        return salesTax;
    }
    
    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }
    
    public double getRecordingFee() {
        if(this.recordingFee == 0){
            this.recordingFee = 100;
        }
        return this.recordingFee;
    }
    
    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }
    
    public double getProcessingFee() {
        return processingFee;
    }
    
    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }
    
    public boolean isFinance() {
        return finance;
    }
    
    public void setFinance(boolean finance) {
        this.finance = finance;
    }
    
    @Override
    public double getTotalPrice(Vehicle vehicleSold) {
        if(vehicleSold.getPrice() < 10000){
            if(processingFee == 0) {
                processingFee = 295;
            }
        } else{
            if(processingFee == 0) {
                processingFee = 495;
            }
        }
        return vehicleSold.getPrice() + getSalesTax() + getRecordingFee() + getProcessingFee();
    }
    
    @Override
    public double getMonthlyPayment(Vehicle vehicleSold) {
        // how do I get the monthly payment variable to 0 for a
        // NO loan option?
        if(!finance){
            if(vehicleSold.getPrice() >= 10000){
                return (4.25 * vehicleSold.getPrice()) * 48;
            } else{
                return (5.25 * vehicleSold.getPrice()) * 24;
            }
        }
        else{
            return 0;
        }
    }
}