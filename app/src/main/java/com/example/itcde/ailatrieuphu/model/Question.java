package com.example.itcde.ailatrieuphu.model;

/**
 * Created by itcde on 27/07/2017.
 */

public class Question {
    private String mQuestion;
    private String mAnswerA;
    private String mAnswerB;
    private String mAnswerC;
    private String mAnswerD;
    private int mRightAnswer;

    public Question() {
    }

    public Question(String question, String answer_a, String answer_b, String answer_c, String answer_d, int right_answer) {
        this.mQuestion = question;
        this.mAnswerA = answer_a;
        this.mAnswerB = answer_b;
        this.mAnswerC = answer_c;
        this.mAnswerD = answer_d;
        this.mRightAnswer = right_answer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        this.mQuestion = question;
    }

    public String getAnswerA() {
        return mAnswerA;
    }

    public void setAnswerA(String answer_a) {
        this.mAnswerA = answer_a;
    }

    public String getAnswerB() {
        return mAnswerB;
    }

    public void setAnswerB(String answer_b) {
        this.mAnswerB = answer_b;
    }

    public String getAnswerC() {
        return mAnswerC;
    }

    public void setAnswerC(String answer_c) {
        this.mAnswerC = answer_c;
    }

    public String getAnswerD() {
        return mAnswerD;
    }

    public void setAnswerD(String answer_d) {
        this.mAnswerD = answer_d;
    }

    public int getRightAnswer() {
        return mRightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.mRightAnswer = rightAnswer;
    }
}
