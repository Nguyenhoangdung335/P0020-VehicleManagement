package main;

import UI.Window;
import util.DataOperation;
import view.Menu;

public class Main {
    public static void main(String[] args) {
        int choice = DataOperation.readInt("Enter 1 for console application, 2 for window application");
        switch (choice) {
            case 1 -> {
                ConsoleMainController.run();
            } case 2 -> {
                java.awt.EventQueue.invokeLater(() -> {
                    new Window();
                });
            } default -> {
                Menu.printStatement(Menu.INVALIDCHOICE);
                main(args);
            }
        }
    }
}
