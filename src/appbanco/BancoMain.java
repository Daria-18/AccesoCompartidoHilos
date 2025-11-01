package appbanco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
  <p>Clase main que gestiona la creación de los clientes pidiendo al usuario el número de hilos.</p>
 */
public class BancoMain {
    public static void main(String[] args) {
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        Scanner inputUsuario = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        int clientes = 0;

        while (true){
            System.out.println("Introduce el número de clientes (hilos) a simular:");
            try {
                clientes = Integer.parseInt(inputUsuario.nextLine());
                if (clientes>=1) break; // Condición de salida.
                //System.out.println("Numero debe de ser mayor a 0");
            } catch (NumberFormatException e) {
                System.out.println("Error en la entrada; debe de ser un entero igual o mayor a 1.");
            }
        }
        inputUsuario.close(); //Close del scanner.
        System.out.println("Saldo inicial de la cuenta: " + cuentaBancaria.getSaldo());

        for (int i=1;i<=clientes;i++){
            Cliente cliente = new Cliente(cuentaBancaria,"Cliente-" + i);
            listaClientes.add(cliente);
            cliente.start();
        }
        //for (Cliente cliente:listaClientes) cliente.start();
        for (Cliente cliente : listaClientes){
            try {
                cliente.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.printf("Saldo final de la cuenta: %.2f",cuentaBancaria.getSaldo());
        //System.out.println("Saldo final de la cuenta: " + cuentaBancaria.getSaldo());
    }
}
