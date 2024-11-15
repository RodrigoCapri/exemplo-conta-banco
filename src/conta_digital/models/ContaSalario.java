package conta_digital.models;

import conta_digital.entities.Conta;

public class ContaSalario extends Conta{

	
	public ContaSalario(Integer numero, Integer conta, Double valor) {
		super(numero, conta, valor);
	}

	@Override
	public Double transferir(Double valor, Conta conta) {
		
		throw new RuntimeException("Uma conta salário não pode realizar transferências!");
		
	}
	
}
