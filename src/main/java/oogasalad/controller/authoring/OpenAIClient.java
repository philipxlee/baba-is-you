package oogasalad.controller.authoring;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * OpenAIClient is the client to interface with the OpenAI API.
 */
public class OpenAIClient {

  private static final String OPENAI_CONFIG = "/openai/openai.properties";
  private static Properties config;

  /**
   * OpenAIClient constructor.
   */
  public OpenAIClient() {
    config = new Properties();
    try (InputStream inputStream = getClass().getResourceAsStream(OPENAI_CONFIG)) {
      config.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Error loading openai config", e);
    }
  }

  /**
   * Method to send a level configuration request to GPT-3.5-turbo.
   *
   * @return CompletableFuture object representing the HTTP request.
   */
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

  /**
   * Build the payload for the HTTP request.
   *
   * @return String representing the body of the request.
   */
  private String buildPayload() {
    return """
        {
            "model": "gpt-3.5-turbo",
            "messages": [
                {"role": "system", "content": "Assume you are an AI chat bot assistant."},
                {"role": "user", "content": "Hi! How are you doing today?"}
            ],
            "temperature": 0.7
        }
        """;
  }
}
