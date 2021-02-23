package ru.javawebinar.topjava.web.user;

import org.junit.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml"
})

public class InMemoryAdminRestControllerTest {
    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app-inmemory.xml", "spring/spring-app2-web_service.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(AdminRestController.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Before
    public void setUp() throws Exception {
        // re-initialize
        InMemoryUserRepository repository = appCtx.getBean(InMemoryUserRepository.class);
        repository.init();
    }

    @Test
    public void delete() throws Exception {
        controller.delete(USER_ID);
        Collection<User> users = controller.getAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.iterator().next(), admin);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        controller.delete(10);
    }

    @Test
    public void getAll() {
        assertMatch(controller.getAll(), admin, user);
    }

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555,
                false, new Date(), Collections.singleton(Role.USER));
        User created = controller.create(newUser);
        newUser.setId(created.getId());
        assertMatch(controller.getAll(), admin, newUser, user);
    }

    @Test
    public void get() throws Exception {
        User user = controller.get(USER_ID);
        assertMatch(user, user);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        controller.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = controller.getByMail("user@yandex.ru");
        assertMatch(user, user);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(user);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        controller.update(updated, USER_ID);
        assertMatch(controller.get(USER_ID), updated);
    }
}