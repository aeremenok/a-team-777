// ShapeHandler.cpp: implementation of the ShapeHandler class.
//
//////////////////////////////////////////////////////////////////////
#include "stdafx.h"
#include "Sketcher.h"
#include "ShapeHandler.h"
//////////////////////////////////////////////////////////////////////////
#include "shapes/Rectangle.h"
#include "shapes/Text.h"
#include "shapes/Oval.h"
#include "shapes/TextInOval.h"
//////////////////////////////////////////////////////////////////////////
#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
//##ModelId=476EA08D0042
ShapeHandler::ShapeHandler(CSketcherView* view): _view(view)
{
    m_FirstPoint = CPoint(0,0);         // Set 1st recorded point to 0,0
    m_SecondPoint = CPoint(0,0);        // Set 2nd recorded point to 0,0
    m_pTempElement = NULL;              // Set temporary element pointer to 0

    m_pSelected = NULL;                 // No element selected initially
    m_MoveMode = FALSE;                 // Set move mode off

    m_CursorPos = CPoint(0,0);          // Initialize as zero
    m_FirstPos = CPoint(0,0);           // Initialize as zero

    _isGraphVisible = true;
    _ribble = NULL;
    _lastNearestRibbles = NULL;
    _firstVertex = NULL;

    m_Scale = 1;                        // Set scale to 1:1
}

//##ModelId=476EA08D0044
ShapeHandler::~ShapeHandler()
{
}

//##ModelId=476EA08D007D
void ShapeHandler::changeVertex()
{
    if (canProceed())
    { // переходим по ребру
        _firstVertex = _ribble->getAnotherVertex(_firstVertex);
        
        // подсвечиваем переход
        markHighlighted(_firstVertex);
        drawRibble(_ribble, GREEN);
        
        // уничтожаем список инцидентных ребер, т.к. перешли на новую вершину
        _lastNearestRibbles = NULL;
        _ribble = NULL;
    }
}

//##ModelId=476EA08D0072
void ShapeHandler::changeRibble( bool isNext )
{
    ExternalGraphIterator<CElement>* lastNearestRibbles = refreshNearestRibbles();
    // убираем подсветку с текущего ребра
    drawRibble(_ribble, GREEN);
    // двигаемся
    if (isNext)
    {
        if (lastNearestRibbles->hasNext())
        {
            _ribble = lastNearestRibbles->next();
        } 
        else
        {
            _ribble = lastNearestRibbles->first();
        }
    }
    else
    {
        if (lastNearestRibbles->hasPrevious())
        {
            _ribble = lastNearestRibbles->previous();
        } 
        else
        {
            _ribble = lastNearestRibbles->last();
        }
    }
    // подсвечиваем новое ребро
    drawRibble(_ribble, SELECT_COLOR);
}

//##ModelId=476EA08D003F
bool ShapeHandler::canProceed()
{
    if (_ribble == NULL)
    { // ребра нет - нужно подготовить
        //выбираем ребро для перемещения
        ExternalGraphIterator<CElement>* lastNearestRibbles = refreshNearestRibbles();
        
        if (lastNearestRibbles->getGraphRibbleCount() == 0)
        {   // начали обход из петли - перейти никуда не можем
            AfxMessageBox("Тупик!");
            return false;
        } 
        else if (lastNearestRibbles->getGraphRibbleCount() == 1)
        {   // ребро всего одно - по нему и переходим
            _ribble = lastNearestRibbles->next();
            drawRibble(_ribble, SELECT_COLOR);
            return true;
        }
        else
        {   // ребер много - нужно дождаться выбора
            return false;
        }
    }
    return true;
}

//##ModelId=476EA08D0033
void ShapeHandler::markHighlighted(CElement* pCurrentSelection )
{
    CClientDC aDC(_view);
    _view->OnPrepareDC(&aDC);
    CRect aRect;
    if(pCurrentSelection != m_pSelected)
    {
        if(m_pSelected)             // Old elemented selected?
        {                           // Yes, so draw it unselected
            aRect = m_pSelected->GetBoundRect(); // Get bounding rectangle
            aDC.LPtoDP(aRect);                   // Conv to device coords
            aRect.NormalizeRect();               // Normalize
            _view->InvalidateRect(aRect, FALSE);        // Invalidate area
        }
        
        m_pSelected = pCurrentSelection;        // Save elem under cursor
        
        if(m_pSelected)                         // Is there one?
        {                                       // Yes, so get it redrawn
            aRect = m_pSelected->GetBoundRect(); // Get bounding rectangle
            aDC.LPtoDP(aRect);                   // Conv to device coords
            aRect.NormalizeRect();               // Normalize
            _view->InvalidateRect(aRect, FALSE);        // Invalidate area
        }
    }
}

