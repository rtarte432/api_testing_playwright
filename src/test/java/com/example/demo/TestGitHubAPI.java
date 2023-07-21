package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TestGitHubAPI {
    private static final String REPO = "test-repo-2";
    private static final String USER = System.getenv("GITHUB_USER");
    private static final String API_TOKEN = System.getenv("GITHUB_API_TOKEN");

    private Playwright playwright;
    private APIRequestContext request;

    void createPlaywright() {
        playwright = Playwright.create();
    }

    void createAPIRequestContext() {
        Map<String, String> headers = new HashMap<>();
//        headers.put("Accept", "application/vnd.github.v3+json");
//        headers.put("Authorization", "token " + API_TOKEN);
        headers.put("Content-Type", "application/json");

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("http://35.232.135.170/")
                .setExtraHTTPHeaders(headers));
    }

    @BeforeAll
    void beforeAll() {
        createPlaywright();
        createAPIRequestContext();
    }

    void disposeAPIRequestContext() {
        if (request != null) {
            request.dispose();
            request = null;
        }
    }

    void closePlaywright() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    @AfterAll
    void afterAll() {
        disposeAPIRequestContext();
        closePlaywright();
    }

    @Test
    @Order(1)
    void shouldReturnTodos() throws Exception {
//        Integer responseCode = request.get("/todos").status();
//        assertEquals(200, responseCode);

        Integer responseCode = 404;

        try {
            responseCode = request.get("/todos").status();
            System.out.println(responseCode);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        assertEquals(200, responseCode);

//        Exception exception = assertDoesNotThrow(() -> {
//            Integer responseCode = request.get("/todos").status();
//            assertEquals(200, responseCode);
//        });
//        System.out.println("Exception class: " + exception.getClass());
    }

    @Test
    @Order(2)
    void shouldCreateTodo() throws Exception {

        Map<String, String> data = new HashMap<>();
        data.put("title", "Task 3");
        data.put("completed", "true");

        APIResponse newIssue = request.post("/todos", RequestOptions.create().setData(data));
        assertTrue(newIssue.ok());

        Integer responseCode = request.get("/todos/3").status();
        assertEquals(200, responseCode);

    }

    @Test
    @Order(3)
    void shouldUpdateTodo() throws Exception {

        Map<String, String> data = new HashMap<>();
        data.put("title", "Task 4");
        data.put("completed", "true");

        APIResponse newIssue = request.put("/todos/3", RequestOptions.create().setData(data));
        assertTrue(newIssue.ok());

        APIResponse responsePUT = request.get("/todos/3");
        assertTrue(responsePUT.ok());

    }

    @Test
    @Order(4)
    void shouldDeleteTodo() throws Exception {

        APIResponse newIssue = request.delete("/todos/3");
        assertTrue(newIssue.ok());

        Integer responseCode = request.get("/todos/3").status();
        assertNotEquals(200, responseCode);

    }
}

