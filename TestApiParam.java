package com.bifit.edo.autotest.kassa;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Параметизированный тест на проверку запросов, обрабатываемых с ошибкой
 */
public class TestApiParam {

    /**
     * Инициализируем базовый URL
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fish-text.ru";
    }

    /**
     * Проверяем запрос с разными URL
     * @param endpoint URL поступающий в метод
     * @param expectedStatusCode ожидаемый статус код на запрос
     * @param expectedErrorCode ожидаемый errorCode на запрос
     */
    @ParameterizedTest
    @CsvSource({
            "/getInvalidUrl , 404, not found", // Неверный url
            "/get?number=-1, 200, \"errorCode\":31", // Отрицательное значение предложений
            "/get?type=sentence&number=501, 200, \"errorCode\":11", // Большое число предложений (больше 500)
            "/get?type=title&number=501, 200, \"errorCode\":11", // Большое число предложений (больше 500)
            "/get?type=paragraph&number=101, 200, \"errorCode\":11" // Большое число абзацев (больше 100)
    })

    void testErrorResponses(String endpoint, int expectedStatusCode, String expectedErrorCode) {
        //Вызываем метод с определенными URL и Query параметрами
        var response = RestAssured.get(endpoint);
        //Проверяем статус код овтета и код ошибки
        assertEquals(expectedStatusCode, response.getStatusCode(),
                "Ожидался статус код " + expectedStatusCode + " для эндпоинта: " + endpoint);
        assertTrue(response.getBody().asString().contains(expectedErrorCode),
                "Ожидаемое код ошибки '" + expectedErrorCode + "' не найден в ответе для эндпоинта: " + endpoint);
    }
}