//##ModelId=476EA08D0040
ExternalGraphIterator<CElement>* ShapeHandler::refreshNearestRibbles()
{
    if (_lastNearestRibbles == NULL)
    {
        // готовим вершину для начала обхода
        if (_firstVertex == NULL)
        { // ничего не выбрано - начинаем обход с первой вершины
            Iterator<CElement>* iter = GetDocument()->getShapeContainer()->getNewIterator();
            Ribble<CElement>* firstRibble = iter->first();
            _firstVertex = firstRibble->get__vertex1();
            // вершина подготовлена - можно выбирать инцидентные ей ребра
        }
        markHighlighted(_firstVertex);
        _lastNearestRibbles = GetDocument()->getShapeContainer()->getNearestRibbles(_firstVertex);
    }
    return _lastNearestRibbles;
}

//##ModelId=476EA08D0030
void ShapeHandler::drawRibble( Ribble<CElement>* ribble, COLORREF aColor )
{
    if ( _isGraphVisible && ribble != NULL )
    {   // граф отображается - рисуем
        CPoint start = ribble->get__vertex1()->GetBoundRect().CenterPoint();
        CPoint end = ribble->get__vertex2()->GetBoundRect().CenterPoint();
        
        CLine* visibleRibble = new CLine(start, end, aColor);

        CClientDC pDC(_view);
        _view->OnPrepareDC(&pDC);
        visibleRibble->Draw(&pDC);
    }
}

//##ModelId=476EA08D005E
void ShapeHandler::onDraw( CDC* pDC )
{
    CSketcherDoc* pDoc = GetDocument();
    ASSERT_VALID(pDoc);
    
    CElement* pElement = NULL;
    Iterator<CElement>* iter = pDoc->getShapeContainer()->getNewIterator();
    
    while (iter->hasNext())
    {
        Ribble<CElement>* ribble = iter->next();
        
        pElement = ribble->get__vertex1();
        if(pDC->RectVisible(pElement->GetBoundRect()))
        {
            pElement->Draw(pDC, m_pSelected, _isGraphVisible);
        }
        
        pElement = ribble->get__vertex2();
        if(pDC->RectVisible(pElement->GetBoundRect()))
        {
            pElement->Draw(pDC, m_pSelected, _isGraphVisible);
        }
        
        drawRibble(ribble, GREEN);
    }
}

//##ModelId=476EA08D0060
void ShapeHandler::onLBDown( CPoint &point )
{
    if (!m_MoveMode && GetDocument()->getShapeContainer()->GetElementType()!=RIBBLE)
    {
        CClientDC aDC(_view);
        _view->OnPrepareDC(&aDC);
        aDC.DPtoLP(&point);                 // convert point to Logical
    }
    
    if(m_MoveMode)
    {
        // In moving mode, so drop the element
        m_MoveMode = FALSE;                 // Kill move mode
        m_pSelected = 0;                    // De-select the element
        GetDocument()->UpdateAllViews(0);   // Redraw all the views
    }
    else
    {
        if (GetDocument()->getShapeContainer()->GetElementType() == RIBBLE)
        {   // добавляем ребро
            CElement* currentSelection = SelectElement(point);
            if (currentSelection != NULL)
            {   // найдена начальная вершина
                CPoint* exactCenter = &(currentSelection->GetBoundRect().CenterPoint());
                m_FirstPoint = *exactCenter;
                // запоминаем ее
                m_pSelected = currentSelection;
                _view->SetCapture();                       // Capture subsequent mouse messages
            }
        }
        else
        {
            m_FirstPoint = point;               // Record the cursor position
            _view->SetCapture();                       // Capture subsequent mouse messages
        }
    }
}

