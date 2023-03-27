package org.example;

public class Journalist implements IObserver {
    @Override
    public void update(String title, String achievements, String info, String techInfo) {
        System.out.println("Новая игра " + title);
        System.out.println("Краткое описание " + info);
    }
}