package com.example.myroom1.UI.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroom1.R;

import butterknife.ButterKnife;

public class UpDocumentActivity extends AppCompatActivity {
    Long document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_up_document);

        ButterKnife.bind(this);
    setTitle("Редактировать документ");
    Intent intent = getIntent();
    Bundle arguments = getIntent().getExtras();
        document = arguments.getLong("documentid");}
}
