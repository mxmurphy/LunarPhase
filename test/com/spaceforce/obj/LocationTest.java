package com.spaceforce.obj;

import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.ui.UserInterface;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class LocationTest {
    private Map<String,Location> locations;

    @Before
    public void init(){
        //initialize the map, and store the locations locally for readability
        GameMap.init();
        locations = GameMap.locations;
    }

    /**
     * Testing the go method in location.
     */
    @Test
    public void go() {
        //store in local variable for readability during tests
        Location office = locations.get("office");
        Location garage = locations.get("garage");
        Location shuttle = locations.get("shuttle");
        Location moon = locations.get("moon base");

        //starting in office
        assertEquals(office, GameMap.currentLocation);

        //can go to a new location
        UserInterface.useInput("go garage");
        assertEquals(garage, GameMap.currentLocation);

        //stay in the same location after inaccessible one provided
        UserInterface.useInput("go mars base");
        assertEquals(garage, GameMap.currentLocation);

        //can go back to previous location
        UserInterface.useInput("go office");
        assertEquals(office, GameMap.currentLocation);

        //can get to shuttle area without issue
        UserInterface.useInput("go garage");
        UserInterface.useInput("go shuttle");
        assertEquals(shuttle, GameMap.currentLocation);

        //should still be in shuttle, next area isn't available
        assertFalse(moon.isAccessible);
        UserInterface.useInput("go moon base");
        assertEquals(shuttle, GameMap.currentLocation);

        //prerequisites to unlock moon base
        UserInterface.useInput("go garage");
        UserInterface.useInput("go office");
        UserInterface.useInput("pickup towel");
        UserInterface.useInput("go garage");
        UserInterface.useInput("go shuttle");
        UserInterface.useInput("talk pilot");

        //can not move to moon base, since that will require input from user, but it should now be accessible
        assertTrue(moon.isAccessible);
    }
}