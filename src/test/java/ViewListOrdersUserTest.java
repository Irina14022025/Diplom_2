import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.LoginModel;
import model.OrderModel;
import model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import static org.hamcrest.CoreMatchers.equalTo;
import static steps.OrderSteps.*;
import static steps.UserSteps.*;
import static testData.TestValue.*;
import static testData.TestValue.USER_NAME;

public class ViewListOrdersUserTest {
    private UserModel user;
    private UserSteps userSteps;
    private OrderModel order;
    private OrderSteps orderSteps;
    private String accessToken;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);
        userSteps = new UserSteps();
        createUser(user);
        LoginModel loginModel = LoginModel.from(user);
        Response response = userAuthorization(loginModel);
        accessToken = response.path("accessToken");
        order = new OrderModel(INGREDIENT);
        orderSteps = new OrderSteps();
        createOrderAuthorizedUser(order, accessToken);
        createOrderAuthorizedUser(order, accessToken);
    }

    @Test
    @DisplayName("Проверка просмотра заказов авторизованного пользователя")
    public void viewOrderAuthorizedUserTest(){
        viewOrderAuthorizedUser(accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка просмотра заказов неавторизованного пользователя")
    public void viewOrderUnauthorizedUserTest(){
        viewOrderUnauthorizedUser()
                .then()
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
