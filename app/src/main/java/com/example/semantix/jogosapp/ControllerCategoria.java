package com.example.semantix.jogosapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.semantix.jogosapp.database.JogosOpenHelper;

import java.util.ArrayList;


public class ControllerCategoria {

    private SQLiteDatabase db;
    private JogosOpenHelper banco;

    public ControllerCategoria(Context context){
        banco = new JogosOpenHelper(context);
    }

    public String insereCategoria(CategoriaBean u){
        ContentValues valores = new ContentValues();
        long result;
        String retorno;
        db = banco.getWritableDatabase();
        valores.put("tipo", u.getTipo());
        result = db.insert("categoria", null, valores);
        db.close();
        if (result == -1){
            retorno = "Erro ao inserir dados.";
        }else{
            retorno = "Categoria Inserida com Sucesso!";
        }
        return retorno;
    }

    public ArrayList<CategoriaBean> buscaTodos(){

        ArrayList<CategoriaBean> u = new ArrayList<CategoriaBean>();
        db = banco.getWritableDatabase();
        String[] columns = {"id_cat", "tipo"};
        Cursor cursor = db.query("categoria", columns, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CategoriaBean usu = new CategoriaBean();
                usu.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_cat")));
                usu.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));

                u.add(usu);
            } while (cursor.moveToNext());
        }
        return u;
    }

    public Cursor buscaTodosc(){
        db = banco.getWritableDatabase();
        String[] columns = {"id_cat", "tipo"};
        Cursor cursor = db.query("categoria", columns, null, null, null, null, null, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void alteraCategoria(CategoriaBean usu){
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("tipo", usu.getTipo());
        String[] parametros = {Integer.toString(usu.getId())};
        db.update("categoria", valores, "id_cat = ?", parametros);
        db.close();
    }

    public void excluiCategoria(int id){
        db = banco.getWritableDatabase();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        db.delete("categoria","id_cat = ?", parametros);
        db.close();
    }

    public CategoriaBean buscaCategoria(int id){
        CategoriaBean usu = new CategoriaBean();
        Cursor cursor;
        db = banco.getReadableDatabase();
        String[] campos = {"id_cat", "tipo"};
        String where = "id_cat = " + id;
        cursor = db.query("categoria", campos, where, null, null, null, null);

        if(cursor.moveToFirst()){
            usu.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_cat")));
            usu.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
        }

        db.close();
        return usu;
    }


}
