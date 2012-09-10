package com.bcfurtado.EmpresteiAndroid.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bcfurtado.EmpresteiAndroid.R;
import com.bcfurtado.EmpresteiAndroid.bean.Emprestimo;

public class AdpterCustom extends BaseAdapter {

	private Context context;
	private ArrayList<Emprestimo> lista;
	
	public AdpterCustom( Context context, ArrayList<Emprestimo> lista ){
		this.context = context;
		if ( lista != null ){
			this.lista = lista;
		} else {
			lista = new ArrayList<Emprestimo>();
		}
	}
	
	public int getCount() {
		return lista.size();
	}

	public Emprestimo getItem(int position) {
		return lista.get(position);
	}

	public long getItemId(int position) {
		return lista.get(position).getId(); // ***
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Emprestimo e = lista.get(position);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.emprestimo_item , null);
		
		TextView nomeObjeto = (TextView)view.findViewById(R.id.textViewObjeto);
		TextView nomeContato = (TextView)view.findViewById(R.id.textViewContato);
		
		nomeObjeto.setText(e.getObjeto());
		nomeContato.setText(e.getContato());
		
		return view;
	}
	
	public void notifyDataSetChanged( ArrayList<Emprestimo> lista ) {
		
		if ( lista != null ){
			this.lista = lista;
		} else {
			lista = new ArrayList<Emprestimo>();
		}
		this.notifyDataSetChanged();
	}

	
}
