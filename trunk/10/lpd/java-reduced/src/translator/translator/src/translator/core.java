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

    //����������, ����������� ������� - �� �������� � ������� ������ �������� � ������ �� �� �������:
    private final TermList Conditions;     //��������� ��������� ���������� ����������
    private final TermList ABC;            //������� ������� ��������
    private final TermList ABCout;         //������� �������� ��������
    private final TermList L;              //������� ��������
    private final Hashtable<SigmaParam,SigmaResult> SigmaFinction; //����� �������
    private final Term StartState;         //��������� ��������� ��������
    private final TermList EndStates;      //��������� �������� ���������

    private boolean IsCorrect;                   //����� �� � ���� ��������� ��������

    //���������� ����������� ������� ��������� ��������:
    private TermList IncomingStream;          //������� ������
    private int IncomingStreamPosition;       //������� ������� �� ������� ������
    private TermList OutComingStream;         //�������� �������

    private Stack AutomateStack;              //���� ��������

    private Term CurState;                    //������� ��������� �������� (�� ��������� Q)

    private ParseStatus CurStatus;            //����� �� ������� ����� ��������
    //(���� ����� doStep ��������  �����)

    private int LastRuleNumber;               //��� �������� ����������� - ����� ������� �� �������� ��������� �������

    //��� ����������������:
    private ArrayList<LogItem> Log;                    //��������� ��� ���������������� ���������

    private String DebugInfo;                 //���������� ���������� - � ������ ������ ����� ���� ���-���� ������������


    /// <summary>
    /// ����������� �� ������� ��� ���������� ��������
    /// </summary>
    /// <param name="conditions">��������� ��������� ���������� ����������</param>
    /// <param name="abc">������� ������� ��������</param>
    /// <param name="ABCout">������� �������� ��������</param>
    /// <param name="l">���������� �������</param>
    /// <param name="sigmaFinction">����� �������</param>
    /// <param name="startState">��������� ���������</param>
    /// <param name="endStates">��������� �������� ���������</param>
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
    /// ����������� �� ������-XML ������������� ��������
    /// </summary>
    /// <param name="IncXML">XML ������, �������������� ������� ��� ���� � ����� � ���������</param>
    /// <param name="IsFileName">true - IncXML - ��� �����
    ///                          false - IncXML - ��� XML</param>
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
            {//��� � ��� ��� �����
                oDocument.Load( IncXML );
            }
            else
            {//��� � ��� ��� XML
                oDocument.LoadXml( IncXML );
            }
        }
        catch ( Exception e )
        {
            this.DebugInfo = "������ ��� �������� �������� XML �����." + e.Message;
            throw new Exception( "������ ��� �������� �������� XML �����." + e.Message );
        }

        //������ �������� XML
        try
        {
            //������������ ��������
            XmlNode oNode = oDocument.SelectSingleNode( "//Conditions" );
            if ( oNode == null )
            {
                throw new Exception( "����������� ������������ ��� 'conditions'" );
            }
            Conditions.XML = oNode.OuterXml;

            //������������ ��������
            oNode = oDocument.SelectSingleNode( "//ABC" );
            if ( oNode == null )
            {
                throw new Exception( "����������� ������������ ��� 'ABC'" );
            }
            ABC.XML = oNode.OuterXml;

            //������������ ��������
            oNode = oDocument.SelectSingleNode( "//ABCout" );
            if ( oNode == null )
            {
                throw new Exception( "����������� ������������ ��� 'ABCout'" );
            }
            this.ABCout.XML = oNode.OuterXml;


            //������������ ��������
            oNode = oDocument.SelectSingleNode( "//L" );
            if ( oNode == null )
            {
                throw new Exception( "����������� ������������ ��� 'L'" );
            }
            L.XML = oNode.OuterXml;

            //������������ ��������
            oNode = oDocument.SelectSingleNode( "//EndStates" );
            if ( oNode == null )
            {
                throw new Exception( "����������� ������������ ��� 'EndStates'" );
            }
            EndStates.XML = oNode.OuterXml;

            //������������ �������� - ������ ���� �������� �� CurState !!!
            oNode = oDocument.SelectSingleNode( "//StartState" );
            if ( oNode == null )
            {
                throw new Exception( "����������� ������������ ��� 'StartState'" );
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

            //������������ ��������
            //SigmaFinction;
            XmlNode sigmaF = oDocument.SelectSingleNode( "//SigmaFinction" );
            if ( sigmaF == null )
            {
                throw new Exception( "����������� ������������ ��� 'SigmaFinction'" );
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
            throw new Exception( "�� ������� ��������� XML ����, ��������� �������:" + e.Message );
        }
  */
        this.CheckCorrectSSM();
    }

    /// <summary>
    /// ��������� ��� ��������� �� ������������ - �������� ����� ����� ������������
    /// </summary>
    /// <returns></returns>
    public boolean CheckCorrectSSM()
    {
        IsCorrect = false;
        this.DebugInfo = "������� ����������� ����� � �������� �� ����� ���� ���������!";

        //private final TermList Conditions;     //��������� ��������� ���������� ����������
        //�����

        //private final TermList ABC;            //������� ������� ��������
        //�����

        //private final Term StartState;         //��������� ��������� ��������
        if ( ! this.Conditions.contains( this.StartState ) )
        {
            throw new IllegalArgumentException("StartState �� ���������� � Conditions" );
        }

        //private final TermList EndStates;      //��������� �������� ���������
        for ( int i = 0; i < this.EndStates.count(); i++ )
        {
            if ( ! this.Conditions.contains( this.EndStates.get( i ) ) )
            {
                throw new IllegalArgumentException( "EndStates[" + i + "] �� ���������� � Conditions" );
            }
        }

        //private final TermList L;              //���� ?
//            for ( int i = 0; i < this.L.Count; i++ )
//            {
//                if ( ! this.ABC.Contains( this.L[ i ] ) && !Term.Z.Equals( this.L[ i ] ) && !Term.EmptyString.Equals( this.L[ i ] ) )
//                {
//                    throw new ArgumentException( "���� �� �������� �������� [" + i + "] = " + L[ i ].Name + " �� ���������� �� ������� ��������" );
//                }
//            }

        //private final Hashtable SigmaFinction; //����� �������
        for(Map.Entry e : SigmaFinction.entrySet()){

            SigmaParam sp = ( SigmaParam ) e.getKey();
            SigmaResult sr = ( SigmaResult ) e.getValue();

            //��������� ��������� - �� ��������� ��������� ���������
            if ( ! this.Conditions.contains( sp.Condition ) )
            {
                throw new IllegalArgumentException( "sp.Condition=" + sp.Condition.getTermName() + " �� ���������� � ��������� ���������." );
            }
            //������ �� ������� ������� - �� �������� ������� ��������
            if ( ! this.ABC.contains( sp.InChain ) && !Term.Z.equals( sp.InChain ) && !Term.EmptyString.equals( sp.InChain ) )
            {
                throw new IllegalArgumentException( "sp.InChain=" + sp.InChain.getTermName() + " �� ���������� �� ������� ��������." );
            }
            //������ �� ����� - �� �������� �����
            if ( ! this.L.contains( sp.InStack ) && !Term.Z.equals( sp.InStack ) && !Term.EmptyString.equals( sp.InStack ) )
            {
                throw new IllegalArgumentException( "sp.InStack=" + sp.InStack.getTermName() + " �� ���������� �� ������� ��������." );
            }


            //sr.RuleNumber - �� ���������...

            //����� ��������� - �� ��������� ���������
            if ( ! this.Conditions.contains( sr.Condition ) )
            {
                throw new IllegalArgumentException( "sr.Condition.Name=" + sr.Condition.getTermName() + " �� ���������� � ��������� ���������." );
            }

            //������ � ���� - �� L
            for( Term oTerm : sr.ToStack.getList() )
            {
                if ( ! this.L.contains( oTerm ) && !Term.Z.equals( oTerm ) && !Term.EmptyString.equals( oTerm ) )
                {
                    throw new IllegalArgumentException( oTerm.getTermName() + " �� ������-������� �� ���������� � �������� ��������." );
                }
            }

            //������ � �������� ������� - �� ��������� �������� ��������
            for ( Term oTerm : sr.ToChain.getList() )
            {
                if ( ! this.ABCout.contains( oTerm ) && !Term.Z.equals( oTerm ) && !Term.EmptyString.equals( oTerm ) )
                {
                    throw new IllegalArgumentException( oTerm.getTermName() + " �� ���������� � �������� ��������." );
                }
            }

        }

        IsCorrect = true;
        this.DebugInfo = null;

        return true;
    }

    /// <summary>
    /// �������� ���������� ��������
    /// </summary>
    /// <returns></returns>
    public boolean CheckCorrectVariables()
    {
        //private int IncomingStreamPosition;       //������� ������� �� ������� ������
        //�� ���������, ��� ��� ������ = 0
        /*if(this.IncomingStreamPosition < 0 )
            throw new ArgumentException("������� ������� �������� ������ 0!");

        if(this.IncomingStreamPosition > 0 )
        if(this.IncomingStreamPosition > this.IncomingStream.Count)
            throw new ArgumentException("������� ������� �������� � ������ ������� �������� ����� ����� ������� ������");
*/
        //private TermList IncomingStream;          //������� ������
        for ( int i = 0; i < this.IncomingStream.count(); i++ )
        {
            if ( ! this.ABC.contains( this.IncomingStream.get( i ) ) )
            {
                throw new IllegalArgumentException( "IncomingStream[" + i + "]='" + IncomingStream.get( i ) + "' �� ���������� �� ������� ��������" );
            }
        }

        //private Term CurState;                    //������� ��������� �������� (�� ��������� Q)
        //����������, ��� ��� ������ this.StartState
        //if(! this.Conditions.contains( this.CurState ))  throw new IllegalArgumentException("CurState[" + this.CurState.getTermName() + "] �� ���������� �� ��������� ������� ���������");

        //private Stack AutomateStack;              //���� ��������


        Iterator IE = this.AutomateStack.listIterator();

        while ( IE.hasNext() )
        {
            Term t = (Term) IE.next();
            if ( t != null )
            {
                if(! this.L.contains( t ) )
                    throw new IllegalArgumentException( "������ " + t.getTermName() + " �� ���������� � �������� �����" );
            }
        }

        return true;
    }


    /// <summary>
    /// ��������/������ XML-������������� ����������
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

            // Append IncomingStream - �������������� ��������
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

            // Append OutcomingStream - �������������� ��������
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
    /// ��������� ���������� ������ ����
    /// </summary>
    /// <returns></returns>
    public String WriteLogToStr()
    {
        StringBuffer stb = new StringBuffer();
        stb.append("���:");
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
            if( this.IncomingStreamPosition > this.IncomingStream.count() ) throw new Exception("������� �������� ������!");
            Term FromStream =  this.IncomingStream.get( this.IncomingStreamPosition );

            if(! this.ABC.contains( FromStream )) throw new IllegalArgumentException ("������ ������ �� ������� ����� �� �� �������� ��������!");
        }
        else throw new IllegalArgumentException("�� �������� �������� ������� ������!");

    }

    /// <summary>
    /// ��������� ������� � ��������� ��������� � ��������� ��� ������� ����� IncomingStream
    /// </summary>
    /// <param name="IncomingStream">����� ������� �����</param>
    public void Reset( TermList IncomingStream )
    {
        this.IncomingStream = IncomingStream;
        Reset();
    }

    /// <summary>
    /// ������� �������� � ��������� ���������
    /// </summary>
    public void Reset()
    {
        //�������������
        Log = new ArrayList<LogItem>();
        this.DebugInfo = "";
        this.AutomateStack = new Stack();
        this.CurStatus = ParseStatus.Intermediate;
        this.IncomingStreamPosition = 0;
        this.CurState = this.StartState;
        this.OutComingStream = new TermList();

        //��� ���������� ���������
        LogItem Li = new LogItem( this.CurState, this.IncomingStream, this.AutomateStack, 0, OutComingStream );
        this.Log.add( Li );

        CheckCorrectVariables();
    }


    /// <summary>
    /// ���������� ���������� ���� � ����������� ���������, � ������� �������
    /// ���� ������� ��������� - ������ ��� ��������� - ���������� ������� � ���������� ��������� �� ������
    /// </summary>
    /// <returns>���������, � ������� ������� �������</returns>
    public ParseStatus DoStep()
    {
        //�������� �� ������������
        if ( this.CurStatus == ParseStatus.Fail )
        {
            this.DebugInfo = "��������� �������� - ������! ������ ������� ��� ��� ��� ������� ��������� � ��������� ������!";
            return this.CurStatus;
        }

        if ( this.CurStatus == ParseStatus.Ok )
        {
            this.DebugInfo = "��������� �������� - ���������! ������ ������� ���!";
            return this.CurStatus;
        }

        if ( !this.IsCorrect )
        {
            return ParseStatus.Fail;
        }

        /*if(this.IncomingStreamPosition >= this.IncomingStream.count())
        {
            this.DebugInfo = "����� �� ���� ������� �����!";
            return ParseStatus.Fail;
        } */

        //��������� ���
        this.DoAtomicStep();

        //���������� � ����� ��������� �� ������
        return this.CurStatus;
    }

    /// <summary>
    /// ������ ��� ������� ������� �� ��������� ��������� ���������� �������� �� ������ �������
    /// </summary>
    /// <returns>�� ������ ��������� �����</returns>
    public ParseStatus Parse()
    {
        //�������� ������� ����������
        if ( IncomingStream.count() == 0 )
        {
            //��� ������ ?
            this.DebugInfo = "������� ������ ������!";
            return ParseStatus.Fail;
        }
        if ( ! this.IsCorrect )
        {
            //todo ��� ���
            this.DebugInfo = "������� ����������� ����� � �������� �� ����� ���� ���������!";
            return ParseStatus.Fail;
        }


        //�������� ����
        while ( this.CurStatus == ParseStatus.Intermediate )
        {
            this.DoAtomicStep();
        }

        //����������� ��� - �����???
        //this.DoAtomicStep();

        //LogItem Li = new LogItem(this.CurState,this.IncomingStream,this.AutomateStack,0,this.IncomingStreamPosition);
        //this.Log.Add( Li );

        //���������� ��������� ������
        return this.CurStatus;
    }

    /// <summary>
    /// ��������� ���, ������, ��� ��� ������� ������ ���������
    /// </summary>
    private void DoAtomicStep()
    {
        try
        {
            this.LastRuleNumber = 0;
            //��������� ���� ������� ���������
            Term FromStack = ( this.AutomateStack.size() <= 0 ) ? Term.Z : ( Term ) this.AutomateStack.pop();
            Term FromStream = ( this.IncomingStreamPosition < this.IncomingStream.count() ) ? this.IncomingStream.get( this.IncomingStreamPosition ) : new Term( Term.EmptyString.getTermName() );
            if ( !FromStream.isEps() )
            {
                this.IncomingStreamPosition++;
            }

            SigmaParam SParam = new SigmaParam( this.CurState, FromStream, FromStack );

            if ( SParam == null )
            {
                this.DebugInfo = "�� ������� ������� ���������!";
                this.CurStatus = ParseStatus.Fail;
                return;
            }

            //�������� ��������� ���
            SigmaResult SRes = ( SigmaResult ) SigmaFinction.get( SParam );

            if ( SRes == null )
            {
                //����� � �������-����
                FromStream = new Term( Term.EmptyString.getTermName() );
                SParam = new SigmaParam( this.CurState, FromStream, FromStack );

                if ( SParam == null )
                {
                    this.DebugInfo = "�� ������� ������� ��������� ��� ������� �����!";
                    this.CurStatus = ParseStatus.Fail;
                    return;
                }

                SRes = ( SigmaResult ) SigmaFinction.get( SParam );

                if(SRes == null)
                {
                    CheckEndState();

                    if(this.CurStatus == ParseStatus.Intermediate)
                    {
                        this.DebugInfo = "��� ����������� �����-������� � ����������� ";
                        this.CurStatus = ParseStatus.Fail;
                    }
                    return;
                }
                this.IncomingStreamPosition--;
            }

            //���������� ��������

            //������ ������� ���������
            this.CurState = SRes.Condition;

            //���������� � ���� ��� ����
            for ( int i = SRes.ToStack.count() - 1; i >= 0; i-- )
            {
                if ( !SRes.ToStack.get( i ).isEps() )
                {
                    this.AutomateStack.push( SRes.ToStack.get( i ) );
                }
            }
            //���������� �������� �������
            if(!SRes.ToChain.isEmpty() && !SRes.ToChain.getString().equals( "" ) && !SRes.ToChain.getString().equals( " " ))
                this.OutComingStream.AddTermsByString( SRes.ToChain.getString() );

            //���������� ���
            LogItem Li = new LogItem( this.CurState, this.IncomingStream, this.AutomateStack, SRes.RuleNumber, this.IncomingStreamPosition, this.OutComingStream );
            this.Log.add( Li );

//                CheckEndState();

            this.LastRuleNumber = SRes.RuleNumber;

        }
        catch ( Exception e )
        {
            this.DebugInfo = "�� ����� ������ ��������� ��������� ������: " + e.getMessage();
            this.CurStatus = ParseStatus.Fail;
        }
    }

    /// <summary>
    /// ���������, ��������� �� ������� � �������� ���������
    /// </summary>
    private void CheckEndState()
    {
        if ( this.EndStates.contains( this.CurState ) && this.IncomingStream.count() <= this.IncomingStreamPosition )
        {
            //� �������� ����� ���� ��� ������
            // ���� �����, ��� ���� ���� �� ��������� -
            // ���� ����� ������ � ��������� ���������� � ����������� this.L - � ��� ���� ������
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

