package testData;

import java.util.List;

public class TestValue {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    // данные для создания пользователя
    public static final String USER_EMAIL = "Email" + System.currentTimeMillis() + "@mail.ru";
    public static final String USER_PASSWORD = "Password" + System.currentTimeMillis();
    public static final String USER_NAME = "Name" + System.currentTimeMillis();
    public static final String USER_EMPTY_EMAIL = "";
    public static final String USER_EMPTY_PASSWORD = "";
    public static final String USER_EMPTY_NAME = "";
    public static final String USER_NOT_EXIST_EMAIL = "not_exist_email" + System.currentTimeMillis() + "@mail.ru";
    public static final String USER_NOT_EXIST_PASSWORD = "not_exist_password" + System.currentTimeMillis();
    public static final String USER_UPDATE_NAME = "New_name" + System.currentTimeMillis();
    public static final String USER_UPDATE_EMAIL = "New_email" + System.currentTimeMillis() + "@mail.ru";
    public static final String USER_UPDATE_PASSWORD = "New_password" + System.currentTimeMillis();
    public static final String USER_EXIST_EMAIL = "Exist_email" + System.currentTimeMillis() + "@mail.ru";
    public static final List<String> INGREDIENT = List.of("61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa70");
    public static final List<String> INGREDIENT_INVALID_HASH = List.of("m1c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa70");
}
