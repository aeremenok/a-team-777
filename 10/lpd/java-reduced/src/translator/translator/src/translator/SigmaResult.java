package translator;

/**
 * Created by IntelliJ IDEA.
 * User: epa
 * Date: 11.05.2008
 * Time: 13:36:15
 * To change this template use File | Settings | File Templates.
 */
/// <summary>
 /// Класс для реализации значений, возвращаемых сигма-функции
 /// </summary>
 public class SigmaResult
 {
     public Term Condition;
     public TermList ToStack;
     public TermList ToChain;
     public int RuleNumber;



     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;

         SigmaResult that = (SigmaResult) o;

         if (!Condition.equals(that.Condition)) return false;
         if (!ToChain.equals(that.ToChain)) return false;
         if (!ToStack.equals(that.ToStack)) return false;

         return true;
     }

     public int hashCode() {
         int result;
         result = Condition.hashCode();
         result = 31 * result + ToStack.hashCode();
         result = 31 * result + ToChain.hashCode();
         return result;
     }


     /// <summary>
     /// Простой конструктор
     /// </summary>
     /// <param name="condition">Состояние в которое нужно перейти автомату</param>
     /// <param name="toStack">Что надо положить в стек</param>
     /// <param name="ruleNumber">Номер правила по которому осуществляется переход</param>
     public SigmaResult( Term condition, TermList toStack, int ruleNumber, TermList toChain )
     {
         Condition = condition;
         ToStack = toStack;
         RuleNumber = ruleNumber;
         ToChain = toChain;
     }

     /// <summary>
     /// Строковое представление объекта (для лога)
     /// </summary>
     /// <returns></returns>
     public String ToString()
     {
         String strToStack = "";
         for ( Term t : this.ToStack.getList() )
         {
             strToStack += " " + t;
         }

         String strToChain = "";
         for (Term t: this.ToChain.getList()){
             strToChain += " " + t;
         }

         return "Состояние:" + this.Condition + ". " +
                 "Символы:" + strToStack + ". " +
                 "Номер правила:" + this.RuleNumber + ". " +
                 "Выходная цепочка:" + strToChain//.SkippedString
                 + ".";
     }
}

