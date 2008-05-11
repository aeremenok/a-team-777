package translator;

import translator.logging.LogItem;

import java.util.*;

/**
 *
 * User: epa
 * Date: 10.05.2008
 * Time: 23:18:28
 */
public class core {

    enum ParseStatus{
        Fail, Ok, Intermediate;
    }


/***********************************************************************************************************************
 * main functions
 **********************************************************************************************************************/

    //Переменные, описывающие автомат - не меняются в прцессе работы автомата и менять их не следует:
    private final TermList Conditions;     //множество состояний устройства управления
    private final TermList ABC;            //алфавит входных символов
    private final TermList ABCout;         //алфавит выходных символов
    private final TermList L;              //алфавит магазина
    private final Hashtable<SigmaParam,SigmaResult> SigmaFinction; //сигма функция
    private final Term StartState;         //начальное состояние автомата
    private final TermList EndStates;      //множество конечных состояний

    private boolean IsCorrect;                   //можно ли с этим автоматом работать

    //Переменные описывающие текущее состояние автомата:
    private TermList IncomingStream;          //входная строка
    private int IncomingStreamPosition;       //текущая позиция во входной строке
    private TermList OutComingStream;         //выходная цепочка

    private Stack AutomateStack;              //стек автомата

    private Term CurState;                    //текущее состояние автомата (из множества Q)

    private ParseStatus CurStatus;            //может ли автомат далее работать
    //(если будет doStep вызывать  извне)

    private int LastRuleNumber;               //для удобства пользования - номер правила по которому совершили переход

    //Для протоколирования:
    private ArrayList<LogItem> Log;                    //структура для протоколирования переходов

    private String DebugInfo;                 //отладочная информация - в случае ошибки пишем туда что-нить удобоваримое


    /// <summary>
    /// Конструктор по готовым уже параметрам автомата
    /// </summary>
    /// <param name="conditions">множество состояний устройства управления</param>
    /// <param name="abc">алфавит входных символов</param>
    /// <param name="ABCout">алфавит выходных символов</param>
    /// <param name="l">магазинный алфавит</param>
    /// <param name="sigmaFinction">сигма функция</param>
    /// <param name="startState">начальное состояние</param>
    /// <param name="endStates">множество конечных состояний</param>
    public core( TermList conditions, TermList abc, TermList ABCout, TermList l, Hashtable sigmaFinction, Term startState, TermList endStates )
    {
        this.Conditions = conditions;
        this.ABC = abc;
        this.ABCout = ABCout;
        this.L = l;
        this.SigmaFinction = sigmaFinction;
        this.StartState = startState;
        this.EndStates = endStates;
        this.Log = new ArrayList();
        this.AutomateStack = new Stack();
        this.IncomingStream = new TermList();
        this.DebugInfo = "";
        this.CurState = new Term();

        this.OutComingStream = new TermList();

        this.CheckCorrectSSM();
        this.Reset();
    }

