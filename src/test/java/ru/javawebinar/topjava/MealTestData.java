package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID_1 = START_SEQ + 2;
    public static final int USER_MEAL_ID_2 = START_SEQ + 3;
    public static final int USER_MEAL_ID_3 = START_SEQ + 4;
    public static final int USER_MEAL_ID_4 = START_SEQ + 5;

    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 6;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 7;

    public static final Meal MEAL_USER_1 = new Meal(USER_MEAL_ID_1,
            LocalDateTime.of(2021, 2, 25, 10, 0, 0, 0),
            "Завтрак", 500);

    public static final Meal MEAL_USER_2 = new Meal(USER_MEAL_ID_2,
            LocalDateTime.of(2021, 2, 26, 12, 0, 0, 0),
            "Полдник", 500);
    public static final Meal MEAL_USER_3 = new Meal(USER_MEAL_ID_3,
            LocalDateTime.of(2021, 2, 26, 14, 0, 0, 0),
            "Обед", 1500);
    public static final Meal MEAL_USER_4 = new Meal(USER_MEAL_ID_4,
            LocalDateTime.of(2021, 2, 27, 20, 0, 0, 0),
            "Ужин", 2500);

    public static final Meal MEAL_ADMIN_1 = new Meal(ADMIN_MEAL_ID_1,
            LocalDateTime.of(2021, 2, 25, 10, 0, 0, 0),
            "Завтрак", 500);
    public static final Meal MEAL_ADMIN_2 = new Meal(ADMIN_MEAL_ID_2,
            LocalDateTime.of(2021, 2, 27, 10, 0, 0, 0),
            "Завтрак", 1500);

    public static final Meal NEW_MEAL = new Meal(LocalDateTime.now(), "Ужин", 1000);
    public static final Meal NEW_MEAL_EQUALS_DATE = new Meal(
            LocalDateTime.of(2021, 2, 25, 10, 0, 0, 0),
            "Ужин", 1000);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}





