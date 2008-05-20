//-------- NELEM.H ------------ О.Б. ПОЛУБАСОВ ---- 09.07.93 -- 09.10.98 ----
//---------------------------------------------------------------------------
//        ОБЪЕКТ КЛАССА  "NELEM<T>"  ИСПОЛЬЗУЕТ ЗНАНИЕ КОНТЕКСТА,
//     Т.К. "ЗНАЕТ", ЧТО ОН ЯВЛЯЕТСЯ ПЕРВЫМ ПОЛЕМ ОБЪЕКТА КЛАССА "T",
//              И ЧТО КЛАСС "T" ИМЕЕТ ФУНКЦИЮ "setNElem(int)"
//---------------------------------------------------------------------------

#ifndef  __NELEM_H
#define  __NELEM_H

//---------------------------------------------------------------------------
template <class T> class NELEM
   {
 public:
   int n;

       operator int() const { return n; }              // число элементов
       int operator() (void) const { return n; }       // число элементов

   int operator= (int newN)             // установить число элементов
       {
       ((T*)this)->setNElem (newN);
       return n;
	    }

   int operator= (NELEM& x)             // оператор присваивания
       {
       ((T*)this)->setNElem (x.n);
       return n;
       }

   int operator++ (void)                // увеличить число элементов на 1
       {
       ((T*)this)->setNElem (n+1);      // префиксная операция
       return n;
       }

   int operator++ (int)                 // увеличить число элементов на 1
       {
       ((T*)this)->setNElem (n+1);      // постфиксная операция
       return n-1;
       }

   int operator-- (void)                // уменьшить число элементов на 1
       {
       if (--n <= 0)
          ((T*)this)->setNElem (n);     // префиксная операция
       return n;
       }
 
   int operator-- (int)                 // уменьшить число элементов на 1
       {
       if (--n <= 0)
          ((T*)this)->setNElem (n);     // постфиксная операция
       return n+1;
       }

   int operator+= (int dlt)             // увеличить число элементов на <dlt>
       {
       ((T*)this)->setNElem (n+dlt);
       return n;
       }

   int operator-= (int dlt)             // уменьшить число элементов на <dlt>
       {
       ((T*)this)->setNElem (n-dlt);
       return n;
       }
   };

#endif//ndef __NELEM_H
