package com.example.coursetermsapplication.Enitities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class Terms {
    @PrimaryKey(autoGenerate = true)
    private int termid;
    private String termName;
    private String startDate;
    private String endDate;

    public Terms(int termid, String termName, String startDate, String endDate) {
        this.termid = termid;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Terms() {
    }

    public int getTermid() {
        return termid;
    }

    public void setTermid(int termid) {
        this.termid = termid;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
