package com.example.coursetermsapplication.Enitities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="assessments")
public class Assessments {
    @PrimaryKey(autoGenerate = true)
    private int assessmentid;
    private String AssessName;
    private String AssessStartDate;
    private String AssessEndDate;
    private String AssessType;
    private int courseid;

    public Assessments() {
    }

    public Assessments(int assessmentid, String assessName, String assessStartDate, String assessEndDate, String assessType, int courseid) {
        this.assessmentid = assessmentid;
        AssessName = assessName;
        AssessStartDate = assessStartDate;
        AssessEndDate = assessEndDate;
        AssessType = assessType;
        this.courseid = courseid;
    }

    public int getAssessmentid() {
        return assessmentid;
    }

    public void setAssessmentid(int assessmentid) {
        this.assessmentid = assessmentid;
    }

    public String getAssessName() {
        return AssessName;
    }

    public void setAssessName(String assessName) {
        AssessName = assessName;
    }

    public String getAssessStartDate() {
        return AssessStartDate;
    }

    public void setAssessStartDate(String assessStartDate) {
        AssessStartDate = assessStartDate;
    }

    public String getAssessEndDate() {
        return AssessEndDate;
    }

    public void setAssessEndDate(String assessEndDate) {
        AssessEndDate = assessEndDate;
    }

    public String getAssessType() {
        return AssessType;
    }

    public void setAssessType(String assessType) {
        AssessType = assessType;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }
}




