package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {

    static {

        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal getMeal = service.get(USER_MEAL_ID_1, USER_ID);
        MealTestData.assertMatch(MEAL_USER_1, getMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(USER_MEAL_ID_1, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_1, USER_ID);
        assertEquals(service.getAll(USER_ID).size(), 3);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(USER_MEAL_ID_1, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> listBetween = service.getBetweenInclusive(LocalDate.of(2020, 2, 26),
                LocalDate.of(2020, 2, 27), USER_ID);
        assertMatch(listBetween, MEAL_USER_4, MEAL_USER_3, MEAL_USER_2);
    }

    @Test
    public void getAll() {
        MealTestData.assertMatch(service.getAll(ADMIN_ID), MEAL_ADMIN_2, MEAL_ADMIN_1);
    }

    @Test
    public void update() {
        Meal meal = new Meal(USER_MEAL_ID_1,
                LocalDateTime.of(2015, 2, 25, 10, 0, 0, 0),
                "Завтрак", 500);
        service.update(meal, USER_ID);
        MealTestData.assertMatch(service.get(USER_MEAL_ID_1, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(USER_MEAL_ID_1,
                LocalDateTime.of(2015, 2, 25, 10, 0, 0, 0),
                "Завтрак", 500);
        service.update(meal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal meal = service.create(NEW_MEAL, USER_ID);
        MealTestData.assertMatch(service.get(meal.getId(), USER_ID), meal);
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateDateCreate() {
        service.create(NEW_MEAL_EQUALS_DATE, USER_ID);
    }
}