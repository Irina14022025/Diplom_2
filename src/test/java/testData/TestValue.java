package testData;

import java.util.List;

public class TestValue {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    // данные для создания пользователя
    public static final String USER_EMAIL = "email" + System.currentTimeMillis() + "@mail.ru";
    public static final String USER_PASSWORD = "password" + System.currentTimeMillis();
    public static final String USER_NAME = "name" + System.currentTimeMillis();
    public static final String USER_EMPTY_EMAIL = "";
    public static final String USER_EMPTY_PASSWORD = "";
    public static final String USER_EMPTY_NAME = "";
    public static final String USER_NOT_EXIST_EMAIL = "not_exist_email" + System.currentTimeMillis() + "@mail.ru";
    public static final String USER_NOT_EXIST_PASSWORD = "not_exist_password" + System.currentTimeMillis();
    public static final String USER_UPDATE_NAME = "new_name" + System.currentTimeMillis();
    public static final String USER_UPDATE_EMAIL = "new_email" + System.currentTimeMillis() + "@mail.ru";
    public static final String USER_UPDATE_PASSWORD = "new_password" + System.currentTimeMillis();
    public static final String USER_EXIST_EMAIL = "exist_email" + System.currentTimeMillis() + "@mail.ru";
    public static final List<String> INGREDIENT = List.of("61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa70");
    public static final List<String> INGREDIENT_INVALID_HASH = List.of("m1c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa70");
}
