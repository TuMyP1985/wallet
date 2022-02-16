package ru.test.wallet.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.test.wallet.model.User;
import ru.test.wallet.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

//    {
//        PurchasesUtil.purchases.forEach(this::save);
//    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldPurchase) -> user);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return repository.values()
                .stream()
                .sorted((a,b)->a.getEmail().compareTo(b.getEmail()))
                .sorted((a,b)->a.getName().compareTo(b.getName()))
                .collect(Collectors.toList());

    }

    @Override
    public User getByEmail(String email) {
        Predicate<User> filter = (u)->u.getEmail().equals(email);
        return repository.values()
                .stream()
                .filter(filter)
                .findFirst().get();
    }
}
