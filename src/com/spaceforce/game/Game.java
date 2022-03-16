package com.spaceforce.game;

import com.spaceforce.obj.Item;
import com.spaceforce.player.Player;
import com.spaceforce.player.save.Save;
import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.ui.UserInterface;
import com.spaceforce.util.ui.View;


public class Game {
    private Game(){}

    public static void displayIntro(){
        View.renderText("\n\nThis is a text based adventure where you lost the company spaceship.");
                View.renderText("In order to not lose your job you need to find it before anyone notices it gone!");
                View.renderText("Will you take on the challenge of not getting fired!");
                View.renderText("Welcome to the game.");
//         View.renderImage(new File("Resources/Images/birdLogo.txt"));
         View.renderText("\n\nEnter start to begin.");
    }
    public static void newGame(){
        if(Save.hasSave()){
            Save.loadData();
        } else {
            displayIntro();
            UserInterface.beginInput();
        }
        System.out.println(GameMap.currentLocation.introMsg);
        while(true){
            UserInterface.beginInput();

        }
//         Map.init();
//         Map.goTo(Map.area1);
//         com.spaceforce.util.ui.UserInterface.beginInput();
    }
    public static void help(){
        // put these lines of text into a file for game messages
        View.renderText("These are your commands.");
        View.renderText("Talk, Look, Pickup, Go, Use, Drop");
        View.renderText("You can try to scream but no one will hear you.");
        View.renderText("You can combine these verbs with either location, person, or item.");
        View.renderText("Example: Go spaceport");
        View.renderText("To look at current inventory. Type in \"Inventory\"");
    }

    public static void save(){
        // View.renderText("Game Saved");
        // write to save file current area, all area objects alive in Map, and current inventory
        Save.saveData();
    }
    public static boolean load(){
        if(Save.hasSave()){
            Save.loadData();
            return true;
        } else return false;
    }
    public static void exit(){
        System.exit(0);
    }
    public static void grabItem(Item selectedItem){
        for(Item item : GameMap.currentLocation.items){
            if(item.equals(selectedItem)){
                Player.addItem(item);
                item = null;
            }
        }
    }
}
