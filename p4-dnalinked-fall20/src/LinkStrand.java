import java.util.Iterator;

/*
-- Anna Rietbrock & Jake Rosen --

 */
public class LinkStrand implements IDnaStrand{

    private Node myFirst; //references the first node in a linked list of nodes
    private Node myLast; // references the last node in a linked list of nodes
    private long mySize; // represents the total number of characters stored in all nodes together.
    private int myAppends; // is the number of times that the append method has been called. It would be useful to think of this as one less than the number of nodes in the linked list.
    private Node myCurrent;
    private int myLocalIndex;
    private int myIndex;

    public LinkStrand(String s){
    initialize(s);

    }
    public LinkStrand(){

    this("");

    }

/**
     * Returns the number of elements/base-pairs/nucleotides in this strand.
     * @return the number of base-pairs in this strand
     */
    @Override
    public long size() {
        return mySize;
    }

    /**
     * Initialize by copying DNA data from the string into this strand,
     * replacing any data that was stored. The parameter should contain only
     * valid DNA characters, no error checking is done by the this method.
     *
     * @param source
     *            is the string used to initialize this strand
     */
    @Override
    public void initialize(String source) {

        Node n = new Node(source);
        mySize += source.length();
        myFirst = n;
        myLast = n;
        myAppends = 0;


        myCurrent = myFirst;
        myLocalIndex = 0;
        myIndex = 0;

    }
    /**
     * Return this object, useful to obtain
     * an object without knowing its type, e.g.,
     * calling dna.getInstance() returns an IDnaStrand
     * that will be the same concrete type as dna
     * @param source is data from which object constructed
     * @return an IDnaStrand whose .toString() method will be source
     */
    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);

    }

    /**
     * Append dna to the end of this strind.
     * @param dna
     *            is the string appended to this strand
     * @return this strand after the data has been added
     */
    @Override
    public IDnaStrand append(String dna) {

        Node n = new Node(dna);
        myLast.next = n;
        myLast = n;
        mySize += dna.length();
        myAppends ++;


        return this;
    }


    /**
     * Returns an IDnaStrand that is the reverse of this strand, e.g., for
     * "CGAT" returns "TAGC"
     *
     * @return reverse strand
     */
    @Override
    public IDnaStrand reverse() {
        if(myFirst == null) return null;

        Node copy = myFirst.next;

        if(copy   == null)
        {
            StringBuilder s = new StringBuilder(myFirst.info);
            return new LinkStrand(s.reverse().toString());
        }

        Node rev = new Node(myFirst.info);
        Node nextCop = new Node (myFirst.next.info);


        while(copy.next !=null) {


            nextCop.next = rev;
            rev = nextCop;


                copy = copy.next;
                nextCop = new Node(copy.info);

        }

        nextCop = new Node(copy.info);
        nextCop.next = rev;




        StringBuilder s = new StringBuilder(nextCop.info);

        LinkStrand ret = new LinkStrand(s.reverse().toString());

        while(nextCop.next !=null){
            s = new StringBuilder(nextCop.next.info);
            ret.append(s.reverse().toString());
            nextCop = nextCop.next;
        }


        return ret;
    }

    /**
     * Returns the number of times append has been called.
     *
     * @return
     */
    @Override
    public int getAppendCount() {
        return myAppends;
    }

    /**
     * Returns character at a specified index, where 0 <= index < size()
     * @param index specifies which character will be returned
     * @return the character at index
     * @throws IndexOutOfBoundsException if index < 0 or inde >= size()
     */
    @Override
    public char charAt(int index) {

        try {


            int count = 0;
            int dex = 0;
            Node list = myFirst;

            if (index > myIndex) {
                count = myIndex;
                dex = myLocalIndex;
                list = myCurrent;
            }

            while (count != index) {
                count++;
                dex++;
                if (dex >= list.info.length()) {
                    dex = 0;
                    list = list.next;
                }
            }
            if (list != null) {
                myLocalIndex = dex;
                myIndex = count;
                myCurrent = list;
            }
            return list.info.charAt(dex);
        }
        catch(Exception e){
            throw new IndexOutOfBoundsException();
        }

    }
    /**
     *
     * @return the String containing the info values from every node in the strand
     */
    public String toString()
    {
        Node n = myFirst;
        StringBuilder s = new StringBuilder();
        String ret = "";
        while(n != null){

           s.append(n.info);
           n = n.next;
        }
        mySize = s.toString().length();

        return s.toString();
    }


   private class Node {
    String info;
    Node next;

    public Node(String s){
        info = s;
        next = null;
    }



    }


}
class Tester
{
 public static void main(String[] args){

     LinkStrand s = new LinkStrand("aggtccg");
     System.out.println("Found: ");

     s.append("aaagggtttcccaaagggtttccc");
     LinkStrand q = (LinkStrand) s.reverse();

     StringBuilder str = new StringBuilder("aggtccgaaagggtttcccaaagggtttccc");

     System.out.println("Found: " + q.toString());
     System.out.println("Goalw: " + str.reverse().toString());

 }



}
