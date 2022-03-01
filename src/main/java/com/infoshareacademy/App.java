package com.infoshareacademy;

import com.infoshareacademy.presentationlayer.filtration.FiltrationUI;
import com.infoshareacademy.services.NBPApiManager;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        FiltrationUI filtrationUI = new FiltrationUI();
        filtrationUI.filtrationMenu(nbpApiManager);
    }
}
