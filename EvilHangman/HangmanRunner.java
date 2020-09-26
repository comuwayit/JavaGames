
/**
 * Runs the hangman game and handles all of the player imputs and 
 * hangman outputs. man but kinda not tho.
 *
 * Lucas Kotowski
 * version I wanna die
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HangmanRunner
{
    public static void main(String[] args)
    {
        Scanner type = new Scanner(System.in);
        int lengthWord = 0;
        int maxGuesses = 0;
        int currentTot = 0;
        char guess;
        
        ArrayList<String> dictionary = new ArrayList<String>();
        
        //so this think kinda threw me through a loop. i don't get iut but it works? bleh
        try(Scanner fileReader = new Scanner(new File("scrabbleWords.txt")))
        {
            while(fileReader.hasNextLine())
                dictionary.add(fileReader.nextLine());
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        System.out.println("Ya wanna play the fucking game go shove it");
        System.out.println("please enter an interger between 1 and 20.");
        lengthWord = type.nextInt();
        
        System.out.println("Guesses");
        maxGuesses = type.nextInt();
        
        EvilHangman game = new EvilHangman(lengthWord, maxGuesses, dictionary);
        
         System.out.println(game.showBoard());
        
        while(!game.gameOver())
        {
            System.out.println("enter a letter: ");
            guess = type.next().charAt(0);
            if(game.play(guess) == true)
                System.out.println("your success is fleeting");
            else
                System.out.println("What a surprise. failure.");
                
            System.out.println(game.showBoard());
        }
        
        if(game.playerWon() == true)
            System.out.println("good.");
        else
            System.out.println("die.");
            
        System.out.println(game.getAnswer());  
    }
}
