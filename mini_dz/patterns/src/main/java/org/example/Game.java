package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<IObserver> IObserverList = new ArrayList<>();
    private String title;
    private String achievement;
    private String info;
    private String techInfo;
    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
        notifyObservers();
    }

    public void setInfo(String info) {
        this.info = info;
        notifyObservers();
    }

    public void setTechInfo(String techInfo) {
        this.techInfo = techInfo;
        notifyObservers();
    }
    public void add(IObserver IObserver) {
        IObserverList.add(IObserver);
    }

    public void delete(IObserver IObserver) {
        IObserverList.remove(IObserver);
    }

    public void notifyObservers() {
        for (IObserver IObserver : IObserverList) {
            IObserver.update(title, achievement, info, techInfo);
        }
    }
}