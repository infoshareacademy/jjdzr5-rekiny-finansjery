package com.infoshareacademy;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Rekiny Finansjery" );

        NBPApiManager nbpApiManager = new NBPApiManager();

        UsageExamplesCode.showExamples(nbpApiManager);
    }
}
