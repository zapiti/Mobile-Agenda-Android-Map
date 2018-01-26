package com.developer.nathan.agenda.services;

import com.developer.nathan.agenda.dto.AlunoSync;
import com.developer.nathan.agenda.model.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by natha on 09/01/2018.
 */

public interface AlunoService {

    @POST("aluno")
    Call<Void> insere(@Body Aluno aluno);
    @GET("aluno")
    Call<AlunoSync> lista();
}
