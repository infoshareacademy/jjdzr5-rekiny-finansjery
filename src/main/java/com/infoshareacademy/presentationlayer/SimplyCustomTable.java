package com.infoshareacademy.presentationlayer;

import java.util.ArrayList;
import java.util.List;

public class SimplyCustomTable {
    private final Character horizontal = 0x2500;
    private final Character vertical = 0x2502;
    private final Character cornerTopLeft = 0x250C;
    private final Character cornerTopRight = 0x2510;
    private final Character cornerBottomLeft = 0x2514;
    private final Character cornerBottomRight = 0x2518;
    private final Character verticalRight = 0x251C;
    private final Character verticalLeft = 0x2524;
    private final Character horizontalVertical = 0x253C;
    private final Character horizontalTop = 0x2534;
    private final Character horizontalBottom = 0x252C;

    private String[] topics;
    private List<String>[] columns;
    public SimplyCustomTable(int columns){
        topics = new String[columns];
        this.columns = new ArrayList[columns];
        for(int i=0; i<this.columns.length; i++){
            this.columns[i] = new ArrayList<String>();
        }
    }

    public SimplyCustomTable setTopics(String... row){
        for(int i=0; i<topics.length; i++){
            topics[i]=row[i];
        }
        return this;
    }

    public SimplyCustomTable addRow(String... row){
        for(int i=0; i<columns.length; i++){
            columns[i].add(row[i]);
        }
        return this;
    }

    public void displayMenu(){
        int[] columnsSizes = getColumsSizes();
        String tableFormat = getFormat(vertical, columnsSizes);

        System.out.format(getSeparationLine(cornerTopLeft, horizontalBottom, cornerTopRight, horizontal, columnsSizes));
        System.out.format(tableFormat, topics);
        System.out.format(getSeparationLine(verticalRight, horizontalVertical, verticalLeft, horizontal, columnsSizes));
        for(int i=0; i<columns[0].size(); i++){
            String[] row = new String[columns.length];
            for(int j=0; j<columns.length; j++){
                row[j] = columns[j].get(i);
            }
            System.out.format(tableFormat, row);
        }
        System.out.format(getSeparationLine(cornerBottomLeft, horizontalTop, cornerBottomRight, horizontal, columnsSizes));
    }

    private int[] getColumsSizes(){
        int[] columnsSizes = new int[columns.length];
        for(int i=0; i<columns.length; i++){
            columnsSizes[i] = getColumnLength(i);
        }
        return columnsSizes;
    }

    private static String getFormat(Character vertical, int[] columnsSizes){
        String tableFormat = vertical.toString();
        for(int i=0; i<columnsSizes.length; i++){
            tableFormat += " %-" + columnsSizes[i] + "s ";
            tableFormat += vertical;
        }
        return tableFormat + "%n";
    }

    private static String getSeparationLine(Character left, Character center, Character right, Character horizontal, int[] columnsSizes){
        String row = left.toString();
        for(int i=0; i<columnsSizes.length; i++){
            if(i!=0) row += center;
            row += horizontal.toString().repeat(columnsSizes[i]+2);
        }
        return row + right.toString() + "%n";
    }

    private int getColumnLength(int i){
        return columns[i].
                stream().
                max((s1, s2)->((Integer)s1.length()).compareTo(s2.length())).
                get().
                length();
    }
}
