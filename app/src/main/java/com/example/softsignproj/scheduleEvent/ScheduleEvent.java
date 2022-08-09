package com.example.softsignproj.scheduleEvent;

import static android.widget.Toast.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.softsignproj.Database;
import com.example.softsignproj.R;
import com.example.softsignproj.adapter.VenueAdapter;
import com.example.softsignproj.data.model.Events;
import com.example.softsignproj.data.model.Venue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScheduleEvent extends AppCompatActivity implements View.OnClickListener {

    Button btnStartDatePicker, btnStartTimePicker, btnEndDatePicker, btnEndTimePicker, btnAddEvent;
    EditText txtStartDate, txtStartTime, txtEndDate, txtEndTime;
    Context parentContext;
    Database database;
    Venue venue;
    String venueName;
    List<String> eventList;
    String sportSelected;
    int event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scedule_event);

        parentContext = getApplicationContext();

        btnAddEvent = (Button)findViewById(R.id.addEvent);
        btnStartDatePicker = (Button)findViewById(R.id.sendStartDateButton);
        btnStartTimePicker = (Button)findViewById(R.id.sendStartTimeButton);
        btnEndDatePicker = (Button)findViewById(R.id.sendEndDateButton);
        btnEndTimePicker = (Button)findViewById(R.id.sendEndTimeButton);
        txtStartDate = (EditText)findViewById(R.id.editStartDate);
        txtStartTime = (EditText) findViewById(R.id.editStartTime);
        txtStartDate = (EditText)findViewById(R.id.editStartDate);
        txtEndDate = (EditText) findViewById(R.id.editEndDate);
        txtEndTime = (EditText) findViewById(R.id.editEndTime);

        btnEndDatePicker.setOnClickListener(this);
        btnEndTimePicker.setOnClickListener(this);
        btnStartDatePicker.setOnClickListener(this);
        btnStartTimePicker.setOnClickListener(this);
        btnAddEvent.setOnClickListener(this);

        venueName = getIntent().getStringExtra(VenueAdapter.POSITION);
        database = new Database();
        database.read("venue", new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                HashMap<String, HashMap> listOfVenues = (HashMap<String, HashMap>) o;
                venue = new Venue(Objects.requireNonNull(listOfVenues.get(venueName)));
                eventList = venue.getEvents();

                Spinner sportSpinner = (Spinner) findViewById(R.id.sports_spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter(parentContext, android.R.layout.simple_spinner_item,venue.getSports());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sportSpinner.setAdapter(adapter);
                sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        sportSelected = venue.getSports().get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        makeText(parentContext, "sport removed from venue", LENGTH_LONG).show();
                    }
                });
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("schedule event", "failure to read from database");
            }
        }, false);

    }



    protected void getDate(EditText text){
        LocalDate localDate = LocalDate.now();
        int mYear = localDate.getYear();
        int mMonth = localDate.getMonthValue();
        int mDay = localDate.getDayOfMonth();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                LocalDate selectedDate = LocalDate.of(year, month+1, dayOfMonth);
                text.setText(selectedDate.toString());
            }
        }, mYear, mMonth - 1, mDay);
        datePickerDialog.show();
    }



    protected void getTime(EditText text){
        LocalTime localTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        int mHour = localTime.getHour();
        int mMin = localTime.getMinute();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        LocalTime selectedTime = LocalTime.of(hour, minute);
                        text.setText(selectedTime.toString());
                    }
                }, mHour, mMin, false);
        timePickerDialog.show();
    }
    protected Events getInput(){
        EditText txtNumPlayer = (EditText)findViewById(R.id.editPlayerNum);

        String numPlayer = txtNumPlayer.getText().toString();
        String startDate = txtStartDate.getText().toString();
        String startTime = txtStartTime.getText().toString();
        String endDate = txtEndDate.getText().toString();
        String endTime = txtStartTime.getText().toString();

        String dateMatcher = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        String timeMatcher = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$";
        if (!numPlayer.matches("\\s*\\d+\\s*")){
            Toast.makeText(parentContext, "number of players have to be a positive integer", LENGTH_SHORT).show();
            return null;
        }
        if (!(startDate.matches(dateMatcher) && (endDate.matches(dateMatcher)))){
            Toast.makeText(parentContext, "Doesn't match required date format", LENGTH_SHORT).show();
            return null;
        }
        if (!(startTime.matches(timeMatcher)&&endTime.matches(timeMatcher))) {
            Toast.makeText(parentContext, "Doesn't match required time format", LENGTH_SHORT).show();
            return null;
        }

        String startDateTime = startDate + "T" + startTime;
        String endDateTime = endDate + "T" + endTime;
        try {
            LocalDateTime startLocalDateTime = LocalDateTime.parse(startDateTime);
            LocalDateTime endLocalDateTime = LocalDateTime.parse(endDateTime);
            if (startLocalDateTime.isBefore(LocalDateTime.now())){
                Toast.makeText(parentContext, "Cannot schedule an event in the past", LENGTH_SHORT).show();
                return null;
            }
            if (endLocalDateTime.isBefore(startLocalDateTime)){
                Toast.makeText(parentContext, "End time is before start time", LENGTH_SHORT).show();
                return null;
            }
            int maxCount = Integer.parseInt(numPlayer.trim());
            if (maxCount == 0){
                Toast.makeText(parentContext, "Number of Players has to be greater of equal to 1", LENGTH_SHORT).show();
                return null;
            }
            ArrayList<String> participants = new ArrayList<>();
            SharedPreferences preferences = parentContext.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            participants.add(preferences.getString("Current User", "DEFAULT"));
            return new Events(1, endDateTime, startDateTime, maxCount, participants, sportSelected, venueName);

        }catch(java.time.format.DateTimeParseException e){
            Toast.makeText(parentContext, "Invalid date or time entered", LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnStartDatePicker) getDate(txtStartDate);
        if (view == btnStartTimePicker) getTime(txtStartTime);
        if (view == btnEndDatePicker) getDate((txtEndDate));
        if (view == btnEndTimePicker) getTime(txtEndTime);
        if (view == btnAddEvent){
            Events new_events = getInput();
            if (new_events != null)            add_event(new_events);
        }
    }

    private void add_event(Events new_events) {
        event_id = new_events.hashCode();

        database.read("event", new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                HashMap<String, HashMap> listOfEvents = (HashMap<String, HashMap>) o;
                List<String> id_list = new ArrayList<String>(listOfEvents.keySet());
                while (id_list.contains(String.valueOf(event_id))){
                    event_id += 1;
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("schedule event", "failure to read from database");
            }
        }, false);
        database.write("event/" + event_id, new_events, new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(parentContext, "successfully scheduled event", Toast.LENGTH_SHORT).show();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("schedule event", "failure to write to database");
            }
        });
        if (eventList == null) {
            eventList = new ArrayList<>();
        }
        eventList.add(String.valueOf(event_id));
        database.write("venue/" + venueName + "/events", eventList, new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(parentContext, "successfully add event to venue", Toast.LENGTH_SHORT).show();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("schedule event", "failure to write to database");
            }
        });
    }
}