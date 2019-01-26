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

public class TodosCat extends AppCompatActivity {

    ListView lista;
    ControllerCategoria crud;
    ArrayList<CategoriaBean> categorias;
    CategoriaBean cat = new CategoriaBean();
    UsuarioBean usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_cat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Menu");
        usu = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");
        lista = (ListView) findViewById(R.id.listCat);
        registerForContextMenu(lista);

        crud = new ControllerCategoria(getBaseContext());
        categorias = crud.buscaTodos();

        if (categorias.size() > 0) {
            ArrayAdapter<CategoriaBean> adapter = new ArrayAdapter<CategoriaBean>(TodosCat.this, android.R.layout.simple_list_item_1, categorias);
            lista.setAdapter(adapter);

            final Cursor cursor = crud.buscaTodosc();

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String codigo;
                    cursor.moveToPosition(position);
                    codigo = cursor.getString(cursor.getColumnIndexOrThrow("id_cat"));
                    Intent intent = new Intent(TodosCat.this, AlterarCat.class);
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
                    cat.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id_cat"))));
                    return false;
                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuItem = menu.add("Deletar Categoria");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                crud.excluiCategoria(cat.getId());
                categorias = crud.buscaTodos();
                if (categorias != null) {
                    ArrayAdapter<CategoriaBean> adapter = new ArrayAdapter<CategoriaBean>(TodosCat.this, android.R.layout.simple_list_item_1, categorias);
                    lista.setAdapter(adapter);
                }
                Toast.makeText(getApplicationContext(), "Categoria deletada!", Toast.LENGTH_SHORT).show();
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
