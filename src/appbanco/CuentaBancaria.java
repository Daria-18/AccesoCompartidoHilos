package appbanco;

import java.util.concurrent.Semaphore;

/**
 * <p>Clase que simula una cuenta bancaria con metodos para retirar y depositar por los hilos
 * de la clase {@link Cliente}.</p>
 * <p> Se hacen uso de Semáforos ({@link Semaphore}) para la gestión de los hilos.</p>
 */

public class CuentaBancaria {
    private final double SALDO_INICIAL = 10_000.00;
    private double saldo = SALDO_INICIAL;
    private final Semaphore semaforo = new Semaphore(1); //semáforo binario.

    /**
     * <p>Método que quita la cantidad especificada al salario actual.</p>
     * <p>Se hace uso del método acquire del semáforo ({@link Semaphore#acquire()}) para bloquear la actualización del salario.</p>
     * @param cantidad Cantidad a retirar.
     * @param nombreHilo Nombre del hilo como identificador que hace la llamada.
     */

    public void retirar(double cantidad, String nombreHilo){
        try {
            semaforo.acquire(); // tryAcquire no bloquea hasta que quede libre
            if (cantidad >= getSaldo()){
                /*
                System.out.println("Se intentó retirar una cantidad mayor al saldo actual: "
                + cantidad + " por el " + nombreHilo);
                 */
                System.out.printf("Se intentó retirar una cantidad mayor al saldo actual (Saldo actual: %.2f): %.2f. por el hilo [%s]\n"
                        ,getSaldo(),cantidad,nombreHilo);
            }else {
                saldo -= cantidad;
                /*
                System.out.println("Cantidad retirada: " + cantidad
                        + ". Saldo actual: " + getSaldo() + " - " + nombreHilo);
                 */
                System.out.printf("Cantidad retirada: %.2f. Saldo actual: %.2f. [%s]\n"
                        ,cantidad,getSaldo(),nombreHilo);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        semaforo.release();
    }

    /**
     * <p>Método que añade la cantidad especificada al salario actual.</p>
     * <p>Se hace uso del método acquire del semáforo ({@link Semaphore#acquire()}) para bloquear la actualización del salario.</p>
     * @param cantidad Cantidad a ingresar.
     * @param nombreHilo Nombre del hilo como identificador que hace la llamada.
     */

    public void depositar(double cantidad, String nombreHilo){
        try {
            semaforo.acquire();
            saldo += cantidad;
            /*
            System.out.println("Cantidad depositada: " + cantidad
                    + ". Saldo actual: " + getSaldo() + " - " + nombreHilo);
             */
            System.out.printf("Cantidad depositada: %.2f. Saldo actual: %.2f. [%s]\n"
                    ,cantidad,getSaldo(),nombreHilo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        semaforo.release();
    }

    /**
     * <p>Getter de saldo auto generado.</p>
     * @return Saldo como double.
     */
    public double getSaldo() {return saldo;}
}
