package ru.home;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;

public class Transaction implements Runnable{

    private static final Logger LOGGER = LogManager.getLogger(Transaction.class);
    private final long amount;
    private final Account fromAccount;
    private final Account toAccount;

    public Transaction(long amount, Account fromAccount, Account toAccount) {
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            LOGGER.error(Constants.THREAD + Thread.currentThread().getName() + Constants.TERMINATED);
            Thread.currentThread().interrupt();
        }

        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getMoney() >= amount) {
                    fromAccount.setMoney(fromAccount.getMoney() - amount);
                    toAccount.setMoney(toAccount.getMoney() + amount);
                    LOGGER.info(Constants.WITH_ACCOUNT + fromAccount.getId() + Constants.WRITTEN_OFF + amount +
                            Constants.TO_ACCOUNT + toAccount.getId() + Constants.ACCRUED + amount +
                            Constants.BALANCE_ACCOUNT + fromAccount.getId() + Constants.IS + fromAccount.getMoney() +
                            Constants.BALANCE_ACCOUNT + toAccount.getId() + Constants.IS + toAccount.getMoney());

                } else {
                    LOGGER.error(Constants.TO_ACCOUNT + fromAccount.getId() + Constants.NOT_ENOUGH_MONEY_ACCOUNT);
                }
            }
        }

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            LOGGER.error(Constants.THREAD + Thread.currentThread().getName() + Constants.TERMINATED);
            Thread.currentThread().interrupt();
        }
    }
}
