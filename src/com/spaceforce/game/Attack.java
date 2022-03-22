package com.spaceforce.game;

import com.spaceforce.Sound.Sounds;
import com.spaceforce.obj.NPC;
import com.spaceforce.util.ui.View;

import static com.spaceforce.player.Player.player;
import static com.spaceforce.util.ui.UserInterface.userInput;

public class Attack {

    public Attack() {
    }

    /**
     * Checks to see if the enemy needs to be fought. If it does, the damage is adjusted to make the fight winnable.
     * @param enemy an NPC object that is the target of the attack
     */
    public static void attackEnemy(NPC enemy) {

        if (enemy.getName().equalsIgnoreCase("CREEPER")) {
            View.renderText("He screams 'YOU KILLED MY FATHER! PREPARE TO DIE! Which confuses you, due to the fact that you have never killed anyone before.");
            while (enemy.getHealth() > 0 && player.getHealth() > 0) {
                View.renderText("Press enter to attack.");
                userInput.nextLine();
                if (Math.random() > .5) {
                    enemy.setHealth(-2);
                    View.renderText("Your attack was successful!!");
                    View.renderText(enemy.getName() + " has " + enemy.getHealth() + " health remaining.");
                } else {
                    View.renderText("What are you, a stormtrooper? You missed.");
                }
                enemyAttacks(enemy);
            }
            if (enemy.getHealth() <= 0) {
                View.renderText("\nThe CREEPER lies motionless on the ground. People don't die in video games--they just get knocked out, right? At least that's what you'll tell yourself tomorrow morning.");
            }
        } else {
            View.renderText("\n" + enemy.getAttackMsg() + "\n");
            if (Math.random() > .4) {
                enemy.setHealth(-1);
            }
            enemyAttacks(enemy);
            if (enemy.getHealth() <= 0) {
                View.renderText("You have just attacked and killed an innocent civilian. Finding a lost spaceship is the least of your problems now. Better start running.");
                System.exit(0);
            }
        }
    }

    /**
     * If fight is needed damage is adjusted to make fight winnable.
     * @param enemy NPC object that is the target of the attack
     */
    private static void enemyAttacks(NPC enemy) {
        if (enemy.getName().equalsIgnoreCase("CREEPER")) {
            if (Math.random() > .5) {
                player.setHealth(-1);
                View.renderText("You were hit!");
                View.renderText("You now have " + player.getHealth() + " health remaining.");

            }
        } else {
            player.setHealth(-5);
            View.renderText("You attacked an innocent civilian and they defended themselves. You were hit!");
            View.renderText("You now have " + player.getHealth() + " health remaining.");
        }

        if (player.getHealth() <= 0) {
            View.renderText("\nNever go against a Sicilian when death is on the line.");
            Sounds.SCREAM.playSFX();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
