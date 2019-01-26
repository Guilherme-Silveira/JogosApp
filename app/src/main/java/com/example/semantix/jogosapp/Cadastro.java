package com.example.semantix.jogosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {
    UsuarioBean usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Voltar Login");
        Button botao = (Button) findViewById(R.id.cadastro);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerUsuario crud = new ControllerUsuario(getBaseContext());
                EditText login = (EditText) findViewById(R.id.editText);
                EditText senha = (EditText) findViewById(R.id.editText2);
                EditText status = (EditText) findViewById(R.id.editText3);
                EditText tipo = (EditText) findViewById(R.id.editText4);
                usuario = new UsuarioBean(0, login.getText().toString().trim(), senha.getText().toString().trim(), status.getText().toString().trim(), tipo.getText().toString().trim());
                String resultado;

                resultado = crud.insereUsuario(usuario);
                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                Intent it = new Intent(Cadastro.this, Login.class);
                startActivity(it);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
