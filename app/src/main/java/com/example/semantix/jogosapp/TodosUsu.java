package com.example.semantix.jogosapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodosUsu extends AppCompatActivity {
    ArrayList<UsuarioBean> usuarios;
    UsuarioBean u = new UsuarioBean();
    ControllerUsuario crud;
    ListView lista;
    UsuarioBean usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_usu);
        usu = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");
        crud =  new ControllerUsuario(getBaseContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Menu");

        lista = (ListView) findViewById(R.id.listarUsu);
        registerForContextMenu(lista);
        usuarios = crud.buscaTodos();
        if (usuarios.size() > 0) {
            ArrayAdapter<UsuarioBean> adapter = new ArrayAdapter<UsuarioBean>(TodosUsu.this, android.R.layout.simple_list_item_1, usuarios);
            lista.setAdapter(adapter);

            final Cursor cursor = crud.buscaTodosc();

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String codigo;
                    cursor.moveToPosition(position);
                    codigo = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    Intent intent = new Intent(TodosUsu.this, AlterarUsu.class);
                    intent.putExtra("codigo", codigo);
                    intent.putExtra("UsuLogado", usu);
                    startActivity(intent);
                    finish();
                }
            });

            lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor.moveToPosition(position);
                    u.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id"))));
                    return false;
                }
            });
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuItem = menu.add("Deletar Usuário");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                crud.excluiUsuario(u.getId());
                usuarios = crud.buscaTodos();
                if (usuarios != null) {
                    ArrayAdapter<UsuarioBean> adapter = new ArrayAdapter<UsuarioBean>(TodosUsu.this, android.R.layout.simple_list_item_1, usuarios);
                    lista.setAdapter(adapter);
                }
                Toast.makeText(getApplicationContext(), "Usuário deletado!", Toast.LENGTH_SHORT).show();
                return true;
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
