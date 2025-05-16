package com.pluralsight;

@SuppressWarnings("ALL")
public class LeaseContract extends Contract{
    
    private double endingValue;
    private double leaseFee;
    
    
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double endingValue, double leaseFee) {
        super(date, customerName, customerEmail, vehicleSold);
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
            this.leaseFee = getVehicleSold().getPrice() * .07;
        }
        return this.leaseFee;
    }
    
    @Override
    public double getTotalPrice(Vehicle vehicleSold) {
        return (this.getMonthlyPayment(vehicleSold)* 36) + this.getExpectedEndingValue();
    }
    
    
    @Override
    public double getMonthlyPayment(Vehicle vehicleSold) {
        double principalAmount = vehicleSold.getPrice();
        double interestRate = 0.04;
        int loanLength = 36;
        
        return principalAmount * (interestRate * Math.pow(1 + interestRate, 12 * loanLength)
                / (Math.pow(1 + interestRate, 12 * loanLength) - 1));
        
    }

    @Override
    public String toString() {
        return String.format(
                """
                        Lease Contract Details:
                        --------------------
                        Date: %s
                        Customer: %s
                        Email: %s
                        Vehicle: %s
                        Total Price: $%.2f
                        Monthly Payment: $%.2f
                        End of Lease Value: $%.2f
                        Lease Fee: $%.2f""",
                super.getDate(),
                super.getCustomerName(),
                super.getCustomerEmail(),
                vehicleDetails(),  // You'll need to create this helper method
                getTotalPrice(super.getVehicleSold()),
                getMonthlyPayment(super.getVehicleSold()),
                getEndingValue(),
                getLeaseFee());
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