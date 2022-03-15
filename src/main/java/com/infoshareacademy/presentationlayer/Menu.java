package com.infoshareacademy.presentationlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Menu { //todo get rid ot table being displayed after the menu is exited

    private final List<MenuOption> options;
    public Menu(){
        options = new ArrayList<>();
    }

    public void displayMenu(){
        displayMenu(options);
    }

    public void displayMenu(List<MenuOption> optionList){
        SimpleCustomTable menuTable = new SimpleCustomTable(2);
        menuTable.setTopics("ID", "Menu Options");
        for(int i = 0; i < optionList.size(); i++){
            menuTable.addRow(String.valueOf(i), optionList.get(i).getDescription());
        }
        menuTable.displayTable();
    }

    public void displayMenuWithReturnAndExecute(String returnText, Runnable codeAtBeginingOfLoop){
        AtomicBoolean stayInLoop = new AtomicBoolean(true);
        List<MenuOption> tempOptions = new ArrayList<>(options);
        tempOptions.add(0, new Menu.MenuOption().
                setDescription(returnText).
                setMethod(()->{
                    stayInLoop.set(false);
                }));

        while(stayInLoop.get()){
            codeAtBeginingOfLoop.run();
            displayMenu(tempOptions);
            executeSelectedOption(ValuesScanner.scanIntegerInRange("Enter id of option", 0, tempOptions.size()), tempOptions);
        }
    }

    public void executeSelectedOption(int index){
        options.get(index).getMethod().run();
    }

    public void executeSelectedOption(int index, List<MenuOption> optionList){ optionList.get(index).getMethod().run(); }

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

