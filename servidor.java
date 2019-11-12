import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class servidor {
   private ServerSocket serverSocket;
   
   public servidor(int puerto, int tamanoCola) throws IOException {
      serverSocket = new ServerSocket(puerto, tamanoCola);
   }

   public void run() {
      Socket socket; 
      while(true) {
         try {
            System.out.println("Esperando cliente en puerto: " + serverSocket.getLocalPort() + "...");
            // Esperar conexiones
            socket = serverSocket.accept();
            boolean comunications = true;
            
            System.out.println("Se acaba de conectar: " + socket.getRemoteSocketAddress());
            
            while(comunications) {
            	int operation;
            	DataInputStream in = new DataInputStream(socket.getInputStream());
            	String message = in.readUTF();
            	operation = analyzeString(socket ,message);
            	comunications = makeResponse(socket, operation, message);
            }
           
            socket.close(); 
         } 
         
         catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } 
         
         catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public int analyzeString(Socket socket, String menssage) {
	   
	   String[] parts = menssage.split(";");
	   String operation = parts[0];
	   
	   if (operation.equals("initGame")) {
		   return 1;
	   }
	   if (operation.equals("stopGame")) {
		   return 2;
	   }
	   if (operation.equals("checkLetter")) {
		   return 3;
	   }
	   if(!operation.equals("initGame") & !operation.equals("stopGame") & !operation.equals("checkLetter")) {
		   return -1;
	   }
	   return 0;
   }
   
   public boolean makeResponse(Socket socket, int operation, String message) throws IOException {
	   DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	   out.writeUTF("Thank you for connecting to " + socket.getLocalSocketAddress()
       + "\nGoodbye!");
       
       return false;
   }
   
   public static void main(String[] args) {
      int puerto = 8067;
      int cola = 5;
      
      try {
         servidor s = new servidor(puerto, cola);
         s.run();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}