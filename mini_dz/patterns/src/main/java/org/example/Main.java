package org.example;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        Journalist firstJournalist = new Journalist();
        Journalist secondJournalist = new Journalist();
        Developer developer = new Developer();
        game.add(firstPlayer);
        game.add(secondPlayer);
        game.add(firstJournalist);
        game.add(secondJournalist);
        game.add(developer);
        game.setTitle("Змейка");
        game.setAchievement("1 уровень");
        game.setInfo("Игрок управляет длинным, тонким существом, напоминающим змею, которое ползает по плоскости (как правило, ограниченной стенками), собирая еду (или другие предметы), избегая столкновения с собственным хвостом и краями игрового поля.");
        game.setTechInfo("IOS 12.0 и выше");
        game.delete(secondJournalist);
        game.setTitle("Тетрис");
        game.setAchievement("2 уровень");
        game.setInfo("«Тетрис» представляет собой головоломку, построенную на использовании геометрических фигур «тетрамино» — разновидности полимино, состоящих из четырёх квадратов");
    }
}