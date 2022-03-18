package com.spaceforce.game;

import com.spaceforce.obj.Item;
import com.spaceforce.player.Player;
import com.spaceforce.player.save.Save;
import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.ui.UserInterface;
import com.spaceforce.util.ui.View;

import java.io.*;

import static com.spaceforce.util.ui.UserInterface.userInput;


public class Game {
    private static BufferedReader br;
    private static String line;
    private Game(){}
    private static boolean splash=true;
    public static void displayStory() throws IOException {
        View.renderText("\n\n");
        while ((line = br.readLine()) != null) {
            if(!(line.trim().length()==0)){
                View.renderText(line);
            }else{
                View.renderText("\n");
                break;
            }
        }
        View.renderText("\nPress Enter to continue.");
        userInput.nextLine();
        if(splash){
            View.renderImage(new File("Resources/Images/birdLogo.txt"));
            splash=false;
            View.renderText("Type 'START' to begin.");
        }
    }
    public static void newGame() throws FileNotFoundException {
        br = new BufferedReader(new FileReader("Resources/story.txt"));

        if(Save.hasSave()){
            Save.loadData();
        } else {
            try {
                displayStory();
            } catch (IOException e) {
            }
            UserInterface.beginInput();
        }
        View.renderText(GameMap.currentLocation.introMsg);
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
