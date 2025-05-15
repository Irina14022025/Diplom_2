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
import static testData.TestValue.USER_NAME;

public class LoginUpdateUserTest {
    private UserModel user;
    private UserSteps userSteps;
    String accessToken;
    String existAccessToken;


    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        userSteps = new UserSteps();
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);
        createUser(user);
        LoginModel loginModel = LoginModel.from(user);
        Response response = userAuthorization(loginModel);
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("Проверка изменения логина авторизованного пользователя")
    public void updateAuthorizedUserEmailTest(){
        updateAuthorizedUserEmail(USER_UPDATE_EMAIL, accessToken)
                .then().assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка изменения пароля авторизованного пользователя")
    public void updateAuthorizedUserPasswordTest(){
        updateAuthorizedUserPassword(USER_UPDATE_PASSWORD, accessToken)
                .then().assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка изменения имени авторизованного пользователя")
    public void updateAuthorizedUserNameTest(){
        updateAuthorizedUserName(USER_UPDATE_NAME, accessToken)
                .then().assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка изменения на существующий логин авторизованного пользователя")
    public void updateAuthorizedUserExistEmailTest(){
        UserModel existUser = new UserModel(USER_EXIST_EMAIL, USER_PASSWORD, USER_NAME);
        createUser(existUser);
        LoginModel existLoginModel = LoginModel.from(existUser);
        Response response = userAuthorization(existLoginModel);
        existAccessToken = response.path("accessToken");
        updateAuthorizedUserEmail(USER_EXIST_EMAIL, accessToken)
                .then().assertThat()
                .statusCode(403)
                .body("message", equalTo("User with such email already exists"));
        deleteUser(existAccessToken)
                .then()
                .statusCode(202)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка изменения логина неавторизованного пользователя")
    public void updateUnauthorizedUserEmailTest(){
        updateUnauthorizedUserEmail(USER_UPDATE_EMAIL)
                .then().assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }


    @Test
    @DisplayName("Проверка изменения пароля неавторизованного пользователя")
    public void updateUnauthorizedUserPasswordTest(){
        updateUnauthorizedUserPassword(USER_UPDATE_PASSWORD)
                .then().assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Проверка изменения имени неавторизованного пользователя")
    public void updateUnauthorizedUserNameTest() {
        updateUnauthorizedUserName(USER_UPDATE_NAME)
                .then().assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void cleanUp(){
        deleteUser(accessToken)
                .then()
                .statusCode(202)
                .body("success", equalTo(true));
    }
}
