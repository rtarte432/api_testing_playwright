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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
                .setBaseURL("http://localhost:3000")
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

//    @Test
//    void shouldCreateBugReport() {
//        Map<String, String> data = new HashMap<>();
//        data.put("title", "[Bug] report 1");
//        data.put("body", "Bug description");
//        APIResponse newIssue = request.post("/repos/" + USER + "/" + REPO + "/issues",
//                RequestOptions.create().setData(data));
//        assertTrue(newIssue.ok());
//
//        APIResponse issues = request.get("/repos/" + USER + "/" + REPO + "/issues");
//        assertTrue(issues.ok());
//        JsonArray json = new Gson().fromJson(issues.text(), JsonArray.class);
//        JsonObject issue = null;
//        for (JsonElement item : json) {
//            JsonObject itemObj = item.getAsJsonObject();
//            if (!itemObj.has("title")) {
//                continue;
//            }
//            if ("[Bug] report 1".equals(itemObj.get("title").getAsString())) {
//                issue = itemObj;
//                break;
//            }
//        }
//        assertNotNull(issue);
//        assertEquals("Bug description", issue.get("body").getAsString(), issue.toString());
//    }
//
//    @Test
//    void shouldCreateFeatureRequest() {
//        Map<String, String> data = new HashMap<>();
//        data.put("title", "[Feature] request 1");
//        data.put("body", "Feature description");
//        APIResponse newIssue = request.post("/repos/" + USER + "/" + REPO + "/issues",
//                RequestOptions.create().setData(data));
//        assertTrue(newIssue.ok());
//
//        APIResponse issues = request.get("/repos/" + USER + "/" + REPO + "/issues");
//        assertTrue(issues.ok());
//        JsonArray json = new Gson().fromJson(issues.text(), JsonArray.class);
//        JsonObject issue = null;
//        for (JsonElement item : json) {
//            JsonObject itemObj = item.getAsJsonObject();
//            if (!itemObj.has("title")) {
//                continue;
//            }
//            if ("[Feature] request 1".equals(itemObj.get("title").getAsString())) {
//                issue = itemObj;
//                break;
//            }
//        }
//        assertNotNull(issue);
//        assertEquals("Feature description", issue.get("body").getAsString(), issue.toString());
//    }
}

