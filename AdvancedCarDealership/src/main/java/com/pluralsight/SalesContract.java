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

    // getters & setters
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
        if(this.processingFee == 0){
            if(super.getVehicleSold().getPrice() < 10000){
                return this.processingFee = 295;
            } else{
                return this.processingFee = 495;
            }
        }
        return this.processingFee;
    }
    
    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }
    
    public boolean isFinanced() {
        return finance;
    }
    
    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    // derived methods
    @Override
    public double getTotalPrice(Vehicle vehicleSold) {
        return vehicleSold.getPrice() + getSalesTax() + getRecordingFee() + getProcessingFee();
    }
    
    @Override
    public double getMonthlyPayment(Vehicle vehicleSold) {
        // how do I get the monthly payment variable to 0 for a
        // NO loan option?
        double principalAmount = vehicleSold.getPrice();
        double interestRate;
        int loanLength;

        if(!finance) {
            return 0;
        }

            if (vehicleSold.getPrice() >= 10000) {
                interestRate = 0.0425;
                loanLength = 48;
            } else{
            interestRate = 0.0525;
            loanLength = 24;
            }

        double monthlyPayment =
                principalAmount * (interestRate * Math.pow(1 + interestRate, 12 * loanLength)
                        / (Math.pow(1 + interestRate, 12 * loanLength) - 1));
        return monthlyPayment;
    }

    // display methods
    @Override
    public String toString() {
        return String.format(
                        "\n--------------------\n" +
                        "Date: %s\n" +
                        "Customer: %s\n" +
                        "Email: %s\n" +
                        "Vehicle: %s\n" +
                        "Total Price: $%.2f\n" +
                        "Monthly Payment: $%.2f\n" +
                        "Sales Tax: $%.2f\n" +
                        "Recording Fee: $%.2f\n" +
                        "Processing Fee: $%.2f\n",
                super.getDate(),
                super.getCustomerName(),
                super.getCustomerEmail(),
                vehicleDetails(),  // create helper method to load vehicle details
                getTotalPrice(super.getVehicleSold()),
                getMonthlyPayment(super.getVehicleSold()),
                getSalesTax(),
                getRecordingFee(),
                getProcessingFee());
    }

    // Helper method to format vehicle details
    private String vehicleDetails() {
        Vehicle v = super.getVehicleSold();
        if (v == null) return "N/A";
        return String.format("VIN: %d | YEAR: %d | MAKE: %s | MODEL: %s " +
                        "| VEHICLE TYPE: %s | COLOR: %s | MILEAGE: %.0f | PRICE: $%.2f"
                ,v.getVin(), v.getYear(), v.getMake(), v.getModel(), v.getType()
                ,v.getColor(), v.getMileage(), v.getPrice());
    }
}