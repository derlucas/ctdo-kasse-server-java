package de.ctdo.kasse.api;

import org.joda.time.DateTime;

/**
 * @author: lucas
 * @date: 16.10.14 11:58
 */
public class TransactionView {

    private int id;
    private String accountId;
    private double balance;
    private DateTime timeOfAction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public DateTime getTimeOfAction() {
        return timeOfAction;
    }

    public void setTimeOfAction(DateTime timeOfAction) {
        this.timeOfAction = timeOfAction;
    }
}
