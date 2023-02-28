package com.example.coursetermsapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursetermsapplication.Enitities.Assessments;

import java.util.List;

@Dao
public interface AssessmentsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessments assessments);

    @Update
    void update(Assessments assessments);

    @Delete
    void delete(Assessments assessments);

    @Query("Select * FROM Assessments ORDER BY assessmentid")
    List<Assessments> getAllAssessments();

    @Query("Select * FROM assessments WHERE courseid = :courseid")
    List<Assessments> getAllAssociatedAssessments(int courseid);
}
