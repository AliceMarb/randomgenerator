
/**
 * Write a description of class Nonterminal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Nonterminal extends Term
{

    /**
     * Constructor for objects of class Nonterminal
     */
    public Nonterminal(String name)
    {
        // This just inherits the name from Term
        super(name);
    }

    /**
     * Overwrites the equals method so two Nonterminals are the same if there name variable is the same
     * @param Object o, a nonterminal
     * @return true if this.name and o.name name are the same.
     */
    public boolean equals(Object o){

        if(!(o instanceof Nonterminal))
            return false;
        else{
            Nonterminal on = (Nonterminal) o;
            if (this.name.equals(on.name)){
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Overwrites the hashCode method so two Nonterminals will ave the same hashCode in the HashMap if their names are the same
     * @return the hascode of the instance variable, name.
     */
    public int hashCode(){
        return name.hashCode();
    }
}
