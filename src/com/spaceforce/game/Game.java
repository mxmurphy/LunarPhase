package com.spaceforce.game;

import com.spaceforce.obj.Item;
import com.spaceforce.player.Player;
import com.spaceforce.player.save.Save;
import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.ui.UserInterface;
import com.spaceforce.util.ui.View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.spaceforce.util.ui.UserInterface.userInput;
import static java.lang.ClassLoader.getSystemResourceAsStream;


public class Game {
    private static BufferedReader br;
    private static String line;
    private static String playerName = null;
    private static boolean splash = true;

    private Game() {
    }

    public static void displayStory() throws IOException {
        View.renderText("\n\n");
        while ((line = br.readLine()) != null) {
            if (!(line.trim().length() == 0)) {
                View.renderText(line);
            } else {
                View.renderText("\n");
                break;
            }
        }
        View.renderText("\nPress Enter to continue.");
        userInput.nextLine();

        //get player name
        InputStreamReader input = new InputStreamReader(getSystemResourceAsStream("Images/birdLogo.txt"));
        if (splash) {
            View.renderImage(input);
            splash = false;
            View.renderText("Type 'START' to begin.");
        }

    }

    public static void newGame() throws FileNotFoundException {
        br = new BufferedReader(new InputStreamReader(getSystemResourceAsStream("story.txt")));

        try {
            if (playerName == null) {
                View.renderText("\nPlease enter your name.");
                String playerName = userInput.nextLine();
                Player.newPlayer(playerName);
                System.out.println("Welcome, " + playerName + ", to LUNAR CYCLE!");
                View.renderText("\nPress Enter to continue.");
                userInput.nextLine();
            }
            displayStory();
        } catch (IOException e) {
        }
        UserInterface.beginInput();

        View.renderText("\n");
        help();
        View.renderText("\n");
        View.renderText(GameMap.currentLocation.introMsg);
        View.renderText(GameMap.currentLocation.description);
        while (true) {
            UserInterface.beginInput();

        }
    }

    public static void help() {
        // put these lines of text into a file for game messages
        View.renderText("These are your commands.");
        View.renderText("Talk, Look, Pickup, Go, Use, Drop");
        View.renderText("You can try to scream but no one will hear you.");
        View.renderText("You can combine these verbs with either location, person, or item.");
        View.renderText("Example: Go spaceport");
        View.renderText("To look at current inventory. Type in \"Inventory\"");
    }

    public static void save() {
        // View.renderText("Game Saved");
        // write to save file current area, all area objects alive in Map, and current inventory
        Save.saveData();
    }

    public static boolean load() {
        if (Save.hasSave()) {
            Save.loadData();
            return true;
        } else return false;
    }

    public static void exit() {
        System.exit(0);
    }

    public static void grabItem(Item selectedItem) {
        for (Item item : GameMap.currentLocation.items) {
            if (item.equals(selectedItem)) {
                Player.addItem(item);
                item = null;
            }
        }
    }
}
