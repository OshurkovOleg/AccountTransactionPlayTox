package ru.home;

import java.util.concurrent.ThreadLocalRandom;

public class Account {
    private final int id;
    private long money;
    public int getId() {
        return id;
    }
    public long getMoney() {
        return money;
    }
    public void setMoney(long money) {
        this.money = money;
    }
    public Account() {
        this.id = ThreadLocalRandom.current().nextInt(0, 100);
        this.money = ConfigApp.MONEY_ACCOUNT;
    }
}
