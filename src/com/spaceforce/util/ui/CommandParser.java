package com.spaceforce.util.ui;

import com.spaceforce.obj.Interaction;
import com.spaceforce.obj.Item;
import com.spaceforce.obj.Location;
import com.spaceforce.obj.NPC;
import com.spaceforce.player.Player;
import com.spaceforce.util.fileParsing.GameMap;
import com.spaceforce.util.fileParsing.JsonImporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.spaceforce.util.fileParsing.JsonImporter.objectMapper;
import static java.lang.ClassLoader.getSystemResourceAsStream;

public class CommandParser {
    private static BufferedReader garbageFeed;

    private CommandParser() {
    }

    static public Interaction getTarget(String request) {
        List<Item> inventoryTargets = Player.getInventory();
        request = request.toUpperCase();
        if (inventoryTargets != null) {
            for (Item target : inventoryTargets) {
                if (request.contains(target.name.toUpperCase())) {
                    return target;
                }
            }
        }
        Item[] locationItemTargets = GameMap.currentLocation.items;
        if (locationItemTargets != null) {
            for (Item target : locationItemTargets) {
                if (request.contains(target.name.toUpperCase())) {
                    return target;
                }
            }
        }
        NPC[] locationNPCTargets = GameMap.currentLocation.npcs;
        if (locationNPCTargets != null) {
            for (NPC target : locationNPCTargets) {
                if (request.contains(target.name.toUpperCase())) {
                    return target;
                }
            }
        }
        for (String location : GameMap.locations.keySet()) {
            if (request.contains(location.toUpperCase())) {
                return GameMap.locations.get(location);
            }
        }

        return null; //null if nothing was found. bad target provided
    }

    static public String getAction(String request) {
        List<String> validVerbs = new ArrayList<>();
        validVerbs.add("TALK");
        validVerbs.add("LOOK");
        validVerbs.add("PICKUP");
        validVerbs.add("USE");
        validVerbs.add("GO");
        validVerbs.add("DROP");
        for (String verb : validVerbs) {
            if (request.contains(verb)) {
                return verb;
            }
        }
        return null;
    }


    // static Map actionWords = new HashMap(JsonImporter.parseDictionary());

    // private static StringBuilder listValidItems(){
    // StringBuilder actionTargetWords = new StringBuilder();
    // for (String item : Inventory.items){
    //    actionTargetWords.append(item);
    //        }
    //        for (String item : Map.currentLocation.items){
    //                  actionTargetWords.append(item);
    //        }
    //        return actionTargetWords;
    //    }

    static String parse(String request) {
        //        listValidItems();
        StringBuilder nounBuilder = new StringBuilder();
        String noun;
        try {
            request = findActionPairs(request);
            String[] requests = request.split(" ");
            String verb = requests[0];
            for (int i = 1; i < requests.length; i++) {
                nounBuilder.append(requests[i] + " ");
            }
            noun = String.valueOf(nounBuilder);
            verb = findSynonyms(verb.toLowerCase());
            return (verb.toUpperCase() + " " + noun.trim());

        } catch (Exception e) {
            return request;
        }

        // we have to find and replace synonyms before we filter out articles and prepositions because some action word synonyms contain prepositions (ex: "get a load of...")

    }

    static private String findSynonyms(String request) {
        // "leave" was in both "drop" and "go", it's been removed from the JSON for now
        try {
            // ObjectMapper.readValue takes File src and Class<T> valueType arguments, we want to a Map of our JSON
            // The ObjectMapper is putting the value field into an ArrayList, so we set our map to expect an ArrayList
            InputStreamReader input=new InputStreamReader(getSystemResourceAsStream("JSON/actionWords.json"));
            Map<String, ArrayList<String>> map = objectMapper.readValue(input, Map.class);
            outerLoop:
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) { // for each key-value pair in our new map
                for (String word : entry.getValue()) {   // for each word in the value ArrayList
                    if (request.contains(word)) {    // check to see if our request string contains that word
                        request = request.replaceAll(word, entry.getKey());
                        break outerLoop;// and replace it with the key word
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return request;
    }

    // strip out unnecessary words -> articles (the, a, an), prepositions (to, at, of, between), pronouns
    // anything after an action word and before the next action word should be an actionFocus
    // returns action and actionTarget pairs
    private static String findActionPairs(String request) {
        // consider conjunctions (and, but, so)
        // consider negations (without, not)
        // if you use the word my, can we replace that with inventory?
        InputStreamReader input=new InputStreamReader(getSystemResourceAsStream("garbageWords.txt"));
        garbageFeed = new BufferedReader(input);    // the BufferedReader wraps around the FileReader to make file reads more efficient
        String garbageWord;
        while (true) {
            try {
                while ((garbageWord = garbageFeed.readLine()) != null)
                    request = request.replaceAll("\\b" + garbageWord.toUpperCase() + "\\b", ""); // the regex looks for the garbageWord with word delimeters on either side of it ex: preventing toga from removing to

            } catch (IOException e) {
            }


            return request;
        }
    }
}