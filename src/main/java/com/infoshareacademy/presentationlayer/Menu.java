package com.infoshareacademy.presentationlayer;

import java.util.Scanner;

public class Menu {

    public void showMenu(){
        Scanner scanner = new Scanner(System.in);


        String[] menuOptions = {"Option 1", "Option 2", "Option 3"};
        String[] option1SubOptions = {"Sub-option 1", "Sub-option 2", "Sub-option 3"};


        for (int i = 0; i < menuOptions.length; i++) {
            System.out.println(menuOptions[i]);
        }
        System.out.println("\n");
        boolean isInt = false;
        do {
            try {
                System.out.println("Please choose the desired menu option and press <ENTER>:");
                int menuChoice = scanner.nextInt();
                isInt = true;
                switch (menuChoice) {
                    case 1:
                        System.out.println("You've chosen " + menuOptions[0]);
                        System.out.println("The available sub-options are: ");
                        for (int i = 0; i < option1SubOptions.length; i++) {
                            System.out.println(option1SubOptions[i]);
                        }
                        System.out.println("\n");
                        System.out.println("Please choose your sub-option:");
                        try {
                            isInt = false;
                            menuChoice = scanner.nextInt();
                            isInt = true;
                            switch (menuChoice) {
                                case 1:
                                    System.out.println("You've chosen " + option1SubOptions[0]);
                                    break;
                                case 2:
                                    System.out.println("You've chosen " + option1SubOptions[1]);
                                    break;
                                case 3:
                                    System.out.println("You've chosen " + option1SubOptions[2]);
                                    break;
                                default:
                                    System.out.println("You've selected an invalid menu option. Please try again.");
                                    isInt = false;
                            }
                        } catch (Exception e) {
                            System.out.println("The input must be a number corresponding to the menu option.");
                            scanner.next();
                        }
                        break;
                    case 2:
                        System.out.println("You've chosen " + menuOptions[1]);
                        break;
                    case 3:
                        System.out.println("You've chosen " + menuOptions[2]);
                        break;
                    default:
                        System.out.println("You've selected an invalid menu option. Please try again.");
                        isInt = false;
                }
            } catch (Exception e) {
                System.out.println("The input must be a number corresponding to the menu option.");
                scanner.next();
            }
        } while(!isInt);

    }

}
