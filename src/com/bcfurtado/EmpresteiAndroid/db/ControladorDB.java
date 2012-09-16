package com.bcfurtado.EmpresteiAndroid.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.bcfurtado.EmpresteiAndroid.bean.Emprestimo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ControladorDB {

	private Context context;
	private Cursor cursor;
	private SQLiteDatabase db;
	private DBHelper dbHelper;
	private SimpleDateFormat formato;
	
	public ControladorDB(Context context) {
		this.context = context;
		this.formato = new SimpleDateFormat("yyyy-MM-dd");
	}

	public void inserir(Emprestimo emprestimo) {

		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		
		values.put(DBHelper.EMPRESTIMO_CONTATO ,emprestimo.getContato());
		values.put(DBHelper.EMPRESTIMO_OBJETO ,emprestimo.getObjeto());
		values.put(DBHelper.EMPRESTIMO_OBSERVACAO ,emprestimo.getObservacao());
		values.put(DBHelper.EMPRESTIMO_DT_EMPRESTIMO , formato.format(emprestimo.getDt_emprestimo()) );
		values.put(DBHelper.EMPRESTIMO_DT_DEVOLUCAO ,  formato.format(emprestimo.getDt_devolucao()) );
		values.put(DBHelper.EMPRESTIMO_ENTREGUE , emprestimo.isEntregue() );

		db.insert(DBHelper.NOME_TABELA, null, values);
		
		db.close();
		
		Log.d("ControladorDB","Inserindo Emprestimo");

	}

	public Emprestimo selecionar(long id) {

		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();

		Emprestimo emprestimo = new Emprestimo();
		cursor = db.query(DBHelper.NOME_TABELA, new String[]{ 
			DBHelper.EMPRESTIMO_ID,
			DBHelper.EMPRESTIMO_CONTATO,
			DBHelper.EMPRESTIMO_OBJETO,
			DBHelper.EMPRESTIMO_OBSERVACAO,
			DBHelper.EMPRESTIMO_DT_EMPRESTIMO,
			DBHelper.EMPRESTIMO_DT_DEVOLUCAO,
			DBHelper.EMPRESTIMO_ENTREGUE }, DBHelper.EMPRESTIMO_ID + "=" + id, null, null, null, null);
		
		
		if ( cursor == null ) {
			Log.d("ControladorDB","Id não encontrado");
			
			cursor.close();
			db.close();
			
			return null;
		} else {
			cursor.moveToFirst();
			
			emprestimo.setId( 		cursor.getLong(cursor.getColumnIndex(DBHelper.EMPRESTIMO_ID)) );
			emprestimo.setContato( 	cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_CONTATO)) );
			emprestimo.setObjeto( 	cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_OBJETO)) );
			emprestimo.setObservacao( cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_OBSERVACAO)) );
			
			try {
				emprestimo.setDt_emprestimo(formato.parse( cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_DT_EMPRESTIMO)) ) );
				emprestimo.setDt_devolucao(formato.parse( cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_DT_DEVOLUCAO)) ) );

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if ( cursor.getInt(cursor.getColumnIndex(DBHelper.EMPRESTIMO_ENTREGUE)) == 0) {
				emprestimo.setEntregue( false );
			} else {
				emprestimo.setEntregue( true );
			}
			
			cursor.close();
			db.close();
			return emprestimo;
			
		}
		
	}

	public ArrayList<Emprestimo> selecionarTodos(){

		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();

		cursor = db.query(DBHelper.NOME_TABELA, new String[]{ 
				DBHelper.EMPRESTIMO_ID,
				DBHelper.EMPRESTIMO_CONTATO,
				DBHelper.EMPRESTIMO_OBJETO,
				DBHelper.EMPRESTIMO_OBSERVACAO,
				DBHelper.EMPRESTIMO_DT_EMPRESTIMO,
				DBHelper.EMPRESTIMO_DT_DEVOLUCAO,
				DBHelper.EMPRESTIMO_ENTREGUE }, null, null, null, null, null);
		
		cursor.moveToFirst();
		ArrayList<Emprestimo> lista = new ArrayList<Emprestimo>();
		
		while ( !cursor.isAfterLast() ){
			
			Emprestimo emprestimo = new Emprestimo();
			
			emprestimo.setId( cursor.getLong(cursor.getColumnIndex(DBHelper.EMPRESTIMO_ID)));
			emprestimo.setContato( cursor.getString( cursor.getColumnIndex(DBHelper.EMPRESTIMO_CONTATO)) ) ;
			emprestimo.setObjeto( cursor.getString( cursor.getColumnIndex(DBHelper.EMPRESTIMO_OBJETO)) ) ;
			emprestimo.setObservacao( cursor.getString( cursor.getColumnIndex(DBHelper.EMPRESTIMO_OBSERVACAO) ) );
			
			try {
				emprestimo.setDt_emprestimo(formato.parse( cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_DT_EMPRESTIMO) ) ) );
				emprestimo.setDt_devolucao(formato.parse( cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_DT_DEVOLUCAO)) ) );
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if ( cursor.getInt(cursor.getColumnIndex(DBHelper.EMPRESTIMO_ENTREGUE)) == 0) {
				emprestimo.setEntregue( false );
			} else {
				emprestimo.setEntregue( true );
			}			
			lista.add(emprestimo);
			
			cursor.moveToNext();
		}
		
		cursor.close();
		db.close();
		
		return lista;

	}
	
	
	// Seleciona Todos os Emprestimos não Devolvidos
	public ArrayList<Emprestimo> selecionarTodosEmprestimosNaoDevolvidos() {

		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();

		cursor = db.query(DBHelper.NOME_TABELA, new String[]{ 
				DBHelper.EMPRESTIMO_ID,
				DBHelper.EMPRESTIMO_CONTATO,
				DBHelper.EMPRESTIMO_OBJETO }, DBHelper.EMPRESTIMO_ENTREGUE + "= 0", null, null, null, DBHelper.EMPRESTIMO_DT_DEVOLUCAO + " ASC");
							//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
		cursor.moveToFirst();
		ArrayList<Emprestimo> lista = new ArrayList<Emprestimo>();
		
		while ( !cursor.isAfterLast() ){
			
			Emprestimo emprestimo = new Emprestimo();
			
			emprestimo.setId(		cursor.getInt	(cursor.getColumnIndex(DBHelper.EMPRESTIMO_ID)));
			emprestimo.setContato(	cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_CONTATO)));
			emprestimo.setObjeto(	cursor.getString(cursor.getColumnIndex(DBHelper.EMPRESTIMO_OBJETO)));
			
			lista.add(emprestimo);
			
			cursor.moveToNext();
		}
		
		cursor.close();
		db.close();
		
		return lista;

	}

	public void realizarDevolucao( long id ) {
		
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DBHelper.EMPRESTIMO_ENTREGUE , true );
		
		db.update(DBHelper.NOME_TABELA, values,  DBHelper.EMPRESTIMO_ID + "=" + id, null);
		
		db.close();
	}

	public void remover() {

	}

}
