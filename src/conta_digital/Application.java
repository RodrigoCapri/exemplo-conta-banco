package conta_digital;

import java.util.Locale;

import conta_digital.entities.Cliente;
import conta_digital.models.ContaCorrente;
import conta_digital.models.ContaSalario;

public class Application {

	public Application() {
		
		Locale.setDefault(Locale.US);
		
		System.out.println("-> Criando cliente");
		Cliente cli1 = new Cliente("Rogerio da Silva", "44488899900", "rogerio@gmail.com");
		System.out.println(cli1);
		cli1.addConta(1000, 4004, 500, "corrente");
		
		ContaCorrente conta_cli1 = (ContaCorrente) cli1.getConta("corrente");
		
		conta_cli1.sacar(100d);
		conta_cli1.transferir(250d, new ContaSalario(303, 7007, 200d) ); //Sem creditos
		conta_cli1.extrato().forEach(System.out::println);
		
		System.out.println("\n-> Criando outro cliente");
		Cliente cli2 = new Cliente("Larissa Pereira", "00011122233", "larissa@gmail.com");
		System.out.println(cli2);
		cli2.addConta(777, 8500, 200, "corrente", 1.1f, 400d); //Creditos com uma taxa de juro de 10%
		
		ContaCorrente conta_cli2 = (ContaCorrente) cli2.getConta("corrente");
		
		conta_cli2.sacar(560d);
		conta_cli2.extrato().forEach(System.out::println);
		
	}
	
	public static void main(String args[]) {
	
		new Application();
		
	}
	
}
