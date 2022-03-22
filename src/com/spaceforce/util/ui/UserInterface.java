package com.spaceforce.util.ui;

import com.spaceforce.Sound.Sounds;
import com.spaceforce.game.Game;
import com.spaceforce.obj.Interaction;
import com.spaceforce.obj.Item;
import com.spaceforce.player.Player;
import com.spaceforce.util.fileParsing.GameMap;

import java.util.Scanner;

public class UserInterface {

    // consider System.console().readLine() to take user input - it doesn't echo the input which may be good for style's sake
    public static Scanner userInput = new Scanner(System.in);
    public static Interaction requestTarget = null;
    public static String requestAction = null;

    private UserInterface() {
    }

    /**
     * Uses scanner to take a user input
     */
    public static void beginInput() {
        String userRequest;
        do {
            userRequest = userInput.nextLine().toUpperCase(); // in what case can userRequest be null? what happens if it's an empty string?
        } while (!GameMap.isInitialized() && !userRequest.equalsIgnoreCase("start"));

        //Game map will need to be initialized first, otherwise runtime exception occurs
        useInput(userRequest);
    }

    /**
     * takes the user input and checks for valid commands
     * @param userRequest User input
     */
    public static void useInput(String userRequest){
        requestTarget=null;
        requestAction=null;
        switch (userRequest) {
            case "START":
                GameMap.init();
                break;
            case "HELP":
                Game.help();
                break;
            case "INVENTORY":
                View.renderText(Player.getInventory().toString());
                break;
            case "LOOK":
                GameMap.currentLocation.look();
                break;
            case "TALK":
                GameMap.currentLocation.talk();
                break;

            case "MUSIC":
                if (Sounds.playing){
                    Sounds.MUSIC.stopMusic();
                } else {
                    Sounds.MUSIC.playMusic();
                }
                break;
            case "SOUND":
                if(Sounds.sound){
                    View.renderText("Turning soundFX off.\n");
                    Sounds.sound=false;
                }else{
                    View.renderText("Turning SoundFX on.\n");
                    Sounds.sound=true;
                }
                break;
            case "EXIT": {
                Game.exit();
                return; // if the switch doesn't have a return somewhere the ide complains, probably because of the infinite loop.
            }
            default: {
                userRequest = CommandParser.parse(userRequest);
                if (CommandParser.getTarget(userRequest) != null) {
                    requestTarget = CommandParser.getTarget(userRequest);
                }
                if (CommandParser.getAction(userRequest) != null) {
                    requestAction = CommandParser.getAction(userRequest);
                }
                if (requestAction != null && requestTarget != null) {
                    if (requestAction.equals("PICKUP") && requestTarget.getClass().getSimpleName().equalsIgnoreCase("Item")) {
//                        GameMap.currentLocation.items
                        Game.grabItem((Item) requestTarget);
                        requestTarget.pickup();
                    } else if (requestAction.equals("DROP") && Player.checkInventory((Item) requestTarget)) {
                        Player.removeItem((Item) requestTarget);
                    } else if (requestAction.equals("GO")) { //&& GameMap.currentLocation.checkExit(((Location) requestTarget).name)
                        //boolean validLocation = false;
                        GameMap.currentLocation.go();

                    } else if (requestAction.equals("USE")) {
                        for (Item item : Player.getInventory()) {
                            if (requestTarget.getName().equalsIgnoreCase(item.getName())) {
                                item.use();
                            }
                        }
                    } else if (requestAction.equals("TALK")) {
                        for (var npc : GameMap.currentLocation.getNpcs()) {
                            if (requestTarget.getName().equalsIgnoreCase(npc.getName())) {
                                npc.talk();
                            }
                        }
                    } else if (requestAction.equalsIgnoreCase("LOOK")) {
                        if (requestTarget.getClass().getSimpleName().equalsIgnoreCase("Item")) {
                            for (Item item : Player.getInventory()) {
                                if (requestTarget.getName().equalsIgnoreCase(item.getName())) {
                                    item.look();
                                }
                            }
                            if (GameMap.currentLocation.findItem(requestTarget.getName()) != null) {
                                GameMap.currentLocation.findItem(requestTarget.getName()).look();
                            }
                        } else if (requestTarget.getClass().getSimpleName().equalsIgnoreCase("NPC")) {
                            if (GameMap.currentLocation.findNpc(requestTarget.getName()) != null) {
                                GameMap.currentLocation.findNpc(requestTarget.getName()).look();
                            }
                        } else {
                            if(GameMap.currentLocation.equals(requestTarget)){
                                requestTarget.interact(requestAction);
                            }else{
                                View.renderText("I can't see the " + requestTarget.getName() + " from here.");
                            }
                        }

                    }else if(requestAction.equalsIgnoreCase("ATTACK")){
                        if(GameMap.currentLocation.findNpc(requestTarget.getName())!=null){
                            GameMap.currentLocation.findNpc(requestTarget.getName()).attack();
                        }
                    }


                    }else if (requestAction != null) {
                    //This can be refactored into using a .txt file instead. hardcoding to make sure it works first
                    String message;
                    switch (requestAction) {
                        case "PICKUP":
                            message = "I can't pick that up.";
                            break;
                        case "DROP":
                            message = "Can't drop something I don't have.";
                            break;
                        case "GO":
                            message = "I can't go there right now.";
                            break;
                        case "USE":
                            message = "Can't use something I don't have.";
                            break;
                        case "TALK":
                            message = "Talk to who??";
                            break;
                        case"ATACK":
                            message="You swing wildly at the air. You look absolutely ridiculous.";
                            break;
                        default:
                            message = "Are you speaking another language? I don't know what that is";
                    }
                    message += "\n If you're confused, you can try typing help to see what you can do.";
                    View.renderText(message);
                } else {
                    View.renderText("I don't know how to do that.");
                }
            }
        }
    }

}