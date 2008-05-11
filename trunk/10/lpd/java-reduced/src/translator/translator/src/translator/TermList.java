package translator;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: epa
 * Date: 11.05.2008
 * Time: 13:34:25
 * To change this template use File | Settings | File Templates.
 */
public class TermList{
    private ArrayList<Term> mylist;


    public TermList() {
        this.mylist = new ArrayList<Term>();
    }

    public TermList(ArrayList<Term> mylist) {
        this.mylist = mylist;
    }

    public TermList(TermList l) {
        this.mylist = l.mylist;
    }

//        protected Object clone() throws CloneNotSupportedException {
//            return super.clone();    //To change body of overridden methods use File | Settings | File Templates.
//        }

    public void add(Term t){
        this.mylist.add(t);
    }

    public int count(){
        return this.mylist.size();
    }


    public ArrayList<Term> getList() {
        return mylist;
    }

    public boolean contains(Term t){
        return this.mylist.contains(t);
    }


    public Term get(int i){
        return this.mylist.get(i);
    }

    public boolean isEmpty(){
        return this.mylist.isEmpty();
    }


    public String getString(){
        StringBuffer res = new StringBuffer();
        int count = this.mylist.size();
        for ( int i = 0; i < count; ++i )
        {
            res.append( this.mylist.get(i).getTermName() );
            if ( i != count - 1 )
            {
                res .append( " " );
            }
        }
        return res.toString();
    }

    /// <summary>
    /// добавить символы в список используя строку
    /// </summary>
    /// <param name="p_sTerms">строка состоит из имен символов, разделенных пробелами</param>
    public void AddTermsByString( String p_sTerms )
    {
        if ( p_sTerms.equals( " " ) )
        {
            this.mylist.add(new Term(""));
        }
        else
        {
            if ( p_sTerms.endsWith( " " ) )
            {
                p_sTerms = p_sTerms.substring( 0, p_sTerms.length() - 1 );   //todo
            }
            if ( p_sTerms.startsWith( " " ) )
            {
                p_sTerms = p_sTerms.substring( 1, p_sTerms.length() - 1 ); //todo
            }
            String [] terms = p_sTerms.split( " " );
            for ( String  s : terms )
            {
                Term oTerm = new Term( s );
                this.mylist.add(oTerm);
            }
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TermList termList = (TermList) o;

        if (mylist != null ? !mylist.equals(termList.mylist) : termList.mylist != null) return false;

        return true;
    }

    public int hashCode() {
        return (mylist != null ? mylist.hashCode() : 0);
    }
}
