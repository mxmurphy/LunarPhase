package com.spaceforce.obj;

import com.spaceforce.player.Player;
import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.ui.UserInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NPCTest {

    @Before
    public void init(){
        //initialize the map before the game starts
        GameMap.init();
    }

    /**
     * Test created to test that certain npc will add items to player inventory
     */
    @Test
    public void testReward() {
        //Setting current location to one where npc will give player an item
        GameMap.currentLocation = GameMap.locations.get("ticket box");
        GameMap.currentLocation.initItems();
        GameMap.currentLocation.initNpcs();

        //talking to the salesman without the required item should not add items to inventory. size should still be 0
        UserInterface.useInput("talk salesman");
        assertEquals(0, Player.getInventory().size());

        //Pick up the required item
        UserInterface.useInput("pickup number");

        //after talking to salesman, the player should have a ticket in their inventory, as well as the number they picked up
        UserInterface.useInput("talk SALESMAN");
        assertEquals(2, Player.getInventory().size());
    }
}