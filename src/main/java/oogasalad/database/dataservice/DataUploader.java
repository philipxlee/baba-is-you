package oogasalad.database.dataservice;

import com.mongodb.client.MongoDatabase;

public class DataUploader {

  MongoDatabase database;

  public DataUploader(MongoDatabase database) {
    this.database = database;
  }

}
