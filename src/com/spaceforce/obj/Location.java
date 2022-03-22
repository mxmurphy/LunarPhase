package com.spaceforce.obj;

import com.spaceforce.Sound.Sounds;
import com.spaceforce.game.Attack;
import com.spaceforce.player.Player;
import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.fileParsing.JsonImporter;
import com.spaceforce.util.ui.UserInterface;
import com.spaceforce.util.ui.View;

import java.io.IOException;

import static com.spaceforce.game.Game.displayStory;
import static com.spaceforce.util.fileParsing.GameMap.currentLocation;
import static com.spaceforce.util.ui.UserInterface.requestTarget;
import static com.spaceforce.util.ui.UserInterface.userInput;

public class Location implements Interaction {
    public String lookMsg="look";
    public String name;
    public int[] itemIds;
    public int[] npcIds;
    public String description = "room";
    public String talkMsg = "You better stop talking to the wall before you get too many weird looks.";
    public String introMsg = "It stinky in here :(";
    public String pickupMsg = "You tried to lift " + name + "but it went as expected";
    public String goMsg = "You're already here";
    public String useMsg = "This isn't a bathroom!";
    public String dropMsg = "Try picking this up before you drop it";
    public Item[] items;
    public NPC[] npcs;
    public String[] exits; //name of valid locations to move to
    public boolean isAccessible;

    private Location() {
    }

    /**
     *     initializes items from id array
     */
    public void initItems() {
        if (itemIds != null) {
            items = new Item[itemIds.length];
            try {
                for (int i = 0; i < itemIds.length; i++) {
                    items[i] = JsonImporter.parseItem(itemIds[i]);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Already initialized");
        }
    }

    /**
     * initializes NPCS from id array
     */
    public void initNpcs() {
        if (npcIds != null) {
            npcs = new NPC[npcIds.length];
            try {
                for (int i = 0; i < npcIds.length; i++) {
                    npcs[i] = JsonImporter.parseNpc(itemIds[i]);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            View.renderText("Already initialized.");
        }
    }

    /**
     * Gets and returns the NPC in the location the player is located.
     * @return
     */
    public NPC getNPC(){
        currentLocation.initNpcs();
        return npcs[0];
    }

    /**
     * Search items array for matching name
     * @param noun The player target of the item
     * @return the item from your inventory or null if it does not exist
     */
    public Item findItem(String noun) {
        for (Item item : items) {
            if (noun.equalsIgnoreCase(item.name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Search location for an NPC
     * @param noun the player target of NPCs
     * @return the NPC in the current location or null if none exist.
     */
    public NPC findNpc(String noun) {
        for (NPC npc : npcs) {
            if (noun.equalsIgnoreCase(npc.name)) {
                return npc;
            }
        }
        return null;
    }

    public boolean checkExit(String exit) {
        // if the exit provided is an exit for this location && the user has the required item to go there (for now just that the exit is valid)
        for(String loc : exits){
            if(loc.equalsIgnoreCase(exit) && GameMap.locations.get(exit).isAccessible){
                return true;
            }
        }
        return false;
    }

    @Override
    public void talk() {


        if(currentLocation.getName().equalsIgnoreCase("MARS PARKING")){
            talkMsg=talkMsg.replace("name", Player.getName());
            try {
                View.renderText(talkMsg);
                View.renderText("Press enter to continue.");
                userInput.nextLine();
                displayStory();
            } catch (IOException e) {
            }
        }else{
            View.renderText(talkMsg);
        }
    }


    @Override
    public void look() {
        View.renderText(lookMsg);
    }

    @Override
    public void pickup() {
        View.renderText(pickupMsg);
    }

    /**
     * Player moves locations
     * Checks to see if the location advances the story
     */
    @Override
    public void go() {
        if(checkExit(requestTarget.getName())){
            View.renderText("Going to " + requestTarget.getName()+"\n");
            currentLocation = (Location) UserInterface.requestTarget;
            if(currentLocation.name.equalsIgnoreCase("MOON BASE")||currentLocation.name.equalsIgnoreCase("MARS BASE")){
                try {
                    displayStory();
                } catch (IOException e) {
                }
            }
            if(currentLocation.name.equalsIgnoreCase("MARS PARKING")){
                View.renderText(currentLocation.introMsg);
                View.renderText("\nThe CREEPER has taken up residence in your spaceship--and he runs out and to ATTACK you.\n");
                Attack.attackEnemy(currentLocation.getNPC());
            }else{
                View.renderText(currentLocation.introMsg);

            }
            Sounds.DOOR.playSFX();
            View.renderText("\n" + currentLocation.description+"\n" + currentLocation.lookMsg+"\n");
            currentLocation.initNpcs();
            currentLocation.initItems();
        }else if(currentLocation.equals(requestTarget)){
            View.renderText("I'm already here.");
        }else{
            View.renderText("I can't go there right now.");
        }



    }

    @Override
    public void use() {
        View.renderText(useMsg);
    }

    @Override
    public void drop() {
        View.renderText(dropMsg);
    }

    /**
     * Generated equals method using name and description to compare two locations
     * @param o the object to be compared to
     * @return true if locations are the same, false if they are different
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (name != null ? !name.equals(location.name) : location.name != null) return false;
        return description != null ? description.equals(location.description) : location.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public String getName(){
        return name;
    }
}
