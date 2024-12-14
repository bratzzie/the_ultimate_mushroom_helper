package org.nau.diploma.chantarellecheck.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Month;
import java.time.ZonedDateTime;

import rg.nau.diploma.chantarellecheck.R;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_fragment);

        FloatingActionButton returnButton = findViewById(R.id.calendar_acitvity_return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarActivity.this.finish();
            }
        });

        ZonedDateTime currentTime = ZonedDateTime.now();
        Month currentMonth =  currentTime.getMonth();

        TextView currentMonthTextView = findViewById(R.id.current_month_textview);
        currentMonthTextView.setText(currentMonth.toString());

        switch (currentMonth.getValue()) {
            case 1:
                findViewById(R.id.january_tv).setBackgroundResource(R.color.current_month);
                break;
            case 2:
                findViewById(R.id.february_tv).setBackgroundResource(R.color.current_month);
                break;
            case 3:
                findViewById(R.id.march_tv).setBackgroundResource(R.color.current_month);
                break;
            case 4:
                findViewById(R.id.april_tv).setBackgroundResource(R.color.current_month);
                break;
            case 5:
                findViewById(R.id.may_tv).setBackgroundResource(R.color.current_month);
                break;
            case 6:
                findViewById(R.id.june_tv).setBackgroundResource(R.color.current_month);
                break;
            case 7:
                findViewById(R.id.july_tv).setBackgroundResource(R.color.current_month);
                break;
            case 8:
                findViewById(R.id.august_tv).setBackgroundResource(R.color.current_month);
                break;
            case 9:
                findViewById(R.id.september_tv).setBackgroundResource(R.color.current_month);
                break;
            case 10:
                findViewById(R.id.october_tv).setBackgroundResource(R.color.current_month);
                break;
            case 11:
                findViewById(R.id.november_tv).setBackgroundResource(R.color.current_month);
                break;
            case 12:
                findViewById(R.id.december_tv).setBackgroundResource(R.color.current_month);
                break;
            default:
                break;
        }


    }
}