//##ModelId=476EA08D0024
CElement* ShapeHandler::CreateElement()
{
    // Get a pointer to the document for this view
    CSketcherDoc* pDoc = GetDocument();
    ASSERT_VALID(pDoc);                  // Verify the pointer is good
    
    // Now select the element using the type stored in the document
    switch(pDoc->getShapeContainer()->GetElementType())
    {
        case RECTANGLE:
            return Rectangle2::create(m_FirstPoint, m_SecondPoint, pDoc->getShapeContainer()->GetElementColor());
        case TEXT:
            return Text::create(m_FirstPoint, m_SecondPoint, pDoc->getShapeContainer()->GetElementColor());
        case OVAL:
            return Oval::create(m_FirstPoint, m_SecondPoint, pDoc->getShapeContainer()->GetElementColor());
        case TEXT_IN_OVAL:
            return TextInOval::create(m_FirstPoint, m_SecondPoint, pDoc->getShapeContainer()->GetElementColor());
        //////////////////////////////////////////////////////////////////////////
        case RIBBLE:                  
            return new CLine(m_FirstPoint, m_SecondPoint, GREEN);
        //////////////////////////////////////////////////////////////////////////
        default:
            //	Something's gone wrong
            AfxMessageBox("Bad Element code", MB_OK);
            AfxAbort();
            return NULL;
    }
}

//##ModelId=476EA08D0025
CElement* ShapeHandler::SelectElement( CPoint aPoint)
{
    CClientDC aDC(_view);
    _view->OnPrepareDC(&aDC);

    // Convert parameter aPoint to logical coordinates
    aDC.DPtoLP(&aPoint);
    
    CSketcherDoc* pDoc = GetDocument();  // Get a pointer to the document
    CElement* pElement = NULL;  // Store an element pointer
    CRect aRect(0,0,0,0);       // Store a rectangle
    
    Iterator<CElement>* iter = pDoc->getShapeContainer()->getNewIterator();
    while (iter->hasNext())
    {
        Ribble<CElement>* ribble = iter->next();
        
        pElement = ribble->get__vertex1();
        aRect = pElement->GetBoundRect();
        if(aRect.PtInRect(aPoint))
            return pElement;
        
        pElement = ribble->get__vertex2();
        aRect = pElement->GetBoundRect();
        if(aRect.PtInRect(aPoint))
            return pElement;
    }
    
    return NULL;                              // No element found
}

//##ModelId=476EA08D002E
void ShapeHandler::MoveElement( CPoint& point )
{
    CClientDC aDC(_view);
    _view->OnPrepareDC(&aDC);
    
    CSize Distance = point - m_CursorPos;   // Get move distance
    m_CursorPos = point;          // Set current point as 1st for next time
    
    // If there is an element, selected, move it
    if(m_pSelected)
    {
        aDC.SetROP2(R2_NOTXORPEN);
        m_pSelected->Draw(&aDC,m_pSelected,_isGraphVisible); // Draw the element to erase it
        m_pSelected->Move(Distance);                        // Now move the element
        m_pSelected->Draw(&aDC,m_pSelected,_isGraphVisible); // Draw the moved element
    }
}

//##ModelId=476EA08D0062
void ShapeHandler::onLBUp( CPoint& point )
{
    if(_view == _view->GetCapture())
        ReleaseCapture();        // Stop capturing mouse messages

    // If there is an element, add it to the document
    if(m_pTempElement)
    {
        if (GetDocument()->getShapeContainer()->GetElementType()==RIBBLE)
        {   // соединяем ребрами
            CElement* secondVertex = SelectElement(point);
            if (secondVertex!=NULL)
            {   // найдена конечная вершина
                GetDocument()->getShapeContainer()->linkElements(m_pSelected, secondVertex);
            }
        } 
        else
        {   // добавляем новый элемент
            GetDocument()->getShapeContainer()->AddElement(m_pTempElement);
        }
        
        GetDocument()->UpdateAllViews(0,0,m_pTempElement);  // Tell all the views
        m_pTempElement = 0;        // Reset the element pointer
    }
}

