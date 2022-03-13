package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.configuration.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ValuesScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValuesScanner.class);

    public static final Scanner scanner = new Scanner(System.in);
    public static LocalDate scanLocalDate(String text) {
        LocalDate since;
        System.out.print(text + ": ");
        while (true) {
            try {
                since = LocalDate.parse(scanner.nextLine(),
                            DateTimeFormatter
                            .ofPattern(PropertiesLoader.getInstance().returnDateFormat())
                        );
                break;
            } catch (DateTimeException e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");
            }
        }
        return since;
    }
    public static Double scanDouble(String text) {
        Double value;
        System.out.print(text + ": ");
        while (true) {
            try {
                value = scanner.nextDouble();
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
    public static Integer scanInteger(String text) {
        int value;
        System.out.print(text + ": ");
        while (true) {
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
    public static Integer scanIntegerInRange(String text, int min, int max) {
        int value;
        System.out.print(text + ": ");
        while (true) {
            try {
                value = scanner.nextInt();
                if(value < min || value >= max){
                    throw new IndexOutOfBoundsException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");
            } catch (IndexOutOfBoundsException e){
                System.out.println("No such option.");
                System.out.print(text + ": ");
            }
            finally {
                scanner.nextLine();
            }
        }
        return value;
    }
    public static String[] scanMultipleStrings(String text) {
        String[] str;
        System.out.print(text + ": ");
        while (true) {
            try {
                str = scanner.nextLine().replaceAll(" ", "").split(",");
                break;
            } catch (Exception e) {
                System.out.println("Incorrect date value.");

                System.out.print(text + ": ");
            }
        }
        return str;
    }
    public static String scanString(String text) {
        String str;
        System.out.print(text + ": ");
        while (true) {
            try {
                str = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Incorrect date value.");
                System.out.print(text + ": ");
            }
        }
        return str;
    }
}
