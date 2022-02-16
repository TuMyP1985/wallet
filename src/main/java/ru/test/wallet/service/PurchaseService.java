package ru.test.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.wallet.model.Purchase;
import ru.test.wallet.repository.PurchaseRepository;
import ru.test.wallet.to.PurchaseTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import static ru.test.wallet.repository.inmemory.InMemoryPurchaseRepository.*;
import static ru.test.wallet.util.PurchasesUtil.DEFAULT_PRICE_LIMIT_DAY;
import static ru.test.wallet.util.ValidationUtil.checkNotFoundWithId;


@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }


    public PurchaseTo create(PurchaseTo purchaseTo, Integer userId) {
        return createTo(repository.save(createFromTo(purchaseTo, userId), userId)
                ,purchaseTo.isExcess());
    }

    public void delete(int id, Integer userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public PurchaseTo get(int id, Integer userId) {
        return checkNotFoundWithId(createTo(repository.get(id, userId),false),id);
    }

    public List<PurchaseTo> getAll(Integer userId) {
        Predicate<Purchase> filterUserId = m->m.getUserId().equals(userId);
        return getTos(repository.getAll(userId),DEFAULT_PRICE_LIMIT_DAY, filterUserId);
    }

    public List<PurchaseTo> getAllFilter(Integer userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        Predicate<Purchase> filterUserId = m->m.getUserId().equals(userId);
        return getFilteredTos(repository.getAll(userId), DEFAULT_PRICE_LIMIT_DAY, startDate, startTime, endDate, endTime, filterUserId);
    }

    public void update(PurchaseTo purchaseTo, Integer userId) {
        checkNotFoundWithId(repository.save(createFromTo(purchaseTo,userId), userId), purchaseTo.getId());
    }







}