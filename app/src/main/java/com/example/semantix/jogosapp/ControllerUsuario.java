package com.example.semantix.jogosapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.semantix.jogosapp.database.JogosOpenHelper;

import java.util.ArrayList;


public class ControllerUsuario {

    private SQLiteDatabase db;
    private JogosOpenHelper banco;

    public ControllerUsuario(Context context){
        banco = new JogosOpenHelper(context);
    }

    public String insereUsuario(UsuarioBean u){
        ContentValues valores = new ContentValues();;
        long result;
        String retorno;
        db = banco.getWritableDatabase();
        valores.put("login", u.getLogin());
        valores.put("senha", u.getSenha());
        valores.put("status", u.getStatus());
        valores.put("tipo", u.getTipo());
        result = db.insert("usuarios", null, valores);
        db.close();
        if (result == -1){
            retorno = "Erro ao inserir dados.";
        }else{
            retorno = "Usuário Inserido com Sucesso!";
        }
        return retorno;
    }

    public ArrayList<UsuarioBean> buscaTodos(){

        ArrayList<UsuarioBean> u = new ArrayList<UsuarioBean>();
        db = banco.getWritableDatabase();
        String[] columns = {"id", "login", "senha", "status", "tipo"};
        Cursor cursor = db.query("usuarios", columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                UsuarioBean usu = new UsuarioBean();
                usu.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                usu.setLogin(cursor.getString(cursor.getColumnIndexOrThrow("login")));
                usu.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("senha")));
                usu.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                usu.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));

                u.add(usu);
            } while (cursor.moveToNext());
        }
        return u;
    }

    public Cursor buscaTodosc(){
        db = banco.getWritableDatabase();
        String[] columns = {"id", "login", "senha", "status", "tipo"};
        Cursor cursor = db.query("usuarios", columns, null, null, null, null, null, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void alteraUsuario(UsuarioBean usu){
        db = banco.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("login", usu.getLogin());
        valores.put("senha", usu.getSenha());
        valores.put("status", usu.getStatus());
        valores.put("tipo", usu.getTipo());
        String[] parametros = {Integer.toString(usu.getId())};
        db.update("usuarios", valores, "id = ?", parametros);
        db.close();
    }

    public void excluiUsuario(int id){
        db = banco.getWritableDatabase();
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);
        db.delete("usuarios","id = ?", parametros);
        db.close();
    }

    public UsuarioBean buscaUsuario(int id){
        UsuarioBean usu = new UsuarioBean();
        Cursor cursor;
        db = banco.getReadableDatabase();
        String[] campos = {"id", "login", "senha", "status", "tipo"};
        String where = "id = " + id;
        cursor = db.query("usuarios", campos, where, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
            usu.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            usu.setLogin(cursor.getString(cursor.getColumnIndexOrThrow("login")));
            usu.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("senha")));
            usu.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            usu.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
        }

        db.close();
        return usu;
    }

    public UsuarioBean validaUsuario(UsuarioBean usuario){
        Cursor cursor;
        db = banco.getWritableDatabase();
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        String[] campos = new String[] {usuario.getLogin(), usuario.getSenha()};
        cursor = db.rawQuery(sql, campos);
        UsuarioBean u = new UsuarioBean();
        if(cursor.moveToNext()){
            u.setId(cursor.getInt(cursor.getColumnIndex("id")));
            u.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            u.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            u.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            u.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
            return u;
        }
        else{
            return usuario;
        }

    }

}
