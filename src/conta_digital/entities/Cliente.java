package conta_digital.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import conta_digital.models.ContaCorrente;

public class Cliente {

	private String nome;
	private String cpf;
	private String email;
	
	private Set<Conta> contas;
	
	public Cliente() {
		
	}

	public Cliente(String nome, String cpf, String email) {
		
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		
		this.contas = new HashSet<Conta>();
		
	}

	public void addConta(int numero, int conta, double valor, String tipo) {
		
		if(tipo.equalsIgnoreCase("corrente")) {
			
			contas.add(new ContaCorrente(numero, conta, valor));
			
			System.out.println("\n-> Adicionado uma conta corrente para o cliente:\n "+this.toString());
			
		}else if( tipo.equals("pupança") ) {
			
			
			
		}else {
			throw new RuntimeException("Tipo de conta inexistente!");
		}
		
	}
	
	public void addConta(int numero, int conta, double valor, String tipo, Float juros, Double credito) {
		
		if(tipo.equalsIgnoreCase("corrente")) {
			
			contas.add( new ContaCorrente(numero, conta, valor, juros, credito) );
			
			System.out.println("\n-> Adicionado uma conta corrente para o cliente:\n "+this.toString());
			
		}else if( tipo.equals("pupança") ) {
			
			
			
		}else {
			throw new RuntimeException("Tipo de conta inexistente!");
		}
		
	}
	
	public Conta getConta(String tipo) {
		
		if( tipo.equalsIgnoreCase("corrente")) {
			
			return this.contas.stream().filter( t -> (t instanceof ContaCorrente) ).collect(Collectors.toList()).getFirst();
			
		}
		
		return null;
		
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", contas=" + contas + "]";
	}
	
}
