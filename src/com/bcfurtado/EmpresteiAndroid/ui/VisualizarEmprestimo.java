package com.bcfurtado.EmpresteiAndroid.ui;

import java.text.SimpleDateFormat;

import com.bcfurtado.EmpresteiAndroid.R;
import com.bcfurtado.EmpresteiAndroid.bean.Emprestimo;
import com.bcfurtado.EmpresteiAndroid.db.ControladorDB;
import com.bcfurtado.EmpresteiAndroid.util.FotoContato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VisualizarEmprestimo extends Activity {

	private ControladorDB db;
	private long id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visualizar_emprestimo);
		
		db = new ControladorDB(getApplicationContext());
		
		TextView contato = (TextView)findViewById(R.id.textViewContatoEdit);
		TextView objeto = (TextView)findViewById(R.id.textViewObjetoEdit);
		TextView observacao = (TextView)findViewById(R.id.TextViewObservacaoEdit);
		TextView dt_devolucao = (TextView)findViewById(R.id.textViewDtDevolucaoEdit);
		
		ImageView foto_contato = (ImageView)findViewById(R.id.imageView1);
		
		Bundle parametros = getIntent().getExtras();
		
		
		if ( parametros == null || parametros.isEmpty() ) {
			Log.e("VisualizarEmprestimo", "Bundle null ou vazios");
		} else {
			
			if( parametros.containsKey("id") ) {
				id = parametros.getLong("id");
				
				Emprestimo emprestimo = db.selecionar(id);
				
				contato.setText(emprestimo.getContato());
				objeto.setText(emprestimo.getObjeto());
				
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				dt_devolucao.setText( formato.format( emprestimo.getDt_devolucao()) );
				
				if ( emprestimo.getObservacao() == null || emprestimo.getObservacao().equals("") ) {
					observacao.setText("Sem Observações!");
				} else {
					observacao.setText(emprestimo.getObservacao());
				}
				
				//Bitmap foto = new FotoContato().pegar_foto(this.getContentResolver(), emprestimo.getContato());
				Bitmap foto = FotoContato.pegar_foto(this.getContentResolver(), emprestimo.getContato());
				
				if ( foto != null ) {
					foto_contato.setImageBitmap(foto);				}
			}
			
		}
		
		Button realizarDevolucao = (Button)findViewById(R.id.buttonDevolver);		
		Button verInformacaoesContato = (Button)findViewById(R.id.buttonVerInformacoesContato);

		realizarDevolucao.setOnClickListener( new View.OnClickListener() {
			
			public void onClick(View v) {
				Log.d("ButtonDevolver", "Funciona");
				AlertDialog.Builder confirmacao = new AlertDialog.Builder( VisualizarEmprestimo.this );
				confirmacao.setTitle("Deseja confirmar a devolução?");
				confirmacao.setCancelable(true);
				confirmacao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						db.realizarDevolucao(id);
						finish();
					}
				});
				confirmacao.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = confirmacao.create();
				alert.show();
				
			}
				
		});		
	}
	
}
