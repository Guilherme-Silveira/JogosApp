package com.example.semantix.jogosapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.semantix.jogosapp.database.JogosOpenHelper;

import java.util.ArrayList;

public class ControllerJogo {
    private SQLiteDatabase db;
    private JogosOpenHelper banco;

    public ControllerJogo(Context context){
        banco = new JogosOpenHelper(context);
    }

    public String insereJogo(JogoBean u){
        ContentValues valores = new ContentValues();
        long result;
        String retorno;
        db = banco.getWritableDatabase();
        valores.put("nome", u.getNome());
        valores.put("descricao", u.getDescricao());
        valores.put("id_cat", u.getCat().getId());
        result = db.insert("jogos", null, valores);
        db.close();
        if (result == -1){
            retorno = "Erro ao inserir dados.";
        }else{
            retorno = "Jogo Inserido com Sucesso!";
        }
        return retorno;
    }

    public ArrayList<JogoBean> buscaTodos(){

        ArrayList<JogoBean> u = new ArrayList<JogoBean>();
        db = banco.getReadableDatabase();
        String sql = "SELECT * FROM jogos /*JOIN categoria ON (categoria.id_cat=jogos.id_cat)*/";
        Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToFirst()) {
                do {
                    JogoBean usu = new JogoBean();
                    CategoriaBean cat = new CategoriaBean();

                    cat.setId(cursor.getInt(cursor.getColumnIndexOrThrow("jogos.id_cat")));
                    //cat.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("categoria.tipo")));
                    usu.setId_jogo(cursor.getInt(cursor.getColumnIndexOrThrow("jogos.id_jogo")));
                    usu.setNome(cursor.getString(cursor.getColumnIndexOrThrow("jogos.nome")));
                    usu.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("jogos.descricao")));
                    usu.setCat(cat);

                    u.add(usu);
                } while (cursor.moveToNext());
            }
        return u;
    }

    public Cursor buscaTodosc(){
        db = banco.getWritableDatabase();
        String sql = "SELECT * FROM jogos";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void alteraJogo(JogoBean u){
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", u.getNome());
        valores.put("descricao", u.getDescricao());
        valores.put("id_cat", u.getCat().getId());
        String[] parametros = {Integer.toString(u.getId_jogo())};
        db.update("jogos", valores, "id_jogo = ?", parametros);
        db.close();
    }

    public void excluiJogo(int id){
        db = banco.getWritableDatabase();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        db.delete("jogos","id_jogo = ?", parametros);
        db.close();
    }

    public JogoBean buscaJogo(int id){
        JogoBean usu = new JogoBean();
        Cursor cursor;
        db = banco.getReadableDatabase();
        String[] campos = {"id_jogo", "nome", "descricao", "id_cat"};
        String where = "id_jogo = " + id;
        cursor = db.query("jogos", campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
            CategoriaBean cat = new CategoriaBean();
            cat.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_cat")));
            usu.setId_jogo(cursor.getInt(cursor.getColumnIndexOrThrow("id_jogo")));
            usu.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            usu.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            usu.setCat(cat);

        }

        db.close();
        return usu;
    }

    public String atribuirUsu(UsuarioBean usu, JogoBean jogo){
        ContentValues valores = new ContentValues();
        long result;
        db = banco.getWritableDatabase();
        valores.put("id_usuario", usu.getId());
        valores.put("id_jogo", jogo.getId_jogo());
        result = db.insert("jogos_usu", null, valores);
        if (result == -1){
            return "Erro ao adicionar jogo";
        }else{
            return "Jogo adicionado com sucesso!";
        }
    }

    public ArrayList<JogoUsuBean> buscaJogoUsu(int id){
        ArrayList<JogoUsuBean> jogosUsu = new ArrayList<JogoUsuBean>();
        db = banco.getWritableDatabase();
        String[] campos = {"id", "id_usuario", "id_jogo"};
        String where = "id_usuario = " + id;
        Cursor cursor = db.query("jogos_usu", campos, where, null, null, null, null, null);

            if(cursor.moveToFirst()) {
                do {
                    JogoUsuBean ju = new JogoUsuBean();
                    UsuarioBean u = new UsuarioBean();
                    JogoBean j = new JogoBean();
                    u.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario")));
                    j.setId_jogo(cursor.getInt(cursor.getColumnIndexOrThrow("id_jogo")));
                    ju.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    ju.setUsu(u);
                    ju.setJogo(j);

                    jogosUsu.add(ju);
                } while (cursor.moveToNext());
            }

        return jogosUsu;
    }

    public void excluiJogoUsu(int id){
        db = banco.getWritableDatabase();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        db.delete("jogos_usu","id = ?", parametros);
        db.close();
    }

    public Cursor buscaTodosJogosUsucs(){
        db = banco.getWritableDatabase();
        String sql = "SELECT * FROM jogos_usu";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
}
