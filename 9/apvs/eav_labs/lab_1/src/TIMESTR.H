/* ================================================================ */
/*                                                                  */
/* FILE : sampler.h                                                 */
/*                                                                  */
/* CREATED  05.02.99 10:00   by A.V.                                */
/*                                                                  */
/* ================================================================ */

#ifndef __TimeStr_H
#define __TimeStr_H

#ifndef UINT8
typedef unsigned char UINT8;
#endif

#ifndef UINT16
typedef unsigned int UINT16;
#endif

#ifndef UINT32
typedef unsigned long UINT32;
#endif

class Time {

public:
    UINT32 dTl, dTh;

    // constructors
    Time() {};
    Time(UINT32 t) { dTl=t; dTh=0; }
    Time(UINT32 th, UINT32 tl) { dTl=tl; dTh=th; };

    // Binary Operator Functions
    friend Time operator+(const Time &, const Time &);
    friend Time operator-(const Time &, const Time &);
    friend int operator==(const Time &, const Time &);
    friend int operator!=(const Time &, const Time &);
    friend int operator>=(const Time &, const Time &);
    friend int operator<=(const Time &, const Time &);
    friend int operator>(const Time &, const Time &);
    friend int operator<(const Time &, const Time &);
    Time & operator+=(const Time &);

    operator double() const { return (double(dTl)+double(dTh)*0x100*0x1000*0x1000); };
    operator UINT32() const { return dTl; };
};

// Definitions of compound-assignment operator member functions

inline Time& Time::operator+=(const Time& t)
{
    dTl+= t.dTl;
    dTh+= !!(_FLAGS&0x1) + t.dTh;
    return *this;
}

// Definitions of non-member binary operator functions

inline Time operator+(const Time& a, const Time& b)
{
    UINT32 tl= a.dTl + b.dTl; UINT32 th= (_FLAGS&0x1) + a.dTh + b.dTh;
    return Time(th, tl);
}

inline Time operator-(const Time& a, const Time& b)
{
    UINT32 tl= a.dTl - b.dTl;
    UINT32 th= a.dTh - b.dTh - (a.dTl < b.dTl);
    return Time( th, tl );
}

inline int operator==(const Time& a, const Time& b)
{
    return ( (a.dTh == b.dTh) && (a.dTl == b.dTl) );
}

inline int operator!=(const Time& a, const Time& b)
{
    return ( (a.dTh != b.dTh) || (a.dTl != b.dTl) );
}

inline int operator>=(const Time& a, const Time& b)
{
    return ( (a.dTh == b.dTh) ? (a.dTl >= b.dTl) : (a.dTh >= b.dTh) );
}

inline int operator<=(const Time& a, const Time& b)
{
    return ( (a.dTh == b.dTh) ? (a.dTl <= b.dTl) : (a.dTh <= b.dTh) );
}

inline int operator>(const Time& a, const Time& b)
{
    return ( (a.dTh == b.dTh) ? (a.dTl > b.dTl) : (a.dTh > b.dTh) );
}

inline int operator<(const Time& a, const Time& b)
{
    return ( (a.dTh == b.dTh) ? (a.dTl < b.dTl) : (a.dTh < b.dTh) );
}

#endif  // __TimeStr_H
