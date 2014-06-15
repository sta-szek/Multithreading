/**
 * 
 */
package laboratory;

/**
 * @author Piotr Jo�ski
 *
 * Zadanie polega�o na stworzeniu modelu muzeum z dwoma bramkami - wej�cie, wyj�cie.
 * Ka�dy zwiedzaj�cy ustawia si� w kolejce, czekaj�c na swoj� kolej.
 * Gdy zwiedzaj�cy opu�ci muzeum, zwalnia si� miejsce i nast�pna osoba mo�e wej��
 */

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Museum {

	public static void main(String[] args) {

		int num_of_visitors = 100;
		int num_of_visitors_per_time = 30;

		Semaphore freeSpace = new Semaphore(num_of_visitors_per_time);
		Entrance enterance = new Entrance(freeSpace, "Entrance");
		Exit exit = new Exit(freeSpace, "Exit");

		for (int i = 1; i <= num_of_visitors; i++) {
			new Visitor("Visitor number " + i, enterance, exit);
		}

	}

}

class Visitor implements Runnable {

	private String name;
	private Entrance entrance;
	private Exit exit;

	Visitor(String name, Entrance entrance, Exit exit) {
		this.name = name;
		this.entrance = entrance;
		this.exit = exit;
		new Thread(this).start();
	}

	public void run() {
		enter();
		Random r = new Random();
		try {
			Thread.sleep(2000 + r.nextInt(3000));
		} catch (InterruptedException e) {
			System.err.println("Visitor " + name + " has been killed.");
		}
		exit();

	}

	void enter() {
		entrance.enter();
		ConsoleSupport.write(name + " finnaly came in.");
	}

	void exit() {
		exit.exit();
		ConsoleSupport.write(name + " felt sick and left the Museum.");
	}
}

class Entrance {
	Semaphore semaphore;
	String name;

	Entrance(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name = name;
	}

	void enter() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			ConsoleSupport.write("We do not like this client.");
		}
	}
}

class Exit {
	Semaphore semaphore;
	String name;

	Exit(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name = name;
	}

	void exit() {
		semaphore.release();
		ConsoleSupport.writeError("There is one more place in the Museum.");
	}
}

class ConsoleSupport {
	public static synchronized void write(String message) {
		System.out.println(message);
		System.out.flush();
	}

	public static synchronized void writeError(String message) {
		System.err.println(message);
		System.out.flush();
	}
}