    /// <summary>
    /// Конструктор по строке-XML представлению автомата
    /// </summary>
    /// <param name="IncXML">XML строка, представляющая автомат или путь к файлу с автоматом</param>
    /// <param name="IsFileName">true - IncXML - имя файла
    ///                          false - IncXML - сам XML</param>
    public core( String IncXML, boolean IsFileName )
    {
        this.Conditions = new TermList();
        this.ABC = new TermList();
        this.L = new TermList();
        this.SigmaFinction = new Hashtable();
        this.StartState = new Term();
        this.EndStates = new TermList();
        this.Log = new ArrayList();
        this.AutomateStack = new Stack();
        this.IncomingStream = new TermList();
        this.DebugInfo = "";
        this.IsCorrect = false;
        this.CurState = new Term();
        this.CurStatus = ParseStatus.Intermediate;
        this.IncomingStreamPosition = 0;
        this.OutComingStream = new TermList();
        this.ABCout = new TermList();
/*
        XmlDocument oDocument = new XmlDocument();

        try
        {
            if(IsFileName)
            {//это у нас имя файла
                oDocument.Load( IncXML );
            }
            else
            {//это у нас сам XML
                oDocument.LoadXml( IncXML );
            }
        }
        catch ( Exception e )
        {
            this.DebugInfo = "Ошибка при парсинге входного XML файла." + e.Message;
            throw new Exception( "Ошибка при парсинге входного XML файла." + e.Message );
        }

        //Разбор входного XML
        try
        {
            //Обязательный параметр
            XmlNode oNode = oDocument.SelectSingleNode( "//Conditions" );
            if ( oNode == null )
            {
                throw new Exception( "Отсутствует обязательный тег 'conditions'" );
            }
            Conditions.XML = oNode.OuterXml;

            //Обязательный параметр
            oNode = oDocument.SelectSingleNode( "//ABC" );
            if ( oNode == null )
            {
                throw new Exception( "Отсутствует обязательный тег 'ABC'" );
            }
            ABC.XML = oNode.OuterXml;

            //Обязательный параметр
            oNode = oDocument.SelectSingleNode( "//ABCout" );
            if ( oNode == null )
            {
                throw new Exception( "Отсутствует обязательный тег 'ABCout'" );
            }
            this.ABCout.XML = oNode.OuterXml;


            //Обязательный параметр
            oNode = oDocument.SelectSingleNode( "//L" );
            if ( oNode == null )
            {
                throw new Exception( "Отсутствует обязательный тег 'L'" );
            }
            L.XML = oNode.OuterXml;

            //Обязательный параметр
            oNode = oDocument.SelectSingleNode( "//EndStates" );
            if ( oNode == null )
            {
                throw new Exception( "Отсутствует обязательный тег 'EndStates'" );
            }
            EndStates.XML = oNode.OuterXml;

            //Обязательный параметр - должен быть прочитан до CurState !!!
            oNode = oDocument.SelectSingleNode( "//StartState" );
            if ( oNode == null )
            {
                throw new Exception( "Отсутствует обязательный тег 'StartState'" );
            }
            StartState.Name = oNode.Attributes[ "name" ].Value;

            try
            {
                IsCorrect = "TRUE".Equals( oDocument.SelectSingleNode( "//IsCorrect" ).Value );
            }
            catch ( Exception )
            {
                //do nothing
            }

            try
            {
                oNode = oDocument.SelectSingleNode( "//IncomingStream" );
                if ( oNode != null )
                {
                    IncomingStream.XML = oNode.OuterXml;
                }
            }
            catch ( Exception )
            {
                //do nothing
            }

            oNode = oDocument.SelectSingleNode( "//CurState" );
            if ( oNode == null )
            {
                this.CurState = this.StartState;
            }
            else
            {
                CurState.Name = oNode.Attributes[ "name" ].Value;
            }

            try
            {
                this.IncomingStreamPosition = Int32.Parse( oDocument.SelectSingleNode( "//IncomingStreamPosition" ).Attributes[ "val" ].Value );
            }
            catch ( Exception )
            {
                //do nothing
            }


            oNode = oDocument.SelectSingleNode( "//CurStatus" );
            if ( oNode != null )
            {
                String CurStatusStr = oNode.Attributes[ "name" ].Value;

                if ( "Ok".Equals( CurStatusStr ) )
                {
                    this.CurStatus = ParseStatus.Ok;
                }
                if ( "Intermediate".Equals( CurStatusStr ) )
                {
                    this.CurStatus = ParseStatus.Intermediate;
                }
            }

            oNode = oDocument.SelectSingleNode( "//DebugInfo" );
            if ( oNode != null )
            {
                DebugInfo = oNode.Attributes[ "name" ].Value;
            }

            //Обязательный параметр
            //SigmaFinction;
            XmlNode sigmaF = oDocument.SelectSingleNode( "//SigmaFinction" );
            if ( sigmaF == null )
            {
                throw new Exception( "Отсутствует обязательный тег 'SigmaFinction'" );
            }
            XmlNodeList sigmaFList = sigmaF.ChildNodes;
            for ( int i = 0; i < sigmaFList.Count; i++ )
            {
                XmlNode sigmaRule = sigmaFList.Item( i );
                int sParam = 0;
                int sRes = 1;

                if ( sigmaRule.ChildNodes.Item( 0 ).Name != "SigmaParam" )
                {
                    sParam = 1;
                    sRes = 0;
                }

                XmlNode sigmaParam = sigmaRule.ChildNodes.Item( sParam );
                XmlNode sigmaResult = sigmaRule.ChildNodes.Item( sRes );

                Term cond = new Term( sigmaParam.Attributes[ "Condition" ].Value );
                Term inChain = new Term( sigmaParam.Attributes[ "InChain" ].Value );
                Term inStack = new Term( sigmaParam.Attributes[ "InStack" ].Value );


                int toStackNum = 0;
                int toChainNum = 1;
                if ( sigmaResult.ChildNodes[ 0 ].Name != "ToStack" )
                {
                    toStackNum = 1;
                    toChainNum = 0;
                }
                Term condRes = new Term( sigmaResult.Attributes[ "Condition" ].Value );
                TermList toStack = new TermList();
                toStack.XML = sigmaResult.ChildNodes[ toStackNum ].OuterXml;
                int ruleNum = Int32.Parse( sigmaResult.Attributes[ "RuleNumber" ].Value );

                TermList toChain = new TermList();
                toChain.XML = sigmaResult.ChildNodes[ toChainNum ].OuterXml;

                SigmaParam Param = new SigmaParam( cond, inChain, inStack );
                SigmaResult Result = new SigmaResult( condRes, toStack, ruleNum, toChain );
                this.SigmaFinction.Add( Param, Result );
            }

            //AutomateStack;
            XmlNode AutomateStackXML = oDocument.SelectSingleNode( "//AutomateStack" );
            if ( AutomateStackXML != null )
            {
                XmlNodeList automateItems = AutomateStackXML.ChildNodes;
                for ( int i = 0; i < automateItems.Count; i++ )
                {
                    //this.AutomateStack.Push( automateItems.Item( i ).Attributes["name"] );
                    this.AutomateStack.Push( new Term( automateItems.Item( i ).InnerText ) );
                }
            }
            if ( this.AutomateStack.Count == 0 )
            {
                this.AutomateStack.Push( Term.Z );
            }

            //Log;
            XmlNode LogXml = oDocument.SelectSingleNode( "//Log" );
            if ( LogXml != null )
            {
                for ( int i = 0; i < LogXml.ChildNodes.Count; i++ )
                {
                    oNode = LogXml.ChildNodes.Item( i );

                    Term cond = new Term( oNode.Attributes[ "Condition" ].Value );
                    int ruleNum = Int32.Parse( oNode.Attributes[ "RuleNumber" ].Value );

                    TermList incStr = new TermList();
                    TermList outStr = new TermList();
                    int outIdx = 0;
                    int incStrIdx = 0;
                    Stack stack = new Stack();
                    int stackIdx = 0;


                    if ( oNode.ChildNodes.Item( 0 ).Name == "IncomingStream" )
                    {
                        incStrIdx = 0;
                    }
                    if ( oNode.ChildNodes.Item( 1 ).Name == "IncomingStream" )
                    {
                        incStrIdx = 1;
                    }
                    if ( oNode.ChildNodes.Item( 2 ).Name == "IncomingStream" )
                    {
                        incStrIdx = 2;
                    }
                    incStr.XML = oNode.ChildNodes.Item( incStrIdx ).OuterXml;

                    if ( oNode.ChildNodes.Item( 0 ).Name == "OutcomingStream" )
                    {
                        outIdx = 0;
                    }
                    if ( oNode.ChildNodes.Item( 1 ).Name == "OutcomingStream" )
                    {
                        outIdx = 1;
                    }
                    if ( oNode.ChildNodes.Item( 2 ).Name == "OutcomingStream" )
                    {
                        outIdx = 2;
                    }
                    outStr.XML = oNode.ChildNodes.Item( incStrIdx ).OuterXml;

                    if ( oNode.ChildNodes.Item( 0 ).Name == "AutomateStack" )
                    {
                        stackIdx = 0;
                    }
                    if ( oNode.ChildNodes.Item( 1 ).Name == "AutomateStack" )
                    {
                        stackIdx = 1;
                    }
                    if ( oNode.ChildNodes.Item( 2 ).Name == "AutomateStack" )
                    {
                        stackIdx = 2;
                    }

                    XmlNodeList stackNodeList = oNode.ChildNodes.Item( stackIdx ).ChildNodes;
                    for ( int j = 0; j < stackNodeList.Count; j++ )
                    {
                        stack.Push( new Term( stackNodeList.Item( j ).Attributes[ "name" ].Value ) );
                    }

                    LogItem li = new LogItem( cond, incStr, stack, ruleNum, outStr );
                    Log.Add( li );
                }
            }
        }
        catch ( Exception e )
        {
            throw new Exception( "Не удалось разобрать XML файл, возможная причина:" + e.Message );
        }
  */
        this.CheckCorrectSSM();
    }

