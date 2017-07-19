
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer{
	
	private LinkedList<ChatClient> myclients;
	LinkedList<String> nomConnectes;
	
	public ChatServerImpl() throws RemoteException{
		myclients = new LinkedList<ChatClient>();
		nomConnectes = new LinkedList<String>();
	}
	
	public synchronized void register (ChatClient c, String identifiant) throws RemoteException	{
		myclients.add(c);
	}

	public synchronized void logout (ChatClient c) throws RemoteException	{
		for(int i=0; i<myclients.size(); i++){
		    if(c.equals(myclients.get(i))){
			myclients.remove(c);
			nomConnectes.remove(i);
			for(int j=0;j< myclients.size();j++){
			myclients.get(j).receiveConnectes(nomConnectes);
			}
			i = myclients.size();
		    }
		}
	}
	
	public synchronized void broadcast (String identifiant, String msg) throws RemoteException{
		for(int i=0;i< myclients.size();i++){
			myclients.get(i).receive("["+identifiant+"]: "+msg);
		}
	}
	
	public synchronized void broadcastConnectes (String identifiant) throws RemoteException{
		nomConnectes.add(identifiant);
		for(int i=0;i< myclients.size();i++){
			myclients.get(i).receiveConnectes(nomConnectes);
		}
	}
	
	public static void main (String[] args)	{
		try	{
			Naming.rebind("ChatServer", new ChatServerImpl());
		}
		catch(Exception e)		{
			System.err.println("Probleme") ;
		}
	}
}
