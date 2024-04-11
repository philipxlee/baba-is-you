package oogasalad.model.gameplay.blocks;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class GrammarLoader {

  private static final String GRAMMAR_FILE = "/grammar/grammar.properties";
  private final Properties grammarProperties;

  public GrammarLoader() {
    grammarProperties = new Properties();
    try (InputStream inputStream = getClass().getResourceAsStream(GRAMMAR_FILE)) {
      grammarProperties.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Error loading grammar properties", e);
    }
  }

  public List<String> getGrammarForBlock(String blockName) {
    String grammar = grammarProperties.getProperty(blockName + ".grammar", "default");
    return Arrays.asList(grammar.split(","));
  }
}
