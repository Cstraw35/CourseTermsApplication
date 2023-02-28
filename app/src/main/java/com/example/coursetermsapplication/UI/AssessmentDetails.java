package com.example.coursetermsapplication.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.coursetermsapplication.Database.Repository;
import com.example.coursetermsapplication.Enitities.Assessments;
import com.example.coursetermsapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    int id;
    int courseId;
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    String assessName;
    String startDate;
    String endDate;
    String type;
    Assessments assessment;
    Assessments currentAssessment;
    Repository repository;
    Button startDatebutton;
    Button endDatebutton;
    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar endDateCalendar = Calendar.getInstance();
    boolean validated = false;
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_assessment_view_layout);
        //Setup buttons and Editfields
        editName = findViewById(R.id.assessmentName);
        startDatebutton = findViewById(R.id.assessmentStartDateButton);
        editStartDate = findViewById(R.id.assessmentStartDate);
        endDatebutton = findViewById(R.id.assessmentEndDatebutton);
        editEndDate = findViewById(R.id.assessmentEndDate);
        id = getIntent().getIntExtra("assessId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        assessName = getIntent().getStringExtra("assessName");
        startDate = getIntent().getStringExtra("assessStartDate");
        endDate = getIntent().getStringExtra("assessEndDate");
        type = getIntent().getStringExtra("assessType");
        Spinner assessSpinner = (Spinner) findViewById(R.id.assessspinner);
        ArrayAdapter<CharSequence> assessAdapter = ArrayAdapter.createFromResource(this, R.array.Assessment_Types, android.R.layout.simple_spinner_item);
        assessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        assessSpinner.setAdapter(assessAdapter);
        assessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = (assessAdapter.getItem(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type = ("objective");
            }
        });

        if (id != -1) {
            editName.setText(assessName);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
            assessSpinner.setSelection(assessAdapter.getPosition(type));

        }
        repository = new Repository(getApplication());

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
                new DatePickerDialog(AssessmentDetails.this, startDatePicker, startDateCalendar.get(Calendar.YEAR),
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
                new DatePickerDialog(AssessmentDetails.this, endDatePicker, endDateCalendar.get(Calendar.YEAR),
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

        Button saveButton = findViewById(R.id.assessSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id == -1) {
                    assessment = new Assessments(0, editName.getText().toString(), editStartDate.getText().toString(),editEndDate.getText().toString(), type, courseId);
                    try {
                        validated = validateInputs(assessment.getAssessName(), assessment.getAssessStartDate(), assessment.getAssessEndDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (validated) {
                        repository.insert(assessment);
                        Toast.makeText(getApplicationContext(), "Assessment Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    assessment = new Assessments(id, editName.getText().toString(),editStartDate.getText().toString(), editEndDate.getText().toString(), type, courseId);
                    try {
                        validated = validateInputs(assessment.getAssessName(), assessment.getAssessStartDate(), assessment.getAssessEndDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (validated) {
                        repository.update(assessment);
                        Toast.makeText(getApplicationContext(), "Assessment Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_SHORT).show();
                    }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deleteAssessment:


                for (Assessments a : repository.mgetAllAssessments()) {
                    if (a.getAssessmentid() == id && id != -1) {
                        currentAssessment = a;
                        repository.delete(currentAssessment);
                        Toast.makeText(AssessmentDetails.this, currentAssessment.getAssessName() + " successfully deleted", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.notifyAssessmentStart:
                String dateStart = editStartDate.getText().toString();
                Date myDate = null;
                try{
                    myDate=sdf.parse(dateStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger=myDate.getTime();
                Intent intent=new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", assessName + " is going to start today." );
                PendingIntent sender= PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                return true;
            case R.id.notifyAssessmentEnd:
                String dateEnd = editEndDate.getText().toString();
                Date myDate2 = null;
                try{
                    myDate2=sdf.parse(dateEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger2 = myDate2.getTime();
                Intent intent2=new Intent(AssessmentDetails.this, MyReceiver.class);
                intent2.putExtra("key", assessName + " is going to end today." );
                PendingIntent sender2= PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, intent2, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);


                return true;        }


        return super.onOptionsItemSelected(item);


    }

    // Validate all required inputs and send toast for failed inputs.
    private boolean validateInputs(String assessmentName,String startDate, String endDate) throws ParseException {

        if (assessmentName.length() == 0) {
            Toast.makeText(AssessmentDetails.this, "Please enter an assessment name", Toast.LENGTH_LONG).show();
            return false;

        }
        try {
            String dateFormat = "MM/dd/yy";
            Date checkDate = new SimpleDateFormat(dateFormat).parse(startDate);
        } catch (ParseException e) {
            Toast.makeText(AssessmentDetails.this, "Please enter the start date from the select date button.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }


        try {
            String dateFormat = "MM/dd/yy";
            Date checkDate = new SimpleDateFormat(dateFormat).parse(endDate);
        } catch (ParseException e) {
            Toast.makeText(AssessmentDetails.this, "Please enter the end date from the select date button.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }

        return true;


    }
}
