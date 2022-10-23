package com.quiza.data;

public class Db_data {
    private String qiuestion;
    private String option1;
    private String option2;
    private String option3;
    private int answer_number;


    public Db_data(String qiuestion, String option1, String option2, String option3, int answer_number) {
        this.qiuestion = qiuestion;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answer_number = answer_number;
    }
    public String getQuestion() {
        return qiuestion;
    }

    public void setQuestion(String qiuestion) {
        this.qiuestion = qiuestion;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswer_number() {
        return answer_number;
    }

    public void setAnswer_number(int answer_number) {
        this.answer_number = answer_number;
    }





        }
