package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.LoginModel;
import model.UserModel;


import static io.restassured.RestAssured.given;

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
    public static Response userAuthorization(LoginModel login) {
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
    public static Response deleteUser(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Обновление данных авторизованного пользователя PATCH /api/auth/user")
    public static Response updateAuthorizedUser(UserModel user, String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }


    @Step("Обновление данных неавторизованного пользователя PATCH /api/auth/user")
    public static Response updateUnauthorizedUser(UserModel user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .patch(USER_UPDATE_PATH)
                .then().log().all()
                .extract().response();
    }

}