package com.infoshareacademy;

import com.infoshareacademy.configuration.PropertiesLoader;
import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.presentationlayer.MenuContents;
import com.infoshareacademy.presentationlayer.ValuesScanner;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class App
{
    public static void main( String[] args ) throws IOException {

        MenuContents menuContents = new MenuContents();
        menuContents.loadMenu();

        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        UsageExamplesCode.showExamples(nbpApiManager);
    }
}
