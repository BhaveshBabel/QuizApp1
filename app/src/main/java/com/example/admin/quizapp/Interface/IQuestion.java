package com.example.admin.quizapp.Interface;

import com.example.admin.quizapp.Model.CurrentQuestion;

public interface IQuestion {
    CurrentQuestion getSelectedAnswer();    // Get selected answer from user
    void showCorrectAnswer();       // Bold the correct answer
    void disableAnswer();           // Disable all checkbox
    void resetQuestion();           // Reset all functions on question
}
