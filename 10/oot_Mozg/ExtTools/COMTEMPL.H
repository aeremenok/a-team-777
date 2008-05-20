//------ COMTEMPL.H ----------- О.Б. ПОЛУБАСОВ ---- 10.10.98 -- 10.10.98 ----
//---------------------------------------------------------------------------
//            Ш А Б Л О Н Ы   О Б Щ Е Г О   Н А З Н А Ч Е Н И Я
//---------------------------------------------------------------------------

#ifndef __COMTEMPL_H
#define __COMTEMPL_H

#undef min                     // мелкомягкие совсем бессовестность потеряли!
#undef max
#undef range
#undef swap

template <class T>                // обменять две переменные значениями
   inline void swap (T& a, T& b)  { register T c = a;   a = b;   b = c; }

template <class T>                // меньшее из двух значений
   inline T min (T a, T b) { return (a > b ? b : a); }

template <class T>                // большее из двух значений
   inline T max (T a, T b) { return (a > b ? a : b); }

template <class T>                // меньшее из трёх значений
   inline T min (T a, T b, T c) { return (a > b ? min (b, c) : min (a, c)); }

template <class T>                // большее из трёх значений
   inline T max (T a, T b, T c) { return (a > b ? max (a, c) : max (b, c)); }

template <class T>                // ограничить значение сверху и снизу
   inline T range (T lbound, T hbound, T a) // lbound <= retv <= hbound
      { return max (lbound, min (a, hbound)); }

#endif//ndef __COMTEMPL_H

