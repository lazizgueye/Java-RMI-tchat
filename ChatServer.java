

import java.rmi.*;

public interface ChatServer extends Remote
{
public void register(ChatClient c, String identifiant) throws RemoteException;
public void broadcast(String identifiant, String msg) throws RemoteException;
public void broadcastConnectes(String identifiant) throws RemoteException;

public void logout(ChatClient c) throws RemoteException;
}
