package com.developer.nathan.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.nathan.agenda.adapter.AlunosAdapter;
import com.developer.nathan.agenda.dao.AlunoDAO;
import com.developer.nathan.agenda.dto.AlunoSync;
import com.developer.nathan.agenda.model.Aluno;
import com.developer.nathan.agenda.retrofit.RetrofitInicializador;
import com.developer.nathan.agenda.task.EnviaAlunosTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);


                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);

            }
        });


//        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
//                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
//
//
//                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
//                intentVaiProFormulario.putExtra("aluno",aluno);
//                startActivity(intentVaiProFormulario);
//
//            }
//        });

//        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListaAlunosActivity.this,"Click long", Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });



        Button novoAluno = (Button) findViewById(R.id.novo_aluno);

        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<AlunoSync> call = new RetrofitInicializador().getAlunoService().lista();

        call.enqueue(new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.sincroniza(alunoSync.getAlunos());
                dao.close();
                carregaLista();
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                Log.e("onFailure chamado",t.getMessage());

            }
        });
        carregaLista();
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();

        for (Aluno aluno :
                alunos) {
            Log.i("id do aluno ", String.valueOf(aluno.getId()));

        }

        dao.close();


        AlunosAdapter adapter = new AlunosAdapter(this,alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();
                break;
            case R.id.menu_baixar_provas:
                Intent vaiParaProvas = new Intent(this,ProvasActivity.class);
                startActivity(vaiParaProvas);
                break;
            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this,MapaActivity.class);
                startActivity(vaiParaMapa);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        //pegar aluno
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
        //aluno  ligar
        MenuItem itemLigar = menu.add("Ligar:" + aluno.getTelefone());
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

               if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this,Manifest.permission.CALL_PHONE)
                       != PackageManager.PERMISSION_GRANTED){
                   ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                           new String[]{Manifest.permission.CALL_PHONE}
                   ,123);
               }else{
                   Intent intentLigar = new Intent(Intent.ACTION_CALL);
                   intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                   startActivity(intentLigar);
               }

                return false;
            }
        });





        //fim ligar
        //enviar mensagem
        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+ aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        //fim mensagem
        //ver no mapa
        MenuItem itemMapa = menu.add("Visualizar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));

        itemMapa.setIntent(intentMapa);

        //fim mapa


       //site
        MenuItem itemSite = menu.add("Visitar Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = aluno.getSite();
        if(!site.startsWith("http://")){
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);
        //fim site



        //deleta
        MenuItem deletar = menu.add("Deletar");
       deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {



               AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
               dao.deleta(aluno);
               dao.close();

               carregaLista();

               Toast.makeText(ListaAlunosActivity.this,"Aluno: "+aluno.getNome()+" deletado!",Toast.LENGTH_SHORT).show();

               return false;
           }
       });
    }



//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if(requestCode == 123){
//            //fazligaçao
//
//        }
//    }
    //    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        return super.onContextItemSelected(item);
//    }
}
