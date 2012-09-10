package com.bcfurtado.EmpresteiAndroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private final static String NOME_DB = "emprestei_db";
	private final static int VERSAO_DB = 1;

	public final static String NOME_TABELA = "pendente";
	
	public final static String EMPRESTIMO_ID = "id";
	public final static String EMPRESTIMO_CONTATO = "contato";
	public final static String EMPRESTIMO_OBJETO = "objeto";
	public final static String EMPRESTIMO_OBSERVACAO = "observacao";
	public final static String EMPRESTIMO_DT_EMPRESTIMO = "dt_emprestimo";
	public final static String EMPRESTIMO_DT_DEVOLUCAO = "dt_devolucao";
	public final static String EMPRESTIMO_ENTREGUE = "entregue";


	// public final static String

	public DBHelper(Context contexto) {
		super(contexto, NOME_DB, null, VERSAO_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String sql = "";
		sql += "CREATE TABLE " + NOME_TABELA + " ( "
				+ "	    id            INTEGER PRIMARY KEY AUTOINCREMENT "
				+ "	                          NOT NULL"
				+ "	                          UNIQUE,"
				+ "	    contato       VARCHAR NOT NULL,"
				+ "	    objeto        VARCHAR NOT NULL,"
				+ "	    observacao    TEXT,"
				+ "	    dt_emprestimo DATE    NOT NULL,"
				+ "	    dt_devolucao  DATE    NOT NULL,"
				+ "		entregue      BOOLEAN );";
		
		Log.d("DBHelper.onCreate", "Criação da Tabela");
		
		db.execSQL(sql);
		
		
//		ContentValues values = new ContentValues();
//		
//		values.put("contato" , "Nico Cavalera");
//		values.put("objeto" , "Diablo 3" );
//		values.put("observacao",	"Sem Arranhões" );
//		values.put("dt_emprestimo",	"2012-01-01" );
//		values.put("dt_devolucao", "2012-01-03");
//
//		db.insert(this.NOME_TABELA, null, values);
//		
//		values = new ContentValues();
//		
//		values.put("contato" , "Bruno Furtado");
//		values.put("objeto" , "Cabo HDMI" );
//		values.put("dt_emprestimo",	"2012-01-02" );
//		values.put("dt_devolucao", "2012-01-04");
//
//		db.insert(this.NOME_TABELA, null, values);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("DBHelper.onUpgrade","Atualizando o Banco de Dados");

	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		Log.d("DBHelper.getWritableDatabase"," Carregando o Banco de Dados");
		return super.getWritableDatabase();
	}
}
