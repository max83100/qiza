package com.quiza

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import com.quiza.Question

class Question : Parcelable {
    var question: String?
    var option1: String?
    var option2: String?
    var option3: String?
    var answer_number: Int

    constructor(
        qiuestion: String?,
        option1: String?,
        option2: String?,
        option3: String?,
        answer_number: Int
    ) {
        question = qiuestion
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.answer_number = answer_number
    }

    protected constructor(`in`: Parcel) {
        question = `in`.readString()
        option1 = `in`.readString()
        option2 = `in`.readString()
        option3 = `in`.readString()
        answer_number = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(question)
        parcel.writeString(option1)
        parcel.writeString(option2)
        parcel.writeString(option3)
        parcel.writeInt(answer_number)
    }

    companion object {
        val CREATOR: Creator<Question?> = object : Creator<Question?> {
            override fun createFromParcel(`in`: Parcel): Question? {
                return Question(`in`)
            }

            override fun newArray(size: Int): Array<Question?> {
                return arrayOfNulls(size)
            }
        }
    }

     object CREATOR : Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}