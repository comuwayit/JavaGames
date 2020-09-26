
/**
 * Evil Hangman runs a version of the game hangman where the computer
 * will not choose a word until there is only one word left avaliable.
 *
 * Lucas Kotowski
 * Version whatever the fuck -- fuck off
 * goddamnit please just work
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;

public class EvilHangman
{
    private int maxLength;
    private int numGuess;
    private ArrayList<String> wordList;
    String answer;
    String finalJeapordy;
    private ArrayList<Character> guessed = new ArrayList<>();
    
    
    public EvilHangman(int maxLength, int numGuess, ArrayList<String> dictionary)
    {
        this.maxLength = maxLength;
        this.numGuess = numGuess;
        wordList = new ArrayList<>();
        
        char[] initialPos = new char[maxLength];
        for(int i = 0; i < maxLength; i++)
        {
            initialPos[i] = '_';
        }
        answer = new String(initialPos);
        
        wordList.addAll(dictionary.stream().filter(word -> word.length() == maxLength).collect(Collectors.toList()));
    }
    
    public boolean play(char guess) {
        String temp = answer;
        boolean snailedIt = false;
        HashMap<String, ArrayList<String>> wordChoices = new HashMap<>();

        if (gameOver())
        {
            System.out.println("Game Over");
            return playerWon();
        }

        
        for (String word : wordList) 
        {
            char[] key = new char[maxLength];

            for (int index = 0; index < maxLength; index++) 
            {
                if (word.charAt(index) == guess) 
                {
                    key[index] = guess;
                } 
                else 
                {
                    key[index] = answer.charAt(index);
                }
            }

            String keyString = new String(key);

            addWordChoice(keyString, word, wordChoices);
        }
        
        if (numGuess == 1) 
        {
            if (wordChoices.keySet().contains(answer)) 
            {
                wordList = new ArrayList<>(wordChoices.get(answer));
                numGuess--;
                
                finalJeapordy = wordChoices.get(answer).get(0);
                guessed.add(guess);
                
                return false;
            }
        }

        for (String keyString : wordChoices.keySet()) 
        {
            if (!wordChoices.keySet().contains(temp))
            {
               temp = keyString;
            }

            
            if (wordChoices.get(keyString).size() > wordChoices.get(temp).size())
               temp = keyString;
            
        }

        if (wordChoices.keySet().contains(temp))
        {
            wordList = new ArrayList<>(wordChoices.get(temp));
            snailedIt = !temp.equals(answer);

            if (!snailedIt)
                numGuess--;

            answer = temp;
            finalJeapordy = wordChoices.get(answer).get(0);
            guessed.add(guess);
            return snailedIt;
        }
        else 
        {

            
            wordList = new ArrayList<>();
            guessed.add(guess);
            return false;
        }

    }
    
    public String showBoard()
    {
        char[] output = answer.toCharArray();
        return Arrays.toString(output);
    }
   
    public void addWordChoice(String key, String word, HashMap<String,ArrayList<String>> wordChoiceMap)
    {
        if(wordChoiceMap.get(key) == null)
            wordChoiceMap.put(key, new ArrayList<>());
        wordChoiceMap.get(key).add(word);
    }
    
    public int wordListSize()
    {
        return wordList.size();
    }
    
    public boolean playerWon()
    {
        if(!gameOver())
        {
            System.out.println("You're not done yet");
            return false;
        }
        else
            return !answer.contains("_");
        
    }
    
    public String printGuess()
    {
        return guessed.toString();
    }
    
    public String getAnswer()
    {
        if(gameOver())
            return finalJeapordy;
        else
            return null;
            
    }
    
    public boolean gameOver()
    {
        return numGuess == 0 || !answer.contains("_") || wordList.isEmpty();
    }
}

