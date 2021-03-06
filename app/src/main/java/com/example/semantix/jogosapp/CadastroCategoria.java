package com.example.semantix.jogosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroCategoria extends AppCompatActivity {

    EditText tipo;
    Button cadCat;
    ControllerCategoria crud;
    CategoriaBean cat;
    UsuarioBean usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_categoria);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Menu");
        usu = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");
        crud = new ControllerCategoria(getBaseContext());
        tipo = (EditText) findViewById(R.id.tipoCat);
        cadCat = (Button) findViewById(R.id.btnCadCat);

        cadCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat = new CategoriaBean(0, tipo.getText().toString().trim());
                String resultado = crud.insereCategoria(cat);
                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CadastroCategoria.this, MeusJogos.class);
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
