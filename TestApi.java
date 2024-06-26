package com.bifit.edo.autotest.kassa;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты допустимых комбинаций fish-text.ru
 */
public class TestApi {

    /**
     * Проверка комбинации type = sentence, number = 2, format = html
     */
    @Test
    public void testSentenceHtml() {
        // Выполянем запрос и получаем ответ
        var response = request("sentence", 2, "html");
        // Подсчитываем количество знаков препинания
        var punctuationCount = response.asString().chars()
                .filter(ch -> ch == '.' || ch == '!' || ch == '?')
                .count();
        // Проверяем, что количество знаков препинания равно запрашиваемому
        assertEquals(response.getStatusCode(), 200);
        assertEquals(2, punctuationCount,
                "Количество знаков препинания должно быть 2, так как запрашивалось 2 предложения");
    }

    /**
     * Проверка комбинации type = paragraph, number = 2, format = html
     */
    @Test
    public void testParagraphHtml() {
        // Выполянем запрос и получаем ответ
        var response = request("paragraph", 2, "html");
        // Разбиваем строку на количество абзацев
        String[] paragraph = response.asString().split("</p>");
        // Проверяем, что количество абзацев равно запрашеваемому
        assertEquals(response.getStatusCode(), 200);
        assertEquals(2, paragraph.length, "Количество абзацев должно быть равно запрашеваемому");
    }

    /**
     * Проверка комбинации type = paragraph, number = 2, format = html
     */
    @Test
    public void testTitleHtml() {
        // Выполянем запрос и получаем ответ
        var response = request("title", 2, "html");
        // Разбиваем строку на количество заголовков
        String[] title = response.asString().split("</h1>");
        // Проверяем, что количество заголовков равно запрашеваемому
        assertEquals(response.getStatusCode(), 200);
        assertEquals(2, title.length, "Количество заголовков должно быть равно запрашиваемому");
    }

    /**
     * Проверка комбинации type = sentence, number = 2, format = json
     */
    @Test
    public void testSentenceJson() {
        // Выполянем запрос и получаем ответ
        var response = request("sentence", 2, "json");
        // Подсчитываем количество знаков препинания
        var punctuationCount = response.asString().chars()
                .filter(ch -> ch == '.' || ch == '!' || ch == '?')
                .count();
        // Проверяем, что количество знаков препинания равно запрашиваемому
        assertEquals(response.getStatusCode(), 200);
        assertEquals(2, punctuationCount,
                "Количество знаков препинания должно быть 2, так как запрашивалось 2 предложения");
    }

    /**
     * Проверка комбинации type = paragraph, number = 2, format = json
     */
    @Test
    public void testParagraphJson() {
        // Выполянем запрос и получаем ответ
        var response = request("paragraph", 2, "json");
        // Разбиваем строку на количество предложений
        String[] paragraph = response.asString().split("\\\\\\\\n\\\\\\\\n");
        // Проверяем, что количество абзацев равно запрашеваемому + 1 из-за особенностей подсчета
        assertEquals(response.statusCode(), 200);
        assertEquals(3, paragraph.length, "Количество абзацев дожно быть запрашиваемому + 1");
    }

    /**
     * Проверка комбинации type = title, number = 2, format = json
     */
    @Test
    public void testTitleJson() {
        // Выполянем запрос и получаем ответ
        var response = request("title", 2, "json");
        // Разбиваем строку на количество заголовков
        String[] title = response.asString().split("\\\\\\\\n\\\\\\\\n");
        // Проверяем, что количество абзацев равно запрашеваемому + 1 из-за особенностей подсчета
        assertEquals(response.statusCode(), 200);
        assertEquals(3, title.length, "Количество заголовков дожно быть запрашиваемому + 1");
    }

    /**
     * Выполняем запрос с полученным параметрами
     * @param type тип текста
     * @param number количество текста
     * @param format формат отввета
     * @return текст
     */
    public Response request(String type, int number, String format) {
        var response = RestAssured.given()
                .queryParam("type", type)
                .queryParam("number", number)
                .queryParam("format", format)
                .get("https://fish-text.ru/get");
        return response;
    }
}
