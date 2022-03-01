package com.infoshareacademy.presentationlayer;

import java.util.ArrayList;
import java.util.List;

public class BetterMenu {

    private final List<MenuOption> options;
    public BetterMenu(){
        options = new ArrayList<>();
    }

    public void displayMenu(){
        SimpleCustomTable menuTable = new SimpleCustomTable(2);
        menuTable.setTopics("ID", "Menu Options");
        for(Integer i = 0; i < options.size(); i++){
            menuTable.addRow(i.toString(), options.get(i).getDescription());
        }
        menuTable.displayTable();
    }

    public void executeSelectedOption(int index){
        options.get(index).getMethod().run();
    }

    public void addMenuOption(MenuOption option){
        options.add(option);
    }

    public int getMenuSize(){
        return options.size();
    }

    public static class MenuOption {
        private String description;
        private Runnable method;

        public MenuOption setDescription(String description) {
            this.description = description;
            return this;
        }

        public MenuOption setMethod(Runnable method) {
            this.method = method;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public Runnable getMethod() {
            return method;
        }
    }

}

