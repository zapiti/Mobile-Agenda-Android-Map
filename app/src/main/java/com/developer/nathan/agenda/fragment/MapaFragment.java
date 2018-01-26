package com.developer.nathan.agenda.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.nathan.agenda.Localizador;
import com.developer.nathan.agenda.dao.AlunoDAO;
import com.developer.nathan.agenda.model.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by natha on 02/01/2018.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {


    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        //converter latitude longitude do local

        String endereco = "Rua Rio Solimoes 1661,Jardim Europa,Uberlandia";
        LatLng posicaoDaEscola = pegaCordenadaDoEndereco(endereco);
        //posiçao
        if(posicaoDaEscola != null){
            centralizaEm(posicaoDaEscola);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno :alunoDAO.buscaAlunos()) {
            LatLng cordenada = pegaCordenadaDoEndereco(aluno.getEndereco());
            if(cordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(cordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
            
        }
        alunoDAO.close();
        //atualiza localizaçao atual
        new Localizador(getContext(),googleMap);
    }

    public void centralizaEm(LatLng coordenada) {
        if (googleMap != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            googleMap.moveCamera(update);
        }
    }


    private LatLng pegaCordenadaDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
                    if(!resultados.isEmpty()){
                        LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                        return posicao;
                    }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
