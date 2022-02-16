package ru.test.wallet.util;

import ru.test.wallet.model.Purchase;
import ru.test.wallet.to.PurchaseTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PurchasesUtil {
    public static final int DEFAULT_PRICE_LIMIT_DAY = 2000;

    public static final List<Purchase> purchases = Arrays.asList(
            new Purchase(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Телефон", 500),
            new Purchase(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Хлеб", 1000),
            new Purchase(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Телевизор", 500),
            new Purchase(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Мука", 100),
            new Purchase(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Машина", 1000),
            new Purchase(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Гречка", 500),
            new Purchase(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Рис", 410)
    );

    public static List<PurchaseTo> getTos(Collection<Purchase> purchases, int priceLimitDay) {
        return filterByPredicate(purchases, priceLimitDay, purchase -> true);
    }

    public static List<PurchaseTo> getFilteredTos(Collection<Purchase> purchases, int pricePerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(purchases, pricePerDay, purchase -> DateTimeUtil.isBetweenHalfOpen(purchase.getTime(), startTime, endTime));
    }

    public static List<PurchaseTo> filterByPredicate(Collection<Purchase> purchases, int pricePerDay, Predicate<Purchase> filter) {
        Map<LocalDate, Integer> priceSumByDate = purchases.stream()
                .collect(
                        Collectors.groupingBy(Purchase::getDate, Collectors.summingInt(Purchase::getPrice))
//                      Collectors.toMap(Purchase::getDate, Purchase::getPrice, Integer::sum)
                );

        return purchases.stream()
                .filter(filter)
                .map(purchase -> createTo(purchase, priceSumByDate.get(purchase.getDate()) > pricePerDay))
                .collect(Collectors.toList());
    }

    private static PurchaseTo createTo(Purchase purchase, boolean excess) {
        return new PurchaseTo(purchase.getId(), purchase.getDateTime(), purchase.getProduct(), purchase.getPrice(), excess);
    }
}
