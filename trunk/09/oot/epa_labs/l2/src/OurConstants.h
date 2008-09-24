//Definitions of constants

#ifndef OurConstants_h
#define OurConstants_h

   // Element type definitions
   // Each type value must be unique
   const WORD CSHAPE_SQUARE = 101U;
   const WORD CSHAPE_TEXT = 102U;
   const WORD CSHAPE_TEXT_IN_SQUARE = 103U;
   const WORD CSHAPE_DIAMOND = 104U;
   const WORD OTHER = 105U;
   ///////////////////////////////////

   // Color values for drawing
   const COLORREF BLACK = RGB(0,0,0);
   const COLORREF RED = RGB(255,0,0);
   const COLORREF SELECT_COLOR = RGB(255,0,180);
   ///////////////////////////////////
   // Program version number for use in serialization
   const UINT VERSION_NUMBER = 1;


#endif
