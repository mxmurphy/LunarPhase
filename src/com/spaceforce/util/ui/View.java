package com.spaceforce.util.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class View {

    private View() {
    }

    public static void renderText(String text) {
        System.out.println(text);
    }

    public static void renderImage(InputStreamReader image) {
        try (BufferedReader imageFeed = new BufferedReader(image)) {
            String imageLine;

            while ((imageLine = imageFeed.readLine()) != null) {
                System.out.println(imageLine);
            }
        } catch (Exception e) {
            View.renderText(e.getMessage());
        }
    }
}