//------ COMTEMPL.H ----------- �.�. ��������� ---- 10.10.98 -- 10.10.98 ----
//---------------------------------------------------------------------------
//            � � � � � � �   � � � � � �   � � � � � � � � � �
//---------------------------------------------------------------------------

#ifndef __COMTEMPL_H
#define __COMTEMPL_H

#undef min                     // ����������� ������ �������������� ��������!
#undef max
#undef range
#undef swap

template <class T>                // �������� ��� ���������� ����������
   inline void swap (T& a, T& b)  { register T c = a;   a = b;   b = c; }

template <class T>                // ������� �� ���� ��������
   inline T min (T a, T b) { return (a > b ? b : a); }

template <class T>                // ������� �� ���� ��������
   inline T max (T a, T b) { return (a > b ? a : b); }

template <class T>                // ������� �� ��� ��������
   inline T min (T a, T b, T c) { return (a > b ? min (b, c) : min (a, c)); }

template <class T>                // ������� �� ��� ��������
   inline T max (T a, T b, T c) { return (a > b ? max (a, c) : max (b, c)); }

template <class T>                // ���������� �������� ������ � �����
   inline T range (T lbound, T hbound, T a) // lbound <= retv <= hbound
      { return max (lbound, min (a, hbound)); }

#endif//ndef __COMTEMPL_H

