package com.example.finalexam801129943;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class ScheduleMeetingActivity extends AppCompatActivity {
    Button AddPlace;
    TextView SelectedCityName;
    Button SetDate;
    DatePickerDialog picker;
    TextView SelectedDate;
    Button SetTime;
    TextView SelectTime;
    Button Save;
    EditText Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meeting);
        AddPlace = findViewById(R.id.AddPlace);
        SelectedCityName = findViewById(R.id.add_place);
        SelectedCityName.setVisibility(View.GONE);
        SetDate = findViewById(R.id.SetDate);
        SelectedDate = findViewById(R.id.set_date);
        SelectedDate.setVisibility(View.GONE);
        SetTime = findViewById(R.id.SetTime);
        SelectTime=findViewById(R.id.set_time);
        SelectTime.setVisibility(View.GONE);
        Save = findViewById(R.id.save);
        Title = findViewById(R.id.et_name);

        final Intent intent = getIntent();

        AddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleMeetingActivity.this,AddPlace.class);
                startActivityForResult(intent,1);
            }
        });


        SetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);


                picker = new DatePickerDialog(ScheduleMeetingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                SetDate.setVisibility(View.GONE);
                                SelectedDate.setVisibility(View.VISIBLE);
                                SelectedDate.setText("DATE: "+ dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }


        });


        SetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
               int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                SetTime.setVisibility(View.GONE);
                                SelectTime.setText("Time: "+ hourOfDay + ":" + minute);
                                SelectTime.setVisibility(View.VISIBLE);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });



        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (SelectedCityName.getText().equals(null) || SelectedCityName.getText().equals("")){
                    Toast.makeText(ScheduleMeetingActivity.this, "Add Place", Toast.LENGTH_SHORT).show();
                }else if (SelectedDate.getText().equals(null) ||SelectedDate.getText().equals("")){
                    Toast.makeText(ScheduleMeetingActivity.this, "Add Date", Toast.LENGTH_SHORT).show();
                }else if (SelectTime.getText().equals(null) ||SelectTime.getText().equals("")){
                    Toast.makeText(ScheduleMeetingActivity.this, "Add Time", Toast.LENGTH_SHORT).show();
                }else if (Title.getText().toString().equals(null) ||Title.getText().toString().equals("")){
                    Title.setError("Add Title");
                }else{
                    MeetingDomain meetingDomain = new MeetingDomain(Title.getText().toString(),SelectedCityName.getText().toString(),SelectedDate.getText().toString(),SelectTime.getText().toString(), UUID.randomUUID().toString());
                    System.out.println("OUTPUT DATA: "+meetingDomain.toString());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Meetings").document(meetingDomain.getUuid()).set(meetingDomain).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            setResult(2,intent);
                            finish();
                        }
                    });
                }

            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            AddPlace.setVisibility(View.GONE);
            SelectedCityName.setText("City Name: "+data.getStringExtra("city"));
            SelectedCityName.setVisibility(View.VISIBLE);


        }
    }







}
