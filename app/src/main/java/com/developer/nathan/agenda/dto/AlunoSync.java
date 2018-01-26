package com.developer.nathan.agenda.dto;

import com.developer.nathan.agenda.model.Aluno;

import java.util.List;

/**
 * Created by natha on 09/01/2018.
 */

public class AlunoSync {
    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
