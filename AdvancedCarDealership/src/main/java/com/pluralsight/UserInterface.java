package com.pluralsight;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.StyledUI.styledHeader;

public class UserInterface {
    private Dealership dealership;
    public DealershipFileManager fileManager = new DealershipFileManager();
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
        fileManager.printVehicleInventory(vehicles);
    }
    
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
        if(vehicleType.isEmpty()){
            System.out.println("There were no vehicle matching the given vehicle type");
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
        fileManager.printVehicleInventory(dealership.inventory);
        
        int vin = console.promptForInt("Please enter the vin number of the vehicle you want to remove: ");
        
        List<Vehicle> vehicleVin = dealership.getVehicleByVIN(vin);
        for(Vehicle v : vehicleVin){
            dealership.removeVehicle(v);
        }
        System.out.println("This vehicle was removed: ");
        displayVehicle(vehicleVin);
    }
}