package com.bcfurtado.EmpresteiAndroid.bean;

import java.io.Serializable;
import java.util.Date;

public class Emprestimo implements Serializable {

	private long id;
	private String contato;
	private String objeto;
	private String observacao;
	private Date dt_emprestimo;
	private Date dt_devolucao;
	private boolean entregue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getDt_emprestimo() {
		return dt_emprestimo;
	}
	public void setDt_emprestimo(Date dt_emprestimo) {
		this.dt_emprestimo = dt_emprestimo;
	}
	public Date getDt_devolucao() {
		return dt_devolucao;
	}
	public void setDt_devolucao(Date dt_devolucao) {
		this.dt_devolucao = dt_devolucao;
	}
	public boolean isEntregue() {
		return entregue;
	}
	public void setEntregue(boolean entregue) {
		this.entregue = entregue;
	}
	
	
	
	
}
