package de.ctdo.kasse.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@NamedQueries({
        @NamedQuery(name = "de.ctdo.kasse.core.Transaction.findAll",
                    query = "select t from Transaction t"),
        @NamedQuery(name = "de.ctdo.kasse.core.Transaction.findByAccount",
                query = "select t from Transaction t where t.account = :account")
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance = BigDecimal.ZERO;

    @NotNull
    private DateTime timeOfAction;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;

    private String ipAddress;
    private String banker;


    public Transaction() {

    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getTimeOfAction() {
        return timeOfAction;
    }

    public void setTimeOfAction(DateTime timeOfAction) {
        this.timeOfAction = timeOfAction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBanker() {
        return banker;
    }

    public void setBanker(String banker) {
        this.banker = banker;
    }
}
