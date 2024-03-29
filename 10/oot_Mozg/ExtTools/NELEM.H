//-------- NELEM.H ------------ �.�. ��������� ---- 09.07.93 -- 09.10.98 ----
//---------------------------------------------------------------------------
//        ������ ������  "NELEM<T>"  ���������� ������ ���������,
//     �.�. "�����", ��� �� �������� ������ ����� ������� ������ "T",
//              � ��� ����� "T" ����� ������� "setNElem(int)"
//---------------------------------------------------------------------------

#ifndef  __NELEM_H
#define  __NELEM_H

//---------------------------------------------------------------------------
template <class T> class NELEM
   {
 public:
   int n;

       operator int() const { return n; }              // ����� ���������
       int operator() (void) const { return n; }       // ����� ���������

   int operator= (int newN)             // ���������� ����� ���������
       {
       ((T*)this)->setNElem (newN);
       return n;
	    }

   int operator= (NELEM& x)             // �������� ������������
       {
       ((T*)this)->setNElem (x.n);
       return n;
       }

   int operator++ (void)                // ��������� ����� ��������� �� 1
       {
       ((T*)this)->setNElem (n+1);      // ���������� ��������
       return n;
       }

   int operator++ (int)                 // ��������� ����� ��������� �� 1
       {
       ((T*)this)->setNElem (n+1);      // ����������� ��������
       return n-1;
       }

   int operator-- (void)                // ��������� ����� ��������� �� 1
       {
       if (--n <= 0)
          ((T*)this)->setNElem (n);     // ���������� ��������
       return n;
       }
 
   int operator-- (int)                 // ��������� ����� ��������� �� 1
       {
       if (--n <= 0)
          ((T*)this)->setNElem (n);     // ����������� ��������
       return n+1;
       }

   int operator+= (int dlt)             // ��������� ����� ��������� �� <dlt>
       {
       ((T*)this)->setNElem (n+dlt);
       return n;
       }

   int operator-= (int dlt)             // ��������� ����� ��������� �� <dlt>
       {
       ((T*)this)->setNElem (n-dlt);
       return n;
       }
   };

#endif//ndef __NELEM_H
