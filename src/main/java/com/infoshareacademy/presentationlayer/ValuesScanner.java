package com.infoshareacademy.presentationlayer;

import com.infoshareacademy.configuration.PropertiesLoader;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class ValuesScanner {

    public static final Scanner scanner = new Scanner(System.in);

    public static LocalDate scanLocalDate(String text) {
        LocalDate since;
        while (true) {
            System.out.print(text + ": ");
            try {
                since = LocalDate.parse(scanner.nextLine(),
                        DateTimeFormatter
                                .ofPattern(PropertiesLoader.getInstance().returnDateFormat())
                );
                break;
            } catch (DateTimeException e) {
                System.out.println("Incorrect date value.");
            }
        }
        return since;
    }

    public static Double scanDouble(String text) {
        Double value;
        while (true) {
            System.out.print(text + ": ");
            try {
                value = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect date value.");
            } finally {
                scanner.nextLine();
            }
        }
        return value;
    }

    public static Integer scanInteger(String text) {
        int value;
        while (true) {
            System.out.print(text + ": ");
            try {
                value = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect date value.");
            } finally {
                scanner.nextLine();
            }
        }
        return value;
    }

    public static Integer scanIntegerInRange(String text, int min, int max) {
        int value;
        while (true) {
            System.out.print(text + ": ");
            try {
                value = scanner.nextInt();
                if (value < min || value >= max) {
                    throw new IndexOutOfBoundsException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect date value.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No such option.");
            } finally {
                scanner.nextLine();
            }
        }
        return value;
    }

    public static String[] scanMultipleStrings(String text) {
        String[] str;
        while (true) {
            System.out.print(text + ": ");
            try {
                str = scanner.nextLine().replaceAll(" ", "").split(",");
                break;
            } catch (Exception e) {
                System.out.println("Incorrect date value.");
            }
        }
        return str;
    }

    public static String scanString(String text) {
        String str;
        while (true) {
            System.out.print(text + ": ");
            try {
                str = scanner.nextLine();
                return str;
            } catch (Exception e) {
                System.out.println("Incorrect value.");
            }
        }
    }

    public static String scanStringWithLength(String text) {
        String str;
        while (true) {
            str = scanString(text);
            if (str.length() >= 3) {
                return str;
            } else {
                System.out.println("Searched phrase is too short.");
            }
        }
    }

    public static Optional<Month> scanMonth(String text) {
        String str = "";
        while (true) {
            System.out.print(text + ": ");
            try {
                str = scanner.nextLine();
                return Optional.of(Month.of(Integer.parseInt(str)));
            } catch (NumberFormatException formatException) {
                try {
                    return str.equals("-") ? Optional.empty() : Optional.of(Month.valueOf(str.toUpperCase()));
                } catch (IllegalArgumentException argumentException) {
                    System.out.println("Incorrect month.");
                }
            } catch (DateTimeException dateTimeException) {
                System.out.println("Month out of range.");
            }
        }
    }

}
