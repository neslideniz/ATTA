package com.example.autismapp.ui.maths

import com.example.autismapp.R

class NumberRecognitionActivity : BaseMathsActivity() {
    override val questions = listOf(
        QuestionsMaths(R.drawable.number2, listOf(3, 5, 2), 2),
        QuestionsMaths(R.drawable.number5, listOf(5, 8, 4), 5),
        QuestionsMaths(R.drawable.number7, listOf(9, 5, 7), 7),
        QuestionsMaths(R.drawable.number10, listOf(9, 8, 10), 10),
        QuestionsMaths(R.drawable.number3, listOf(3, 1, 5), 3),
        QuestionsMaths(R.drawable.number6, listOf(6, 7, 9), 6),
        QuestionsMaths(R.drawable.number1, listOf(2, 1, 3), 1),
        QuestionsMaths(R.drawable.number8, listOf(10, 7, 8), 8),
        QuestionsMaths(R.drawable.number4, listOf(3, 5, 4), 4),
        QuestionsMaths(R.drawable.number9, listOf(9, 10, 8), 9)
    )

    override val initialPrompt: String
        get() = "Ekranda kaç tane nesne var?"
}
