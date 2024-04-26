package oogasalad.database.gamedata;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import org.bson.Document;
import org.junit.Test;
import java.util.Date;

public class AbstractGameDataTest {

  @Test
  public void testGetDate() {
    AbstractGameData data = new AbstractGameData("user1", "level1", new Date()) {
      @Override
      public Document toDocument() {
        return new Document();
      }
    };
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    String expectedDate = dateFormat.format(new Date());
    assertEquals(expectedDate, data.getDate());
  }

  @Test
  public void testGetUsername() {
    AbstractGameData data = new AbstractGameData("user1", "level1", new Date()) {
      @Override
      public Document toDocument() {
        return new Document();
      }
    };
    assertEquals("user1", data.getUsername());
  }

  @Test
  public void testGetLevelName() {
    AbstractGameData data = new AbstractGameData("user1", "level1", new Date()) {
      @Override
      public Document toDocument() {
        return new Document();
      }
    };
    assertEquals("level1", data.getLevelName());
  }
}

