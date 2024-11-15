package conta_digital.models;

import java.util.List;
import java.util.stream.Collectors;

import conta_digital.entities.Conta;

public class ContaCorrente extends Conta{
	
	private Float juros;
	private Double creditos;
	
	public ContaCorrente() {
		
	}

	public ContaCorrente(Integer numero, Integer conta, Double valor, Float juros, Double creditos) {
		super(numero, conta, valor);
		this.juros = juros;
		this.creditos = creditos;
	}

	public ContaCorrente(Integer numero, Integer conta, Double valor) {
		super(numero, conta, valor);
		this.juros = 0.0f;
		this.creditos = 0d;
	}

	public Float getJuros() {
		return juros;
	}

	public void setJuros(Float juros) {
		this.juros = juros;
	}

	public Double getCreditos() {
		return creditos;
	}

	public void setCreditos(Double creditos) {
		this.creditos = creditos;
	}

	@Override
	public Double sacar(Double valor) {

		double new_valor = this.getValor()-valor;
		
		if( new_valor  >= 0 ) { //Se houver saldo, então realiza o saque
			
			this.setValor(new_valor);
			
			this.addMovimentation("* Saque", valor);
			
			return new_valor;
			
		}else if( (this.creditos + (new_valor*this.juros) ) >= 0 ){ //Se não houver saldo, verifica se há créditos
			
			//new_valor += new_valor * this.juros;
			
			this.setValor(new_valor);
			
			this.creditos += new_valor * this.juros;
			
			this.addMovimentation("* Saque", valor);
			
			return new_valor;
			
		}else {
			throw new RuntimeException("Sem limite para saque tanto no débito quanto no crédito!");
		}
		
	}

	@Override
	public Double depositar(Double valor) {
		
		double new_valor = this.getValor() + valor;
		
		if(this.getValor() <= 0) {
			
			this.creditos += valor;
			
			this.setValor(new_valor);
			
			this.addMovimentation("* Depósito", valor);
			
			return new_valor;
			
		}else {
			
			this.setValor(new_valor);
			
			this.addMovimentation("* Depósito", valor);
			
			return new_valor;
			
		}
		
	}

	@Override
	public Double transferir(Double valor, Conta conta) {
		
		double new_valor = this.getValor()-valor;
		
		if( new_valor  >= 0 ) { //Se houver saldo, então realiza a transferencia
			
			this.setValor(new_valor);
			
			conta.setValor( conta.getValor()+valor );
			
			this.addMovimentation("* Transferencia -> conta: "+conta.getNumero()+"/"+conta.getConta(), valor);
			
			return new_valor;
			
		}else if( (this.creditos + (new_valor+(new_valor*this.juros)) ) >= 0 ){ //Se não houver saldo, verifica se há créditos
			
			new_valor += new_valor * this.juros;
			
			this.setValor(new_valor);
			
			this.creditos += new_valor;
			
			conta.setValor( conta.getValor()+valor );
			
			this.addMovimentation("* Transferencia -> conta: "+conta.getNumero()+"/"+conta.getConta(), valor);
			
			return new_valor;
			
		}else {
			throw new RuntimeException("Sem limite para transfência tanto no débito quanto no crédito!");
		}
		
	}
	
	@Override
	public List<String> extrato(){
		
		//Monta uma lista de String contendo todas as movimentações
		List<String> list = this.getMovimentation()
				.entrySet()
				.stream()
				.map( t -> t.getKey()+" - R$"+t.getValue() )
				.collect(Collectors.toList());
		
		list.addFirst("\n-> EXTRATO");
		list.add("------------------SALDO------------------\n-> Débito: R$"+String.format("%.2f", this.getValor())+" | Crédito: R$"+String.format("%.2f", this.creditos));
		
		return list;
		
	}

	@Override
	public String toString() {
		return "ContaCorrente [juros=" + juros + ", creditos=" + creditos + "]";
	}
	
}
