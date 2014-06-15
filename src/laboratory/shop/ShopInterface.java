package laboratory.shop;

import java.rmi.Remote;
import java.rmi.RemoteException;

import laboratory.shop.items.ItemInterface;

public interface ShopInterface extends Remote {

	public boolean buyItem(ItemInterface item) throws RemoteException;
	public boolean addItem(ItemInterface item) throws RemoteException;
}
