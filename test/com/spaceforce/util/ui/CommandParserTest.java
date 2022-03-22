package com.spaceforce.util.ui;

import org.junit.Assert;
import org.junit.Test;

class CommandParserTest {
    @Test
    public void parseTest(){
        String result=CommandParser.parse("take towel");
        Assert.assertEquals("PICKUP towel", result);
        result=CommandParser.parse("strike fred");
        Assert.assertEquals("ATTACK fred", result);
        result=CommandParser.parse("move garage");
        Assert.assertEquals("GO garage", result);
        result=CommandParser.parse("asdf asdf");
        Assert.assertEquals("ASDF asdf",result);

    }
    @Test
    public void findActionPairsTest(){
        String result=CommandParser.findActionPairs("MOVE TO THE GARAGE");
        Assert.assertEquals("MOVE GARAGE", result);
        result=CommandParser.findActionPairs("TALK TO FRED");
        Assert.assertEquals("TALK FRED", result);
        result=CommandParser.findActionPairs("ASDF TO FRED");
        Assert.assertEquals("ASDF FRED",result);
    }
    @Test
    public void findSynonymsTest(){
        String result=CommandParser.findSynonyms("move");
        Assert.assertEquals("go", result);
        result=CommandParser.findSynonyms("say");
        Assert.assertEquals("talk", result);
        result=CommandParser.findSynonyms("asdf");
        Assert.assertEquals("asdf", result);
    }

}