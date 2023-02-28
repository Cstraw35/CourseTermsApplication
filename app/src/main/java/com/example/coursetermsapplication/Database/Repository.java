package com.example.coursetermsapplication.Database;

import android.app.Application;

import com.example.coursetermsapplication.DAO.AssessmentsDAO;
import com.example.coursetermsapplication.DAO.CoursesDAO;
import com.example.coursetermsapplication.DAO.TermsDAO;
import com.example.coursetermsapplication.Enitities.Assessments;
import com.example.coursetermsapplication.Enitities.Courses;
import com.example.coursetermsapplication.Enitities.Terms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Repository {
    private AssessmentsDAO mAssessmentsDAO;
    private CoursesDAO mCoursesDAO;
    private TermsDAO mTermsDAO;
    private List<Assessments> mAllAssessments;
    private List<Courses> mAllCourses;
    private List<Terms> mAllTerms;


    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TermsDatabaseBuilder db = TermsDatabaseBuilder.getDatabase(application);
        mAssessmentsDAO = db.assessmentsDAO();
        mCoursesDAO = db.coursesDAO();
        mTermsDAO = db.termsDAO();
    }

    public List<Assessments> mgetAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentsDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.insert(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.update(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.delete(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Courses> mgetAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCoursesDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;

    }

    public void insert(Courses course) {
        databaseExecutor.execute(() -> {
            mCoursesDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Courses course) {
        databaseExecutor.execute(() -> {
            mCoursesDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Courses courses) {
        databaseExecutor.execute(() -> {
            mCoursesDAO.delete(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public List<Terms> mgetAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermsDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;

    }

    public void insert(Terms terms) {
        databaseExecutor.execute(() -> {
            mTermsDAO.insert(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Terms terms) {
        databaseExecutor.execute(() -> {
            mTermsDAO.update(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Terms terms) {
        databaseExecutor.execute(() -> {
            mTermsDAO.delete(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


