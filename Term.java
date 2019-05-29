
/**
 * Write a description of class Term here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Term
{
    /** Name is, for Terminals, just the name of the Terminal, e.g. for <object>, name will be obj.
       For NonTerminals, it will be the contents of the nonterminal e.g. "cat" will equally just be "cat".
       */
    String name;

    /**
     * Constructor for objects of class Term
     */
    public Term(String name)
    {
        this.name = name;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String toString()
    {
        return name;
    }
}
