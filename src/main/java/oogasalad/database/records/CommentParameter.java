package oogasalad.database.records;

import java.util.Date;
import java.util.List;
import oogasalad.database.gamedata.ReplySchema;

public record CommentParameter(String username, String levelName, Date date, String comment,
                               List<ReplySchema> replies) {}

