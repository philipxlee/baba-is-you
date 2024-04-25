package oogasalad.database.records;

import java.util.Date;
import java.util.List;
import oogasalad.database.gamedata.ReplySchema;

public record CommentRecord(String username, String levelName, Date date, String comment,
                            List<ReplySchema> replies) {}