    /// <summary>
    /// Проверяет всю структуру на корректность - вызываем сразу после конструктора
    /// </summary>
    /// <returns></returns>
    public boolean CheckCorrectSSM()
    {
        IsCorrect = false;
        this.DebugInfo = "Автомат неправильно задан и проверка не может быть выполнена!";

        //private final TermList Conditions;     //множество состояний устройства управления
        //любое

        //private final TermList ABC;            //алфавит входных символов
        //любой

        //private final Term StartState;         //начальное состояние автомата
        if ( ! this.Conditions.contains( this.StartState ) )
        {
            throw new IllegalArgumentException("StartState не содержится в Conditions" );
        }

        //private final TermList EndStates;      //множество конечных состояний
        for ( int i = 0; i < this.EndStates.count(); i++ )
        {
            if ( ! this.Conditions.contains( this.EndStates.get( i ) ) )
            {
                throw new IllegalArgumentException( "EndStates[" + i + "] не содержится в Conditions" );
            }
        }

        //private final TermList L;              //язык ?
//            for ( int i = 0; i < this.L.Count; i++ )
//            {
//                if ( ! this.ABC.Contains( this.L[ i ] ) && !Term.Z.Equals( this.L[ i ] ) && !Term.EmptyString.Equals( this.L[ i ] ) )
//                {
//                    throw new ArgumentException( "Терм из алфавита магазина [" + i + "] = " + L[ i ].Name + " не содержится во входном алфавите" );
//                }
//            }

        //private final Hashtable SigmaFinction; //сигма функция
        for(Map.Entry e : SigmaFinction.entrySet()){

            SigmaParam sp = ( SigmaParam ) e.getKey();
            SigmaResult sr = ( SigmaResult ) e.getValue();

            //Начальное состояние - из множества начальных состояний
            if ( ! this.Conditions.contains( sp.Condition ) )
            {
                throw new IllegalArgumentException( "sp.Condition=" + sp.Condition.getTermName() + " не содержится в множестве состояний." );
            }
            //символ из входной цепочки - из алфавита входных символов
            if ( ! this.ABC.contains( sp.InChain ) && !Term.Z.equals( sp.InChain ) && !Term.EmptyString.equals( sp.InChain ) )
            {
                throw new IllegalArgumentException( "sp.InChain=" + sp.InChain.getTermName() + " не содержится во входном алфавите." );
            }
            //символ из стека - из алфавита стека
            if ( ! this.L.contains( sp.InStack ) && !Term.Z.equals( sp.InStack ) && !Term.EmptyString.equals( sp.InStack ) )
            {
                throw new IllegalArgumentException( "sp.InStack=" + sp.InStack.getTermName() + " не содержится во входном алфавите." );
            }


            //sr.RuleNumber - не проверяем...

            //Новое состояние - из множества состояний
            if ( ! this.Conditions.contains( sr.Condition ) )
            {
                throw new IllegalArgumentException( "sr.Condition.Name=" + sr.Condition.getTermName() + " не содержится в множестве состояний." );
            }

            //символ в стек - из L
            for( Term oTerm : sr.ToStack.getList() )
            {
                if ( ! this.L.contains( oTerm ) && !Term.Z.equals( oTerm ) && !Term.EmptyString.equals( oTerm ) )
                {
                    throw new IllegalArgumentException( oTerm.getTermName() + " из дельта-функции не содержится в алфавите магазина." );
                }
            }

            //символ в выходную цепочку - из множества выходных символов
            for ( Term oTerm : sr.ToChain.getList() )
            {
                if ( ! this.ABCout.contains( oTerm ) && !Term.Z.equals( oTerm ) && !Term.EmptyString.equals( oTerm ) )
                {
                    throw new IllegalArgumentException( oTerm.getTermName() + " не содержится в выходном алфавите." );
                }
            }

        }

        IsCorrect = true;
        this.DebugInfo = null;

        return true;
    }

