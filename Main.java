/**
 * Main is how the user interacts with the program.
 * The user will input a file name for a CFG, and main will parse it and output a random generation.
 * Hello I edited this.
 * I edited this too
 * @Alice and Greer
 * @5/14/2019
 */
import java.util.Scanner;
import java.io.FileReader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.regex.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static Nonterminal start;
    /** 
     * TextReader
     * Terminology:
     * This is how a file will look: 
     * { <start> 
     *   Cat <verb> CS240 Homework.;
     *  }
     *  { <verb>
     *    eats;
     *    kills;
     *    chomps;
     *  }
     * Nonterminal/production rule set: Enclosed by braces e.g. { <start> Cat <verb> CS240 Homework.;}
     * Nonterminal: Enclosed by < > e.g. <start>
     * Term List: An array list, with everything on the right hand side of ONE rule e.g. [Cat <verb> CS240 Homework.], or [eats]
     * Production Rules: An list of term lists, e.g. for <verb>, [[eats],[kills],[chomps]]
     * 
     * @param s, a string which is the input file as a string.
     * @return void, but parses the file and saves it in the map.
     */
    private static void textReader(String s) {

        // Splits file by nonterminal/production rule set
        String[] fileArray = s.split("\\A([^\\{]*)\\{|\\}([^\\{]*)\\{");
        /**
         * This regex is very long so here is an explanation:
         * \\{ is just { ([^\\{]*). [^\\{] means any char that's not {. (..*)
         * means can repeat many times.
         */

        // Checks the last nonterminal/production rule set and makes sure the last } is removed.
        fileArray[fileArray.length - 1] = fileArray[fileArray.length - 1].split("\\}([^.])*\\Z")[0];

        // Array[0] is always empty.
        // i represents each nonterminal/production rule set (delimited by brackets)
        for (int i = 1; i < (fileArray.length); i++) {
            // Have to consider the case where a single term list will go across multiple lines.

            // Split by newline. This splits the nonterminal, which is on the first line, from the production rules, which are on the subsequent lines.
            String[] nontermProdRulesSet = fileArray[i].split("\n",3);         
            // Save the first line as a Nonterminal. ([1] because [0] is empty)
            Nonterminal a = new Nonterminal(nontermProdRulesSet[1]);
            // Save the production rules in one separate string. This ensures that multiline termlists won't be split up incorrectly into different termlists.
            String prodRulesNotSplit = nontermProdRulesSet[2]; 
            // Splits by ; to get an array of term lists.
            String[] prodRules = prodRulesNotSplit.split("\\;\\s*\n");

            ArrayList<ArrayList<Term>> productionRuleList = new ArrayList<ArrayList<Term>>();
            ArrayList<Term> termList = new ArrayList<Term>();

            // Example: This year we saw an <superlative> <applicant-plus> pool of applicants. ;
            // matches more than 2 whitespace, anything before or after < and >, and anything after ; to the end.
            // problem - doesn't match whitespace between > and <. Solution: discard any in array that whitespace.

            // Goes through each term list in the production rule
            for (int j = 0; j < (prodRules.length); j++) {
                String[] lineArray = prodRules[j].split("\\s+");               
                // Goes through terms in each term list.
                for (int k = 0; k < (lineArray.length); k++) { 
                    if (lineArray[k].trim().length() > 0) {
                        // Not whitespace. 
                        if (lineArray[k].charAt(0) == '<') {
                            // If see <, save as a nonterminal
                            termList.add(new Nonterminal(lineArray[k]));
                        } else if (lineArray[k].charAt(0) == ' ' || lineArray[k].charAt(0) == '}') {
                            // do nothing.
                        } else {
                            // should be a Terminal in this case
                            termList.add(new Terminal(lineArray[k]));
                        }
                    }
                }                
                if (!termList.isEmpty()){
                    // Makes sure we don't add an empty term list.
                    productionRuleList.add(termList);
                }
                // Clears the termList variable for the next one.
                termList = new ArrayList<Term>();
            }
            // Puts the nonterminal as a key in the map, with value as its corresponding production ruel list.
            TermMap.map.put(a, productionRuleList);
        }

    }

    /** 
     * Run prints a random generation to the window.
     * @param none
     * @return none, but prints 
     */
    private static void run(){
        // Gets a random generation
        String randomGen = TermMap.randomDerive2(TermMap.deriveOneStep(start));
        // Splits it every 100 characters so it doesn't get too long in the window.
        String parsedString = randomGen.replaceAll("(.{100})", "$1\n");
        // prints it out.
        System.out.println(parsedString);
        System.out.println("\n Would you like to generate another? Type yes or quit. Or type in a new file.");
    }

    /**
     * Main interacts with users, allowing them to create a random generation from their input file.
     *
     * @param none
     * @return void
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Random Sentence Generator.\n Please type in your full file name e.g. /User/Desktop/file.txt or type quit to exit.");
        while(true){
            Scanner s = new Scanner(System.in);
            String str = s.next();
            if (str.equals("quit")){
                return;
            } else if (str.equals("yes")){
                run();
                continue;
            } else{
                String fpath = str;
                try {
                    // Saves the file as a string.
                    String fileAsString = new String(Files.readAllBytes(Paths.get(fpath)), StandardCharsets.UTF_8);
                    textReader(fileAsString); 
                    start = new Nonterminal("<start>");
                    run();
                    continue;
                } catch (IOException e) {
                    System.out.println("There has been an error reading input. Please try again.");
                    continue;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
        }
    }
}

