package translator.logging;

import translator.Term;
import translator.TermList;

import java.util.Stack;

/**
 * User: epa
 * Date: 11.05.2008
 * Time: 13:38:58
 */
  /// <summary>
    /// ����� ��� ���������������� ��������� �� ������� ������� (��� ��������)
    /// </summary>
    public class LogItem
    {
        public Term Condition; //��������� ��������
        public TermList IncomingStream; //������� ������� �����
        public TermList OutcomingStream; //������� ������� �����
        public Stack StackState; //������� ��������� �����
        public int RuleNumber; //����� �������, �� �������� ������ � ��� ���������(0-��������� ���������)

        /// <summary>
        /// ������� �����������
        /// </summary>
        /// <param name="condition">��������� ��������</param>
        /// <param name="incomingStream">������� ������� �����</param>
        /// <param name="stackState">������� ��������� �����</param>
        /// <param name="ruleNumber">����� �������, �� �������� ������ � ��� ���������</param>
        public LogItem( Term condition, TermList incomingStream, Stack stackState, int ruleNumber, TermList outComingStream )
        {
            Condition = condition;
            IncomingStream = incomingStream;
            //todo check if correct
            StackState = new Stack();
            for(Object o: stackState.toArray()){
                this.StackState.push(o);
            }

            RuleNumber = ruleNumber;
            OutcomingStream = outComingStream;
        }

        /// <summary>
        /// ����������� �� ��� �� ����������, ��� � �������,
        /// ������ �������� ����� ������� ������� (������� � incomingStreamHead)
        /// </summary>
        /// <param name="condition">��������� ��������</param>
        /// <param name="incomingStream">������� ������� �����</param>
        /// <param name="stackState">������� ��������� �����</param>
        /// <param name="ruleNumber">����� �������, �� �������� ������ � ��� ���������</param>
        /// <param name="incomingStreamHead">������� � ������ ������� ����������</param>
        public LogItem( Term condition, TermList incomingStream, Stack stackState, int ruleNumber, int incomingStreamHead, TermList outComingStream )
        {
            Condition = condition;
            IncomingStream = new TermList(incomingStream);
            //todo check if correct
            StackState = new Stack();
            for(Object o: stackState.toArray()){
                this.StackState.push(o);
            }
            RuleNumber = ruleNumber;

            OutcomingStream = outComingStream;
        }

        //todo
        /// <summary>
        /// ������ ��������������, ������� ������
        /// </summary>
        /// <returns></returns>
        /*public override string ToString()
        {
            string StrInc = "";
            for ( int i = 0; i < this.IncomingStream.Count; i++ )
            {
                StrInc += " " + this.IncomingStream[ i ].Name;
            }
            string StrStack = "";
            IEnumerator IE = this.StackState.GetEnumerator();
            IE.Reset();
            while ( IE.MoveNext() )
            {
                StrStack += " " + ( ( Term ) ( IE.Current ) ).Name; //todo

            }
            return "���������:" + this.Condition.Name + ". " + "������� �������:" + StrInc + ". " + "����:" + StrStack + ". " + "����� �������:" + this.RuleNumber + ". " + "�������� ������:" + this.OutcomingStream.SkippedString + ".";
        }  */
    }
