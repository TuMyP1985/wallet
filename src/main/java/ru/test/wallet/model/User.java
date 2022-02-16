package ru.test.wallet.model;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static ru.test.wallet.util.PurchasesUtil.DEFAULT_PRICE_LIMIT_DAY;

public class User extends AbstractNamedEntity {

    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Role> roles;

    private int priceLimitDay = DEFAULT_PRICE_LIMIT_DAY;

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_PRICE_LIMIT_DAY, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int priceLimitDay, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.priceLimitDay = priceLimitDay;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPriceLimitDay() {
        return priceLimitDay;
    }

    public void setPriceLimitDay(int priceLimitDay) {
        this.priceLimitDay = priceLimitDay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User (" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", priceLimitDay=" + priceLimitDay +
                ')';
    }
}