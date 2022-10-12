import java.util.*;
import java.security.*;
import java.io.*;

public class Assignment
{
    
    public static void main (String[] args)
    {
        Dictionary myDictionary = new Dictionary();
        
        int word1Min = 0;       //First word ranges from array index 0 - 1919
        int word1Max = 1919;
        int word2Min = 1920;    //Second word ranges from array index 1920 - 1929
        int word2Max = 1929;
        int word3Min = 1930;    //Third word ranges from array index 1930 - 1934
        int word3Max = 1934;
        int word4Min = 1935;    //Fourth word ranges from array index 1935 - 1938
        int word4Max = 1938;
        int word5Min = 1939;    //Fifth word ranges from array index 1939 - 1940
        int word5Max = 1940;
        int count = 0;          //Amount of strings created
        String sentence[] = new String[768000];     //The array the created strings to go into
        String shaSentence[] = new String[768000];  //The array the transformed sha256 strings to go into
        
        
        for(int i = word1Min; i <= word1Max; i++) //Selects First Word
        {
            String w1 = (myDictionary.getWord(i));
            
            for(int u = word2Min; u <= word2Max; u++) //Selects Second Word
            {
                String w2 = (myDictionary.getWord(u));
                
                for(int y = word3Min; y <= word3Max; y++)//Selects Third Word
                {
                    String w3 = (myDictionary.getWord(y));
                    
                    for(int t = word4Min; t <= word4Max; t++)//Selects Fourth Word
                    {
                        String w4 = (myDictionary.getWord(t));
                        
                        for(int e = word5Min; e <= word5Max; e++)//Selects Fifth Word
                        {
                            String w5 = (myDictionary.getWord(e));
                            String complete = (w1 + (" ") + w2 + (" ") + w3 + (" ") + w4 + w5); //Puts strings into a sentence
                            sentence[count] = complete;                                         //Puts string into array
                            shaSentence[count] = (sha256(sentence[count]));                     //Transforms string into sha256
                            count++;                                                            //Incriments count by 1
                        }
                    }
                }
            }
        }
        System.out.println("The Sentence Maker Array is Complete");
        System.out.println(count); // Prints out amount of sentences made
        
        
        int highestValue = 0;
        String s1 = new String ("");
        String s2 = new String ("");
        String s1E = new String ("");
        String s2E = new String ("");
  
        for(int i = 0; i < count; i++) //Selects a sentence
        {
            String selectedEnglish =  new String (sentence[i]);
            String selectedSha =  new String (shaSentence[i]);
            
            for(int u = i+1; u < count; u++) //Select a sentence to compare
            {
                String comparedEnglish =  new String (sentence[u]);
                String compared =  new String (shaSentence[u]);
                int temp = 0;
                
                for(int y = 0; y < selectedSha.length(); y++)
                {
                    if(selectedSha.charAt(y) == compared.charAt(y))
                    {
                        temp++;
                        // Adds a score if char matches
                    }
                    if ((y == 32) && (temp < 5))
                        {
                            break;
                        }
                }
                if(temp >= highestValue) // If Score is Bigger than the Current Score
                {
                    s1 = selectedSha;
                    s1E =selectedEnglish;
                    s2 = compared;
                    s2E = comparedEnglish;
                    highestValue = temp;
                    System.out.println("");
                    System.out.println("The Selected Sentence Index = " + i);
                    System.out.println("The Compared Sentence Index = " + u);
                    System.out.println("");
                    System.out.println(s1);
                    System.out.println(s1E);
                    System.out.println(s2);
                    System.out.println(s2E);
                    System.out.println("");
                    System.out.println("The Highest Score is " + highestValue);
                    System.out.println("");
                    System.out.println("");
                }
            }
        }
        System.out.println("Comparisons are complete.");
        System.out.println(s1); //Prints out sha256 of First Sentence
        System.out.println(s1E);//Prints out English of First Sentence
        System.out.println(s2);//Prints out sha256 of Second Sentence
        System.out.println(s2E);//Prints out English of Second Sentence
        System.out.println("");
        System.out.println("The Highest Score is " + highestValue); //Highest Score
        System.out.println("Amount in the sentence Array = " + count); //Amount of Sentences in Array
        System.out.println("");
    }

    public static String sha256(String input)
    {
        try
        {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] salt = "CS210+".getBytes("UTF-8");
            mDigest.update(salt);
            byte[] data = mDigest.digest(input.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<data.length;i++)
            {
                sb.append(Integer.toString((data[i]&0xff)+0x100,16).substring(1));
            }
            return sb.toString();
        }
        catch(Exception e)
        {
            return(e.toString());
        }
    }

    public static class Dictionary
    {
     
        private String input[]; 

        public Dictionary()
        {
            input = load("/Users/gerardmci01/Desktop/Year 2 Semester 1/CS210/Final Assignment/Words.txt");  
        }
    
        public int getSize()
        {
            return input.length;
        }
        
        public String getWord(int n)
        {
            return input[n];
        }
        
        private String[] load(String file) 
        {
            File aFile = new File(file);     
            StringBuffer contents = new StringBuffer();
            BufferedReader input = null;
            try 
            {
                input = new BufferedReader( new FileReader(aFile) );
                String line = null; 
                int i = 0;
                while (( line = input.readLine()) != null)
                {
                    contents.append(line);
                    i++;
                    contents.append(System.getProperty("line.separator"));
                }
            }
            catch (FileNotFoundException ex)
            {
                System.out.println("Can't find the file - are you sure the file is in this location: "+file);
                ex.printStackTrace();
            }
            catch (IOException ex)
            {
                System.out.println("Input output exception while processing file");
                ex.printStackTrace();
            }
            finally
            {
                try 
                {
                    if (input!= null) 
                    {
                        input.close();
                    }
                }
                catch (IOException ex)
                {
                    System.out.println("Input output exception while processing file");
                    ex.printStackTrace();
                }
            }
            String[] array = contents.toString().split("\n");
            for(String s: array)
            {
                s.trim();
            }
            return array;
        }
    }
}
    
