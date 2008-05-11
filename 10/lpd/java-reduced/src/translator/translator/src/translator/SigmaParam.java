package translator;

/**
 * Created by IntelliJ IDEA.
 * User: epa
 * Date: 11.05.2008
 * Time: 13:35:35
 * To change this template use File | Settings | File Templates.
 */
 public class SigmaParam
{
    public Term Condition; //текущее состояние
    public Term InChain; //символ в цепочке
    public Term InStack; //символ в стеке



    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SigmaParam that = (SigmaParam) o;

        if (Condition != null ? !Condition.equals(that.Condition) : that.Condition != null) return false;
        if (InChain != null ? !InChain.equals(that.InChain) : that.InChain != null) return false;
        if (InStack != null ? !InStack.equals(that.InStack) : that.InStack != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (Condition != null ? Condition.hashCode() : 0);
        result = 31 * result + (InChain != null ? InChain.hashCode() : 0);
        result = 31 * result + (InStack != null ? InStack.hashCode() : 0);
        return result;
    }


    /// <summary>
    /// Простой конструктор
    /// </summary>
    /// <param name="condition">Состояние автомата</param>
    /// <param name="inChain">Символ в цепочке</param>
    /// <param name="inStack">Символ из стека</param>
    public SigmaParam( Term condition, Term inChain, Term inStack )
    {
        Condition = condition;
        InChain = inChain;
        InStack = inStack;
    }

    /// <summary>
    /// Строковое представление объекта (для лога)
    /// </summary>
    /// <returns></returns>

    public String toString() {
        return "Состояние:" + this.Condition + ". " +
                "Символ в цепочке:" + this.InChain + ". " +
                "Символ в стеке:" + this.InStack + ". ";
    }
}
