package jp.ac.Calculation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;

import jp.ac.dentaku03_04_ih13b_22.R;

public class MainActivity extends AppCompatActivity {
    private String strCal = "";
    private String disCal = "";
    private int count = 0;
    private int count2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        数字ボタンのインスタンス化
        Button[] numbtn = new Button[10];
        numbtn[0] = findViewById(R.id.zero);
        numbtn[1] = findViewById(R.id.one);
        numbtn[2] = findViewById(R.id.two);
        numbtn[3] = findViewById(R.id.three);
        numbtn[4] = findViewById(R.id.four);
        numbtn[5] = findViewById(R.id.five);
        numbtn[6] = findViewById(R.id.six);
        numbtn[7] = findViewById(R.id.seven);
        numbtn[8] = findViewById(R.id.eight);
        numbtn[9] = findViewById(R.id.nine);

//        演算記号の初期化
        Button plusBtn = findViewById(R.id.plu);
        Button minusBtn = findViewById(R.id.min);
        Button multiBtn = findViewById(R.id.mul);
        Button diviBtn = findViewById(R.id.div);
        Button equlBtn = findViewById(R.id.eqo);
        Button clearBtn = findViewById(R.id.clear);
        Button plmiBtn = findViewById(R.id.plmi);
        Button backBtn = findViewById(R.id.backspace);
        Button decBtn = findViewById(R.id.dec);

        equlBtn.setOnClickListener(view -> {
            calClicked();
        });

        plusBtn.setOnClickListener(view -> {
            opClicked(view);
        });

        minusBtn.setOnClickListener(view -> {
            opClicked(view);
        });

        multiBtn.setOnClickListener(view -> {
            opClicked(view);
        });

        diviBtn.setOnClickListener(view -> {
            opClicked(view);
        });

        clearBtn.setOnClickListener(view -> {
            clearClicked();
        });

        plmiBtn.setOnClickListener(view -> {
            plmiClicked();
        });

        backBtn.setOnClickListener(view -> {
            backClicked();
        });

        decBtn.setOnClickListener(view -> {
            decClicked();
        });


        for (int i = 0; i < numbtn.length; i++){
            numbtn[i].setOnClickListener(view ->{
//                クリック時のアクション
                numbtnClicked(view);
            });
        }
    }

    private void numbtnClicked(View view){
        Button opcode = (Button) view;
        String op = opcode.getText().toString();

        if (count2 == 1){
            clearClicked();
        }

        if (disCal.equals("0")){
            disCal = op;
        }else {
            disCal += op;
        }
        display();
    }

    private void opClicked(View view){
//        ボタン判定
        Button opcode = (Button) view;
        String op = opcode.getText().toString();
        strCal += disCal;
        count2 = 0;
        count++;

        if (count == 2){
            strCal = calculation(strCal);
            disCal = strCal;

            display();
        }
        strCal += " " + op + " ";
        disCal = "";

        TextView tvDisplay_cal = findViewById(R.id.display_calarea);
        tvDisplay_cal.setText(strCal);
    }


    private void calClicked() {
        strCal += disCal;
        disCal = calculation(strCal);
        count2++;

        TextView tvDisplay_cal = findViewById(R.id.display_calarea);
        tvDisplay_cal.setText(strCal);
        strCal = "";

        display();
    }

    private void clearClicked(){
        strCal = "";
        disCal = "";
        count = 0;
        count2 = 0;

        TextView tvDisplay_cal = findViewById(R.id.display_calarea);
        tvDisplay_cal.setText("");

        TextView tvDisplay = findViewById(R.id.display_area);
        tvDisplay.setText("0");
    }

    private void plmiClicked(){
        disCal = "-" + disCal;
        display();
    }

    private void backClicked(){
        count2 = 0;
        if (!(disCal.equals(""))){
            disCal = disCal.substring(0, disCal.length()-1);

            if (disCal.equals("")){
             disCal = "0";
            }
            display();
        }
    }

    private void decClicked() {
        NumberFormat forCal = NumberFormat.getNumberInstance();
        BigDecimal bigCal = new BigDecimal(disCal);
        disCal += ".";
        TextView tvDisplay = findViewById(R.id.display_area);
        tvDisplay.setText(forCal.format(bigCal) + ".");
    }

    private void display(){
        TextView tvDisplay = findViewById(R.id.display_area);
        NumberFormat forCal = NumberFormat.getNumberInstance();
        BigDecimal bigCal = new BigDecimal(disCal);
        tvDisplay.setText(forCal.format(bigCal));
    }

    private String calculation(String strCal){
        String[] array = strCal.split(" ");
        BigDecimal bigCal1 = new BigDecimal(array[0]);
        BigDecimal bigCal2 = new BigDecimal(array[2]);
        BigDecimal cal;

        if (array[1].equals("+")){
            cal = bigCal1.add(bigCal2);
        }else  if (array[1].equals("-")){
            cal = bigCal1.subtract(bigCal2);
        }else  if (array[1].equals("×")){
            cal = bigCal1.multiply(bigCal2);
        }else{
            if((bigCal1.remainder(bigCal2)).signum() == 0) {
                cal = bigCal1.divide(bigCal2, 0, BigDecimal.ROUND_HALF_UP);
            }else{
                cal = bigCal1.divide(bigCal2, 2, BigDecimal.ROUND_HALF_UP);
            }
        }
        count--;

        String result = cal.toString();
        return result;
    }
}