//##ModelId=476EA08D006F
void ShapeHandler::onMMove( CPoint& point, bool flag )
{
    // Define a Device Context object for the view
    CClientDC aDC(_view);
    _view->OnPrepareDC(&aDC);            // Get origin adjusted

    if(m_MoveMode)
    { // передвигаем фигуру
        aDC.DPtoLP(&point);        // Convert to logical coordinatess
        MoveElement(point);   // Move the element
    }
    else if( flag && (_view == _view->GetCapture()))
    { // рисуем фигуру
        aDC.DPtoLP(&point);
        m_SecondPoint = point;     // Save the current cursor position
        
        if(m_pTempElement)
        { // фигура есть
            aDC.SetROP2(R2_NOTXORPEN);      // Set drawing mode
            m_pTempElement->Draw(&aDC);  
            m_pTempElement->resize(m_FirstPoint, m_SecondPoint);
        }
        else
        { // фигуры нет
            m_pTempElement = CreateElement();
        }
        m_pTempElement->Draw(&aDC);  
    }
    else
    { // ничего не двигаем и не рисуем, только подсвечиваем
        CElement* pCurrentSelection = SelectElement(point);
        markHighlighted(pCurrentSelection);
    }

}

//##ModelId=476EA08D0041
CSketcherDoc* ShapeHandler::GetDocument()
{
    return _view->GetDocument();
}

//##ModelId=476EA08D0058
void ShapeHandler::onMove()
{
    CClientDC aDC(_view);
    _view->OnPrepareDC(&aDC);       // Set up the device context
    
    GetCursorPos(&m_CursorPos);     // Get cursor position in screen coords
    _view->ScreenToClient(&m_CursorPos);   // Convert to client coords
    aDC.DPtoLP(&m_CursorPos);       // Convert to logical
    
    m_FirstPos = m_CursorPos;       // Remember first position
    m_MoveMode = TRUE;              // Start move mode
}

//##ModelId=476EA08D0064
void ShapeHandler::onRBDown( CPoint &point )
{
    if(m_MoveMode)
    {
        // In moving mode, so drop element back in original position
        CClientDC aDC(_view);
        _view->OnPrepareDC(&aDC);                  // Get origin adjusted

        MoveElement(point);       // Move element to orig position
        m_MoveMode = FALSE;                 // Kill move mode
        m_pSelected = NULL;                    // De-select element

        GetDocument()->UpdateAllViews(0);   // Redraw all the views
    }   
}

//##ModelId=476EA08D006D
void ShapeHandler::onRBUp( CPoint &point )
{
    // Create the cursor menu
    CMenu aMenu;
    aMenu.LoadMenu(IDR_CURSOR_MENU);    // Load the cursor menu
    _view->ClientToScreen(&point);             // Convert to screen coordinates
    
    // Display the pop-up at the cursor position
    if(m_pSelected)
    {
        aMenu.GetSubMenu(0)->TrackPopupMenu(TPM_LEFTALIGN|TPM_RIGHTBUTTON,
            point.x, point.y, _view);
    }
    else
    {
        // Check color menu items
        COLORREF Color = GetDocument()->getShapeContainer()->GetElementColor();
        aMenu.CheckMenuItem(ID_COLOR_BLACK,
            (BLACK==Color?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
        aMenu.CheckMenuItem(ID_COLOR_RED,
            (RED==Color?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
        
        // Check element menu items
        WORD ElementType = GetDocument()->getShapeContainer()->GetElementType();
        aMenu.CheckMenuItem(ID_ELEMENT_LINE,
            (RIBBLE==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);

        aMenu.CheckMenuItem(ID_ELEMENT_RECTANGLE,
            (RECTANGLE==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
        aMenu.CheckMenuItem(ID_ELEMENT_OVAL,
            (OVAL==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
        aMenu.CheckMenuItem(ID_ELEMENT_TEXT,
            (TEXT==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);
        aMenu.CheckMenuItem(ID_ELEMENT_TEXT_IN_OVAL,
            (TEXT_IN_OVAL==ElementType?MF_CHECKED:MF_UNCHECKED)|MF_BYCOMMAND);        
        // Display the context pop-up
        aMenu.GetSubMenu(1)->TrackPopupMenu(TPM_LEFTALIGN|TPM_RIGHTBUTTON, point.x, point.y, _view);
    }
}

//##ModelId=476EA08D005D
void ShapeHandler::onDelete()
{
    if(m_pSelected)
    {
        CSketcherDoc* pDoc = GetDocument();  // Get the document pointer
        pDoc->getShapeContainer()->DeleteElement(m_pSelected);    // Delete the element
        pDoc->UpdateAllViews(0);             // Redraw all the views
        m_pSelected = NULL;                  // Reset selected element ptr
    }
}
