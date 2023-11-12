package util;

import exception.BlankInputException;
import java.io.File;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/*------------------------------------------------------------------------------------------
-----The FileManager Class hold fields and methods for managing loading and saving file-----
--------It has two use cases: One for object creating and the other for static usage--------
------------------------------------------------------------------------------------------*/

public class FileManager {
    /*--Object Creation: Used to save data load from file and used it as a way to easily read data from file--*/
    
    //OBJECT FIELDS
    private String currentFile;
    private final int size;
    
    //LINE FIELDS
    private final List<String> dataList;
    private int currentLineNumber = 0;
    
    //VARIABLE ON CURRENT lINE FIELD
    private String[] currentLineData;
    private int currentVariable = 0;
    
    public FileManager (String filename) {
        this.currentFile = filename;
        dataList = loadFile(filename);
        size = (dataList.isEmpty())? -1: dataList.size();
    }
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }
    
    public String getLine () {
        return Arrays.toString(currentLineData);
    }
    
    public String getLine (int line) {
        return dataList.get(line);
    }
    
    public List getList () {
        return dataList;
    }
    
    public int getSize () {
        return size;
    }
    //</editor-fold>
    
    //Return the next line in the list, if there is no more line to read then return null
    //When read new line, reset currentVariable and currentLine data
    public String nextLine () {
        if (currentLineNumber >= size)
            return null;
        currentVariable = 0;
        currentLineData = dataList.get(currentLineNumber).split("_");
        return dataList.get(currentLineNumber++);
    }
    
    //Return the next data in the current Line 
    public String nextVar () {
        return (currentVariable < currentLineData.length)? currentLineData[currentVariable++]: null;
    }
    
    /*--------------------------------------------------------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/
    
    /*---------Static method: Contains load/save file method that can be called anywhere----------*/
    
    //<editor-fold desc="STATIC FIELDS" defaultstate="collapsed">
    private static enum FILENAME {
        VEHICLE("vehicle.dat");
        
        private final String name;
        private FILENAME (String name) {
            this.name = name;
        }
        
        public String getName () {
            return name;
        }
    }
    private final static String TXTPATH = System.getProperty("user.dir") + "\\src\\main\\java\\file";
    private final static String IMAGEPATH = TXTPATH + "\\images";
    //</editor-fold>

    //<editor-fold desc="FILEMANAGER'S STATIC METHODS" defaultstate="collapsed">
    
    //Load the data stored in file and add to a a list of String
    //If there is no file with the passed filePath, called createFile() function
    public static List<String> loadFile (String filename) {
        filename = convertFileName(filename);
        File f = new File(TXTPATH + "\\" + filename);
        if (!f.exists()) {
            createFile(filename);
            return null;
        }
        
        List<String> list = new LinkedList();
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String data;
            while ( (data = r.readLine()) != null) {
                if (data.isEmpty()) throw new BlankInputException("");
                list.add(data);
            }
        } catch (IOException e) {
            System.out.println("There is no such file!");
        } catch (BlankInputException e) {
        }
        return list;
    }
    
    //Used for creating file directory and the file
    public static void createFile (String filename) {
        File f = new File(TXTPATH);
        f.mkdir();
        f = new File(TXTPATH + "\\" + filename);
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println();
        } catch (IOException e){
        }
    }
    
    //Used for saving file from the passed list to the passed file
    //This method overwrite all existing data in the file specified
    public static <T> void saveFile (String filename, List<T> list) {
        filename = convertFileName(filename);
        File f = new File(TXTPATH + "\\" + filename);
        try (PrintWriter pw = new PrintWriter(f)) {
            for (T entry: list) {
                pw.println(entry.toString());
            }    
        } catch (IOException e) {
        }
    }
    
    public static <T> void saveFile (String filename, File saveFile) {
        
    }
    
    //Used for saving file from the passed String of new data to the passed file
    //This method append new data onto the end of the file without affecting the above data
    public static <T> void autoSaveFile (String filename, String newLine) {
        filename = convertFileName(filename);
        File f = new File(TXTPATH + "\\" + filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, true))) {
            bw.write(newLine + "\n");
        } catch (IOException e) {
        }
    }
    
    //Used to convert the passed filename to usuable filename with . tail
    //This method work in parallel with the enum FILENAME
    private static String convertFileName (String filename) {
        for (FILENAME name: FILENAME.values()) {
            if (name.toString().equalsIgnoreCase(filename))
                return name.getName();
        }
        return null;
    }
    
    public String getFileTextPath () {
        return TXTPATH;
    }
    
    public String getFileImagePath () {
        return IMAGEPATH;
    }
    //</editor-fold>
}