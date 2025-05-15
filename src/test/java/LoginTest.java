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

public class LoginTest {
    private UserModel user;
    private UserSteps userSteps;


    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        userSteps = new UserSteps();
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);
        createUser(user);
    }

    @Test
    @DisplayName("Проверка авторизации с полными существующими данными")
    public void LoginSuccessTest(){
        LoginModel loginModel = LoginModel.from(user);
        userAuthorization(loginModel)
                .then().assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка авторизации с несуществующим логином")
    public void LoginNotExistEmailTest(){
        LoginModel loginModel = new LoginModel(USER_NOT_EXIST_EMAIL, USER_PASSWORD);
        userAuthorization(loginModel)
                .then().assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Проверка авторизации с несуществующим паролем")
    public void LoginNotExistPasswordTest(){
        LoginModel loginModel = new LoginModel(USER_EMAIL, USER_NOT_EXIST_PASSWORD);
        userAuthorization(loginModel)
                .then().assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Проверка авторизации с несуществующими логином и паролем")
    public void LoginNotExistEmailAndPasswordTest(){
        LoginModel loginModel = new LoginModel(USER_NOT_EXIST_EMAIL, USER_NOT_EXIST_PASSWORD);
        userAuthorization(loginModel)
                .then().assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void cleanUp(){
        LoginModel login = LoginModel.from(user);
        Response response = userAuthorization(login);
        String accessToken = response.path("accessToken");
        deleteUser(accessToken)
                .then()
                .statusCode(202)
                .body("success", equalTo(true));
    }
}
