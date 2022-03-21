package com.spaceforce.obj;


import com.spaceforce.game.Attack;
import com.spaceforce.util.ui.View;

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
        View.renderText(talkMsg);
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
