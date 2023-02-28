package com.example.coursetermsapplication.UI;

import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursetermsapplication.Database.Repository;
import com.example.coursetermsapplication.Enitities.Assessments;
import com.example.coursetermsapplication.Enitities.Courses;
import com.example.coursetermsapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editOptionalNote;
    int id;
    int termId;
    String courseName;
    String startDate;
    String endDate;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String status;
    String optionalNote;
    Courses course;
    Courses currentCourse;
    Repository repository;
    Button startDatebutton;
    Button endDatebutton;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    boolean validated = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_course_view_layout);
        //Setup buttons and Editfields
        editName = findViewById(R.id.courseName);
        startDatebutton = findViewById(R.id.courseStartDateButton);
        endDatebutton = findViewById(R.id.courseEndDateButton);
        editStartDate = findViewById(R.id.courseStartDate);
        editEndDate = findViewById(R.id.courseEndDate);
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editInstructorName = findViewById(R.id.instructorName);
        editInstructorPhone = findViewById(R.id.instructorPhone);
        editOptionalNote = findViewById(R.id.optionalNote);
        id = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("termId", -1);
        courseName = getIntent().getStringExtra("courseName");
        startDate = getIntent().getStringExtra("courseStartDate");
        endDate = getIntent().getStringExtra("courseEndDate");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        optionalNote = getIntent().getStringExtra("optionalNote");
        status = getIntent().getStringExtra("courseStatus");
        Spinner statusSpinner = (Spinner) findViewById(R.id.coursestatusspinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.course_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = (statusAdapter.getItem(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                status = ("in progress");
            }
        });


        //Only edit text fields if there is already a course id.
        if (id != -1) {
            editName.setText(courseName);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
            editInstructorName.setText(instructorName);
            editInstructorEmail.setText(instructorEmail);
            editInstructorPhone.setText(instructorPhone);
            if (optionalNote.length() != 0) {
                editOptionalNote.setText(optionalNote);
            }
            statusSpinner.setSelection(statusAdapter.getPosition(status));

        }
        //Setup assessment list
        repository = new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> filteredAssessments = new ArrayList<>();
        for (Assessments a : repository.mgetAllAssessments()) {
            if (a.getCourseid() == id) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(repository.mgetAllAssessments());
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
                new DatePickerDialog(CourseDetails.this, startDatePicker, startDateCalendar.get(Calendar.YEAR),
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
                new DatePickerDialog(CourseDetails.this, endDatePicker, endDateCalendar.get(Calendar.YEAR),
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

        Button saveButton = findViewById(R.id.courseSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id == -1) {

                    course = new Courses(0, editName.getText().toString(), status, editStartDate.getText().toString(), editEndDate.getText().toString(), editInstructorName.getText().toString(),
                            editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), editOptionalNote.getText().toString(), termId);
                    try {
                        validated = validateInputs(course.getCourseName(), course.getStartDate(), course.getEndDate(), course.getInstructorName(), course.getInstructorPhone(), course.getInstructorEmail());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (validated) {
                        repository.insert(course);
                        Toast.makeText(getApplicationContext(), "Course Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    course = new Courses(id, editName.getText().toString(), status, editStartDate.getText().toString(), editEndDate.getText().toString(), editInstructorName.getText().toString(),
                            editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), editOptionalNote.getText().toString(), termId);
                    try {
                        validated = validateInputs(course.getCourseName(), course.getStartDate(), course.getEndDate(), course.getInstructorName(), course.getInstructorPhone(), course.getInstructorEmail());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (validated) {
                        repository.update(course);
                        Toast.makeText(getApplicationContext(), "Course Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        FloatingActionButton coursefab = findViewById(R.id.addAssessmentButton);
        coursefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != -1) {
                    Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                    intent.putExtra("courseId", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please save the course first", Toast.LENGTH_LONG).show();
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

        Log.d(TAG, String.valueOf(id));

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> filteredassessments = new ArrayList<>();
        for (Assessments a : repository.mgetAllAssessments()) {
            if (a.getCourseid() == id) filteredassessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredassessments);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deleteCourse:


                for (Courses c : repository.mgetAllCourses()) {
                    if (c.getCourseid() == id && id != -1) {
                        currentCourse = c;
                        repository.delete(currentCourse);
                        Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " successfully deleted", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.shareCourse:
                Intent sendIntent= new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editOptionalNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes from " + editName.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.notifyCourseStart:
                String dateStart = editStartDate.getText().toString();
                Date myDate = null;
                try{
                    myDate=sdf.parse(dateStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger=myDate.getTime();
                Intent intent=new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", courseName + " is going to start today." );
                PendingIntent sender= PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                return true;
            case R.id.notifyCourseEnd:
                String dateEnd = editEndDate.getText().toString();
                Date myDate2 = null;
                try{
                    myDate2=sdf.parse(dateEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger2 = myDate2.getTime();
                Intent intent2=new Intent(CourseDetails.this, MyReceiver.class);
                intent2.putExtra("key", courseName + " is going to end today." );
                PendingIntent sender2= PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, intent2, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);


                return true;        }
        return super.onOptionsItemSelected(item);


    }

    // Validate all required inputs and send toast for failed inputs.
    private boolean validateInputs(String name, String startDate, String endDate, String instructorName,
                                   String instructorPhone, String instructorEmail) throws ParseException {

        if (name.length() == 0) {
            Toast.makeText(CourseDetails.this, "Please enter a course name", Toast.LENGTH_LONG).show();
            return false;

        }
        if (instructorEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(instructorEmail).matches()) {
            Toast.makeText(CourseDetails.this, "Please enter a valid instructor email address", Toast.LENGTH_LONG).show();
            return false;
        }
        if (instructorName.isEmpty()) {
            Toast.makeText(CourseDetails.this, "Please enter the instructors name", Toast.LENGTH_LONG).show();
            return false;
        }

        if (instructorPhone.isEmpty() || !Patterns.PHONE.matcher(instructorPhone).matches()) {
            Toast.makeText(CourseDetails.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
            return false;
        }


        try {
            String dateFormat = "MM/dd/yy";
            Date checkDate = new SimpleDateFormat(dateFormat).parse(startDate);
        } catch (ParseException e) {
            Toast.makeText(CourseDetails.this, "Please enter the start date from the select date button.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
        try {
            String dateFormat = "MM/dd/yy";
            Date checkDate = new SimpleDateFormat(dateFormat).parse(endDate);
        } catch (ParseException e) {
            Toast.makeText(CourseDetails.this, "Please enter the end date from the select date button.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }

        return true;

    }

}