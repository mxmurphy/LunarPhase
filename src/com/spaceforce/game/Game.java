package com.spaceforce.game;

import com.spaceforce.obj.Item;
import com.spaceforce.player.Player;
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

    /**
     * Prints the story "cutscenes" until it reaches the blank line signalling the end of the "cutscene".
     * If the game has just been initialized it prints the splash screen.
     * @throws IOException
     */
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

    /**
     * initializes the game and asks for the player to input their name.
     * @throws FileNotFoundException
     */
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
        View.renderText(GameMap.currentLocation.getIntroMsg());
        View.renderText(GameMap.currentLocation.getDescription());
        while (true) {
            UserInterface.beginInput();

        }
    }

    /**
     * Gives list of commands. Method is called when player inputs "help"
     */
    public static void help() {
        // put these lines of text into a file for game messages
        View.renderText("These are your commands.");
        View.renderText("Talk, Look, Pickup, Go, Use, Drop");
        View.renderText("You can try to scream but no one will hear you.");
        View.renderText("You can combine these verbs with either location, person, or item.");
        View.renderText("Example: Go spaceport");
        View.renderText("To look at current inventory. Type in \"Inventory\"");
    }

    /**
     * exits program. Is called when player inputs "exit".
     */
    public static void exit() {
        System.exit(0);
    }

    /**
     * takes item from location and places it in the players inventory.
     * @param selectedItem
     */
    public static void grabItem(Item selectedItem) {
        for (Item item : GameMap.currentLocation.getItems()) {
            if (item.equals(selectedItem)) {
                Player.addItem(item);
                item = null;
            }
        }
    }
}
