package ru.test.wallet.repository;

import ru.test.wallet.model.Purchase;

import java.util.Collection;
import java.util.List;

public interface PurchaseRepository {

    // null if updated purchase does not belong to userId
    Purchase save(Purchase purchase, Integer purchaseId);

    // false if purchase does not belong to userId
    boolean delete(int id, Integer purchaseId);

    // null if purchase does not belong to userId
    Purchase get(int id, Integer purchaseId);

    // ORDERED dateTime desc
    Collection<Purchase> getAll(Integer purchaseId);

}
