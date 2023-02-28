package com.example.coursetermsapplication.UI;


import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursetermsapplication.Database.Repository;
import com.example.coursetermsapplication.Enitities.Courses;
import com.example.coursetermsapplication.Enitities.Terms;
import com.example.coursetermsapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    String termname;
    int id;
    String startDate;
    String endDate;
    Terms term;
    Terms currentTerm;
    int numCourses;
    Repository repository;
    Button startDatebutton;
    Button endDatebutton;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    boolean validated = false;
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", termname);
        outState.putInt("id", id);
        Log.d(TAG, "onSaveInstanceState:" + termname);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_term_view);
        //Setup buttons and Editfields
        startDatebutton = findViewById(R.id.startDatebutton);
        endDatebutton = findViewById(R.id.endDateButton);
        editStartDate = findViewById(R.id.termStartDate);
        editEndDate = findViewById(R.id.termEndDate);
        id = getIntent().getIntExtra("id", -1);
        termname = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        editName = findViewById(R.id.termname);

        if (id != -1) {
            editName.setText(termname);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);

        }
        repository = new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredCourses = new ArrayList<>();
        for (Courses c : repository.mgetAllCourses()) {
            if (c.getTermID() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);


        String currentDate = sdf.format(new Date());

        //Date Selections
        startDatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = currentDate;
                try {
                    startDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDatePicker, startDateCalendar.get(Calendar.YEAR),
                        startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, monthOfYear);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEditText(editStartDate, startDateCalendar);
            }
        };
        endDatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = currentDate;
                try {
                    endDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDatePicker, endDateCalendar.get(Calendar.YEAR),
                        endDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, monthOfYear);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEditText(editEndDate, endDateCalendar);
            }
        };

        Button saveButton = findViewById(R.id.termSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id == -1) {
                    term = new Terms(0, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    try {
                        validated = validateInputs(term.getTermName(), term.getStartDate(), term.getEndDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (validated) {
                        repository.insert(term);
                        Toast.makeText(getApplicationContext(), "Term Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Validation failed", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    term = new Terms(id, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    try {
                        validated = validateInputs(term.getTermName(), term.getStartDate(), term.getEndDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (validated) {
                        repository.update(term);
                        Toast.makeText(getApplicationContext(), "Term Saved", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Validation failed", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });


        FloatingActionButton coursefab = findViewById(R.id.addCourseButton);
        coursefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != -1) {
                    Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                    intent.putExtra("termId", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please save the term first", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void updateDateEditText(EditText editText, Calendar calendar) {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editText.setText(sdf.format(calendar.getTime()));
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredcourses = new ArrayList<>();
        for (Courses c : repository.mgetAllCourses()) {
            if (c.getTermID() == id) filteredcourses.add(c);
        }
        courseAdapter.setCourses(filteredcourses);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deleteTerm:


                for (Terms t : repository.mgetAllTerms()) {
                    if (t.getTermid() == id) {
                        currentTerm = t;
                    }
                }
                numCourses = 0;
                for (Courses c : repository.mgetAllCourses()) {
                    if (c.getCourseid() == id) ++numCourses;
                }
                if (numCourses == 0 && id != -1) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this, currentTerm.getTermName() + " successfully deleted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(TermDetails.this, "System can't delete a term with classes or no term found", Toast.LENGTH_LONG).show();

                }
                return true;
            case R.id.notifyTermStart:
                String dateStart = editStartDate.getText().toString();
                Date myDate = null;
                try{
                    myDate=sdf.parse(dateStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger=myDate.getTime();
                Intent intent=new Intent(TermDetails.this, MyReceiver.class);
                intent.putExtra("key", termname + " is going to start today." );
                PendingIntent sender= PendingIntent.getBroadcast(TermDetails.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                return true;
            case R.id.notifyTermEnd:
                String dateEnd = editEndDate.getText().toString();
                Date myDate2 = null;
                try{
                    myDate2=sdf.parse(dateEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger2 = myDate2.getTime();
                Intent intent2=new Intent(TermDetails.this, MyReceiver.class);
                intent2.putExtra("key", termname + " is going to end today." );
                PendingIntent sender2= PendingIntent.getBroadcast(TermDetails.this, MainActivity.numAlert++, intent2, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);


                return true;


        }
        return super.onOptionsItemSelected(item);


    }

    // Validate all required inputs and send toast for failed inputs.
    private boolean validateInputs(String termName, String startDate, String endDate) throws ParseException {

        if (termName.length() == 0) {
            Toast.makeText(TermDetails.this, "Please enter a term name", Toast.LENGTH_LONG).show();
            return false;

        }


        try {
            String dateFormat = "MM/dd/yy";
            Date checkDate = new SimpleDateFormat(dateFormat).parse(startDate);
        } catch (ParseException e) {
            Toast.makeText(TermDetails.this, "Please enter the start date from the select date button.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
        try {
            String dateFormat = "MM/dd/yy";
            Date checkDate = new SimpleDateFormat(dateFormat).parse(endDate);
        } catch (ParseException e) {
            Toast.makeText(TermDetails.this, "Please enter the end date from the select date button.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }

        return true;

    }
}





