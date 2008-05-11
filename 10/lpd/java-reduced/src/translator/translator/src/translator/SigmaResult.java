package translator;

/**
 * Created by IntelliJ IDEA.
 * User: epa
 * Date: 11.05.2008
 * Time: 13:36:15
 * To change this template use File | Settings | File Templates.
 */
/// <summary>
 /// ����� ��� ���������� ��������, ������������ �����-�������
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
     /// ������� �����������
     /// </summary>
     /// <param name="condition">��������� � ������� ����� ������� ��������</param>
     /// <param name="toStack">��� ���� �������� � ����</param>
     /// <param name="ruleNumber">����� ������� �� �������� �������������� �������</param>
     public SigmaResult( Term condition, TermList toStack, int ruleNumber, TermList toChain )
     {
         Condition = condition;
         ToStack = toStack;
         RuleNumber = ruleNumber;
         ToChain = toChain;
     }

     /// <summary>
     /// ��������� ������������� ������� (��� ����)
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

         return "���������:" + this.Condition + ". " +
                 "�������:" + strToStack + ". " +
                 "����� �������:" + this.RuleNumber + ". " +
                 "�������� �������:" + strToChain//.SkippedString
                 + ".";
     }
}

