package com.developer.nathan.agenda;


import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.developer.nathan.agenda.fragment.MapaFragment;

public class MapaActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSOES = 1;
    private FragmentManager manager;
    private FragmentTransaction tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        //pegando fragment da google


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION )
                        != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION
                            , android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSOES);
                } else {
                    chamaMapa();
                }
            }

        }

    private void chamaMapa() {
        manager = getSupportFragmentManager();
        tx = manager.beginTransaction();
        tx.replace(R.id.frame_mapa, new MapaFragment());
        tx.commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSOES:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                        == PackageManager.PERMISSION_GRANTED){
                    chamaMapa();
                } else {
                    finish();
                }
                break;
        }
    }
}