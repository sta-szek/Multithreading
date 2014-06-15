/**
 * @author Piotr Jo�ski
 * @version 1.0
 *
 * Zadanie polega�o na utworzeniu dw�ch w�tk�w wstawiaj�cych dane do tablicy co 10 sekund oraz
 * jednego w�tku pobieraj�cych te dane co 3 sekundy.
 * 
 *
 */
package laboratory;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @author Piotr Jo�ski
 * @version 1.0
 * 
 */
class Queue {

	/**
	 * Tablica (kolekcja) przechowywuj�ca dane wyprodukowane przez w�tki
	 */
	static ArrayBlockingQueue<Integer> array = new ArrayBlockingQueue<Integer>(
			100);

	/**
	 * @param value
	 *            warto�� kt�ra ma zostwa� wstawiona do tablicy (kolekcji)
	 */
	static void setInteger(int value) {
		array.add(value);
		System.out.println("Added: " + value);
	}

	/**
	 * @return Zwraca warto�� z pocz�tku tablicy oraz j� usuwa
	 */
	static int getInteger() {
		int value = array.poll();
		System.out.println("Removed: " + value);
		return value;
	}
}

/**
 * @author Piotr Jo�ski
 * @version 1.0
 * 
 *          Klasa, kt�ra produkuje dane i wstawia je do tablicy co 10 sekund
 */
class Put implements Runnable {

	/**
	 * Zmienna do kt�rej przypisuje si� egzemplarz semafora synchronizuj�cego
	 */
	Semaphore semaphore;
	/**
	 * nazwa w�tku
	 */
	String name;

	/**
	 * @param semaphore
	 *            semafor synchronizuj�cy
	 * @param name
	 *            nazwa w�tku
	 */
	Put(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name = name;
		new Thread(this).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		int value;
		Random r = new Random();

		while (true) {
			value = r.nextInt(100);
			Queue.setInteger(value);
			semaphore.release();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.err.println("Thread " + name + " has been interrupted");
			}
		}

	}

}

/**
 * @author Piotr Jo�ski
 * @version 1.0
 * 
 *          Klasa kt�ra pobiera dane z tablicy co 3 sekundy
 */
class Get implements Runnable {
	/**
	 * zmienna synchronizuj�ca.
	 */
	Semaphore semaphore;
	/**
	 * nazwa w�tku
	 */
	String name;

	/**
	 * @param semaphore
	 *            semafor synchronizuj�cy w�tki
	 * @param name
	 *            nazwa w�tku
	 */
	Get(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name = name;
		new Thread(this).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {
			try {
				semaphore.acquire();
				Queue.getInteger();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.err.println("Thread " + name + " has been interrupted");
			}
		}

	}
}

/**
 * @author Piotr Jo�ski
 * 
 *         Klasa testowa
 */
public class SimpleQueue {

	public static void main(String args[]) throws InterruptedException {
		Semaphore semaphore = new Semaphore(0);
		new Put(semaphore, "Put 1");
		new Put(semaphore, "Put 2");
		new Get(semaphore, "Get 1");

	}
}