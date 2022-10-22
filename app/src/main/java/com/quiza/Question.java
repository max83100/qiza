package com.quiza;

import android.os.Parcel;
import android.os.Parcelable;

public class Question  implements Parcelable {
    private String qiuestion;
    private String option1;
    private String option2;
    private String option3;
    private int answer_number;

    public Question() {}

    public Question(String qiuestion, String option1, String option2, String option3, int answer_number) {
        this.qiuestion = qiuestion;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answer_number = answer_number;
    }

    protected Question(Parcel in) {
        qiuestion = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        answer_number = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(qiuestion);
        parcel.writeString(option1);
        parcel.writeString(option2);
        parcel.writeString(option3);
        parcel.writeInt(answer_number);
    }
}
