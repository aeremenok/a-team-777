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
    /// класс для протоколирования переходов по входной цепочке (лог автомата)
    /// </summary>
    public class LogItem
    {
        public Term Condition; //Состояние автомата
        public TermList IncomingStream; //Текущая входная лента
        public TermList OutcomingStream; //Текущая входная лента
        public Stack StackState; //Текущее состояние стека
        public int RuleNumber; //Номер правила, по которому попали в это состояние(0-начальное состояние)

        /// <summary>
        /// Простой конструктор
        /// </summary>
        /// <param name="condition">Состояние автомата</param>
        /// <param name="incomingStream">Текущая входная лента</param>
        /// <param name="stackState">Текущее состояние стека</param>
        /// <param name="ruleNumber">Номер правила, по которому попали в это состояние</param>
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
        /// Конструктор по тем же параметрам, что и простой,
        /// только копирует часть входной цепочки (начаная с incomingStreamHead)
        /// </summary>
        /// <param name="condition">Состояние автомата</param>
        /// <param name="incomingStream">Текущая входная лента</param>
        /// <param name="stackState">Текущее состояние стека</param>
        /// <param name="ruleNumber">Номер правила, по которому попали в это состояние</param>
        /// <param name="incomingStreamHead">начиная с какого символа копировать</param>
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
        /// Строка представляющая, текущий объект
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
            return "Состояние:" + this.Condition.Name + ". " + "Входные символы:" + StrInc + ". " + "Стек:" + StrStack + ". " + "Номер правила:" + this.RuleNumber + ". " + "Выходная строка:" + this.OutcomingStream.SkippedString + ".";
        }  */
    }
