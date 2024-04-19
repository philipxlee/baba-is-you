package oogasalad.controller.authoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class OpenAIClientTest {

  private OpenAIClient client;

  @Before
  public void setUp() throws Exception {
    client = new OpenAIClient();
  }

  @Test
  public void testClientConstructor() {
    assertNotNull(client);
  }
}
