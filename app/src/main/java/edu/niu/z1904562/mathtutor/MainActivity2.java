package edu.niu.z1904562.mathtutor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nodexsoutions.mathtutorapp.R;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private TextView question,question_no,title,correct_ans,wrong_ans,ok;
    private int question_count = 0;
    private AppCompatButton next;
    private EditText answer;
    private int type;
    private int difficulty = 0;
    private AlertDialog dialog;
    private int a,b,c,d,e,f,g,h,right,wrong;
    private double ans;
    private Random random;
    private ImageView stars;
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();

        type = getIntent().getExtras().getInt("type");

        if (type == 1){
            title.setText("Addition");
        }else if (type == 2){
            title.setText("Subtraction");
        }else if (type == 3){
            title.setText("Multiplication");
        }else if (type == 4){
            title.setText("Division");
        }
        setQuestion();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter your answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (type != 4){
                    if (question_count<11){
                        if (String.valueOf(Math.round(ans)).equals(answer.getText().toString().trim())){
                            correctDialogue();
                        }else {
                            wrongDialogue();
                        }
                    }else {
                        if (String.format("%.2f",ans).equals(answer.getText().toString().trim())){
                            correctDialogue();
                        }else {
                            wrongDialogue();
                        }
                    }
                }else {
                    if (String.format("%.2f",ans).equals(answer.getText().toString().trim())){
                        correctDialogue();
                    }else {
                        wrongDialogue();
                    }
                }

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init(){
        random = new Random();
        question = findViewById(R.id.question);
        question_no = findViewById(R.id.question_no);
        title = findViewById(R.id.title);
        next = findViewById(R.id.next);
        answer = findViewById(R.id.answer);
        wrong = right = 0;
    }

    private void setQuestion(){
        answer.setText("");
        question_count++;
        a = b = c = d = e = f = g = h = 0;
        ans = 0;
        question_no.setText("Question No. "+question_count);
        int temp = random.nextInt(300) + 1;
        a =   temp < 0 ? temp+300 : temp + difficulty;
        Log.d(TAG, "setQuestion:a after "+a);
        temp = random.nextInt(300);
        b = temp < 0 ? temp+300 : temp + difficulty;
        temp = random.nextInt(300);
        c = temp < 0 ? temp+300 : temp + difficulty;
        temp = random.nextInt(300);
        d = temp < 0 ? temp+300 : temp + difficulty;
        temp = random.nextInt(300);
        e = temp < 0 ? temp+300 : temp + difficulty;

        if (question_count<=10){
            if (type == 1){
                if (difficulty>800){
                    ans = a + b + c + d + e;
                    question.setText(a+" + "+b+" + "+c+" + "+d+" + "+e);
                }else if (difficulty>500){
                    ans = a + b + c;
                    question.setText(a+" + "+b+" + "+c);
                }else {
                    ans = a + b;
                    question.setText(a+" + "+b);
                }
            }
            else if (type == 2){
                if (difficulty>800){
                    ans = a - b - c - d - e;
                    question.setText(a+" - "+b+" - "+c+" - "+d+" - "+e);
                }else if (difficulty>500){
                    ans = a - b - c;
                    question.setText(a+" - "+b+" - "+c);
                }else {
                    ans = a - b;
                    question.setText(a+" - "+b);
                }
            }
            else if (type == 3){
                if (difficulty>800){
                    ans = a * b * c * d * e;
                    question.setText(a+" x "+b+" x "+c+" x "+d+" x "+e);
                }else if (difficulty>500){
                    ans = a * b * c;
                    question.setText(a+" x "+b+" x "+c);
                }else {
                    ans = a * b;
                    question.setText(a+" x "+b);
                }
            }
            else if (type == 4){
                ans = a / Float.parseFloat(String.valueOf(b));
                question.setText(a+" / "+b);
            }
        }else {
            int select_eq = new Random().nextInt(6);
            switch (select_eq){
                case 0:
                    ans = a + (b * c) + (d / Float.parseFloat(String.valueOf(e)));
                    question.setText(a+" + "+b+" * "+c+" + "+d+" / "+e);
                    break;
                case 1:
                    ans = a + b - c + (d / Float.parseFloat(String.valueOf(e)));
                    question.setText(a+" - "+b+" * "+c+" + "+d+" / "+e);
                    break;
                case 2:
                    ans = a + (b * c) - (d / Float.parseFloat(String.valueOf(e)));
                    question.setText(a+" + "+b+" * "+c+" - "+d+" / "+e);
                    break;
                case 3:
                    ans = (a * (b * c)) + (d / Float.parseFloat(String.valueOf(e)));
                    question.setText(a+" * "+b+" * "+c+" + "+d+" / "+e);
                    break;
                case 4:
                    ans = a + (b * c) + (d * e);
                    question.setText(a+" - "+b+" * "+c+" + "+d+" * "+e);
                    break;
                case 5:
                    ans = a + c - ((d * e) / Float.parseFloat(String.valueOf(b)));
                    question.setText(a+" + "+c+" * "+d+" + "+e+" / "+b);
                    break;
            }
        }

    }

    private void correctDialogue(){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.correct_dialogue,null);

        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty = difficulty + 100;
                right++;
                if (question_count == 10){
                    showCombinationDialogue();
                } else {
                    if (question_count == 15){
                        resultDialogue();
                    }else {
                        setQuestion();
                    }
                }
                dialog.cancel();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void wrongDialogue(){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.wrong_dialogue,null);

        TextView correct_answer = view.findViewById(R.id.correct_answer);
        if (type != 4){
            if (question_count<11){
                correct_answer.setText("Correct answer is "+Math.round(ans));
            }else {
                correct_answer.setText("Correct answer is "+String.format("%.2f",ans));
            }
        } else {
            correct_answer.setText("Correct answer is "+String.format("%.2f",ans));
        }
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                wrong++;
                if (difficulty>0){
                    difficulty = difficulty - 100;
                }
                if (question_count == 10){
                    showCombinationDialogue();
                } else {
                    if (question_count == 15){
                        resultDialogue();
                    }else {
                        setQuestion();
                    }
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showCombinationDialogue(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setMessage("Do you want to try the questions that are a combination of different operations");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
                setQuestion();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
                resultDialogue();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void resultDialogue(){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.result_dialogue,null);

        stars = view.findViewById(R.id.stars);
        correct_ans = view.findViewById(R.id.correct_ans);
        wrong_ans = view.findViewById(R.id.wrong_ans);
        ok = view.findViewById(R.id.ok);

        correct_ans.setText(String.valueOf(right));
        wrong_ans.setText(String.valueOf(wrong));

        if (right>8){
            stars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star3_fill));
        }else if (right>=5){
            stars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star2_fill));
        }else if (right>=1){
            stars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star1_fill));
        }else {
            stars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star3_empty));
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}