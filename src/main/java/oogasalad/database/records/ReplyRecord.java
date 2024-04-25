package oogasalad.database.records;

import java.util.Date;

public record ReplyRecord(String username, String levelName, Date date, String replyText) {

}
