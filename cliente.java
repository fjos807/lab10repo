import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class cliente {
	static private Socket socket;
    String serverName;
    //= "127.0.0.1";
    int port;
    //= 8067;
    static boolean isConnectec=false;
    static String username;
    static String letter;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));	
 
    static String operation,value;
	public static void main(String[] args) throws UnknownHostException, IOException {
		cliente cliente=new cliente("192.168.43.232",8067);
		cliente.game();
	}
	public  void game() throws UnknownHostException, IOException {
		//Pide el username y lo almacena
		askUsername();
		//Conecta con el servidor
		connect();
		//Envia la petición para iniciar el juego
		sendOperation("initGame",username);
		round();
		
		//System.out.println(value);
		close();
		
	}
	   public cliente(String host, int port){
		   this.serverName=host;
		   this.port=port;
	   }
	   public void connect() throws UnknownHostException, IOException {
		   System.out.println("Connecting to " + serverName + " on port " + port);
		   socket=new Socket(serverName, port);
		   System.out.println("Just connected to " + socket.getRemoteSocketAddress());
	   }
	   public void close() throws IOException {
		   socket.close();
	   }
	   
	   public static void setUsername(String username) throws IOException {
		   cliente.username=username;
	   }
	public void askUsername() throws IOException {
		 System.out.println("Ingrese el nombre de usuario: ");
		 String username = reader.readLine();
		 setUsername(username);
		 System.out.println("Bienvenido "+ username);
		 
	}

	   
	   private void sendOperation(String operation, String value) throws UnknownHostException, IOException {
	       OutputStream outToServer = socket.getOutputStream();
	       DataOutputStream out = new DataOutputStream(outToServer);
	       
	       out.writeUTF(operation+","+value);	
		   //System.out.println("Enviado");
	       
	   }
	   
	   public void setInValues(String input) {
	       String[] listOfInput = input.split(",", 2);
	       operation=listOfInput[0];
	       value=listOfInput[1];
	   }
	   
	   public void getInput() throws IOException {
		   DataInputStream in = new DataInputStream(socket.getInputStream());
	       while(in.available() != 0) {
	    	   String input =in.readUTF();
	    	   setInValues(input);
	       }
		   
	       
	       
	   }
	   public static void askLetter() throws IOException {
			 System.out.println("Ingrese la letra a adivinar: ");
			 String letter = reader.readLine();
			 //System.out.println(letter.length());
			 while(letter=="" || letter.length()>1) {
				 askLetter();
			 }
			 cliente.letter=letter;
			 //System.out.println(cliente.letter);
			 
	   }
	   public boolean checkGameOver(String operation) {
		   if(operation!="gameOver" || operation=="winGame") {return true;};
		   return false;
	   }
	   public void printWord() {
		   int count=0;
		   int length=Integer.parseInt(value);
		   String word="";
		   //System.out.println("Value"+value);
		   while(count<length) {
			   word=word+" _ ";
			   count++;
		   }
		   System.out.println(word);
	   }
	   public void round() throws IOException {
		   //System.out.println("Empieza");
		   getInput();
		   while(checkGameOver(operation)) {
			   //printWord();
			   askLetter();
			   sendOperation("checkLetter",letter);
			   System.out.println(value);
			   round();
		}
		   if(operation=="winGame") {
			   System.out.println("Felicidades");
			   System.out.println(" --------------");
			   System.out.println(" |            |");
			   System.out.println(" |          ------");
			   System.out.println(" |         | X  X |");
			   System.out.println(" |         |   o  |");
			   System.out.println(" |         	------ ");
			   System.out.println(" |             |   ");
			   System.out.println(" |           / |'\' ");
			   System.out.println(" |             |   ");
			   System.out.println(" |            /'\'  ");
		   }else {
		   System.out.println("No tiene más intentos");
		   }
	   }
	   public void print(int fail) {
		   switch(fail) {
		   case 1:
			   System.out.println(" --------------");
			   System.out.println(" |            |");
			   System.out.println(" |          ------");
			   System.out.println(" |         | -  - |");
			   System.out.println(" |         |   o  |");
			   System.out.println(" |         	------");
			   break;
		   case 2:
			   System.out.println(" --------------");
			   System.out.println(" |            |");
			   System.out.println(" |          ------");
			   System.out.println(" |         | -  - |");
			   System.out.println(" |         |   o  |");
			   System.out.println(" |         	------");
			   System.out.println(" |             |   ");
			   System.out.println(" |             |   ");

			   break;
		   case 3:
			   System.out.println(" --------------");
			   System.out.println(" |            |");
			   System.out.println(" |          ------");
			   System.out.println(" |         | -  - |");
			   System.out.println(" |         |   o  |");
			   System.out.println(" |         	------");
			   System.out.println(" |             |   ");
			   System.out.println(" |           / |'\' ");
			   System.out.println(" |             |   ");
			   break;
		   case 4:
			   System.out.println(" --------------");
			   System.out.println(" |            |");
			   System.out.println(" |          ------");
			   System.out.println(" |         | -  - |");
			   System.out.println(" |         |   o  |");
			   System.out.println(" |         	------ ");
			   System.out.println(" |             |   ");
			   System.out.println(" |           / |'\' ");
			   System.out.println(" |             |   ");
			   System.out.println(" |            /'\'  ");

			   break;
		   case 5:
			   System.out.println(" --------------");
			   System.out.println(" |            |");
			   System.out.println(" |          ------");
			   System.out.println(" |         | X  X |");
			   System.out.println(" |         |   o  |");
			   System.out.println(" |         	------ ");
			   System.out.println(" |             |   ");
			   System.out.println(" |           / |'\' ");
			   System.out.println(" |             |   ");
			   System.out.println(" |            /'\'  ");
			   break;
		   }
	   }
}

/*
System.out.println(" --------------");
System.out.println(" |            |");
System.out.println(" |          ------");
System.out.println(" |         | -  - |");
System.out.println(" |         |   o  |");
System.out.println(" |         	------");



System.out.println(" --------------");
System.out.println(" |            |");
System.out.println(" |          ------");
System.out.println(" |         | -  - |");
System.out.println(" |         |   o  |");
System.out.println(" |         	------");
System.out.println(" |             |   ");
System.out.println(" |             |   ");


System.out.println(" --------------");
System.out.println(" |            |");
System.out.println(" |          ------");
System.out.println(" |         | -  - |");
System.out.println(" |         |   o  |");
System.out.println(" |         	------");
System.out.println(" |             |   ");
System.out.println(" |           / | \ ");
System.out.println(" |             |   ");



System.out.println(" --------------");
System.out.println(" |            |");
System.out.println(" |          ------");
System.out.println(" |         | -  - |");
System.out.println(" |         |   o  |");
System.out.println(" |         	------ ");
System.out.println(" |             |   ");
System.out.println(" |           / | \ ");
System.out.println(" |             |   ");
System.out.println(" |            / \  ");

System.out.println(" --------------");
System.out.println(" |            |");
System.out.println(" |          ------");
System.out.println(" |         | X  X |");
System.out.println(" |         |   o  |");
System.out.println(" |         	------ ");
System.out.println(" |             |   ");
System.out.println(" |           / | \ ");
System.out.println(" |             |   ");
System.out.println(" |            / \  ");
*/