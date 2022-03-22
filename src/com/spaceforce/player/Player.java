package com.spaceforce.player;

import com.spaceforce.obj.Item;
import com.spaceforce.util.fileParsing.JsonImporter;
import com.spaceforce.util.ui.View;

import java.util.ArrayList;
import java.util.List;

final public class Player {
    public static Player player = null;
    private static String name="Zack";
    //private Item[] inventory = new Item[10];
    private static List<Item> inventory = new ArrayList<>(10);
    private static int health = 10;

    public Player(String playerName) {
        this.name=playerName;
    }
    public static Player newPlayer(String playerName){
        if(player==null){
            player=new Player(playerName);
        }
        return player;
    }
    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Player.name = name;
    }

    static public List<Item> getInventory() {
        return inventory;
    }

    public static void setInventory(List<Item> inventory) {
        Player.inventory = inventory;
    }

    public static int getHealth() {
        return Player.health;
    }

    public static void setHealth(int health) {
        Player.health += health;
    }

    public static boolean checkInventory(Item item) {
        View.renderText("");
        return true;
    }

    public static void addItem(Item item) {
        //item = null;
        // for (int i = 0; i < inventory.size(); i++){
        inventory.add(item);//check if spot is null
        //}

    }

    /**
     * Add an item to the player inventory given the item name.
     * @param itemName the name of the item.
     */
    public static void addItem(String itemName){
        for(Item item : JsonImporter.parseAllItems()){
            if(itemName.equalsIgnoreCase(item.getName())){
                inventory.add(item);
                break;
            }
        }
    }

    public static List<Item> removeItem(Item item) {
        inventory.remove(item);
        return inventory;//
    }
}

