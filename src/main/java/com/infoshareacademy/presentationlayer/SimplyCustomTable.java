package com.infoshareacademy.presentationlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private int columns;
    private String[] topics;
    private List<String[]> rows;
    public SimplyCustomTable(int columns){
        this.columns = columns;
        topics = new String[columns];
        for(int i=0; i<columns; i++){
            topics[i] = "";
        }
        this.rows = new ArrayList<>();
    }

    public SimplyCustomTable setTopics(String... row){
        if(columns == row.length) {
            for (int i = 0; i < topics.length; i++) {
                topics[i] = row[i];
            }
        }
        return this;
    }

    public SimplyCustomTable addRow(String... row){
        if(columns == row.length){
            rows.add(row);
        }
        return this;
    }

    public void displayMenu(){
        int[] columnsSizes = getColumsSizes();
        String tableFormat = getFormat(vertical, columnsSizes);

        System.out.format(getSeparationLine(cornerTopLeft, horizontalBottom, cornerTopRight, horizontal, columnsSizes));
        System.out.format(tableFormat, topics);
        System.out.format(getSeparationLine(verticalRight, horizontalVertical, verticalLeft, horizontal, columnsSizes));
        for(String[] row : rows){
            System.out.format(tableFormat, row);
        }
        System.out.format(getSeparationLine(cornerBottomLeft, horizontalTop, cornerBottomRight, horizontal, columnsSizes));
    }

    private int[] getColumsSizes(){
        int[] columnsSizes = new int[columns];
        for(int i=0; i<columns; i++){
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
        Optional<String[]> row =  rows.
                stream().
                max((a1, a2)->((Integer)a1[i].length()).compareTo(a2[i].length()));

        if(row.isPresent()){
            return Math.max(row.get()[i].length(), topics[i].length());
        }else{
            return topics[i].length();
        }
    }
}
