package ru.javawebinar.repositories.inmemory;

import org.slf4j.Logger;
import ru.javawebinar.repositories.MealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        MealsUtil.meals.forEach(meal -> {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        });
    }

    @Override
    public void save(Meal meal) {
        log.info("saving {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        log.info("getting {}", id);
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        log.info("getting all");
        return new ArrayList<>(repository.values());
    }

    @Override
    public void delete(int id) {
        log.info("deleting {}", id);
        repository.remove(id);
    }
}
