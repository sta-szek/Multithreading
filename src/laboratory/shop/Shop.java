package laboratory.shop;

import java.rmi.RemoteException;
import java.util.ArrayList;

import laboratory.shop.items.Category;
import laboratory.shop.items.Item;
import laboratory.shop.items.ItemInterface;

public class Shop implements ShopInterface {

	ArrayList<Item> items = new ArrayList<Item>();
	public synchronized boolean buyItem(ItemInterface item) throws RemoteException {
		
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized boolean addItem(ItemInterface item) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	

	
	public static void main (String args[]){
		ArrayList<Item> items = new ArrayList<Item>();
		Item car = new Item(Category.CAR, 15, "Car jakich ma³o", 10);
		Item car2 = new Item(Category.CAR, 10, "AAA", 12);
		items.add(car);
		items.add(car2);
		System.out.println(items);
		items.remove(car);
		System.out.println(items);
		
	
	}

}