    /// <summary>
    /// Проверка переменных автомата
    /// </summary>
    /// <returns></returns>
    public boolean CheckCorrectVariables()
    {
        //private int IncomingStreamPosition;       //текущая позиция во входной строке
        //не проверяем, так как всегда = 0
        /*if(this.IncomingStreamPosition < 0 )
            throw new ArgumentException("Позиция головки автомата меньше 0!");

        if(this.IncomingStreamPosition > 0 )
        if(this.IncomingStreamPosition > this.IncomingStream.Count)
            throw new ArgumentException("Позиция головки автомата в строке входных символов более длины входной строки");
*/
        //private TermList IncomingStream;          //входная строка
        for ( int i = 0; i < this.IncomingStream.count(); i++ )
        {
            if ( ! this.ABC.contains( this.IncomingStream.get( i ) ) )
            {
                throw new IllegalArgumentException( "IncomingStream[" + i + "]='" + IncomingStream.get( i ) + "' не содержится во входном алфавите" );
            }
        }

        //private Term CurState;                    //текущее состояние автомата (из множества Q)
        //бесполезно, так как всегда this.StartState
        //if(! this.Conditions.contains( this.CurState ))  throw new IllegalArgumentException("CurState[" + this.CurState.getTermName() + "] не содержится во множестве входных состояний");

        //private Stack AutomateStack;              //стек автомата


        Iterator IE = this.AutomateStack.listIterator();

        while ( IE.hasNext() )
        {
            Term t = (Term) IE.next();
            if ( t != null )
            {
                if(! this.L.contains( t ) )
                    throw new IllegalArgumentException( "Символ " + t.getTermName() + " не содержится в алфавите стека" );
            }
        }

        return true;
    }


