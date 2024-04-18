package oogasalad.controller.authoring;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class OpenAIClient {

  private static final String OPENAI_CONFIG = "/openai/openai.properties";
  private static Properties config;

  public OpenAIClient() {
    config = new Properties();
    try (InputStream inputStream = getClass().getResourceAsStream(OPENAI_CONFIG)) {
      config.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Error loading openai config", e);
    }
  }

  public CompletableFuture<String> fetchLevelConfiguration() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(config.getProperty("api.endpoint")))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + config.getProperty("api.key"))
        .POST(HttpRequest.BodyPublishers.ofString(buildPayload()))
        .build();

    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body);
  }

  private String buildPayload() {
    return """
        {
            "model": "gpt-4",
            "messages": [
                {"role": "system", "content": "Assume you are an AI chat bot assistant."},
                {"role": "user", "content": "Hi! How are you doing today?"}
            ],
            "temperature": 0.7
        }
        """;
  }
}
