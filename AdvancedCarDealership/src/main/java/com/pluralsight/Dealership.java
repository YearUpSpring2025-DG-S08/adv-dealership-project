package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Dealership {
    // instance variables
    private String name;
    private String address;
    private String phoneNumber;
    private final DealershipFileManager fileManager = new DealershipFileManager();
    public List<Vehicle> inventory = new ArrayList<>();
    
    // constructor
    public Dealership(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // helper method
    public Vehicle getVehicleByVIN(int vin){
       // List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getVin() == vin){
                return vehicle;
               // filteredVehicles.add(vehicle);
            }
        }
       // return filteredVehicles;
        return null;
    }

    // user menu option filtering methods
    public List<Vehicle> getVehiclesByPrice(double min, double max){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getPrice() >= min && vehicle.getPrice() <= max){
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
    
    public List<Vehicle> getVehiclesByMake(String make){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getMake().equalsIgnoreCase(make)){
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
    
    public List<Vehicle> getVehiclesByModel(String model){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getModel().equalsIgnoreCase(model)){
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
    
    public List<Vehicle> getVehiclesByYear(double min, double max){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getYear() >= min && vehicle.getYear() <= max){
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
    
    public List<Vehicle> getVehiclesByColor(String color){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getColor().equalsIgnoreCase(color)){
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
    
    public List<Vehicle> getVehiclesByMileage(double min, double max){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getMileage() >= min && vehicle.getMileage() <= max){
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
    
    public List<Vehicle> getVehiclesByType(String vehicleType){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        
        for(Vehicle vehicle : inventory){
            if(vehicle.getType().equalsIgnoreCase(vehicleType)){
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
    
    public List<Vehicle> getAllVehicles(){
        
        return inventory;
    }

    // file modifier methods
    public void addVehicle(Vehicle vehicle) {
        //add this vehicle to the inventory of this dealership
        inventory.add(vehicle);
        fileManager.saveDealership(this);
    }
    
    public void removeVehicle(Vehicle vehicle){
        // remove this vehicle from the inventory of the dealership
        inventory.remove(vehicle);
        fileManager.saveDealership(this);
    }
}