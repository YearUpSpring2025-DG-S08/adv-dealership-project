package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("IfCanBeSwitch")
public class ContractDataManager {
    private static final String filepath = "contracts.csv";
    
    public void saveContract(Contract contract){

        // error handling for possible null file
        if(contract == null){
            throw new IllegalArgumentException("Contract file cannot be null");
        }

        if(contract instanceof SalesContract) {

            try (PrintWriter salesWriter = new PrintWriter(new FileWriter(filepath, true))) {

                // error handling for possible null properties
                String date = contract.getDate() != null ? contract.getDate() : "N/A";
                String customerName = contract.getCustomerName() != null ? contract.getCustomerName() : "N/A";
                String customerEmail = contract.getCustomerEmail() != null ? contract.getCustomerEmail() : "N/A";
                Vehicle vehicleSold = contract.getVehicleSold() != null ? contract.getVehicleSold() : null;

                // checks if vehicle is null before attempting to calculate price and monthly payment
                double totalPrice = contract.getVehicleSold() != null ? contract.getTotalPrice(contract.getVehicleSold()) : 0;
                double monthlyPayment = contract.getVehicleSold() != null ? contract.getMonthlyPayment(contract.getVehicleSold()) : 0;

            salesWriter.printf("%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f|%b\n"
                    , "SALE", date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment
                    , ((SalesContract) contract).getSalesTax()
                    , ((SalesContract) contract).getRecordingFee()
                    , ((SalesContract) contract).getProcessingFee()
                    , ((SalesContract) contract).isFinanced());

            } catch(IOException e){
                System.out.println("Could not write to file");
            }
        }
        else if(contract instanceof LeaseContract){


            try (PrintWriter leaseWriter = new PrintWriter(new FileWriter(filepath, true))) {

                // error handling for possible null properties
                String date = contract.getDate() != null ? contract.getDate() : "N/A";
                String customerName = contract.getCustomerName() != null ? contract.getCustomerName() : "N/A";
                String customerEmail = contract.getCustomerEmail() != null ? contract.getCustomerEmail() : "N/A";
                Vehicle vehicleSold = contract.getVehicleSold() != null ? contract.getVehicleSold() : null;

                // checks if vehicle is null before attempting to calculate price and monthly payment
                double totalPrice = contract.getVehicleSold() != null ? contract.getTotalPrice(contract.getVehicleSold()) : 0;
                double monthlyPayment = contract.getVehicleSold() != null ? contract.getMonthlyPayment(contract.getVehicleSold()) : 0;

                leaseWriter.printf("%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f\n"
                    , "LEASE", date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment
                    , ((LeaseContract) contract).getEndingValue()
                    , ((LeaseContract) contract).getLeaseFee());

            }
            catch(IOException e){
                System.out.println("Could not write to file");
            }
        } else{
            // if both instances do not run, then there is an Illegal Argument being passed
            // the error message will allow us to know what is being passed
            throw new IllegalArgumentException("Unknown contract type: " + contract.getClass().getName());
        }
    }
}
