package com.spaceforce.obj;


import com.spaceforce.game.Attack;
import com.spaceforce.player.Player;
import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.ui.View;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class NPC implements Interaction {

    public String talkMsg = "Go jump into a black hole!";
    public String lookMsg = "Just your average looking person";
    public String pickMsg = "You sure you want to try picking this person up?";
    public String goMsg = "Where are you trying to make this person go? Not like they are going to listen to you.";
    public String dropMsg = "Like drop them into a black hole? I think not!";
    public String useMsg = "Don;t think using them would be a great idea either";
    public String name = "What's my name?";
    public String attackMsg;
    public String needItem;
    public String specialMsg;
    public String reward;
    public String unlocks;
    public int health = 10;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public void attack() {
        Attack.attackEnemy(this);
    }

    public String getName() {
        return this.name;
    }

    public void talk() {
        //if this npc needs an item, check if the player has the item
        if(needItem != null){
            boolean hasItem = false;
            for(Item item : Player.getInventory()){
                if(needItem.equalsIgnoreCase(item.getName())){
                    hasItem = true;
                    break;
                }
            }

            //if the player has the required item, they can proceed to the next location
            if(hasItem){
                //once the correct item is obtained and the npc has been spoken to, the next location is accessible.
                for(Location location : GameMap.locations.values()){
                    if(unlocks != null && unlocks.equalsIgnoreCase(location.getName())){
                        location.isAccessible = true;
                    }
                }

                //if the npc gives a reward, add it to inventory
                if(reward != null){
                    Player.addItem(reward);
                }

                //finally, display their special message
                View.renderText(specialMsg);
            }else{
                View.renderText(talkMsg);
            }
        }else{
            View.renderText(talkMsg);
        }
    }


    public void look() {
        View.renderText(lookMsg);
    }


    public void pickup() {
        View.renderText(pickMsg);
    }


    public void go() {
        View.renderText(goMsg);
    }


    public void use() {
        View.renderText(useMsg);
    }


    public void drop() {
        View.renderText(dropMsg);
    }
}
