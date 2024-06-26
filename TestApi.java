package com.bifit.edo.autotest.kassa;

import io.restassured.RestAssured;
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
        var response = RestAssured.given()
                .queryParam("type", "sentence")
                .queryParam("number", "2")
                .queryParam("format", "html")
                .get("https://fish-text.ru/get");

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
        var response = RestAssured.given()
                .queryParam("type", "paragraph")
                .queryParam("number", "2")
                .queryParam("format", "html")
                .get("https://fish-text.ru/get");
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
        var response = RestAssured.given()
                .queryParam("type", "title")
                .queryParam("number", "2")
                .queryParam("format", "html")
                .get("https://fish-text.ru/get");
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
        var response = RestAssured.given()
                .queryParam("type", "sentence")
                .queryParam("number", "2")
                .queryParam("format", "json")
                .get("https://fish-text.ru/get");

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
        var response = RestAssured.given()
                .queryParam("type", "paragraph")
                .queryParam("number", "2")
                .queryParam("format", "json")
                .get("https://fish-text.ru/get");
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
        var response = RestAssured.given()
                .queryParam("type", "title")
                .queryParam("number", "2")
                .queryParam("format", "json")
                .get("https://fish-text.ru/get");
        // Разбиваем строку на количество заголовков
        String[] title = response.asString().split("\\\\\\\\n\\\\\\\\n");
        // Проверяем, что количество абзацев равно запрашеваемому + 1 из-за особенностей подсчета
        assertEquals(response.statusCode(), 200);
        assertEquals(3, title.length, "Количество заголовков дожно быть запрашиваемому + 1");
    }
}
