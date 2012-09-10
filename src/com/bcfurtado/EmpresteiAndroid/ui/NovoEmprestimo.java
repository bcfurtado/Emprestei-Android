package com.bcfurtado.EmpresteiAndroid.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.bcfurtado.EmpresteiAndroid.R;
import com.bcfurtado.EmpresteiAndroid.bean.Emprestimo;
import com.bcfurtado.EmpresteiAndroid.db.ControladorDB;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NovoEmprestimo extends Activity {

	private ControladorDB db;
	
	private AutoCompleteTextView contato;
	private EditText objeto;
	private EditText observacao;
	private DatePicker dt_devolucao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.novo_emprestimo);
		
		contato = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewContato);
		objeto = (EditText)findViewById(R.id.editTextObjeto);
		observacao = (EditText)findViewById(R.id.editTextObservacao);
		dt_devolucao = (DatePicker)findViewById(R.id.datePickerDevolucao);
		
		ArrayAdapter<String> adpterContatos = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, this.carregaInformacoesDosContatos());
		contato.setAdapter(adpterContatos);
		
		Button cadastrar = (Button)findViewById(R.id.buttonCadastrar);
		cadastrar.setOnClickListener( new View.OnClickListener() {
			
			public void onClick(View v) {
				if ( valida_campos() ) {
					Emprestimo emprestimo = new Emprestimo();
					emprestimo.setContato(contato.getText().toString() );
					emprestimo.setObjeto( objeto.getText().toString() );
					emprestimo.setObservacao( observacao.getText().toString() );
					
					GregorianCalendar calendarioCadastro = new GregorianCalendar();
					GregorianCalendar calendarioDevolucao = new GregorianCalendar();

					Date cadastro = calendarioCadastro.getTime();
					calendarioDevolucao.set( dt_devolucao.getYear(), dt_devolucao.getMonth(), dt_devolucao.getDayOfMonth() );
					Date devolucao = calendarioDevolucao.getTime();

					emprestimo.setDt_emprestimo( cadastro );
					emprestimo.setDt_devolucao( devolucao );
					emprestimo.setEntregue(false);
					
					db = new ControladorDB(getApplicationContext());
					db.inserir(emprestimo);

					Toast.makeText(getApplicationContext(), "Emprestimo Cadastrado com sucesso.", Toast.LENGTH_LONG).show();
					
					finish();
					
				} else {
					Log.d("NovoEmprestimo","Campos não validados.");
					Toast.makeText(getApplicationContext(), "As informações não estão corretas", Toast.LENGTH_SHORT).show();
					
				}
			}
		});
		
	}
	
	private boolean valida_campos(){
		
		GregorianCalendar calendarioCadastro = new GregorianCalendar();
		GregorianCalendar calendarioDevolucao = new GregorianCalendar();

		Date cadastro = calendarioCadastro.getTime();
		calendarioDevolucao.set( dt_devolucao.getYear(), dt_devolucao.getMonth(), dt_devolucao.getDayOfMonth() );
		Date devolucao = calendarioDevolucao.getTime();
		
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		
		Log.d("ValidaCampos", "DT CADASTRO " + formato.format(cadastro));
		Log.d("ValidaCampos", "DT DEVOLUÇÃO " + formato.format(devolucao));
		
		
		if ( contato.getText().toString() == null || contato.getText().toString().equals("") ){
			return false;
		} else if ( objeto.getText().toString() == null || objeto.getText().toString().equals("") ) {
			return false;
		} else if ( cadastro.after(devolucao)) {
			return false;
		} else {
			return true;
		}

	}
	
	private String[] carregaInformacoesDosContatos(){
		
		Log.d("NovoEmprestimo","Carregar informações dos contatos");
		ArrayList<String> contatos = new ArrayList<String>();
	
//		String[] camposContato = new String[]{
//				ContactsContract.Contacts._ID,
//				ContactsContract.Contacts.DISPLAY_NAME,
//				ContactsContract.Contacts.PHOTO_ID
//		};
		
		Cursor informacoesContatos = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
		
		informacoesContatos.moveToFirst();
		
		while ( !informacoesContatos.isAfterLast()) {
			contatos.add( informacoesContatos.getString(informacoesContatos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) );
			Log.d("NovoEmprestimo","Contato: " + informacoesContatos.getString(informacoesContatos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) );
			
			informacoesContatos.moveToNext();

		}
		
		return contatos.toArray( new String[contatos.size()] );
		
	}
	
}
