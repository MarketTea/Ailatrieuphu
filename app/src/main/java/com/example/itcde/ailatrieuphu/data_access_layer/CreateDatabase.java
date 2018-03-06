package com.example.itcde.ailatrieuphu.data_access_layer;

public class CreateDatabase {

    private Database database;

    public CreateDatabase(Database database) {
        this.database = database;
    }


    public void createTableQuestion() {
        String sql = "CREATE TABLE IF NOT EXIST question(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                "question TEXT" +
                "answer_a TEXT" +
                "answer_b TEXT" +
                "answer_c TEXT" +
                "answer_d TEXT" +
                "right_answer int)";

        database.queryData(sql);

    }

    public void createTableRank() {
        String sql = "CREATE TABLE IF NOT EXIST rank(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                "name TEXT" +
                "money TEXT)";

        database.queryData(sql);
    }

    //Create data for table "work"

}
