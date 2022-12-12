package com.quiza.ui.rv

class Data(
     question: String,
     option1: String,
     option2: String,
     option3: String,
     rightAnswer: Int,
     explain: String,
     fav: String
){

    var question: String
    var option1: String
    var option2: String
    var option3: String
    var rightAnswer: Int
    var explain: String
    var fav: String

    init {
        this.question = question
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.rightAnswer = rightAnswer
        this.explain = explain
        this.fav = fav
    }


    fun getFavor(): String {
        return fav
    }
    fun setFavStatus(fav: String) {
        this.fav = fav
    }


}
