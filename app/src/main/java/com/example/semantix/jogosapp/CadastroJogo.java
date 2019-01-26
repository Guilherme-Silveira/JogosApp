package com.example.semantix.jogosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastroJogo extends AppCompatActivity {
    JogoBean jogo;
    ControllerCategoria crudC;
    ControllerJogo crud;
    Spinner categorias;
    EditText nome;
    EditText descricao;
    ArrayList<CategoriaBean> c;
    Button cad;
    CategoriaBean cat;
    UsuarioBean usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogo);
        usu = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Menu");

        crud = new ControllerJogo(getBaseContext());
        crudC = new ControllerCategoria(getBaseContext());
        c = crudC.buscaTodos();

        categorias = (Spinner) findViewById(R.id.spnCatA);
        nome = (EditText) findViewById(R.id.edtNomeJogoA);
        descricao = (EditText) findViewById(R.id.edtDescA);
        cad = (Button) findViewById(R.id.btnGame);

        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, c);
        categorias.setAdapter(adapter);

        cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat = (CategoriaBean) categorias.getSelectedItem();
                jogo = new JogoBean(0, nome.getText().toString().trim(), descricao.getText().toString().trim(), cat);
                String resultado = crud.insereJogo(jogo);
                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CadastroJogo.this, MeusJogos.class);
                intent.putExtra("UsuLogado", usu);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent i = new Intent(this, MeusJogos.class);
                i.putExtra("UsuLogado", usu);
                startActivity(i);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        Intent iten = new Intent(new Intent(this, MeusJogos.class));
        iten.putExtra("UsuLogado", usu);
        startActivity(iten); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
