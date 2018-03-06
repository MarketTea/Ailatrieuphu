package com.example.itcde.ailatrieuphu.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itcde.ailatrieuphu.R;
import com.example.itcde.ailatrieuphu.data_access_layer.Database;
import com.example.itcde.ailatrieuphu.data_logic_layer.DLLQuestion;
import com.example.itcde.ailatrieuphu.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnHalf;
    private ImageView imbCallRelative;
    private ImageView imbAudienceComments;
    private ImageView imbChangeQuestion;
    private TextView txvTime;
    private TextView txvMoney;
    private TextView txvQuestionNumber;
    private TextView txvQuestion;
    private TextView txvAnswerA;
    private TextView txvAnswerB;
    private TextView txvAnswerC;
    private TextView txvAnswerD;

    private ArrayList<Question> arrQuestionEasy;
    private ArrayList<Question> arrQuestionMedium;
    private ArrayList<Question> arrQuestionHard;

    private Database database;
    private DLLQuestion dllQuestion;

    private int money;
    private int questionNumber;
    private int choiceAnswer;
    private int rightAnswer;
    private CountDownTimer downTimer = null;
    private boolean isUsedHelpHaft, isUsedHelpCallRelative, isUsedHelpAudienceComments, isUsedHelpChangeQuestion;

    private boolean[] isAppear;

    private Dialog dialogRepeatChoiceAnswer;
    private Dialog dialogFinish;
    private Dialog dialogCallRelative;
    private AlertDialog alertDialog;

    private TextView txvAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        getWidget();
        setEvent();
        createDialog();
        initializeObjectAndValue();
        getQuestionFromData();
        resetAll();
    }

    private void initializeObjectAndValue(){
        try {
            this.database = new Database(this);
        } catch (IOException e) {
            Log.d("error", e.toString());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        this.dllQuestion = new DLLQuestion(database);
        this.arrQuestionEasy = new ArrayList<>();
        this.arrQuestionHard = new ArrayList<>();
        this.arrQuestionMedium = new ArrayList<>();

        isAppear = new boolean[100];

    }

    private void resetAll(){
        money = 0;
        questionNumber = 0;
        this.txvMoney.setText("0");
        for(int i = 0; i < 100; i++){
            isAppear[i] = false;
        }
        newQuestion();
        if(dialogCallRelative.isShowing()){
            dialogCallRelative.dismiss();
        }
        if(dialogRepeatChoiceAnswer.isShowing()){
            dialogRepeatChoiceAnswer.dismiss();
        }

        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }


        isUsedHelpHaft = false;
        isUsedHelpAudienceComments = false;
        isUsedHelpCallRelative = false;
        isUsedHelpChangeQuestion = false;
        btnHalf.setBackgroundResource(R.drawable.button_help_valid);
        imbAudienceComments.setBackgroundResource(R.drawable.button_help_valid);
        imbCallRelative.setBackgroundResource(R.drawable.button_help_valid);
        imbChangeQuestion.setBackgroundResource(R.drawable.button_help_valid);
    }

    private void resetBackgroudTextViewAnswer(){
        this.txvAnswerA.setBackgroundResource(R.drawable.answer);
        this.txvAnswerB.setBackgroundResource(R.drawable.answer);
        this.txvAnswerC.setBackgroundResource(R.drawable.answer);
        this.txvAnswerD.setBackgroundResource(R.drawable.answer);
    }

    private void getWidget(){
        this.btnHalf = (Button) findViewById(R.id.btn_half);
        this.imbCallRelative = (ImageView) findViewById(R.id.imb_callRelative);
        this.imbAudienceComments = (ImageView) findViewById(R.id.imb_audienceComments);
        this.imbChangeQuestion = (ImageView) findViewById(R.id.imb_changeQuestion);
        this.txvTime = (TextView) findViewById(R.id.txv_time);
        this.txvMoney = (TextView) findViewById(R.id.txv_money);
        this.txvAnswerA = (TextView) findViewById(R.id.txv_answerA);
        this.txvAnswerC = (TextView) findViewById(R.id.txv_answerC);
        this.txvAnswerD = (TextView) findViewById(R.id.txv_answerD);
        this.txvAnswerB = (TextView) findViewById(R.id.txv_answerB);
        this.txvQuestion = (TextView) findViewById(R.id.txv_question);
        this.txvQuestionNumber = (TextView) findViewById(R.id.txv_questionNumber);
    }

    private void setEvent(){
        this.btnHalf.setOnClickListener(this);
        this.imbCallRelative.setOnClickListener(this);
        this.imbAudienceComments.setOnClickListener(this);
        this.imbChangeQuestion.setOnClickListener(this);
        this.txvAnswerA.setOnClickListener(this);
        this.txvAnswerB.setOnClickListener(this);
        this.txvAnswerC.setOnClickListener(this);
        this.txvAnswerD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.txv_answerA:
                //Nếu TextView không có text(text = "") thì ko làm gì cả
                if(txvAnswerA.getText().toString().equals("")){
                    break;
                }
                choiceAnswer = 1;
                txvAnswer = txvAnswerA;
                repeatChoiceAnswer("A");
                break;
            case R.id.txv_answerB:
                if(txvAnswerB.getText().toString().equals("")){
                    break;
                }
                choiceAnswer = 2;
                txvAnswer = txvAnswerB;
                repeatChoiceAnswer("B");
                break;
            case R.id.txv_answerC:
                if(txvAnswerC.getText().toString().equals("")){
                    break;
                }
                choiceAnswer = 3;
                txvAnswer = txvAnswerC;
                repeatChoiceAnswer("C");
                break;
            case R.id.txv_answerD:
                if(txvAnswerD.getText().toString().equals("")){
                    break;
                }
                choiceAnswer = 4;
                txvAnswer = txvAnswerD;
                repeatChoiceAnswer("D");
                break;
            case R.id.btn_half:
                handleRepeatChoiceHelp(1);
                break;
            case R.id.imb_callRelative:
                handleRepeatChoiceHelp(2);
                break;
            case R.id.imb_audienceComments:
                handleRepeatChoiceHelp(3);
                break;
            case R.id.imb_changeQuestion:
                handleRepeatChoiceHelp(4);
                break;
        }
    }

    private void createDialog(){
        //Tạo dialog khi thua hoặc kết thúc(khi người chơi trả lời được 15 câu hỏi)
        this.dialogFinish = new Dialog(this);
        dialogFinish.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFinish.setCanceledOnTouchOutside(false);
        dialogFinish.setContentView(R.layout.dialog_finish);

        //Khi người chơi chọn một đáp án, có thể người chơi chọn nhầm. Để chắc chắn rằng họ đã quyết định chọn đáp án đó
        //Dialog này sẽ đó vai trò hỏi lại người chơi.
        dialogRepeatChoiceAnswer = new Dialog(this);
        dialogRepeatChoiceAnswer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRepeatChoiceAnswer.setCanceledOnTouchOutside(false);
        dialogRepeatChoiceAnswer.setContentView(R.layout.dialog_repeat_choice_answer);

        //Dialog cho người dùng chọn người thân để gọi trợ giúp
        dialogCallRelative = new Dialog(this);
        dialogCallRelative.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCallRelative.setCanceledOnTouchOutside(false);
        dialogCallRelative.setContentView(R.layout.dialog_call_relative);

    }

    private void repeatChoiceAnswer(String answer){
        TextView txvMessage = (TextView) dialogRepeatChoiceAnswer.findViewById(R.id.txv_message);
        Button btnConfirm = (Button) dialogRepeatChoiceAnswer.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) dialogRepeatChoiceAnswer.findViewById(R.id.btn_cancel);

        txvMessage.setText("Bạn có chắc chắn chọn phương án " + answer);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer();
                dialogRepeatChoiceAnswer.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRepeatChoiceAnswer.dismiss();
            }
        });

        dialogRepeatChoiceAnswer.show();
    }

    //Tạo một Alert Dialog thực hiện 2 công việc
    // 1 - Kiểm tra quyền trợ giúp có được dùng hay chưa
    // 2 - Nếu chưa thông báo cho người chơi để đảm bảo chắc chắn rằng họ muốn dùng quyền trợ giúp vừa chọn
    private void handleRepeatChoiceHelp(int help){

        //Tạo Alert để hỏi lại người chơi. Nếu quyền trợ giúp chưa được dùng
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Trợ giúp");
        builder.setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        switch (help){
            case 1:
                if(isUsedHelpHaft){
                    return ;
                }
                else{
                    builder.setMessage("Bạn chắc chắn chọn quyền trợ giúp 50: 50");
                    builder.setNegativeButton("Chắn chắn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            helpHaft();
                            isUsedHelpHaft = true;
                            btnHalf.setBackgroundResource(R.drawable.button_help_invalid);
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();

                }
                break;
            case 2:
                if(isUsedHelpCallRelative){
                    return ;
                }
                else{
                    builder.setMessage("Bạn chắc chắn chọn quyền trợ giúp điện thoại tư vấn");
                    builder.setNegativeButton("Chắn chắn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            helpCallRelative();
                            isUsedHelpCallRelative = true;
                            imbCallRelative.setBackgroundResource(R.drawable.button_help_invalid);
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            case 3:
                if(isUsedHelpAudienceComments){
                    return ;
                }
                break;
            case 4:
                if(isUsedHelpChangeQuestion){
                    return ;
                }
                else{
                    builder.setMessage("Bạn chắc chắn chọn quyền trợ giúp đổi câu hỏi mới");
                    builder.setNegativeButton("Chắn chắn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            questionNumber--;
                            newQuestion();
                            isUsedHelpChangeQuestion = true;
                            imbChangeQuestion.setBackgroundResource(R.drawable.button_help_invalid);
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
        }
    }

    private void handleAnswer(){
        TextView txvRightAnswer = getTextViewAnswerByLocation(rightAnswer);
        txvAnswer.setBackgroundResource(R.drawable.choice_answer);
        if(rightAnswer != choiceAnswer){
            falseAnswer(txvRightAnswer);
        }
        else{
            rightAnswer(txvRightAnswer);
        }
    }

    //Trả về textView hiện đang chứa câu trả lời chính xác
    private TextView getTextViewAnswerByLocation(int loc){
        switch (loc){
            case 1:
                return txvAnswerA;
            case 2:
                return txvAnswerB;
            case 3:
                return txvAnswerC;
            case 4:
                return txvAnswerD;
        }
        return null;
    }

    //Tạo hiện ứng khi chọn câu trả lời đúng. Và xử lí để người chơi tiếp tục với câu hỏi tiếp theo
    private void rightAnswer(final TextView txv){

        final int[] count = {1};
        //Tạo hiệu ứng cho textView có câu trả lời chính xác
        final CountDownTimer countDownTimer = new CountDownTimer(1200, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(count[0] % 2 != 0){
                    txv.setBackgroundResource(R.drawable.right_answer);
                }
                else{
                    txv.setBackgroundResource(R.drawable.answer);
                }
                count[0]++;
            }

            @Override
            public void onFinish() {
                resetBackgroudTextViewAnswer();

                //Tiền thưởng cho câu trả lời đúng
                txvMoney.setText(getMoney(questionNumber) + "");
                //Nếu trả lời đúng cả 15 câu hỏi
                if(questionNumber == 15){
                    doFinish(2);
                }
                else{
                    newQuestion();
                }
            }
        };
        countDownTimer.start();
    }



    //Tạo hiện ứng khi chọn câu trả lời sai. Và kết thúc lượt chơi.
    private void falseAnswer(final TextView txv){
        final int[] count = {1};
        //Tạo hiệu ứng cho textView có câu trả lời chính xác
        final CountDownTimer countDownTimer = new CountDownTimer(1200, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(count[0] % 2 != 0){
                    txv.setBackgroundResource(R.drawable.false_answer);
                }
                else{
                    txv.setBackgroundResource(R.drawable.answer);
                }
                count[0]++;
            }

            @Override
            public void onFinish() {
                doFinish(1);
            }
        };
        countDownTimer.start();
    }

    //Khi trò chơi kết thúc, hoặc người chơi trả lời sai một câu hỏi
    private void doFinish(int type){
        TextView txvScore = (TextView) dialogFinish.findViewById(R.id.txv_info);
        Button btnRetry = (Button) dialogFinish.findViewById(R.id.btn_retry);

        if(type == 1){
            txvScore.setText("Bạn nhận được số tiền: " + this.txvMoney.getText());
        }
        else{
            txvScore.setText("Chúc mừng bạn đã chiến thắng: " + this.txvMoney.getText());
        }

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackgroudTextViewAnswer();
                resetAll();
                dialogFinish.dismiss();
            }
        });
        dialogFinish.show();
    }

    //Kiểm tra. Chọn ra mức câu hỏi (dễ, trung bình, khó)
    private void newQuestion(){

        questionNumber++;
        this.txvQuestionNumber.setText("Câu hỏi số: " + questionNumber);
        if(this.questionNumber <= 5){
            loadQuestion(arrQuestionEasy);
        }
        else if(this.questionNumber <= 10){
            loadQuestion(arrQuestionMedium);
        }
        else{
            loadQuestion(arrQuestionHard);
        }

        //Đếm thời gian
        timeToChooseAnswer();
    }

    private void timeToChooseAnswer(){
        //Bắt đầu tính thời gian cho câu hỏi
        //Trước khi bắt đầu tính thời gian thì phải kết thúc tiểu trình downTimer trước đó. Nếu có
        if(downTimer != null){
            downTimer.cancel();
        }
        this.downTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txvTime.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                txvTime.setText("0");
                doFinish(1);
            }
        };
        downTimer.start();
    }

    private int randomLocationQuestion(ArrayList arrList){
        Random random = new Random();
        int loc = random.nextInt(arrList.size());
        return loc;
    }

    //Tải câu hỏi và câu trả lời lên Activity
    private void loadQuestion(ArrayList<Question> arrQue){
        int loc = randomLocationQuestion(arrQue);
        this.txvQuestion.setText(arrQue.get(loc).getQuestion());
        this.txvAnswerA.setText( "A: " + arrQue.get(loc).getAnswerA());
        this.txvAnswerB.setText( "B: " + arrQue.get(loc).getAnswerB());
        this.txvAnswerC.setText( "C: " + arrQue.get(loc).getAnswerC());
        this.txvAnswerD.setText( "D: " + arrQue.get(loc).getAnswerD());
        this.rightAnswer = arrQue.get(loc).getRightAnswer();
    }

    private void getQuestionFromData(){
        try {
            arrQuestionEasy = dllQuestion.getArrayQuestion(1);
            arrQuestionMedium = dllQuestion.getArrayQuestion(2);
            arrQuestionHard = dllQuestion.getArrayQuestion(3);
        } catch (IOException e) {
            Log.d("error", e.toString());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    //Xử lí các quyền trợ giúp

    //Quyền trợ giúp 50:50
    private void helpHaft(){
        int locationRemoveAnswerFirst, locationRemoveAnswerSecond;
        Random rand = new Random();
        //Tìm đáp án sẽ bị loại bỏ đầu tiên
        while(true){
            locationRemoveAnswerFirst = rand.nextInt(4) + 1;
            if(locationRemoveAnswerFirst != rightAnswer){
                break;
            }
        }
        //Tìm đáp án sẽ bị loại bỏ thứ 2
        while(true){
            locationRemoveAnswerSecond = rand.nextInt(4) + 1;
            if(locationRemoveAnswerSecond != rightAnswer && locationRemoveAnswerSecond != locationRemoveAnswerFirst){
                break;
            }
        }

        //Lấy ra 2 TextView đang chứa 2 đáp án cần loại bỏ
        TextView txvAnswerFirst = getTextViewAnswerByLocation(locationRemoveAnswerFirst);
        TextView txvAnswerSecond = getTextViewAnswerByLocation(locationRemoveAnswerSecond);

        //Loại bỏ text của 2 TextView
        txvAnswerFirst.setText("");
        txvAnswerSecond.setText("");

    }

    //Quyền trợ giúp gọi điện thoại cho người thân
    private void helpCallRelative(){
        ImageButton imbMessi = (ImageButton) dialogCallRelative.findViewById(R.id.imb_messi);
        ImageButton imbTamLe = (ImageButton) dialogCallRelative.findViewById(R.id.imb_tamLe);
        ImageButton imbElonMusk = (ImageButton) dialogCallRelative.findViewById(R.id.imb_elonMusk);
        ImageButton imbMarkZuckerberg = (ImageButton) dialogCallRelative.findViewById(R.id.imb_markZuckerberg);

        imbMessi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallRelative.dismiss();
                informedAnswerSuggested("Messi");
            }
        });

        imbTamLe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallRelative.dismiss();
                informedAnswerSuggested("Tam Le");
            }
        });

        imbElonMusk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallRelative.dismiss();
                informedAnswerSuggested("Elon Musk");
            }
        });

        imbMarkZuckerberg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallRelative.dismiss();
                informedAnswerSuggested("Mark Zuckerberg");
            }
        });

        dialogCallRelative.show();


    }

    private void informedAnswerSuggested(String person){
        String suggestAnswer = getAnswerHelp();

        //Tạo một Alert thông báo gợi ý cho người chơi
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gọi điện thoại cho " + person);
        if(suggestAnswer.equals("")){
            builder.setMessage(person + " không có gợi ý dành cho bạn");
        }
        else{
            builder.setMessage(person + " khuyên bạn nên chọn " + suggestAnswer);
        }
        builder.setNegativeButton("Đã hiểu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    //Xử lí và đưa ra một gợi ý chọn câu trả lời cho người chơi
    //Xác suất cho ra một gợi ý câu trả lời đúng là 80%
    //Trả về một String là gợi ý cho câu trả lời ("A", "B", "C" hoặc "D")
    private String getAnswerHelp(){
        //Random ra 1 con số trong khoảng từ 0 -> 9
        //Nếu cho ra số 3 hoặc 7 thì trả về gợi ý là một câu trả lời sai
        Random rand = new Random();
        int temp = rand.nextInt(10);

        int answer;
        //Lấy ra vị trí một câu trả lời sai
        if(temp == 3 || temp == 7){
            while (true){
                answer = rand.nextInt(4) + 1;
                if(answer != rightAnswer && !getTextViewAnswerByLocation(answer).getText().toString().equals("") ){
                    break;
                }
            }
        }
        else{
            answer = rightAnswer;
        }

        //Từ vị trí có được, Trả về một đáp án gợi ý
        switch (answer){
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            default:
                return "";
        }

    }

    //Truyền vào vị trí câu hỏi hiện tại. Trả về số tiền thưởng
    private int getMoney(int loc){
        switch (loc){
            case 1:
                return 200;
            case 2:
                return 400;
            case 3:
                return 600;
            case 4:
                return 1000;
            case 5:
                return 2000;
            case 6:
                return 3000;
            case 7:
                return 6000;
            case 8:
                return 10000;
            case 9:
                return 14000;
            case 10:
                return 22000;
            case 11:
                return 30000;
            case 12:
                return 40000;
            case 13:
                return 60000;
            case 14:
                return 85000;
            case 15:
                return 150000;
        }
        return 0;
    }

}
