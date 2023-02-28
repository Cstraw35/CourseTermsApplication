package com.example.coursetermsapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursetermsapplication.Enitities.Courses;

import java.util.List;


@Dao
public interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Courses course);

    @Update
    void update(Courses course);

    @Delete
    void delete(Courses course);

    @Query("Select * FROM Courses ORDER BY courseid")
    List<Courses> getAllCourses();

    @Query("Select * FROM Courses WHERE termid = :termid ORDER BY courseid")
    List<Courses> getAllAssociatedCourses(int termid);
}

