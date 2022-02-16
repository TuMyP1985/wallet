package ru.test.wallet.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.test.wallet.model.Purchase;
import ru.test.wallet.repository.PurchaseRepository;
import ru.test.wallet.to.PurchaseTo;
import ru.test.wallet.util.DateTimeUtil;
import ru.test.wallet.util.PurchasesUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryPurchaseRepository implements PurchaseRepository {

    private final Map<Integer, Purchase> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryPurchaseRepository() {
        PurchasesUtil.purchases.forEach(n-> {n.setId(null);save(n, 1);});
    }


    @Override
    public Purchase save(Purchase purchase, Integer userId) {
        if (purchase.isNew()) {
            purchase.setId(counter.incrementAndGet());
            purchase.setUserId(userId);
            repository.put(purchase.getId(), purchase);
            return purchase;
        }
        if (!valudeUser(purchase, userId))
            return null;
        // handle case: update, but not present in storage
        return repository.computeIfPresent(purchase.getId(), (id, oldPurchase) -> purchase);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (!valudeUser(repository.get(id), userId))
            return false;
        return repository.remove(id) != null;
    }

    @Override
    public Purchase get(int id, Integer userId) {
        if (!valudeUser(repository.get(id), userId))
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<Purchase> getAll(Integer userId) {
        return repository.values()
                .stream()
                .sorted((a,b)->b.getDate().compareTo(a.getDate()))
                .collect(Collectors.toList());
    }



    //add
    public boolean valudeUser(Purchase purchase, Integer userId){
        return purchase!=null &&
                purchase.getUserId()!=null &&
                purchase.getUserId().equals(userId);
    }


    public static List<PurchaseTo> getTos(Collection<Purchase> purchase,
                                          int pricePerDay,
                                          Predicate<Purchase> filterUserId) {
        return filterByPredicate(purchase, pricePerDay, p -> true, p -> true, filterUserId);
    }

    public static List<PurchaseTo> getFilteredTos(Collection<Purchase> purchases,
                                                  int pricePerDay,
                                                  LocalDate startDate,
                                                  LocalTime startTime,
                                                  LocalDate endDate,
                                                  LocalTime endTime,
                                                  Predicate<Purchase> filterUserId) {
        return filterByPredicate(purchases,
                pricePerDay,
                purchase -> DateTimeUtil.isBetweenHalfOpen(purchase.getDate(), startDate, endDate),
                purchase -> DateTimeUtil.isBetweenHalfOpen(purchase.getTime(), startTime, endTime),
                filterUserId);
    }

    public static List<PurchaseTo> filterByPredicate(Collection<Purchase> purchases, int pricePerDay,
                                                     Predicate<Purchase> filterDate,
                                                     Predicate<Purchase> filterTime,
                                                     Predicate<Purchase> filterUserId) {
        Map<LocalDate, Integer> priceSumByDate = purchases.stream()
                .collect(
                        Collectors.groupingBy(Purchase::getDate, Collectors.summingInt(Purchase::getPrice))
//                      Collectors.toMap(Purchase::getDate, Purchase::getPrice, Integer::sum)
                );

        return purchases.stream()
                .filter(filterDate)
                .filter(filterTime)
                .filter(filterUserId)
                .map(purchase -> createTo(purchase, priceSumByDate.get(purchase.getDate()) > pricePerDay))
                .collect(Collectors.toList());
    }

    public static PurchaseTo createTo(Purchase purchase, boolean excess) {
        return purchase==null?null:new PurchaseTo(
                purchase.getId(),
                purchase.getDateTime(),
                purchase.getProduct(),
                purchase.getPrice(),
                excess);
    }

    public static Purchase createFromTo(PurchaseTo purchaseTo, Integer userId) {
        return purchaseTo==null?null:new Purchase(
                purchaseTo.getId(),userId,
                purchaseTo.getDateTime(),
                purchaseTo.getProduct(),
                purchaseTo.getPrice());
    }

}