    /// <summary>
    /// получить/задать XML-представление грамматики
    /// </summary>
    public String getXML()
    {
        /*
            //create start elements
            XmlDocument oDocument = new XmlDocument();
            oDocument.AppendChild( oDocument.CreateXmlDeclaration( "1.0", "utf-8", "" ) );

            XmlElement oElem = null;
            XmlElement oRootElem = null;

            oRootElem = oDocument.CreateElement( "SST" );
            oDocument.AppendChild( oRootElem );

            // Append Conditions
            oRootElem = oDocument.CreateElement( "Conditions" );
            foreach ( Term oTerm in Conditions.Terms )
            {
                oElem = oDocument.CreateElement( "Term" );
                oElem.SetAttribute( "name", oTerm.getTermName() );
                oRootElem.AppendChild( oElem );
            }
            oDocument.DocumentElement.AppendChild( oRootElem );

            // Append ABC
            oRootElem = oDocument.CreateElement( "ABC" );
            foreach ( Term oTerm in ABC.Terms )
            {
                oElem = oDocument.CreateElement( "Term" );
                oElem.SetAttribute( "name", oTerm.getTermName() );
                oRootElem.AppendChild( oElem );
            }
            oDocument.DocumentElement.AppendChild( oRootElem );

            // Append ABCout
            oRootElem = oDocument.CreateElement( "ABCout" );
            foreach ( Term oTerm in ABCout.Terms )
            {
                oElem = oDocument.CreateElement( "Term" );
                oElem.SetAttribute( "name", oTerm.getTermName() );
                oRootElem.AppendChild( oElem );
            }
            oDocument.DocumentElement.AppendChild( oRootElem );

            // Append L
            oRootElem = oDocument.CreateElement( "L" );
            foreach ( Term oTerm in L.Terms )
            {
                oElem = oDocument.CreateElement( "Term" );
                oElem.SetAttribute( "name", oTerm.getTermName() );
                oRootElem.AppendChild( oElem );
            }
            oDocument.DocumentElement.AppendChild( oRootElem );

            // Append EndStates
            oRootElem = oDocument.CreateElement( "EndStates" );
            foreach ( Term oTerm in EndStates.Terms )
            {
                oElem = oDocument.CreateElement( "Term" );
                oElem.SetAttribute( "name", oTerm.getTermName() );
                oRootElem.AppendChild( oElem );
            }
            oDocument.DocumentElement.AppendChild( oRootElem );


            // Append StartState
            oElem = oDocument.CreateElement( "StartState" );
            oElem.SetAttribute( "name", StartState.getTermName() );
            oDocument.DocumentElement.AppendChild( oElem );

            // Append IsCorrect
            oElem = oDocument.CreateElement( "IsCorrect" );
            oElem.SetAttribute( "name", ( IsCorrect ) ? "TRUE" : "FALSE" );
            oDocument.DocumentElement.AppendChild( oElem );

            // Append SigmaFinction
            oRootElem = oDocument.CreateElement( "SigmaFinction" );
            IEnumerator ie = SigmaFinction.GetEnumerator();
            ie.Reset();
            while ( ie.MoveNext() )
            {
                SigmaParam sp = ( SigmaParam ) ( ( DictionaryEntry ) ie.Current ).Key;
                SigmaResult sr = ( SigmaResult ) ( ( DictionaryEntry ) ie.Current ).Value;

                XmlElement sigmaRule = oDocument.CreateElement( "sigmaRule" );

                XmlElement sigmaParam = oDocument.CreateElement( "SigmaParam" );

                sigmaParam.SetAttribute( "Condition", sp.Condition.getTermName() );
                sigmaParam.SetAttribute( "InChain", sp.InChain.getTermName() );
                sigmaParam.SetAttribute( "InStack", sp.InStack.getTermName() );

                XmlElement sigmaResult = oDocument.CreateElement( "SigmaResult" );

                sigmaResult.SetAttribute( "Condition", sr.Condition.getTermName() );
                sigmaResult.SetAttribute( "RuleNumber", "" + sr.RuleNumber );

                oElem = oDocument.CreateElement( "ToStack" );
                foreach ( Term oTerm in sr.ToStack.Terms )
                {
                    XmlElement xmlTerm_inner = oDocument.CreateElement( "Term" );
                    xmlTerm_inner.SetAttribute( "name", oTerm.getTermName() );
                    oElem.AppendChild( xmlTerm_inner );
                }
                sigmaResult.AppendChild( oElem );

                oElem = oDocument.CreateElement( "ToChain" );
                foreach ( Term oTerm in sr.ToChain.Terms )
                {
                    XmlElement xmlTerm_inner = oDocument.CreateElement( "Term" );
                    xmlTerm_inner.SetAttribute( "name", oTerm.getTermName() );
                    oElem.AppendChild( xmlTerm_inner );
                }
                sigmaResult.AppendChild( oElem );

                sigmaRule.AppendChild( sigmaResult );
                sigmaRule.AppendChild( sigmaParam );

                oRootElem.AppendChild( sigmaRule );
            }
            oDocument.DocumentElement.AppendChild( oRootElem );

            // Append IncomingStream - необязательный параметр
            if ( this.IncomingStream != null )
            {
                if ( this.IncomingStream.count() > 0 )
                {
                    oRootElem = oDocument.CreateElement( "IncomingStream" );
                    foreach ( Term oTerm in IncomingStream.Terms )
                    {
                        oElem = oDocument.CreateElement( "Term" );
                        oElem.SetAttribute( "name", oTerm.getTermName() );
                        oRootElem.AppendChild( oElem );
                    }
                    oDocument.DocumentElement.AppendChild( oRootElem );
                }
            }
            // Append IncomingStreamPosition
            if ( this.IncomingStreamPosition != 0 )
            {
                oElem = oDocument.CreateElement( "IncomingStreamPosition" );
                oElem.SetAttribute( "val", "" + IncomingStreamPosition );
                oDocument.DocumentElement.AppendChild( oElem );
            }

            // Append OutcomingStream - необязательный параметр
            if ( this.OutComingStream != null )
            {
                if ( this.OutComingStream.count() > 0 )
                {
                    oRootElem = oDocument.CreateElement( "OutcomingStream" );
                    foreach ( Term oTerm in IncomingStream.Terms )
                    {
                        oElem = oDocument.CreateElement( "Term" );
                        oElem.SetAttribute( "name", oTerm.getTermName() );
                        oRootElem.AppendChild( oElem );
                    }
                    oDocument.DocumentElement.AppendChild( oRootElem );
                }
            }

            // Append CurState
            oElem = oDocument.CreateElement( "CurState" );
            oElem.SetAttribute( "name", CurState.getTermName() );
            oDocument.DocumentElement.AppendChild( oElem );

            // Append CurStatus
            //todo
            oElem = oDocument.CreateElement( "CurStatus" );
            oElem.SetAttribute( "name", CurStatus.ToString() );
            oDocument.DocumentElement.AppendChild( oElem );

            // Append DebugInfo
            if ( this.DebugInfo != null )
            {
                if ( this.DebugInfo.Length > 0 )
                {
                    oElem = oDocument.CreateElement( "DebugInfo" );
                    oElem.SetAttribute( "name", DebugInfo );
                    oDocument.DocumentElement.AppendChild( oElem );
                }
            }

            // Append AutomateStack
            oRootElem = oDocument.CreateElement( "AutomateStack" );
            IEnumerator IE = this.AutomateStack.GetEnumerator();
            IE.Reset();
            while ( IE.MoveNext() )
            {
                if ( IE.Current != null )
                {
                    oElem = oDocument.CreateElement( "AutomateStackElement" );
                    oElem.InnerText = ( ( Term ) ( IE.Current ) ).getTermName();
                    oRootElem.AppendChild( oElem );
                }
            }
            oDocument.DocumentElement.AppendChild( oRootElem );


            if ( this.Log != null )
            {
                if ( this.Log.count() > 0 )
                {
                    oRootElem = oDocument.CreateElement( "Log" );
                    for ( int i = 0; i < this.Log.count(); i++ )
                    {
                        LogItem li = ( LogItem ) ( this.Log[ i ] );


                        oElem = oDocument.CreateElement( "LogElement" );
                        oElem.SetAttribute( "Condition", li.Condition.getTermName() );
                        oElem.SetAttribute( "RuleNumber", "" + li.RuleNumber );


                        XmlElement rEl = oDocument.CreateElement( "IncomingStream" );
                        foreach ( Term oTerm in li.IncomingStream.Terms )
                        {
                            XmlElement el = oDocument.CreateElement( "Term" );
                            el.SetAttribute( "name", oTerm.getTermName() );
                            rEl.AppendChild( el );
                        }
                        oElem.AppendChild( rEl );

                        rEl = oDocument.CreateElement( "OutcomingStream" );
                        foreach ( Term oTerm in li.OutcomingStream.Terms )
                        {
                            XmlElement el = oDocument.CreateElement( "Term" );
                            el.SetAttribute( "name", oTerm.getTermName() );
                            rEl.AppendChild( el );
                        }
                        oElem.AppendChild( rEl );


                        rEl = oDocument.CreateElement( "AutomateStack" );
                        IEnumerator IE1 = li.StackState.GetEnumerator();
                        IE1.Reset();
                        while ( IE1.MoveNext() )
                        {
                            XmlElement el = oDocument.CreateElement( "AutomateStackElement" );
                            el.SetAttribute( "name", ( ( Term ) ( IE1.Current ) ).getTermName() );
                            rEl.AppendChild( el );
                        }
                        oElem.AppendChild( rEl );

                        oRootElem.AppendChild( oElem );
                    }
                    oDocument.DocumentElement.AppendChild( oRootElem );
                }
            }

            return oDocument;
        }
         */
        return "NOT IMPLEMENTED YET";
    }


