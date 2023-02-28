package com.example.coursetermsapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursetermsapplication.Enitities.Terms;
import com.example.coursetermsapplication.R;

import java.util.List;


public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsViewHolder> {
    private List<Terms> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public class TermsViewHolder extends RecyclerView.ViewHolder {
        private final TextView TermsItemView;

        private TermsViewHolder(View itemView) {
            super(itemView);
            TermsItemView = itemView.findViewById(R.id.assessmentTexView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Terms current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("id", current.getTermid());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());

                    context.startActivity(intent);

                }
            });
        }
    }

    public TermsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermsAdapter.TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.terms_list_item, parent, false);
        return new TermsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.TermsViewHolder holder, int position) {
        if (mTerms != null) {
            Terms current = mTerms.get(position);
            String name = current.getTermName();
            holder.TermsItemView.setText(name);
        } else {
            holder.TermsItemView.setText("No Courses");
        }

    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }


    public void setTerms(List<Terms> products) {
        mTerms = products;
        notifyDataSetChanged();
    }
}


