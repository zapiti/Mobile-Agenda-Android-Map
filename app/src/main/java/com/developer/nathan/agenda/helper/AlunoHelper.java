package com.developer.nathan.agenda.helper;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.developer.nathan.agenda.AlunoActivity;
import com.developer.nathan.agenda.R;
import com.developer.nathan.agenda.model.Aluno;

/**
 * Created by natha on 19/12/2017.
 */

public class AlunoHelper {


    private final TextView campoTelefone;
    private final TextView campoSite;
    private final TextView campoEndereco;
    private final TextView campoNome;
    private final RatingBar campoNota;
    private Aluno aluno;

    public AlunoHelper(AlunoActivity activity) {
        campoNome = (TextView) activity.findViewById(R.id.aluno_nome);
        campoEndereco = (TextView) activity.findViewById(R.id.aluno_endereco);
        campoTelefone = (TextView) activity.findViewById(R.id.aluno_telefone);
        campoSite = (TextView) activity.findViewById(R.id.aluno_site);
        campoNota = (RatingBar) activity.findViewById(R.id.aluno_nota);

        aluno = new Aluno();
    }

//    public Aluno getAluno() {
//
//
//        aluno.setNome(campoNome.getText().toString());
//        aluno.setEndereco(campoEndereco.getText().toString());
//        aluno.setTelefone(campoTelefone.getText().toString());
//        aluno.setSite(campoSite.getText().toString());
//        aluno.setNota(Double.valueOf(campoNota.getProgress()));
//
//        return aluno;
//    }

    public void peencheAluno(Aluno aluno) {

        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        this.aluno=aluno;

    }
}