    /// <summary>
    /// формирует построчную запись лога
    /// </summary>
    /// <returns></returns>
    public String WriteLogToStr()
    {
        StringBuffer stb = new StringBuffer();
        stb.append("Лог:");
        for (LogItem aLog : this.Log) {
            stb.append("\n").append(aLog);
        }
        return stb.toString();
    }








    public void ChangeIncomingStream(TermList NewStream) throws Exception {
        if(NewStream.count() >= this.IncomingStreamPosition)
        {
            this.IncomingStream = NewStream;
            if( this.IncomingStreamPosition == this.IncomingStream.count() ) return;
            if( this.IncomingStreamPosition > this.IncomingStream.count() ) throw new Exception("Автомат завершил работу!");
            Term FromStream =  this.IncomingStream.get( this.IncomingStreamPosition );

            if(! this.ABC.contains( FromStream )) throw new IllegalArgumentException ("Введен символ во входной ленте не из входного алфавита!");
        }
        else throw new IllegalArgumentException("Не правльно заданная входная строка!");

    }

    /// <summary>
    /// Переводит автомат в начальное состояние и назначает ему входную ленту IncomingStream
    /// </summary>
    /// <param name="IncomingStream">Новая входная лента</param>
    public void Reset( TermList IncomingStream )
    {
        this.IncomingStream = IncomingStream;
        Reset();
    }

