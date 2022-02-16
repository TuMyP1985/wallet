package ru.test.wallet.to;

import ru.test.wallet.model.AbstractBaseEntity;

import java.time.LocalDateTime;

public class PurchaseTo extends AbstractBaseEntity {

//    private final Integer userId;

    private final LocalDateTime dateTime;

    private final String product;

    private final int price;

    private final boolean excess;

    public PurchaseTo(Integer id, LocalDateTime dateTime, String product, int price, boolean excess) {
        super(id);
//        this.userId = userId;
        this.dateTime = dateTime;
        this.product = product;
        this.price = price;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getProduct() {
        return product;
    }

    public int getPrice() {
        return price;
    }

    public boolean isExcess() {
        return excess;
    }


    @Override
    public String toString() {
        return "PurchaseTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", product='" + product + '\'' +
                ", price=" + price +
                ", excess=" + excess +
                '}';
    }
}
