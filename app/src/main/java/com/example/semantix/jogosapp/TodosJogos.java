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

public class TodosJogos extends AppCompatActivity {
    ListView lista;
    ArrayList<JogoBean> jogos;
    ArrayList<CategoriaBean> categorias;
    ControllerJogo crud;
    ControllerCategoria crudC;
    JogoBean jogo = new JogoBean();
    UsuarioBean usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_jogos);

        usu = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Menu");

        crud = new ControllerJogo(getBaseContext());
        crudC = new ControllerCategoria(getBaseContext());

        lista = (ListView) findViewById(R.id.l);
        registerForContextMenu(lista);
        jogos = crud.buscaTodos();
        if (jogos.size() > 0) {
            categorias = crudC.buscaTodos();

            for (JogoBean j : jogos) {
                for (CategoriaBean c : categorias) {
                    if (j.getCat().getId() == c.getId()) {
                        j.setCat(c);
                    }
                }
            }

            ArrayAdapter<JogoBean> adapter = new ArrayAdapter<JogoBean>(TodosJogos.this, android.R.layout.simple_list_item_1, jogos);
            lista.setAdapter(adapter);


            final Cursor cursor = crud.buscaTodosc();

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String codigo;
                    cursor.moveToPosition(position);
                    codigo = cursor.getString(cursor.getColumnIndexOrThrow("id_jogo"));
                    Intent intent = new Intent(TodosJogos.this, AlterarJogo.class);
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
                    jogo.setId_jogo(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id_jogo"))));
                    return false;
                }
            });
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuItem2 = menu.add("Adicionar Jogo");
        MenuItem menuItem = menu.add("Deletar Jogo");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                crud.excluiJogo(jogo.getId_jogo());
                jogos = crud.buscaTodos();
                    categorias = crudC.buscaTodos();

                    for (JogoBean j : jogos) {
                        for (CategoriaBean c : categorias) {
                            if (j.getCat().getId() == c.getId()) {
                                j.setCat(c);
                            }
                        }
                    }

                    ArrayAdapter<JogoBean> adapter = new ArrayAdapter<JogoBean>(TodosJogos.this, android.R.layout.simple_list_item_1, jogos);
                    lista.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), "Jogo deletado!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ArrayList<JogoUsuBean> jogosusu = crud.buscaJogoUsu(usu.getId());
                int decision = 0;
                for (JogoUsuBean ju : jogosusu){
                    if (ju.getJogo().getId_jogo() == jogo.getId_jogo()){
                        decision = 1;
                    }
                }
                if (decision == 1){
                    Toast.makeText(TodosJogos.this, "Você já possui esse jogo em sua lista", Toast.LENGTH_SHORT).show();
                }else {
                    String resul = crud.atribuirUsu(usu, jogo);
                    Toast.makeText(getApplicationContext(), resul, Toast.LENGTH_SHORT).show();
                }
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
