import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.LoginModel;
import model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static org.hamcrest.CoreMatchers.equalTo;


import static steps.UserSteps.*;
import static testData.TestValue.*;

public class CreateUserTest {
    private UserModel user;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        UserSteps userSteps = new UserSteps();
    }

    @Test
    @DisplayName("Проверка создания пользователя со всеми полями")
    public void createUserSuccessTest(){
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);
        createUser(user)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания существующего пользователя")
    public void createUserDuplicateTest(){
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);
        createUser(user)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
        createUser(user)
                .then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Проверка создания пользователя с пустым логином")
    public void createUserEmptyEmailTest(){
        user = new UserModel(USER_EMPTY_EMAIL, USER_PASSWORD, USER_NAME);
        createUser(user)
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Проверка создания пользователя с пустым паролем")
    public void createUserEmptyPasswordTest(){
        user = new UserModel(USER_EMAIL, USER_EMPTY_PASSWORD, USER_NAME);
        createUser(user)
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Проверка создания пользователя с пустым именем")
    public void createUserEmptyNameTest(){
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_EMPTY_NAME);
        createUser(user)
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void cleanUp(){
        LoginModel login = LoginModel.from(user);
        Response response = userAuthorization(login);
        if (response.getStatusCode() == 200) {
            String accessToken = response.path("accessToken");
            if (accessToken != null) {
                deleteUser(accessToken)
                        .then()
                        .statusCode(202)
                        .body("success", equalTo(true));
            }
        }
    }
}
