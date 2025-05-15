package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.LoginModel;
import model.UserModel;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserSteps {
    public static final String USER_CREATE_PATH = "/api/auth/register";
    public static final String USER_LOGIN_PATH = "/api/auth/login";
    public static final String USER_UPDATE_PATH = "/api/auth/user";


    @Step("Создание пользователя POST /api/auth/register")
    public static Response createUser(UserModel user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_CREATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Авторизация пользователя POST /api/auth/login")
    public static Response userAuthorization(LoginModel login){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(USER_LOGIN_PATH)
                .then().log().all()
                .extract().response();
    }


    @Step("Удаление пользователя DELETE /api/auth/user")
    public static Response deleteUser(String accessToken){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Обновление логина авторизованного пользователя PATCH /api/auth/user")
    public static Response updateAuthorizedUserEmail(String newEmail, String accessToken){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(Map.of("email", newEmail))
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Обновление пароля авторизованного пользователя PATCH /api/auth/user")
    public static Response updateAuthorizedUserPassword(String newPassword, String accessToken){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(Map.of("password", newPassword))
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Обновление имени авторизованного пользователя PATCH /api/auth/user")
    public static Response updateAuthorizedUserName(String newName, String accessToken){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(Map.of("name", newName))
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Обновление логина неавторизованного пользователя PATCH /api/auth/user")
    public static Response updateUnauthorizedUserEmail(String newEmail){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("email", newEmail))
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Обновление пароля неавторизованного пользователя PATCH /api/auth/user")
    public static Response updateUnauthorizedUserPassword(String newPassword){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("password", newPassword))
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Обновление имени неавторизованного пользователя PATCH /api/auth/user")
    public static Response updateUnauthorizedUserName(String newName){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("name", newName))
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }
}