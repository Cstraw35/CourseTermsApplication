package com.example.coursetermsapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursetermsapplication.Enitities.Assessments;
import com.example.coursetermsapplication.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessments> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView AssessmentItemView;
        private final TextView AssessmentTypeView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            AssessmentTypeView = itemView.findViewById(R.id.assessmenttype);
            AssessmentItemView = itemView.findViewById(R.id.assessmentTexView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessId", current.getAssessmentid());
                    intent.putExtra("courseId", current.getCourseid());
                    intent.putExtra("assessName", current.getAssessName());
                    intent.putExtra("assessStartDate", current.getAssessStartDate());
                    intent.putExtra("assessEndDate", current.getAssessEndDate());
                    intent.putExtra("assessType", current.getAssessType());
                    context.startActivity(intent);

                }
            });
        }
    }

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessement_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessments current = mAssessments.get(position);
            String type = current.getAssessType();
            String name = current.getAssessName();
            holder.AssessmentItemView.setText(name);
            holder.AssessmentTypeView.setText(type);
        } else {
            holder.AssessmentItemView.setText("No Courses");
        }

    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }


    public void setAssessments(List<Assessments> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}

