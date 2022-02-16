package com.infoshareacademy.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ValuesScanner {
    public static final Scanner scanner = new Scanner(System.in);
    public static LocalDate scanLocalDate(String text) {
        LocalDate since = null;
        System.out.print(text + ": ");;
        while (scanner.hasNext()) {
            try {
                since = LocalDate.parse(scanner.nextLine());
                break;
            } catch (DateTimeException e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");;
            }
        }
        return since;
    }
    public static Double scanDouble(String text) {
        Double value = 0D;
        System.out.print(text + ": ");;
        while (scanner.hasNext()) {
            try {
                value = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");;
            } finally {
                scanner.nextLine();
            }
        }
        return value;
    }
    public static Integer scanInteger(String text) {
        int value = 0;
        System.out.print(text + ": ");;
        while (scanner.hasNext()) {
            try {
                value = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");
            } finally {
                scanner.nextLine();
            }
        }
        return value;
    }
    public static String[] scanMultipleStrings(String text) {
        String[] str = new String[0];
        System.out.print(text + ": ");;
        while (scanner.hasNext()) {
            try {
                str = scanner.nextLine().replaceAll(" ", "").split(",");
                break;
            } catch (DateTimeException e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");
            }
        }
        return str;
    }
    public static String scanString(String text) {
        String str = "";
        System.out.print(text + ": ");;
        while (scanner.hasNext()) {
            try {
                str = scanner.nextLine();
                break;
            } catch (DateTimeException e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");
            }
        }
        return str;
    }
}
