import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class servidor {
   private ServerSocket serverSocket;
   private String word;
   int wordSize;
   int tries = 5;
   int rightLetters = 0;
   String goods;
   
   public servidor(int puerto, int tamanoCola) throws IOException {
      serverSocket = new ServerSocket(puerto, tamanoCola);
   }

   public void run() throws InterruptedException {
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
            	if(in.available() != 0) {
            		System.out.println(in.available());
                	String message = in.readUTF();
                	operation = analyzeString(socket ,message);
                	comunications = makeResponse(socket, operation, message);
            	} else {
            		System.out.println("**************A dormir**************");
            		Thread.sleep(3000);
            	}
            	
            	
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
	   
	   String[] parts = menssage.split(",");
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
	   if(!operation.equals("initGame") && !operation.equals("stopGame") && !operation.equals("checkLetter")) {
		   return -1;
	   }
	   return 0;
   }
   
   public boolean makeResponse(Socket socket, int operation, String message) throws IOException {
	   DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	   String output;
	   
	   switch(operation) {
	   		case 1:
	   			System.out.println("InitGame");
	   			word = generateRandomWord();
	     	   out.writeUTF("checkLetter," + word.length());
	     	   System.out.println("checkLetter," + word.length());
	     	   wordSize = word.length();
	     	   for(int i =0; i <= wordSize-1; i++) {
	     		   if(goods == null) {
	     			   goods = "*";
	     		   } else {
	     			  goods += "*";
	     		   }

	     	   }
	     	   return true;
	   
	   		case 2:
	   			System.out.println("EndGame");
	   			return false;
	   		
	   		case 3:
	   			System.out.println("Checkletter");
	   			System.out.println(word);
	   			
	    		   boolean state = false;
	    		   String[] parts = message.split(",");
	    		   String charWord = parts[1];
	    		   System.out.println("Letra: " + charWord);
	    		   for (int i = 0; i < word.length(); i++) {
	    			   String letter = String.valueOf(word.charAt(i));
	    			   if(rightLetters == wordSize) {
	    				   output = "winGame,Felicidades";
	    				   break;
	    			   }
	    			   if (letter.equals(charWord)) {
	    				   char[] myNameChars = goods.toCharArray();
	    				   myNameChars[i] = charWord.charAt(0);
	    				   goods = String.valueOf(myNameChars);
	    				   //output += charWord;
	    				   state = true;
	    				   rightLetters++;
	    			   } else {
	    				  // output += "*";
	    			   }
	    		   } 
	    		   
	    		   if (state == false){
	    			   if (tries == 0) {
	    				   output = "gameOver,Se acabaron los intentos";
	    				   
	    				   return false;
	    			   } else {
	    				   tries --;
	    				   System.out.println("Intentos: " + String.valueOf(tries));
	    				   return true;
	    			   }
	    			   
	    		   }
	    		   if(rightLetters == wordSize) {
    				   output = "winGame,Felicidades";
    				   out.writeUTF(output);
    			   } else {
    				   output = "checkLetter,Word: " + goods;
    	    		   System.out.println("Termina revisar = " + output);
    	    		   out.writeUTF(output);
    			   }
	    		   
	    		   return true;
	    		   
	    	default:
	    		System.out.println("NoOp");
	    		return true;
	   }
       
   }
   
   public String generateRandomWord() {
	   ArrayList<String> words = new ArrayList<String>();
       words.add("color");
       words.add("chicas");
       words.add("tararear");
       words.add("reyes");
       words.add("vegetal");
       
       int sizeWords = words.size();
       
       Random rand = new Random(); 
       
       // Generate random integers in range 0 to 999 
       int rand_int1 = rand.nextInt(sizeWords);
       return words.get(rand_int1);
   }
   
   public static void main(String[] args) throws InterruptedException {
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