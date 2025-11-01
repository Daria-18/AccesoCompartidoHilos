package appbanco;

import java.util.Random;

/**
 * <p>Clase que simula un cliente que opera sobre la clase {@link CuentaBancaria}. </p>
 * <p>Cada cliente es un hilo que realiza operaciones aleatorias (de depósito y retirada) sobre una única clase de Cuenta Bancaria.</p>
 */

public class Cliente extends Thread{
    private final Random random = new Random(); // rng
    private final CuentaBancaria cuentaBancaria;

    // Constantes requeridas
    private final double MAX_CANTIDAD = 10_000.00; // Cantidad máxima a retirar o depositar.
    private final int MAX_OPERACIONES = 50; // Cantidad máxima de operaciones por hilo.
    private final int MAX_PAUSA_MILIS = 200; // Tiempo en milisegundos que se pausara el hilo tras una operación.

    public Cliente(CuentaBancaria cuentaBancaria, String nombreProceso) {
        super(nombreProceso); // constructor Thread con nombre del Proceso
        this.cuentaBancaria = cuentaBancaria;
    }

    @Override
    public void run(){
        boolean isDepositar; // Solo dos posibles acciones (True -> Depositar, False -> Retirar).
        int operacionesRng = random.nextInt(MAX_OPERACIONES)+1; // Mínimo una operación.

        for (int i=0;i<operacionesRng;i++){
            isDepositar = random.nextBoolean();
            if(isDepositar){
                depositar();
            }else retirar();
            try {
                Thread.sleep(random.nextInt(MAX_PAUSA_MILIS));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("El " + this.getName() + " finalizó todas las transacciones");
    }

    /**
     * <p>Llamada al metodo retirar [{@link CuentaBancaria#retirar(double, String)}] del proceso padre pasando el nombre del hilo actual como identificador.</p>
     */
    private void retirar() {
        cuentaBancaria.retirar(random.nextDouble(MAX_CANTIDAD),
                this.getName());
    }

    /**
     * <p> Llamada al metodo depositar [{@link CuentaBancaria#depositar(double, String)}] del proceso padre pasando el nombre hilo actual como identificador.</p>
     */
    private void depositar() {
        cuentaBancaria.depositar(random.nextDouble(MAX_CANTIDAD),
                this.getName());
    }
}
