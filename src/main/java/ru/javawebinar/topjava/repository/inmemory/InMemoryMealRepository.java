package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Comparator<Meal> COMPARATOR = Comparator.comparing(Meal::getDate).reversed().thenComparing(Meal::getTime);

    {
        MealsUtil.MEALS.forEach(MEAL -> save(MEAL, SecurityUtil.authUserId()));
        Meal meal = new Meal(8, LocalDateTime.of(2015, Month.MAY, 30, 12, 0), "Полдник", 500);
        save(meal, 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.putIfAbsent(userId, new ConcurrentHashMap<>());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null ? mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;

    }

    @Override
    public boolean delete(int mealId, int userId) {
        log.info("delete {}", mealId);
        return get(mealId, userId) != null && repository.get(userId).remove(mealId) != null;

    }

    @Override

    public Meal get(int mealId, int userId) {
        log.info("get {}", mealId);
        return repository.getOrDefault(userId, Collections.emptyMap()).getOrDefault(mealId, null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        return getListAllFilter(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getBetweenDates {}", userId);
        return getListAllFilter(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getListAllFilter(int userId, Predicate<Meal> filter) {
        return repository.getOrDefault(userId, Collections.emptyMap()).values()
                .stream()
                .filter(filter)
                .sorted(COMPARATOR)
                .collect(Collectors.toList());
    }
}

