package com.developer.nathan.agenda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.nathan.agenda.ProvasActivity;
import com.developer.nathan.agenda.R;
import com.developer.nathan.agenda.model.Prova;

import java.util.Arrays;
import java.util.List;

/**
 * Created by natha on 02/01/2018.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPort = Arrays.asList("Sujeito","Objeto direto");
        Prova provaPortgues = new Prova("Portugues","25/05/2016",topicosPort);

        List<String> topicosMat = Arrays.asList("Equacoes","Trigonometria");
        Prova provaMatemat = new Prova("Matematica","26/06/2011",topicosMat);

        final List<Prova> provas = Arrays.asList(provaMatemat,provaPortgues);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(),android.R.layout.simple_list_item_1,provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),"Prova de "+prova, Toast.LENGTH_SHORT).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();

                provasActivity.selecionaProva(prova);


            }
        });

        return view;
    }
}
