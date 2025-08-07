package com.example.autismapp.ui.maths

import com.example.autismapp.R

class AdditionSubtractionActivity : BaseMathsActivity() {
    override val questions = listOf(
        QuestionsMaths(R.drawable.toplama1, listOf(5,6,4), 5), //2+3
        QuestionsMaths(R.drawable.cikarma3, listOf(3,2,1), 3), //8-5
        QuestionsMaths(R.drawable.toplama2, listOf(10,11,12), 12), //4+8
        QuestionsMaths(R.drawable.toplama3, listOf(12,13,11), 11), //5+6
        QuestionsMaths(R.drawable.cikarma4, listOf(4,6,3), 4), //7-3
        QuestionsMaths(R.drawable.cikarma5, listOf(0,1,3), 1), //5-4
        QuestionsMaths(R.drawable.toplama4, listOf(14,15,5), 15), //10+5
        QuestionsMaths(R.drawable.cikarma1, listOf(2,8,4), 2),  //5-3
        QuestionsMaths(R.drawable.toplama5, listOf(14,0,10), 14), //7+7
        QuestionsMaths(R.drawable.cikarma2, listOf(5,7,4), 4), //10-6
        )

    override val initialPrompt: String
        get() = "Bu matematik işlemini çözebilir misin?"


}
