package com.example.coursetermsapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.coursetermsapplication.DAO.AssessmentsDAO;
import com.example.coursetermsapplication.DAO.CoursesDAO;
import com.example.coursetermsapplication.DAO.TermsDAO;
import com.example.coursetermsapplication.Enitities.Assessments;
import com.example.coursetermsapplication.Enitities.Courses;
import com.example.coursetermsapplication.Enitities.Terms;

@Database(entities = {Terms.class, Courses.class, Assessments.class}, version=6)
public abstract class TermsDatabaseBuilder extends RoomDatabase {
    public abstract TermsDAO termsDAO();
    public abstract CoursesDAO coursesDAO();
    public abstract AssessmentsDAO assessmentsDAO();
    private static volatile TermsDatabaseBuilder INSTANCE;

    static TermsDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (TermsDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermsDatabaseBuilder.class, "MyTermsDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }
        return INSTANCE;
    }
}

