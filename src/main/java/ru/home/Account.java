package ru.home;

import java.util.concurrent.ThreadLocalRandom;

public class Account {
    private final int id = ThreadLocalRandom.current().nextInt(0, 100);
    private long money = 10_000;
    public int getId() {
        return id;
    }
    public long getMoney() {
        return money;
    }
    public void setMoney(long money) {
        this.money = money;
    }
}
