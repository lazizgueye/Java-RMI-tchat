
import java.util.LinkedList;
import java.rmi.*;

public interface ChatClient extends Remote{
  public String receive (String s) throws RemoteException;
  public String receiveConnectes (LinkedList<String> s) throws RemoteException;
public void setIdenfiant(String id) throws RemoteException;
  public void deconnexion() throws RemoteException;
}
