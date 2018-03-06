package com.example.itcde.ailatrieuphu.data_logic_layer;

import android.database.Cursor;

import com.example.itcde.ailatrieuphu.data_access_layer.Database;
import com.example.itcde.ailatrieuphu.model.Question;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by itcde on 28/07/2017.
 */

public class DLLQuestion {
    public static ArrayList<Question> ARR_QUESTION_EASY;
    public static ArrayList<Question> ARR_QUESTION_MEDIUM;
    public static ArrayList<Question> ARR_QUESTION_HARD;

    private Database database;

    public DLLQuestion(Database data){
        this.database = data;
    }

    //Truyền vào một level, Đọc tất cả "Question" có trong dữ liệu bằng mức leve
    //truyền vào
    public ArrayList<Question> getArrayQuestion(int level) throws IOException {
        String sql = "SELECT * FROM question WHERE level = " + level ;


        Cursor cursor = database.getData(sql);

        ArrayList<Question> arrQuestion = new ArrayList<>();
        while(cursor.moveToNext()){
            Question que = new Question();
            que.setQuestion(cursor.getString(1));
            que.setAnswerA(cursor.getString(2));
            que.setAnswerB(cursor.getString(3));
            que.setAnswerC(cursor.getString(4));
            que.setAnswerD(cursor.getString(5));
            que.setRightAnswer(cursor.getInt(6));

            arrQuestion.add(que);
        }

        database.closeDatabase();
        return arrQuestion;
    }

}
