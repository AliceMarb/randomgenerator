
/**
 * TermMap saves the nonterminals as keys and production rules as values in a map, and gets random derivations from the map.
 *
 * @Alice and Greer
 * @5/14/2019
 */
import java.util.*;
public class TermMap
{
    public List<Terminal> array = new ArrayList<Terminal>();
    /** makes a new map (HashMap is just an implementation of Map), which has Nonterminals as keys 
    and an ArrayList containing Terms (nonterminals and terminals) as value.
     */
    public static Map<Nonterminal,ArrayList> map = new HashMap<>();
    
    /**
       deriveOneStep
       @param nonterminal input
       @return picks one of the term lists in the production rule list associated in the map with the nonterminal input
       */
    public static ArrayList<Term> deriveOneStep(Nonterminal input){
        ArrayList<ArrayList<Term>> prodRules = map.get(input); 
        ArrayList<Term> randomDeriv;

        while(true){
            double randomIndex = Math.random() * prodRules.size();
            // random Deriv should never go to a production Rule which is empty e.g. blank:
            randomDeriv = prodRules.get((int)randomIndex);
            
            // This deals with a nonterminal that goes to nothing. This needs to be avoided, otherwise we will not be able to ever fully derive the string.
            if (map.get(randomDeriv.get(0)) != null){
                if (!map.get(randomDeriv.get(0)).isEmpty()){
                    break;
                } else{
                    continue;
                }
            }
            break;
        }
        return randomDeriv;
    }

    /**
     * RANDOMDERIVE2
     * @param Takes the current derivation of the string (i.e. stage including nonterminals and terminals)
     * e.g. if S-> A|B and A -> a, if we go from S to A, then our current derivation would be the ArrayList containing the Nonterminal term A.
     * @return String
     */
    public static String randomDerive2(ArrayList<Term> curDerivation) {
        String ret = "";
        for (Term t : curDerivation){
            if (t instanceof Nonterminal){
                // Derive the first nonterminal we see
                ArrayList<Term> nextDerivation = deriveOneStep((Nonterminal) t);
                // recursively calls so derivation is pending the value of the derivation of the nonterminal.
                ret += randomDerive2(nextDerivation);
            } else {
                // If a terminal, add to the return value
                ret = ret + t + " ";
            }
        }
        // This is using recursion. Below is the base case
        // Once everything is a terminal, (no more nonterminals), then we will return.
        return ret;
    }
}

