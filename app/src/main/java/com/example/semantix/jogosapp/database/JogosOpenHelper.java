package com.example.semantix.jogosapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JogosOpenHelper extends SQLiteOpenHelper {
    public JogosOpenHelper(Context context) {
        super(context, "jogos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE usuarios(id integer primary key autoincrement, login text, senha text, status text, tipo text)";
        String sql2 = "CREATE TABLE jogos(id_jogo integer primary key autoincrement, nome text, descricao text, id_cat integer, foreign key (id_cat) references categoria (id_cat))";
        String sql3 = "CREATE TABLE categoria(id_cat integer primary key autoincrement, tipo text)";
        String sql4 = "CREATE TABLE jogos_usu(id integer primary key autoincrement, id_usuario integer, id_jogo integer, foreign key (id_usuario) references usuarios (id), foreign key (id_jogo) references jogos (id_jogo))";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists jogos");
        db.execSQL("drop table if exists usuarios");
        db.execSQL("drop table if exists jogos_usu");
        db.execSQL("drop table if exists categoria");
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }
}
