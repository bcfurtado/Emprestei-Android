package com.bcfurtado.EmpresteiAndroid.ui;

import java.util.ArrayList;

import com.bcfurtado.EmpresteiAndroid.R;
import com.bcfurtado.EmpresteiAndroid.bean.Emprestimo;
import com.bcfurtado.EmpresteiAndroid.db.ControladorDB;
import com.bcfurtado.EmpresteiAndroid.util.AdpterCustom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends Activity implements OnItemClickListener {
    
	private ControladorDB controlador_db;
	
	private ListView listView;
	private AdpterCustom adpterCustom;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        controlador_db = new ControladorDB(getApplicationContext());

        adpterCustom = new AdpterCustom(getApplicationContext(),controlador_db.selecionarTodosEmprestimosNaoDevolvidos());
        
        listView = (ListView)findViewById(R.id.listViewObjetosEmprestados);
        listView.setAdapter(adpterCustom);
        listView.setOnItemClickListener(this);
        
        
        
    }

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	adpterCustom.notifyDataSetChanged(controlador_db.selecionarTodosEmprestimosNaoDevolvidos());
    }
    
	public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
		
		Bundle parametros = new Bundle();
		parametros.putLong("id", id);
		Log.d("Home.onItemClick", "Item Selecionado. ID:" + id);
		Intent it = new Intent(Home.this,VisualizarEmprestimo.class);
		it.putExtras(parametros);
		
		startActivity(it);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.home_menu, menu);
		
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
		case R.id.textMenuNovoEmprestimo:
			Intent it = new Intent(Home.this,NovoEmprestimo.class);
			startActivity(it);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
	}
    
}

