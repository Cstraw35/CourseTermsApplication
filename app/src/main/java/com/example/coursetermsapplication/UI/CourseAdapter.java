package com.example.coursetermsapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursetermsapplication.Enitities.Courses;
import com.example.coursetermsapplication.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
//    private List<Courses> mCourse;
//    private final Context context;
//    private final LayoutInflater mInflater;

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView CourseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            CourseItemView = itemView.findViewById(R.id.assessmentTexView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses current = mCourse.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseId", current.getCourseid());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStatus", current.getStatus());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate", current.getEndDate());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("optionalNote", current.getOptionalNote());
                    intent.putExtra("termId", current.getTermID());
                    intent.putExtra("courseStatus", current.getStatus());
                    context.startActivity(intent);

                }
            });
        }
    }
    private List<Courses> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;


    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourse != null) {
            Courses current = mCourse.get(position);
            String name = current.getCourseName();
            holder.CourseItemView.setText(name);
        } else {
            holder.CourseItemView.setText("No Courses");
        }

    }

    @Override
    public int getItemCount() {
        return mCourse.size();
    }


    public void setCourses(List<Courses> courses) {
        mCourse = courses;
        notifyDataSetChanged();
    }
}
