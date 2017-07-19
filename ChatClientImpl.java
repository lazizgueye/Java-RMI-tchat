
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
//import java.util.FileWrite;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient, Runnable, Serializable{
	
	ChatClient ch;
	private ChatServer mycs;
	private String identifiant;
	ArrayList<String> Messages = new ArrayList<String>();
	LinkedList<String> Connectes = new LinkedList<String>();
	LinkedList<String> Historique = new LinkedList<String>();

	public ChatClientImpl(ChatServer cs, String id) throws RemoteException{
		mycs=cs;
		identifiant = id;
		//mycs.register(this, identifiant);
		ch = this;		
	}
	
	public void setIdenfiant(String id) throws RemoteException{
		this.identifiant = id;
		mycs.register(ch, this.identifiant);
	}
	
	public void deconnexion() throws RemoteException{
		mycs.logout(ch);
	}	

	public synchronized String receive (String s) throws RemoteException{
		System.out.println(s);
		Messages.add(s);
		try{laDate();ecrireHistoriq(s);}catch(Exception ioe){}
		return s;
	}

	public synchronized String receiveConnectes (LinkedList<String> s) throws RemoteException{
		System.out.println(s);
		Connectes.clear();
		Connectes.addAll(s);
		return "0";
	}
	
	public void resetMessages(){
		Messages.clear();
	}

	public ChatServer getMycs(){
		return this.mycs;
	}
	
	public void run ()	{
	}
	
	public void chargerHistoriq() throws IOException{
		try{		
			String Fichier = "Historique/"+this.identifiant+".txt";
			Scanner scanner = new Scanner(new File(Fichier));
		
			while (scanner.hasNextLine()) {
				String Ligne = scanner.nextLine();
				//System.out.println(Ligne);
				Historique.add(Ligne);
			}
			scanner.close();
		}catch (IOException ioe){}
	}

	public void ecrireHistoriq(String ligne) throws IOException{
		try{		
			String Fichier = "Historique/"+this.identifiant+".txt";
			FileWriter ecrire = new FileWriter(Fichier, true);
			ecrire.write(ligne+"\n");
			ecrire.close();
		}catch (IOException ioe){}
	}

	public void laDate() throws IOException{
		try{		
			/*String format = "dd/MM/yy";
			java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
			java.util.Date date = new java.util.Date();
			String ladate = date+"";*/
			java.text.Format formatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
			java.util.Date date = new java.util.Date();
			String ladate = "########## "+formatter.format(date)+ " ########";
			chargerHistoriq();
			if(Historique.size() == 0){
					ecrireHistoriq(ladate);
				}
				else{
				for(int i=0; i<Historique.size(); i++){
				
					if(ladate.equals(Historique.get(i))){
						i=Historique.size();
					}
					else if((i==Historique.size()-1)&&(!ladate.equals(Historique.get(i)))){
						ecrireHistoriq(ladate);
					}
				}
			}
		}catch (Exception ioe){}
	}
	
		
}
