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

public class AlterarJogo extends AppCompatActivity {

    EditText nome;
    EditText desc;
    Spinner cat;
    Button alterar;
    ArrayList<CategoriaBean> categorias;
    ControllerJogo crud;
    ControllerCategoria crudC;
    JogoBean jogo;
    CategoriaBean categoria;
    String codigo;
    UsuarioBean usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_jogo);
        usu = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");
        codigo = getIntent().getStringExtra("codigo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Jogos");

        crud = new ControllerJogo(getBaseContext());
        crudC = new ControllerCategoria(getBaseContext());


        nome = (EditText) findViewById(R.id.edtNomeJogoA);
        desc = (EditText) findViewById(R.id.edtDescA);
        cat = (Spinner) findViewById(R.id.spnCatA);
        alterar = (Button) findViewById(R.id.btnGameA);

        jogo = crud.buscaJogo(Integer.parseInt(codigo));

        categorias = crudC.buscaTodos();

        nome.setText(jogo.getNome());
        desc.setText(jogo.getDescricao());
        ArrayAdapter adapter = new ArrayAdapter(AlterarJogo.this, android.R.layout.simple_spinner_item, categorias);
        cat.setAdapter(adapter);

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = (CategoriaBean) cat.getSelectedItem();
                JogoBean j = new JogoBean(Integer.parseInt(codigo), nome.getText().toString().trim(), desc.getText().toString().trim(), categoria);
                crud.alteraJogo(j);
                Toast.makeText(getApplicationContext(), "Jogo Alterado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AlterarJogo.this, TodosJogos.class);
                intent.putExtra("UsuLogado", usu);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent i = new Intent(this, TodosJogos.class);
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
