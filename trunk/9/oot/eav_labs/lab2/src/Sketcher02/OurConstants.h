//Definitions of constants

#ifndef OurConstants_h
#define OurConstants_h

   // Element type definitions
   // Each type value must be unique
   const WORD LINE = 101U;
   const WORD RECTANGLE = 102U;
   const WORD OVAL = 103U;
   const WORD TEXT = 104U;
   const WORD TEXT_IN_OVAL = 105U;
   ///////////////////////////////////

   // Color values for drawing
   const COLORREF BLACK = RGB(0,0,0);
   const COLORREF RED = RGB(255,0,0);
   const COLORREF SELECT_COLOR = RGB(255,0,180);
   const COLORREF GREEN = RGB(0, 255, 0);
   ///////////////////////////////////

   // Program version number for use in serialization
   const UINT VERSION_NUMBER = 1;
#endif
