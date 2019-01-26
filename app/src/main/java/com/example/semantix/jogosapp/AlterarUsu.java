package com.example.semantix.jogosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlterarUsu extends AppCompatActivity {
    String codigo;
    EditText login;
    EditText senha;
    EditText status;
    EditText tipo;
    ControllerUsuario crud;
    UsuarioBean usua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_usu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Usuários");
        usua = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");
        codigo = getIntent().getStringExtra("codigo");

        crud = new ControllerUsuario(getBaseContext());
        login = (EditText) findViewById(R.id.editText);
        senha = (EditText) findViewById(R.id.editText2);
        status = (EditText) findViewById(R.id.editText3);
        tipo = (EditText) findViewById(R.id.editText4);
        Button alterar = (Button) findViewById(R.id.alterar);

        UsuarioBean usu = crud.buscaUsuario(Integer.parseInt(codigo));

        login.setText(usu.getLogin());
        senha.setText(usu.getSenha());
        status.setText(usu.getStatus());
        tipo.setText(usu.getTipo());

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioBean u = new UsuarioBean(Integer.parseInt(codigo), login.getText().toString().trim(), senha.getText().toString().trim(), status.getText().toString().trim(), tipo.getText().toString().trim());
                crud.alteraUsuario(u);
                Toast.makeText(AlterarUsu.this, "Usuário Alterado!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AlterarUsu.this, TodosUsu.class);
                it.putExtra("UsuLogado", usua);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent i = new Intent(this, TodosUsu.class);
                i.putExtra("UsuLogado", usua);
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
        iten.putExtra("UsuLogado", usua);
        startActivity(iten); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
