package com.example.admin.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quizapp.Common.Common;
import com.example.admin.quizapp.Interface.IQuestion;
import com.example.admin.quizapp.Model.CurrentQuestion;
import com.example.admin.quizapp.Model.Question;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class QuestionFragment extends Fragment implements IQuestion{

    TextView txt_question;
    CheckBox chkA, chkB, chkC, chkD;
    FrameLayout layout_image;
    ProgressBar progressBar;

    Question question;
    int questionIndex = -1;


    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView =  inflater.inflate(R.layout.fragment_question, container, false);

        //GET QUESTIONS,,,,,"index" from bundle from QuestionActivity.java
        questionIndex = getArguments().getInt("index", -1);
        question = Common.questionList.get(questionIndex);

        if(question != null) {

            layout_image = (FrameLayout) itemView.findViewById(R.id.layout_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);

            if (question.isImageQuestion())
            {
                ImageView img_question = (ImageView) itemView.findViewById(R.id.img_questions);
                Picasso.get().load(question.getQuestionImage()).into(img_question, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                layout_image.setVisibility(View.GONE);


            txt_question = (TextView) itemView.findViewById(R.id.txt_question);
            txt_question.setText(question.getQuestionText());

            chkA = (CheckBox) itemView.findViewById(R.id.chkA);
            chkA.setText(question.getAnswerA());
            chkA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        Common.selected_values.add(chkA.getText().toString());
                    else
                        Common.selected_values.remove(chkA.getText().toString());
                }
            });

            chkB = (CheckBox) itemView.findViewById(R.id.chkB);
            chkB.setText(question.getAnswerB());
            chkB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        Common.selected_values.add(chkB.getText().toString());
                    else
                        Common.selected_values.remove(chkB.getText().toString());
                }
            });

            chkC = (CheckBox) itemView.findViewById(R.id.chkC);
            chkC.setText(question.getAnswerC());
            chkC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        Common.selected_values.add(chkC.getText().toString());
                    else
                        Common.selected_values.remove(chkC.getText().toString());
                }
            });

            chkD = (CheckBox) itemView.findViewById(R.id.chkD);
            chkD.setText(question.getAnswerD());
            chkD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        Common.selected_values.add(chkD.getText().toString());
                    else
                        Common.selected_values.remove(chkD.getText().toString());
                }
            });

        }
        return itemView;
    }

    @Override
    public CurrentQuestion getSelectedAnswer() {

        //This will return state of answer
        // Right, Wrong or normal

        CurrentQuestion currentQuestion = new CurrentQuestion(questionIndex, Common.ANSWER_TYPE.NO_ANSWER);
        StringBuilder result  = new StringBuilder();
        if(Common.selected_values.size() > 1)
        {
            // If multi-choice
            // Split answer to array
            Object[] arrayAnswer = Common.selected_values.toArray();
            for(int i = 0; i < arrayAnswer.length; i++)
                if(i < arrayAnswer.length - 1)
                    result.append(new StringBuilder(((String)arrayAnswer[i]).substring(0, 1)).append(","));
                else
                    result.append(new StringBuilder((String)arrayAnswer[i]).substring(0, 1));
        }
        else if(Common.selected_values.size() == 1)
        {
            // If only single choice
            Object[] arrayAnswer = Common.selected_values.toArray();
            result.append((String)arrayAnswer[0]).substring(0,1);
        }

        if(question != null)
        {
            // Compare user answer with the correct answer
            if (!TextUtils.isEmpty(result)) {
                if (result.toString().equals(question.getCorrectAnswer()))
                    currentQuestion.setType(Common.ANSWER_TYPE.RIGHT_ANSWER);
                else
                    currentQuestion.setType(Common.ANSWER_TYPE.WRONG_ANSWER);
            }
            else
                currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
        }
        else
        {
            Toast.makeText(getContext(),"Cannot get Question",Toast.LENGTH_SHORT).show();
            currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
        }
        Common.selected_values.clear();
        return currentQuestion;
    }

    @Override
    public void showCorrectAnswer() {
        //Bold the correct answer
        String[] correctAnswer = question.getCorrectAnswer().split(",");
        for(String answer : correctAnswer)
        {
            if(answer.equals("A")){
                chkA.setTypeface(null, Typeface.BOLD);
                chkA.setTextColor(Color.RED);
            }
            else if(answer.equals("B")){
                chkB.setTypeface(null, Typeface.BOLD);
                chkB.setTextColor(Color.RED);
            }
            else if(answer.equals("C")){
                chkC.setTypeface(null, Typeface.BOLD);
                chkC.setTextColor(Color.RED);
            }
            else if(answer.equals("D")){
                chkD.setTypeface(null, Typeface.BOLD);
                chkD.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public void disableAnswer() {
        chkA.setEnabled(false);
        chkB.setEnabled(false);
        chkC.setEnabled(false);
        chkD.setEnabled(false);
    }

    @Override
    public void resetQuestion() {
        chkA.setEnabled(true);
        chkB.setEnabled(true);
        chkC.setEnabled(true);
        chkD.setEnabled(true);

        chkA.setChecked(false);
        chkB.setChecked(false);
        chkC.setChecked(false);
        chkD.setChecked(false);

        chkA.setTypeface(null, Typeface.NORMAL);
        chkA.setTextColor(Color.BLACK);
        chkB.setTypeface(null, Typeface.NORMAL);
        chkB.setTextColor(Color.BLACK);
        chkC.setTypeface(null, Typeface.NORMAL);
        chkC.setTextColor(Color.BLACK);
        chkD.setTypeface(null, Typeface.NORMAL);
        chkD.setTextColor(Color.BLACK);
    }
}
