package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("One", "1@1", "0000", Role.USER, Role.USER),
            new User("Two", "2@2", "1234", Role.ADMIN, Role.ADMIN),
            new User("One", "3@3", "4321", Role.ADMIN, Role.USER, Role.ADMIN)
    );
}

