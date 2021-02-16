package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User("userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.getAll().forEach(System.out::println);
            System.out.println();
            System.out.println(adminUserController.get(2));
            System.out.println();
            adminUserController.delete(2);
            adminUserController.getAll().forEach(System.out::println);
            System.out.println();
            System.out.println(adminUserController.getByMail("1@1"));
            adminUserController.update(new User("userName", "email@gjhkk", "password", Role.ADMIN), 4);
            adminUserController.getAll().forEach(System.out::println);
//


            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();

            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 11, 0), "Завтрак", 0));
            System.out.println();
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();

            System.out.println();
            System.out.println(mealRestController.get(7));

//            mealRestController.createUpdate(meal);
            System.out.println();
//            System.out.println(mealRestController.get(8));
            mealRestController.update(new Meal(7, LocalDateTime.of(2015, Month.MAY, 30, 12, 0), "Полдник", 500));
            System.out.println();
            System.out.println(mealRestController.get(7));
            mealRestController.delete(7);
            System.out.println();
            mealRestController.getAll().forEach(System.out::println);

        }
    }
}
