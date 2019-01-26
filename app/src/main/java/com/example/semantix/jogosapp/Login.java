package com.example.semantix.jogosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    UsuarioBean usuEnt;
    UsuarioBean usuSai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView cadastrar = (TextView) findViewById(R.id.txt_cadastro);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, Cadastro.class);
                startActivity(it);
            }
        });
        Button botao = (Button) findViewById(R.id.btnConfirma);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerUsuario crud = new ControllerUsuario(getBaseContext());
                EditText login = (EditText) findViewById(R.id.edtLogin);
                EditText senha = (EditText) findViewById(R.id.edtSenha);
                usuEnt = new UsuarioBean();
                usuEnt.setId(0);
                usuEnt.setLogin(login.getText().toString().trim());
                usuEnt.setSenha(senha.getText().toString().trim());
                usuSai = crud.validaUsuario(usuEnt);
                if (usuSai.getId()!=0){
                    Intent it = new Intent(Login.this, MeusJogos.class);
                    it.putExtra("UsuLogado", usuSai);
                    startActivity(it);
                }else{
                    Toast.makeText(getApplicationContext(), "Usuário e/ou senha inválida(s)", Toast.LENGTH_SHORT).show();
                    login.setText("");
                    senha.setText("");
                }
            }
        });
    }
}
