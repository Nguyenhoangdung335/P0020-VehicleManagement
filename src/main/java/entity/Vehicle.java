package entity;

import java.awt.Color;
import java.awt.Image;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import util.ColorUtil;
import util.ConstVars;
import util.input.VehicleInputUtil;

public final class Vehicle {
    private ConstVars consts = ConstVars.getInstance();
    private static int incrementingID = 0;
    
    private String vehicleID;
    private String name;
    private Color color;
    private double price;
    private String brand;
    private String type;
    private LocalDate productYear;
    
    private Image vehicleImage;
    
    //<editor-fold desc="CONSTRUCTORS" defaultstate="collapsed">
    public Vehicle() {
        this.vehicleID = String.format("V%04d", incrementingID++);
        this.name = "";
        this.color = null;
        this.price = 0;
        this.brand = "";
        this.type = "";
        this.productYear = null;
        this.vehicleImage = null;
    }

    public Vehicle(String name, Color color, double price, String brand, String type, LocalDate productYear) {
        this.vehicleID = String.format("V%04d", incrementingID++);
        this.name = name;
        this.color = color;
        this.price = price;
        this.brand = brand;
        this.type = type;
        this.productYear = productYear;
        this.vehicleImage = null;
    }
    
    public Vehicle (String name, Color color, double price, String brand, String type, String productYear) {
        this.vehicleID = String.format("V%04d", incrementingID++);
        this.name = name;
        this.color = color;
        this.price = price;
        this.brand = brand;
        this.type = type;
        setProductYear(productYear);
        this.vehicleImage = null;
    }
    
    public Vehicle (String vehicleID, String name, String hexColor, String price, String brand, String type, String productYear) {
        incrementingID++;
        this.vehicleID = vehicleID;
        this.name = name;
        this.color = ColorUtil.hexToColor(hexColor);
        setPrice(price);
        this.brand = brand;
        this.type = type;
        setProductYear(productYear);
        this.vehicleImage = null;
    }
    //</editor-fold>
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }
    
    public int getIDNumber () {
        int idNum = Integer.parseInt(vehicleID.substring(1));
        return idNum;
    }
    
    public void setIDNumber (int idNumber) {
        this.vehicleID = String.format("V%04d", idNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; 
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setPrice (String price) {
        this.price = Double.parseDouble(price);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getProductYear() {
        return productYear;
    }
    
    public String getProductYearString () {
        return (productYear == null)? null: productYear.format(consts.YEARPATTERN);
    }

    public void setProductYear(LocalDate productYear) {
        this.productYear = productYear;
    }
    
    public void setProductYear (String productYear) {
        this.productYear = LocalDate.parse(String.format("01-01-%s", productYear), consts.DATEPATTERN);
    }
    
    public Image getVehicleImage () {
        return vehicleImage;
    }
    
    public void setVehicleImage (Image newImage) {
        this.vehicleImage = newImage;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return String.format("%s_%s_%s_%s_%s_%s_%s", getVehicleID(), getName(), ColorUtil.colorToHex(color), getPrice(), getBrand(), getType(), getProductYearString());
    }

    public boolean isEmpty() {
        return name.isBlank() && color == null && price == 0.0 && brand.isBlank() 
                && type.isBlank() && productYear == null;
    }
    
    public void input () {
        String vName = VehicleInputUtil.enterVehicleName("Enter the vehicle name");
        String hexCode = VehicleInputUtil.enterVehicleHexColor("Enter the vehicle hex color");
        double vPrice = VehicleInputUtil.enterVehiclePrice("Enter the vehicle price");
        String vBrand = VehicleInputUtil.enterVehicleBrand("Enter the vehicle brand");
        String vType = VehicleInputUtil.enterVehicleType("Enter the vehicle type");
        String productYear = VehicleInputUtil.enterVehicleProductYear("Enter the vehicle product year");
        
        this.setName(vName);
        this.setColor(ColorUtil.hexToColor(hexCode));
        this.setPrice(vPrice);
        this.setBrand(vBrand);
        this.setType(vType);
        this.setProductYear(productYear);
    }
}
