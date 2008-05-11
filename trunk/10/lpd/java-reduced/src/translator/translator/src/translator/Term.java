package translator;

/**
 * Created by IntelliJ IDEA.
 * User: epa
 * Date: 11.05.2008
 * Time: 13:33:45
 * To change this template use File | Settings | File Templates.
 */
public class Term {


    /// <summary>
    /// имя символа
    /// </summary>
    private String termName;

    public static final Term EmptyString = new Term( "" ); //пустая строка. Эпсилон-символ
    public static final Term Z = new Term( "Z" ); //Символ конца стека автомата

    public Term()
    {
       termName = "";
    }

    public Term( String p_sName )
    {
       termName = p_sName;
    }


    public String getTermName() {
       return termName;
    }

    public void setTermName(String termName) {
       this.termName = termName;
    }


    /// <summary>
    /// Возвращает true, если текущий символ - эпсилон
    /// </summary>
    /// <returns></returns>
    public boolean isEps()
    {
       return EmptyString.equals( this );
    }


    public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;

       Term term = (Term) o;

       if (termName != null ? !termName.equals(term.termName) : term.termName != null) return false;

       return true;
    }

    public int hashCode() {
       int result;
       result = (termName != null ? termName.hashCode() : 0);
       return result;
    }

    public Term Clone()
    {
       return new Term( this.termName);
    }

    public String toString() {
       return this.termName;
}
}
