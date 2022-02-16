package ru.test.wallet.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Purchase extends AbstractBaseEntity {
    private Integer userId;
    private LocalDateTime dateTime;
    private String product;
    private int price;

    public Purchase(LocalDateTime dateTime, String product, int price) {
        this(null, null, dateTime, product, price);
    }

    public Purchase(Integer id,  Integer userId, LocalDateTime dateTime, String product, int price) {
        super(id);
        this.userId = userId;
        this.dateTime = dateTime;
        this.product = product;
        this.price = price;

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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


    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", product='" + product + '\'' +
                ", price=" + price +
                '}';
    }
}
