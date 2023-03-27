package org.example;

public class Player implements IObserver {
    @Override
    public void update(String title, String achievements, String info, String techInfo) {
        System.out.println("Игра " + title);
        System.out.println("Достижения: " + achievements);
    }
}