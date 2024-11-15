package conta_digital.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Conta {

	private Integer numero;
	private Integer conta;
	private Double valor;
	
	private Map<String, Double> movimentation;
	
	public Conta() {
		
	}

	public Conta(Integer numero, Integer conta, Double valor) {
		
		this.numero = numero;
		this.conta = conta;
		this.valor = valor;
		this.movimentation = new HashMap<String, Double>();
		
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	protected void addMovimentation(String description, Double valor) {
		this.movimentation.put(description, valor);
	}
	
	protected Map<String, Double> getMovimentation(){
		return this.movimentation;
	}
	
	public Double sacar(Double valor) {
		
		double new_valor = this.valor - valor;
		
		if( new_valor < 0)
			throw new RuntimeException("Não há saldo para sacar este valor!");
		
		this.valor = new_valor;
		
		this.addMovimentation("* Saque", valor);
		
		return this.valor;
		
	}
	
	public Double depositar(Double valor) {
		
		this.valor += valor;
		
		this.addMovimentation("* Depósito", valor);
		
		return this.valor;
	}

	public Double transferir(Double valor, Conta conta){
		
		double new_valor = this.valor - valor;
		
		if( new_valor < 0 )
			throw new RuntimeException("Não há saldo para transferir este valor!");
		
		this.valor = new_valor;
		
		this.addMovimentation("* Transferencia\n-> Conta: "+conta, valor);
		
		return new_valor;
	}
	
	public List<String> extrato(){
		//Monta uma lista de String contendo todas as movimentações
		List<String> list = this.getMovimentation()
				.entrySet()
				.stream()
				.map( t -> t.getKey()+" - R$"+t.getValue() )
				.collect(Collectors.toList());
		
		list.addFirst("\n-> EXTRATO");
		list.add("------------------SALDO------------------\n-> Débito: R$"+this.valor);
		
		return list;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(conta, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(conta, other.conta) && Objects.equals(numero, other.numero);
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", conta=" + conta + ", valor=" + valor + ", movimentation=" + movimentation
				+ "]";
	}
	
}
