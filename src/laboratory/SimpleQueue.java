/**
 * @author Piotr Joñski
 * @version 1.0
 *
 * Zadanie polega³o na utworzeniu dwóch w¹tków wstawiaj¹cych dane do tablicy co 10 sekund oraz
 * jednego w¹tku pobieraj¹cych te dane co 3 sekundy.
 * 
 *
 */
package laboratory;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @author Piotr Joñski
 * @version 1.0
 * 
 */
class Queue {

	/**
	 * Tablica (kolekcja) przechowywuj¹ca dane wyprodukowane przez w¹tki
	 */
	static ArrayBlockingQueue<Integer> array = new ArrayBlockingQueue<Integer>(
			100);

	/**
	 * @param value
	 *            wartoœæ która ma zostwaæ wstawiona do tablicy (kolekcji)
	 */
	static void setInteger(int value) {
		array.add(value);
		System.out.println("Added: " + value);
	}

	/**
	 * @return Zwraca wartoœæ z pocz¹tku tablicy oraz j¹ usuwa
	 */
	static int getInteger() {
		int value = array.poll();
		System.out.println("Removed: " + value);
		return value;
	}
}

/**
 * @author Piotr Joñski
 * @version 1.0
 * 
 *          Klasa, która produkuje dane i wstawia je do tablicy co 10 sekund
 */
class Put implements Runnable {

	/**
	 * Zmienna do której przypisuje siê egzemplarz semafora synchronizuj¹cego
	 */
	Semaphore semaphore;
	/**
	 * nazwa w¹tku
	 */
	String name;

	/**
	 * @param semaphore
	 *            semafor synchronizuj¹cy
	 * @param name
	 *            nazwa w¹tku
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
 * @author Piotr Joñski
 * @version 1.0
 * 
 *          Klasa która pobiera dane z tablicy co 3 sekundy
 */
class Get implements Runnable {
	/**
	 * zmienna synchronizuj¹ca.
	 */
	Semaphore semaphore;
	/**
	 * nazwa w¹tku
	 */
	String name;

	/**
	 * @param semaphore
	 *            semafor synchronizuj¹cy w¹tki
	 * @param name
	 *            nazwa w¹tku
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
 * @author Piotr Joñski
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