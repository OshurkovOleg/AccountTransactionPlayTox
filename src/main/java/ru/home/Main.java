package ru.home;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final List<Account> ACCOUNTS = new ArrayList<>(ConfigApp.NUMBER_OF_ACCOUNTS);
    private static ExecutorService threadPool = Executors.newFixedThreadPool(ConfigApp.NUMBER_OF_THREADS);


    public static void main(String[] args) {
        createAccounts(ConfigApp.NUMBER_OF_ACCOUNTS);
        startTransaction();
    }

    private static void createAccounts(int numberAccounts) {
        for (int i = 0; i < numberAccounts; i++) {
            ACCOUNTS.listIterator().add(new Account());
        }
    }


    private static void startTransaction() {
        for (int i = 0; i < 30; i++) {
            int money = ThreadLocalRandom.current().nextInt(1000, 7000);
            Account accountFrom = ACCOUNTS.get(getRandomIndex());
            Account accountTo = ACCOUNTS.get(getRandomIndex());

            if (accountFrom != accountTo) {
                Transaction transaction = new Transaction(money, accountFrom, accountTo);
                threadPool.submit(transaction);
            } else {
                i--;
            }
        }
        threadPool.shutdown();

        try {
            boolean term = threadPool.awaitTermination(10000, TimeUnit.SECONDS);

            if (term) {
                LOGGER.info(Constants.ALL_TRANSACTIONS_COMPLETED);
            } else {
                LOGGER.info(Constants.TRANSACTION_TIMED_OUT);
            }

        } catch (InterruptedException e) {
            LOGGER.error(Constants.THREAD + Thread.currentThread().getName() + Constants.TERMINATED);
            Thread.currentThread().interrupt();
        }
    }

    private static int getRandomIndex() {
        return ThreadLocalRandom.current().nextInt(0, ACCOUNTS.size());
    }

}
