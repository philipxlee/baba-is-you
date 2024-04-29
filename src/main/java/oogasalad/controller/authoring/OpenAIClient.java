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
  private static final String PAYLOAD_CONFIG = "/openai/payload_config.properties";
  private static Properties config;
  private static Properties payloadConfig;

  /**
   * OpenAIClient constructor.
   */
  public OpenAIClient() {
    config = new Properties();
    payloadConfig = new Properties();
    try (InputStream configInputStream = getClass().getResourceAsStream(OPENAI_CONFIG);
        InputStream payloadInputStream = getClass().getResourceAsStream(PAYLOAD_CONFIG)) {
      config.load(configInputStream);
      payloadConfig.load(payloadInputStream);
    } catch (IOException e) {
      throw new RuntimeException("Error loading properties files", e);
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
    String promptTemplate = payloadConfig.getProperty("payload.template");
    return """
        {
            "model": "gpt-4-turbo",
            "messages": [
                {"role": "user", "content": "Assume you are an AI chat bot assistant."},
                {"role": "user", "content": "%s"}
            ],
            "temperature": 0.7
        }
        """.formatted(promptTemplate);
  }
}
