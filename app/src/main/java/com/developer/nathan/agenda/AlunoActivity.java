package com.developer.nathan.agenda;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.developer.nathan.agenda.helper.AlunoHelper;
import com.developer.nathan.agenda.model.Aluno;

public class AlunoActivity extends AppCompatActivity {

    private AlunoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);


        helper = new AlunoHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno != null){
            helper.peencheAluno(aluno);

        }






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.editar_aluno);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
