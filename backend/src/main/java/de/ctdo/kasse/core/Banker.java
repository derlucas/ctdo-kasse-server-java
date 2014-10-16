package de.ctdo.kasse.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "banker")
@NamedQueries({
        @NamedQuery(name = "de.ctdo.kasse.core.Banker.findAll",
                query = "select b from Banker b"),
        @NamedQuery(name = "de.ctdo.kasse.core.Banker.findByUsername",
                query = "select b from Banker b where b.username = :username")
})
public class Banker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String ipAddress;

    private String ip6Address;

    public Banker() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIp6Address() {
        return ip6Address;
    }

    public void setIp6Address(String ip6Address) {
        this.ip6Address = ip6Address;
    }
}
