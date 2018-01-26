package com.developer.nathan.agenda;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developer.nathan.agenda.fragment.DetalhesProvaFragment;
import com.developer.nathan.agenda.fragment.ListaProvasFragment;
import com.developer.nathan.agenda.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        tx.replace(R.id.frame_principal,new ListaProvasFragment());
        if(estaModoPaisagem()){
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        tx.commit();

    }

    private boolean estaModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();

        if(!estaModoPaisagem()) {            
            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle paramentros = new Bundle();
            paramentros.putSerializable("prova",prova);
            detalhesProvaFragment.setArguments(paramentros);

            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            //Efeito de desempinha da activity no fragment
            tx.addToBackStack(null);


            tx.commit();
        }else {
            DetalhesProvaFragment detalhesFragment =
                    (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCamposCom(prova);
        }
        
    }
}
