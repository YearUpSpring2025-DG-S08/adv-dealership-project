package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ContractDataManager {
    private static final String filepath = "contracts.csv";
    
    public void saveContract(Contract contract) throws IOException {
        if(contract instanceof SalesContract) {
            
            PrintWriter salesWriter = new PrintWriter(new FileWriter(filepath, true));

//            Contract file header - where can I put this to safely print without adding this line to the file?
//            salesWriter.printf("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s\n",
//                    "Contract Type", "Date of Contract", "Customer Name", "Customer Email",
//                    "Vehicle Sold", "Contract Price", "Monthly Payment", "Sales Tax", "Recording Fee",
//                    "Processing Fee", "Finance Option");

            salesWriter.printf("%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f|%b\n"
                , "SALE", contract.getDate(), contract.getCustomerName(), contract.getCustomerEmail()
                , contract.getVehicleSold(), contract.getTotalPrice(contract.getVehicleSold())
                , contract.getMonthlyPayment(contract.getVehicleSold()), ((SalesContract) contract).getSalesTax()
                , ((SalesContract) contract).getRecordingFee(), ((SalesContract) contract).getProcessingFee()
                , ((SalesContract) contract).isFinance());
        }
        else if(contract instanceof LeaseContract){
            
            PrintWriter leaseWriter = new PrintWriter(new FileWriter(filepath, true));
            
            leaseWriter.printf("\n%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f"
                , "LEASE", contract.getDate(), contract.getCustomerName(), contract.getCustomerEmail()
                , contract.getVehicleSold(), contract.getTotalPrice(contract.getVehicleSold())
                , contract.getMonthlyPayment(contract.getVehicleSold()), ((LeaseContract) contract).getEndingValue()
                , ((LeaseContract) contract).getLeaseFee());
        }
    }
}
