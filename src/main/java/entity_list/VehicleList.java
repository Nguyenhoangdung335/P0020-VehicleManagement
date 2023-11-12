package entity_list;

import entity.Vehicle;
import interfaces.ICRUDOperations;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import util.ConstVars;
import util.FileManager;

public class VehicleList extends LinkedList<Vehicle> implements ICRUDOperations<Vehicle>{
    private final ConstVars consts = ConstVars.getInstance();
    
    public VehicleList() {
        loadFileData();
    }
    
    public VehicleList (List<Vehicle> copy) {
        super(copy);
    }
    
    //<editor-fold desc="VEHICLE LIST CRUD OPERATIONS IMPLEMENTAION" defaultstate="collapsed">
    @Override
    public Boolean addNew(Vehicle newElement) {
        if (newElement == null || newElement.isEmpty())
            return false;        
        this.add(newElement);
        FileManager.autoSaveFile("vehicle", newElement.toString());
        return true;
    }
    
    

    @Override
    public Boolean deleting(Vehicle removeElement) {
        if (removeElement == null || removeElement.isEmpty()) 
            return false;
        this.remove(removeElement);
        FileManager.saveFile("vehicle", this);
        return true;
    }

    @Override
    public Boolean updating(Vehicle updateElement) {
        if (updateElement == null || updateElement.isEmpty())
            return false;
        updateElement.input();
        FileManager.saveFile("vehicle", this);
        return true;
    }

    @Override
    public Boolean showAll() {
        int counter = 0;
        for (var vehicle: this) {
                System.out.printf("%d. %s\n", ++counter, vehicle.toString());
        }
        return true;
    }

    @Override
    public Vehicle get(String ID) {
        for (var vehicle: this) {
            if (vehicle.getVehicleID().equals(ID)) 
                return vehicle;
        }
        return null;
    }
    //</editor-fold>
    
    //<editor-fold desc="VEHICLE LIST UTILITIES" defaultstate="collapsed">
    private void loadFileData () {
        FileManager f = new FileManager("vehicle");
        while (f.nextLine() != null) {
            this.add(new Vehicle(f.nextVar(), f.nextVar(), f.nextVar(), f.nextVar(), f.nextVar(), f.nextVar(), f.nextVar()));
        }
    }
    
    public boolean isExist (Vehicle v) {
        if (v == null) return false;
        for (var vehicle: this) {
            if (vehicle.getVehicleID().equals(v.getVehicleID())) 
                return true;
        }
        return false;
    }
    
    
    //</editor-fold>

    //<editor-fold desc="VEHICLE LIST SEARCH ALGORITHMS" defaultstate="collapsed">
    public List<Vehicle> searchByID (String ID) {
        List<Vehicle> result = new LinkedList();
        for (var vehicle: this) {
            if (vehicle.getVehicleID().equals(ID))
                result.add(vehicle);
        }
        return result;
    }
    
    public List<Vehicle> searchByName (String name) {
        List<Vehicle> result = new LinkedList();
        for (var vehicle: this) {
            if (vehicle.getName().toLowerCase().contains(name.toLowerCase()))
                result.add(vehicle);
        }
        return result;
    }
    
    public List<Vehicle> searchByType (String type) {
        List<Vehicle> result = new LinkedList();
        for (var vehicle: this) {
            if (vehicle.getType().equalsIgnoreCase(type))
                result.add(vehicle);
        }
        return result;
    }
    //</editor-fold>
    
    //<editor-fold desc="VEHICLE LIST SORT ALGORITHMS" defaultstate="collapsed">
    public VehicleList sortByPrice (boolean isAscending) {
        VehicleList newList = new VehicleList(this);
        Collections.sort(newList, (Vehicle v1, Vehicle v2) -> {
            int priceOffset = (int)(v1.getPrice() - v2.getPrice());
            return (isAscending)? priceOffset: priceOffset * (-1);
        });
        return newList;
    }
    
    public VehicleList sortByType () {
        List<Vehicle> newList = new LinkedList();
        for (var type: consts.VEHICLETYPES) {
            for (var vehicle: this) {
                if (vehicle.getType().equalsIgnoreCase(type))
                    newList.add(vehicle);
            }
        }
        return new VehicleList(newList);
    }
    //</editor-fold>
    
    public static void main(String[] args) {
        VehicleList list = new VehicleList();
        /*
        //Vehicle v1 = new Vehicle ("Kia Sorento", Color.LIGHT_GRAY, 31000, "Kia", "SUV", "2022");
        Vehicle v2 = new Vehicle ("Toyota Corolla", Color.RED, 40450, "Toyota", "Hatchback", "2023");
        Vehicle v3 = new Vehicle ("Subaru Impreza", Color.BLUE, 33690, "Subaru", "Sedan", "2023");
        Vehicle v4 = new Vehicle ("Ford Mustang GT", Color.YELLOW, 74990, "Ford", "Coupe", "2022");
        list.addNew(v2);
        list.addNew(v3);
        list.addNew(v4);
        list.showAll();
        */
        //FileManager.saveFile("VEHICLE", list);
        list.showAll();
        System.out.println();
        VehicleList list2 = list.sortByPrice(true);
        list2.showAll();
        System.out.println();
        VehicleList list3 = list.sortByType();
        list3.showAll();
    }
}
