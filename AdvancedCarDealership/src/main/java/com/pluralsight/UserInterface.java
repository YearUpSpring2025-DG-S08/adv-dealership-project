package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.ColorCodes.BLACK_BACKGROUND;
import static com.pluralsight.ColorCodes.RESET;
import static com.pluralsight.ColorCodes.YELLOW;
import static com.pluralsight.StyledUI.styledHeader;

@SuppressWarnings("ALL")
public class UserInterface {
    private Dealership dealership;
    public DealershipFileManager fileManager = new DealershipFileManager();
    public ContractDataManager dataManager = new ContractDataManager();
    private final Console console = new Console();
    private final Scanner scanner = new Scanner(System.in);

    public UserInterface() {
    }

    // this method initializes the Dealership object to be accessed by the User
    private void init() {
        this.dealership = fileManager.getDealership();
        if (this.dealership == null) {
            System.out.println("Could not load dealership details");
        }
    }

    // user interface display methods
    public void display() {
        init();
        // create a loop and display the menu
        // read user's command
        // code a switch statement that calls the correct process method()
        // created a helper method to display the menu
        displayMenu();
    }

    private void displayMenu() {
        // this helper method will display the menu for the User to make a selection
        // for which process they would like to choose
        // will include accepting user input within this method
        int input = 0;
        while(input != -1) {
            printDealershipInfo(this.dealership);
            styledHeader("Welcome to the Dealership!");
            String welcomeMenuPrompt ="""
                    [1] Search by Price
                    [2] Search by Make/Model
                    [3] Search by Year
                    [4] Search by Color
                    [5] Search by Mileage
                    [6] Search by Vehicle Type
                    [7] Search All Vehicles
                    [8] Add Vehicle to lot
                    [9] Remove Vehicle from lot
                    [10] Sell a Vehicle
                    [11] Lease a Vehicle
                    """;

            input = console.promptForInt(welcomeMenuPrompt);

            switch (input) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processSalesContract();
                    break;
                case 11:
                    processLeaseContract();
                    break;
                default:
                    System.out.println("Please make a selection from the menu");
            }
        }

    }

    private void displayVehicle(List<Vehicle> vehicles) {
        // helper method - displays the list and can be called from all the get-vehicles type method
        // this method should have a parameter that is passed in containing the vehicles to list
        // within the method, create a loop and display the vehicles
        // format this to display more aesthetically for the user
        printVehicleInventory(vehicles);
    }

    private void displayVehicle(Vehicle vehicle){
        ArrayList<Vehicle> v = new ArrayList<>();
        v.add(vehicle);
        displayVehicle(v);
    }

    // user menu options
    private void processGetByPriceRequest() {
        // get min/max from the user
        // add formatted header so that users are aware what they are doing
        // in this instance: Searching for vehicle by price
        System.out.println(StyledUI.styledBoxTitle("Search Vehicles By Price"));
        double min;
        double max;

        while(true) {
            try {
                System.out.print("Please enter a minimum price: ");
                min = scanner.nextDouble();
                System.out.print("Please enter a maximum price: ");
                max = scanner.nextDouble();
                scanner.nextLine();

                if (min > max) {
                    System.out.println("Your minimum price cannot be greater than maximum price");
                    continue;
                }


            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value for price.");
                scanner.nextLine();
                return;
            }

            List<Vehicle> vehiclePrices = dealership.getVehiclesByPrice(min, max);

            if (vehiclePrices.isEmpty()) {
                System.out.println("There were no vehicles found within this price range");
            } else {
                // add formatting so that the output is easier to read for users
                displayVehicle(vehiclePrices);
                break;
            }
        }
    }

    private void processGetByMakeModelRequest() {
        // get make/model from the user
        // add formatted header so that users are aware what they are doing
        // in this instance: Searching for vehicle by Make/Model
        System.out.print("Please enter the Make of the vehicle to search: ");
        String make = scanner.nextLine().trim().toLowerCase();

        System.out.print("Please enter the Model of the vehicle to search: ");
        String model = scanner.nextLine().trim().toLowerCase();

        List<Vehicle> results = new ArrayList<>();

        for (Vehicle vehicle : dealership.getAllVehicles()) {
            String vehicleMake = vehicle.getMake().toLowerCase();
            String vehicleModel = vehicle.getModel().toLowerCase();

            boolean matchesMake = make.isEmpty() || vehicleMake.contains(make);
            boolean matchesModel = model.isEmpty() || vehicleModel.contains(model);

            if (matchesMake && matchesModel) {
                results.add(vehicle);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No vehicles found matching your search criteria.");
        } else {
            displayVehicle(results);
        }
    }

    private void processGetByYearRequest() {
        // get min/max year of vehicle from the user
        // add formatted header so that users are aware what they are doing
        // in this instance: Searching for vehicle by year
        System.out.println(StyledUI.styledBoxTitle("Search Vehicles By Year"));
        double min;
        double max;
        while(true){
            try {
                min = console.promptForDouble("Please enter the minimum year of the vehicle to search: ");
                max = console.promptForDouble("Please enter the maximum year of the vehicle to search: ");

                if(min > max){
                    System.out.println("Minimum year cannot be greater than maximum year. Please try again.");
                    continue;
                }

                break;
            } catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a numerical value for year.");
                console.clearScanner();
            }
        }

        List<Vehicle> vehicleYear = dealership.getVehiclesByYear(min, max);
        if(vehicleYear.isEmpty()){
            System.out.println("No vehicles found within the given year range");
        }else {
            displayVehicle(vehicleYear);
        }
    }

    private void processGetByColorRequest() {
        // get color of vehicle from the user
        System.out.println(StyledUI.styledBoxTitle("Search Vehicles By Color"));
        System.out.print("Please enter the color of the vehicle to search: ");
        String color = scanner.nextLine();

        List<Vehicle> vehicleColor = dealership.getVehiclesByColor(color);

        if(vehicleColor == null || vehicleColor.isEmpty()){
            System.out.println("There were no vehicles found matching that color");
            return;
        }

        displayVehicle(vehicleColor);
    }

    private void processGetByMileageRequest() {
        // get mileage of vehicle from the user
        System.out.println(StyledUI.styledBoxTitle("Search Vehicles By Mileage"));
        double min;
        double max;
        while(true) {
            try {
                min = console.promptForDouble("Please enter the minimum mileage of the vehicle to search: ");
                max = console.promptForDouble("Please enter the maximum mileage of the vehicle to search: ");

                if(min > max){
                    System.out.println("Minimum mileage cannot be greater than maximum mileage. Please try again");
                    continue;
                }
                break;
            } catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a numerical value for the mileage");
                console.clearScanner();
            }
        }
        List<Vehicle> vehicleMileage = dealership.getVehiclesByMileage(min, max);
        displayVehicle(vehicleMileage);
    }

    private void processGetByVehicleTypeRequest() {
        // get vehicle type from the user
        System.out.println(StyledUI.styledBoxTitle("Search Vehicles By Type"));
        System.out.print("Please enter the vehicle type to search: ");
        String type = scanner.nextLine();

        List<Vehicle> vehicleType = dealership.getVehiclesByType(type);
        if(vehicleType.isEmpty()) {
            System.out.println("There were no vehicles matching the given vehicle type");
        } else if(vehicleType != vehicleType){
            System.out.println("Invalid input, please enter a vehicle type");
        } else{
        displayVehicle(vehicleType);
        }
    }

    public void processGetAllVehiclesRequest() {
        // list all vehicles in the dealership
        // this method will be used to test that we successfully load the dealership and vehicles from the file
        // call the dealership's getAllVehicles() method
        // call the displayVehicles() helper method and pass it the list that is returned from getAllVehicles()
        System.out.println(StyledUI.styledBoxTitle("Search All Vehicles"));
        displayVehicle(dealership.getAllVehicles());
    }

    private void processAddVehicleRequest(){
        System.out.println(StyledUI.styledBoxTitle("Add a Vehicle to Dealership lot"));
        System.out.println("Please enter the following details to add a vehicle to the lot: ");
        int vin = console.promptForInt("Vehicle VIN: (numerical vin's only) ");
        int year = console.promptForInt("Vehicle Year: ");
        String make = console.promptForString("Vehicle Make: ");
        String model = console.promptForString("Vehicle Model: ");
        String type = console.promptForString("Vehicle Type: ");
        String color = console.promptForString("Vehicle Color: ");
        double mileage = console.promptForDouble("Vehicle Mileage: ");
        double price = console.promptForDouble("Vehicle Price: ");

        try{
        Vehicle newVehicle = new Vehicle(vin, year, make, model, type, color, mileage, price);
        dealership.addVehicle(newVehicle);

        System.out.println("This vehicle has been successfully added to the lot!");
        } catch(Exception e){
            System.out.println("There was an error trying to add this vehicle to the lot!");
        }
    }

    private void processRemoveVehicleRequest() {
        // get a vehicle to remove from the List<Vehicle> inventory - in the Dealership class
        System.out.println(StyledUI.styledBoxTitle("Remove a Vehicle from Dealership lot"));
        printVehicleInventory(dealership.inventory);

        int vin = console.promptForInt("Please enter the vin number of the vehicle you want to remove: ");

        Vehicle removedVehicle = dealership.getVehicleByVIN(vin);
            dealership.removeVehicle(removedVehicle);
        System.out.println("This vehicle was removed: ");
        displayVehicle(removedVehicle);
    }

    private void processSalesContract(){
        // user selects to buy a vehicle
        // users need a list of vehicle to buy from
        printVehicleInventory(dealership.inventory);

            // create loop for potential InputErrorMismatch
        int vin;
        while(true) {
            vin = console.promptForInt("Please enter the vin number of the vehicle you want to buy: ");

            if (vin >= 11111 && vin <= 99999){
                break;
            } else{
                System.out.println("Invalid VIN. Please try again.");
            }
        }

        Vehicle vehicleForSale = dealership.getVehicleByVIN(vin);
        if(vehicleForSale == null){
            throw new IllegalArgumentException("The vehicle vin is not associated with a vehicle in this lot");
        }


        System.out.println("This vehicle was chosen for sale: " );
        displayVehicle(vehicleForSale);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate now = LocalDate.now();
        String date = now.format(formatter);

        String customerName = "";
        while(true) {
            customerName = console.promptForString("Please enter your full name: ");
            String[] fullName = customerName.trim().split("\\s+");

            if (fullName.length == 2) {
                String firstName = fullName[0];
                String lastName = fullName[1];
            break;
            } else{
                System.out.println("Please enter a first and last name!");
            }
        }

        String customerEmail = console.promptForString("Please enter your email address: ");
        String finance = console.promptForString("Would you like to finance your vehicle purchase? Y/N ");

        boolean isFinanced = false;
        if(finance.equalsIgnoreCase("Y")){
            isFinanced = true;
        }
        double salesTax = 0;
        double recordingFee = 0;
        double processingFee = 0;

        // need to instantiate the correct type of contract and send it to ContractDataManager
        // when instantiating an object of the correct type, we need the data that is going to be passed
        // into the constructor
        SalesContract vehicleSale = new SalesContract(date, customerName, customerEmail, vehicleForSale,
                salesTax, recordingFee, processingFee, isFinanced);

        processSaveSalesContract(vehicleForSale, vehicleSale);
    }

    private void processLeaseContract(){
        // user selects to buy a vehicle
        // users need a list of vehicle to buy from
        printVehicleInventory(dealership.inventory);

        // create loop for potential InputErrorMismatch
        int vin;
        while(true) {
            vin = console.promptForInt("Please enter the vin number of the vehicle you want to lease: ");

            if (vin >= 11111 && vin <= 99999){
                break;
            } else{
                System.out.println("Invalid VIN. Please try again.");
            }
        }

        Vehicle vehicleForLease = dealership.getVehicleByVIN(vin);
        if(vehicleForLease == null){
            throw new IllegalArgumentException("The vehicle vin is not associated with a vehicle in this lot");
        }

        LocalDate now = LocalDate.now();
        if(vehicleForLease.getYear() < (now.getYear() - 3)){
            System.out.println("You cannot lease a vehicle older than 3 years old!");
            return;
        }


        System.out.println("This vehicle was chosen for lease: " );
        displayVehicle(vehicleForLease);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = now.format(formatter);

        String customerName = "";
        while(true) {
            customerName = console.promptForString("Please enter your full name: ");
            String[] fullName = customerName.trim().split("\\s+");

            if (fullName.length == 2) {
                String firstName = fullName[0];
                String lastName = fullName[1];
                break;
            } else{
                System.out.println("Please enter a first and last name!");
            }
        }

        String customerEmail = console.promptForString("Please enter your email address: ");

        double endingValue = 0;
        double leaseFee = 0;

        // need to instantiate the correct type of contract and send it to ContractDataManager
        // when instantiating an object of the correct type, we need the data that is going to be passed
        // into the constructor
        LeaseContract vehicleLease = new LeaseContract(date, customerName, customerEmail, vehicleForLease,
                endingValue, leaseFee);
            processSaveLeaseContract(vehicleForLease, vehicleLease);
    }

    // helper methods
    private void processSaveSalesContract(Vehicle vehicle,Contract contract){
        // save contract to contract csv
        try{
            if(true){
                System.out.println("Please review the details of your Sales Contract: " + contract);

                String acceptSale = console.promptForString("Do you want to move forward with your sale? Y/N ");
                if(acceptSale.equalsIgnoreCase("Y")){
                    dataManager.saveContract(contract);
                    System.out.println("You have successfully created a Sales Contract!");

                    // remove vehicle from inventory
                    try {
                        if (vehicle == null) {
                            System.out.println("There is no vehicle selected to remove");
                            return;
                        }

                        if (!dealership.getAllVehicles().contains(vehicle)) {
                            System.out.println("Vehicle not found in inventory.");
                            return;
                        }

                        dealership.removeVehicle(vehicle);
                        System.out.println("Vehicle removed successfully from dealership.");

                    } catch (NullPointerException e) {
                        System.out.println("Inventory list is null");
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred while removing the vehicle: " + e.getMessage());
                    }

                } else if(acceptSale.equalsIgnoreCase("N")){
                    System.out.println("Thank you for your time");
                } else{
                    System.out.println("Invalid input. Cancelling Sales Contract");
                }
            }
        } catch(RuntimeException e){
            System.out.println("There was an issue creating your Sales Contract: " + e.getMessage());
        }
    }

    private void processSaveLeaseContract(Vehicle vehicle, Contract contract){
        // save contract to contract csv
        try {
            if (true) {
                System.out.println("Please review the details of your Lease Contract: " + contract);

                String acceptLease = console.promptForString("Do you want to move forward with your Lease? Y/N ");
                if (acceptLease.equalsIgnoreCase("Y")) {
                    dataManager.saveContract(contract);
                    System.out.println("You have successfully created a Lease Contract");

                    // remove the vehicle from inventory
                    try {
                        if (contract == null) {
                            System.out.println("There is no vehicle selected to remove");
                            return;
                        }

                        if (!dealership.getAllVehicles().contains(vehicle)) {
                            System.out.println("Vehicle not found in inventory");
                            return;
                        }

                        dealership.removeVehicle(vehicle);
                        System.out.println("Vehicle removed successfully from dealership.");

                    } catch (NullPointerException e) {
                        System.out.println("Inventory list is null");
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred while removing the vehicle: " + e.getMessage());
                    }

                } else if (acceptLease.equalsIgnoreCase("N")) {
                    System.out.println("Thank you for your time!");
                } else {
                    System.out.println("Invalid input. Cancelling Lease Contract");
                }
            }
        } catch (RuntimeException e) {
            System.out.println("There was an issue creating your Lease Contract " + e.getMessage());
        }
    }

    public void printDealershipInfo(Dealership dealership) {
        String name = dealership.getName();
        String address = dealership.getAddress();
        String phone = dealership.getPhoneNumber();

        String border = "=".repeat(60);

        System.out.println(border);
        // Each line is exactly 60 characters wide with formatting and background applied to the entire line
        System.out.print(BLACK_BACKGROUND + YELLOW);
        System.out.printf("  DEALERSHIP: %-47s", name);
        System.out.println(RESET);

        System.out.print(BLACK_BACKGROUND + YELLOW);
        System.out.printf("  Address:    %-47s", address);
        System.out.println(RESET);

        System.out.print(BLACK_BACKGROUND + YELLOW);
        System.out.printf("  Phone:      %-47s", phone);
        System.out.println(RESET);

        System.out.println(border);
    }

    public void printVehicleInventory(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles in inventory \n");
            return;
        }

        // Print styled header
        System.out.println(StyledUI.FormattedTextHeader());

        // Print each vehicle using the Vehicle's toFormattedRow() method
        for (Vehicle v : vehicles) {
            System.out.println(v.toFormattedRow());
        }

        System.out.println();
    }

}