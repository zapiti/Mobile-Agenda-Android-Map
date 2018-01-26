package com.developer.nathan.agenda.retrofit;

import com.developer.nathan.agenda.services.AlunoService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by natha on 09/01/2018.
 */

public class RetrofitInicializador {

    private final Retrofit retrofit;

    public RetrofitInicializador(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder cliente = new OkHttpClient.Builder();
        cliente.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.7:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(cliente.build())
                .build();
    }

    public AlunoService getAlunoService() {

        return  retrofit.create(AlunoService.class);

    }
}