    /// <summary>
    /// Перевод автомата в начальное состояние
    /// </summary>
    public void Reset()
    {
        //инициализация
        Log = new ArrayList<LogItem>();
        this.DebugInfo = "";
        this.AutomateStack = new Stack();
        this.CurStatus = ParseStatus.Intermediate;
        this.IncomingStreamPosition = 0;
        this.CurState = this.StartState;
        this.OutComingStream = new TermList();

        //лог начального состояния
        LogItem Li = new LogItem( this.CurState, this.IncomingStream, this.AutomateStack, 0, OutComingStream );
        this.Log.add( Li );

        CheckCorrectVariables();
    }


    /// <summary>
    /// Выполнение очередного шага и возвращение состояние, в которое перешел
    /// Если текущее состояние - ошибка или завершено - возвращаем текущее и записываем сообщение об ошибке
    /// </summary>
    /// <returns>Состояние, в которое перешел автомат</returns>
    public ParseStatus DoStep()
    {
        //проверка на корректность
        if ( this.CurStatus == ParseStatus.Fail )
        {
            this.DebugInfo = "Состояние автомата - ошибка! Нельзя сделать шаг так как автомат находится в состоянии ошибка!";
            return this.CurStatus;
        }

        if ( this.CurStatus == ParseStatus.Ok )
        {
            this.DebugInfo = "Состояния автомата - завершено! Нельзя сделать шаг!";
            return this.CurStatus;
        }

        if ( !this.IsCorrect )
        {
            return ParseStatus.Fail;
        }

        /*if(this.IncomingStreamPosition >= this.IncomingStream.count())
        {
            this.DebugInfo = "Дошли до края входной ленты!";
            return ParseStatus.Fail;
        } */

        //выполняем шаг
        this.DoAtomicStep();

        //возвращаем в какое состояния мы попали
        return this.CurStatus;
    }

