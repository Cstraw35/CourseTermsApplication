package com.example.coursetermsapplication.UI;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursetermsapplication.Database.Repository;
import com.example.coursetermsapplication.Enitities.Terms;
import com.example.coursetermsapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermsList extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_terms_layout);
        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        recyclerView.setAdapter(termsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository= new Repository(getApplication());
        List<Terms> allTerms = repository.mgetAllTerms();
        FloatingActionButton fab = findViewById(R.id.addtermsfab);
        termsAdapter.setTerms(allTerms);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, TermDetails.class);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        List<Terms> allTerms = repository.mgetAllTerms();
        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        recyclerView.setAdapter(termsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termsAdapter.setTerms(allTerms);


    }



}

