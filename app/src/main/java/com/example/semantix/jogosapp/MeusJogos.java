package com.example.semantix.jogosapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MeusJogos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    UsuarioBean u;
    ListView lista;
    ArrayList<JogoUsuBean> jogosusu;
    ControllerJogo crud;
    ControllerUsuario crudU;
    ControllerCategoria crudC;
    ArrayList<UsuarioBean> usuarios;
    ArrayList<JogoBean> jogos;
    ArrayList<CategoriaBean> categorias;
    JogoUsuBean jogoUsuBean = new JogoUsuBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_jogos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        u = (UsuarioBean) getIntent().getSerializableExtra("UsuLogado");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        crud = new ControllerJogo(getBaseContext());
        crudU = new ControllerUsuario(getBaseContext());
        crudC = new ControllerCategoria(getBaseContext());
        lista = (ListView) findViewById(R.id.myGames);
        registerForContextMenu(lista);
        jogosusu = crud.buscaJogoUsu(u.getId());
        if (jogosusu.size() > 0) {
            usuarios = crudU.buscaTodos();
            jogos = crud.buscaTodos();
            categorias = crudC.buscaTodos();
            for (JogoUsuBean j : jogosusu) {
                for (UsuarioBean u : usuarios) {
                    if (j.getUsu().getId() == u.getId()) {
                        j.setUsu(u);
                    }
                }
                for (JogoBean jo : jogos) {

                    if (j.getJogo().getId_jogo() == jo.getId_jogo()) {
                        for (CategoriaBean c : categorias) {
                            if (jo.getCat().getId() == c.getId()) {
                                jo.setCat(c);
                            }
                        }
                        j.setJogo(jo);
                    }
                }
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, jogosusu);
            lista.setAdapter(adapter);
            final Cursor cursor = crud.buscaTodosJogosUsucs();
            lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor.moveToPosition(position);
                    jogoUsuBean.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id"))));
                    return false;
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meus_jogos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getIntent().removeExtra("UsuLogado");
            Intent intent = new Intent(MeusJogos.this, Login.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent ite = null;

        if (id == R.id.buscaUsuarios) {
            ite = new Intent(MeusJogos.this, TodosUsu.class);
        } else if (id == R.id.buscaJogos) {
            ite = new Intent(MeusJogos.this, TodosJogos.class);
        } else if (id == R.id.cadJogo) {
            ite = new Intent(MeusJogos.this, CadastroJogo.class);
        } else if (id == R.id.buscaCategorias) {
            ite = new Intent(MeusJogos.this, TodosCat.class);
        } else if (id == R.id.cadCat) {
            ite = new Intent(MeusJogos.this, CadastroCategoria.class);
        }
        ite.putExtra("UsuLogado", u);
        startActivity(ite);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem menuItem = menu.add("Remover jogo da lista");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                crud.excluiJogoUsu(jogoUsuBean.getId());
                jogosusu = crud.buscaJogoUsu(u.getId());
                    usuarios = crudU.buscaTodos();
                    jogos = crud.buscaTodos();
                    categorias = crudC.buscaTodos();
                    for (JogoUsuBean j : jogosusu) {
                        for (UsuarioBean u : usuarios) {
                            if (j.getUsu().getId() == u.getId()) {
                                j.setUsu(u);
                            }
                        }
                        for (JogoBean jo : jogos) {

                            if (j.getJogo().getId_jogo() == jo.getId_jogo()) {
                                for (CategoriaBean c : categorias) {
                                    if (jo.getCat().getId() == c.getId()) {
                                        jo.setCat(c);
                                    }
                                }
                                j.setJogo(jo);
                            }
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(MeusJogos.this, android.R.layout.simple_list_item_1, jogosusu);
                    lista.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), "Jogo Removido!", Toast.LENGTH_SHORT).show();
                return true;
                }
            });
        }
    }
