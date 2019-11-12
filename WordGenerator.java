import java.util.ArrayList;
import java.util.Random;

public class WordGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
        System.out.println(words.get(rand_int1));
        
	}

}