    /// <summary>
    /// Пройти всю входную цепочку не перемещая положение считывания символов на начало цепочки
    /// </summary>
    /// <returns>До какого состояния дошли</returns>
    public ParseStatus Parse()
    {
        //проверка входных параметров
        if ( IncomingStream.count() == 0 )
        {
            //что делать ?
            this.DebugInfo = "Входная строка пустая!";
            return ParseStatus.Fail;
        }
        if ( ! this.IsCorrect )
        {
            //todo что еще
            this.DebugInfo = "Автомат неправильно задан и проверка не может быть выполнена!";
            return ParseStatus.Fail;
        }


        //основной цикл
        while ( this.CurStatus == ParseStatus.Intermediate )
        {
            this.DoAtomicStep();
        }

        //завершающий шаг - нужен???
        //this.DoAtomicStep();

        //LogItem Li = new LogItem(this.CurState,this.IncomingStream,this.AutomateStack,0,this.IncomingStreamPosition);
        //this.Log.Add( Li );

        //возвращаем результат работы
        return this.CurStatus;
    }

    /// <summary>
    /// Совершаем шаг, считая, что все входные данные корректны
    /// </summary>
    private void DoAtomicStep()
    {
        try
        {
            this.LastRuleNumber = 0;
            //формируем наши текущие параметры
            Term FromStack = ( this.AutomateStack.size() <= 0 ) ? Term.Z : ( Term ) this.AutomateStack.pop();
            Term FromStream = ( this.IncomingStreamPosition < this.IncomingStream.count() ) ? this.IncomingStream.get( this.IncomingStreamPosition ) : new Term( Term.EmptyString.getTermName() );
            if ( !FromStream.isEps() )
            {
                this.IncomingStreamPosition++;
            }

            SigmaParam SParam = new SigmaParam( this.CurState, FromStream, FromStack );

            if ( SParam == null )
            {
                this.DebugInfo = "Не удалось собрать параметры!";
                this.CurStatus = ParseStatus.Fail;
                return;
            }

            //получаем следующий щаг
            SigmaResult SRes = ( SigmaResult ) SigmaFinction.get( SParam );

            if ( SRes == null )
            {
                //может в эпсилон-такт
                FromStream = new Term( Term.EmptyString.getTermName() );
                SParam = new SigmaParam( this.CurState, FromStream, FromStack );

                if ( SParam == null )
                {
                    this.DebugInfo = "Не удалось собрать параметры для эпсилон такта!";
                    this.CurStatus = ParseStatus.Fail;
                    return;
                }

                SRes = ( SigmaResult ) SigmaFinction.get( SParam );

                if(SRes == null)
                {
                    CheckEndState();

                    if(this.CurStatus == ParseStatus.Intermediate)
                    {
                        this.DebugInfo = "Нет определения сигма-функции с параметрами ";
                        this.CurStatus = ParseStatus.Fail;
                    }
                    return;
                }
                this.IncomingStreamPosition--;
            }

            //записываем значения

            //меняем текущее состояние
            this.CurState = SRes.Condition;

            //записываем в стек что надо
            for ( int i = SRes.ToStack.count() - 1; i >= 0; i-- )
            {
                if ( !SRes.ToStack.get( i ).isEps() )
                {
                    this.AutomateStack.push( SRes.ToStack.get( i ) );
                }
            }
            //записываем выходную цепочку
            if(!SRes.ToChain.isEmpty() && !SRes.ToChain.getString().equals( "" ) && !SRes.ToChain.getString().equals( " " ))
                this.OutComingStream.AddTermsByString( SRes.ToChain.getString() );

            //записываем лог
            LogItem Li = new LogItem( this.CurState, this.IncomingStream, this.AutomateStack, SRes.RuleNumber, this.IncomingStreamPosition, this.OutComingStream );
            this.Log.add( Li );

//                CheckEndState();

            this.LastRuleNumber = SRes.RuleNumber;

        }
        catch ( Exception e )
        {
            this.DebugInfo = "Во время работы алгоритма произошла ошибка: " + e.getMessage();
            this.CurStatus = ParseStatus.Fail;
        }
    }

    /// <summary>
    /// Проверяет, находится ли автомат в конченом состоянии
    /// </summary>
    private void CheckEndState()
    {
        if ( this.EndStates.contains( this.CurState ) && this.IncomingStream.count() <= this.IncomingStreamPosition )
        {
            //В магазине может быть что угодно
            // если решим, что надо одно из состояний -
            // надо будет пихать в отдельную переменную и сравинивать this.L - с тем куда кидаем
//                TermList TL = new TermList();
//                IEnumerator IE = this.AutomateStack.GetEnumerator();
//                IE.Reset();
//                while ( IE.MoveNext() )
//                {
//                    TL.AddTerm( ( ( Term ) ( IE.Current ) ).getTermName() );
//                }
//                if ( this.L.contains( TL ) )
//                {
                this.CurStatus = ParseStatus.Ok;
//                }
        }
    }

}

