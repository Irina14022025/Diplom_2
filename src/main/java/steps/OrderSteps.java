package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static final String ORDER_PATH = "/api/orders";



    @Step("Создание заказа для авторизованного пользователя POST /api/orders")
    public static Response createOrderAuthorizedUser(OrderModel order, String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Создание заказа для неавторизованного пользователя POST /api/orders")
    public static Response createOrderUnauthorizedUser(OrderModel order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Просмотр заказов авторизованного пользователя GET /api/orders")
    public static Response viewOrderAuthorizedUser(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .get(ORDER_PATH)
                .then().log().all()
                .extract().response();
    }

    @Step("Просмотр заказов неавторизованного пользователя GET /api/orders")
    public static Response viewOrderUnauthorizedUser() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(ORDER_PATH)
                .then().log().all()
                .extract().response();
    }

}
