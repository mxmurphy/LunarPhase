package com.spaceforce.game;

import com.spaceforce.obj.NPC;
import com.spaceforce.util.ui.View;

import static com.spaceforce.player.Player.player;
import static com.spaceforce.util.ui.UserInterface.userInput;

public class Attack {

    public Attack() {
    }

    public static void attackEnemy(NPC enemy) {

        if (enemy.name.equalsIgnoreCase("CREEPER")) {
            View.renderText("He screams 'YOU KILLED MY FATHER! PREPARE TO DIE! Which confuses you, due to the fact that you have never killed anyone before.");
            while (enemy.health > 0 && player.getHealth() > 0) {
                View.renderText("Press enter to attack.");
                userInput.nextLine();
                if ((Math.random() * 10) > 4) {
                    enemy.setHealth(-1);
                    View.renderText(enemy.getName()+" has "+enemy.getHealth()+" health remaining.");
                }else{
                    View.renderText("What are you, a stormtrooper? You missed.");
                }
                enemyAttacks(enemy);
            }
            if(enemy.getHealth()<=0){

            }
        } else {
            if ((Math.random() * 10) > 4) {
                enemy.setHealth(-1);
            }
            enemyAttacks(enemy);
        }
        if(player.getHealth()<=0){
            View.renderText("Never go against a Sicilian when death is on the line.");
            System.exit(0);
        }
    }

    private static void enemyAttacks(NPC enemy) {
        if (enemy.name.equalsIgnoreCase("CREEPER")) {
            if ((Math.random() * 10) > 4) {
                player.setHealth(-1);
                View.renderText("You no have "+player.getHealth()+" health remaining.");

            }
        }else{
            player.setHealth(-5);
            View.renderText("You no have "+player.getHealth()+" health remaining.");
        }

    }
}
