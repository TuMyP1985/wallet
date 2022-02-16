package ru.test.wallet.web.purchase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.test.wallet.service.PurchaseService;
import ru.test.wallet.to.PurchaseTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.test.wallet.util.ValidationUtil.assureIdConsistent;
import static ru.test.wallet.util.ValidationUtil.checkNew;
import static ru.test.wallet.web.SecurityUtil.authUserId;

@Controller
public class PurchaseRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PurchaseService service;

    public List<PurchaseTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public PurchaseTo get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public PurchaseTo create(PurchaseTo purchaseTo) {
        log.info("create {}", purchaseTo);
        checkNew(purchaseTo);
        return service.create(purchaseTo, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(PurchaseTo purchaseTo, int id) {
        log.info("update {} with id={}", purchaseTo, id);
        assureIdConsistent(purchaseTo, id);
        service.update(purchaseTo, authUserId());
    }

    public List<PurchaseTo> getAllFilter(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getAll filter");
        return service.getAllFilter(authUserId(), startDate, startTime, endDate, endTime);
    }


}