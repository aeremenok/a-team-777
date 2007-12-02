/* ================================================================ */
/*                                                                  */
/* FILE : sampler.h                                                 */
/*                                                                  */
/* DESC : Программный мониторинг производительности программных     */
/*        средств.                                                  */
/*                                                                  */
/* PROJ : csa & students.                                           */
/*                                                                  */
/* CREATED  03-01-96 01:24pm by L&M.                                */
/* MODIFIED 30-02-98 02:28pm by M.S.                                */
/*          16.02.98 19:57   by L&M :: полный пересмотр и переделка */
/*          08.02.99 20:00   by A.V.                                */
/*                                                                  */
/* ---------------------------------------------------------------- */
/*                           (c) 1995-98 by L&M (Leonid Moiseichuk) */
/* ================================================================ */

#ifndef SAMPLER_H_USED
#define SAMPLER_H_USED
/* ---------------------------[ includes ]-------------------------- */

#include <dos.h>
#include <stdio.h>
#include <process.h>
#include "timestr.h"

/* ---------------------------[ defines ]-------------------------- */

   /* ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ */
   /*                                                        */
   /* Все метки с префиксом "__" - внутренние и могут  быть  */
   /* изменены (удалены) по усмотрению группы сопровождения. */
   /*                                                        */
   /* ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ */

#ifndef __CAST
#define __CAST(__type,__stat)    ((__type)(__stat))
#endif

#ifndef __UINT
#define __UINT(__stat)           __CAST(unsigned int,__stat)
#endif

#ifndef sINTID
#define sINTID    __UINT(0x65)
#endif /* sampler interrupt number */

#ifndef sMAGIC
#define sMAGIC    __UINT(0x55)
#endif /* sampler magic code */

#ifndef sVERSION
#define sVERSION  "1.55"
#endif

#ifndef sPRECISE
#define sPRECISE  5
#endif /* Ограничение точности времени коррекции в минитиках */

#ifndef __NOP
#define __NOP     ((void)0)
#endif /* No Operation in C/C++ */

#ifndef __IRET
#define __IRET    __CAST(unsigned char,0xCF)
#endif

#ifndef __IRETD
#define __IRETD  __CAST(unsigned short,0xCF66)
#endif

#ifdef __cplusplus
   #define __CONST const
   #define __REGS  REGS
#else
   #define __CONST
   #define __REGS  union REGS
#endif

/* Previos version implementation */
#ifdef TIMING_OFF
   #ifndef NSAMPLER
      #define NSAMPLER
   #endif
#endif

#define SamplerInterrupt  sINTID
#define SamplerUniqueCode sMAGIC

#ifdef NSAMPLER
   #ifndef TIMING_OFF
      #define TIMING_OFF
   #endif
#endif

#ifdef NSAMPLER
   #define SAMPLE    __NOP
   #define TIMING    __NOP
#else
   /* см ниже */
#endif

#ifndef UINT8
typedef unsigned char UINT8 ;
#endif

#ifndef UINT16
typedef unsigned int UINT16 ;
#endif

#ifndef UINT32
typedef unsigned long UINT32 ;
#endif

#ifndef Tscl
typedef unsigned long Tscl ;
#endif

#ifndef FAST
#define FAST register
#endif

/* -------------------- Summary --------------------
   Время состоит из двух частей :
    word - количество тиков стандартного таймера ;
    word - количество тиков на аппаратном канале
           (частота ў 1 192 737 Hz);
   ------------------ End Summary ------------------ */

#ifndef TIME
typedef Time    TIME ;
#endif

#ifndef LPTIME
typedef TIME far* LPTIME;
#endif

#ifndef NTIME
   #define NTIME  __CAST(TIME,0)
   #define NTime  NTIME
#endif

#ifndef LPSZ
typedef __CONST char far* LPSZ ;
#endif

/* =======================[ Sampler messages ]=============================== */

/* sampler commands */
const UINT16   scmdMeasure    = 0x01 ; /* команда проведения измерения              */
const UINT16   scmdEnterTime  = 0x02 ; /* команда запроса указателя на время входа  */
const UINT16   scmdEscapeTime = 0x03 ; /* команда запроса указателя на время выхода */
const UINT16   scmdPrecise    = 0x04 ; /* команда задания точного времени коррекции */
const UINT16   scmdVersion    = 0x05 ; /* команда запроса версии Sampler'a          */
const UINT16   scmdScale      = 0x06 ; /* команда задания масштаба коррекции        */

/* -------------------- Базовое сообщение Sampler'a -------------------- */
struct TSamplerMessage
{
   /* navigation */
   UINT16   m_Code ; /* MAGIC-код для верификации */
   UINT16   m_Size ; /* SizeOf() сообщения        */
   UINT16   m_What ; /* Код команды               */

   TSamplerMessage();
   TSamplerMessage(const UINT16 /*What*/);
   int Throw();   /* Обработка сообщения и возврат ненулевого значения */

}; /* TSamplerMessage */

typedef TSamplerMessage far* PSamplerMessage;


inline TSamplerMessage :: TSamplerMessage()
{
   m_Code = 0 ;
   m_Size = sizeof(TSamplerMessage);
   m_What = 0 ;
}

inline TSamplerMessage :: TSamplerMessage(const UINT16 What)
{
   m_Code = 0 ;
   m_Size = sizeof(TSamplerMessage);
   m_What = What ;
}

inline int TSamplerMessage :: Throw()
{
   __REGS regs ;

   m_Code    = sMAGIC ;
   regs.x.di = FP_SEG(this);
   regs.x.si = FP_OFF(this);
   int86(sINTID,&regs,&regs);

   return (m_Code == (~sMAGIC)) ;
}

/* ------------------- Сообщение РегистрацияИзмерения -------------------- */

struct TmsgMeasure : TSamplerMessage
{
   TmsgMeasure(const char* /*szFile*/, const UINT16 /*iLine*/,const TIME /*tmStep*/);

   LPSZ     m_File ;    /* Указатель на имя файла */
   UINT16   m_Line ;    /* Номер строки в файле   */
   TIME     m_Time ;    /* Интервал времени       */
};

inline TmsgMeasure :: TmsgMeasure(const char* szFile, const UINT16 iLine, const TIME tmStep)
   : TSamplerMessage(scmdMeasure)
{
   m_Size = sizeof(TmsgMeasure);
   m_File = __CAST(LPSZ,szFile);
   m_Line = iLine  ;
   m_Time = tmStep ;
}

/* ------------------- Сообщение ЗапросВремени -------------------- */

struct TmsgTimeQuery : public TSamplerMessage
{
   TmsgTimeQuery(const UINT16);
   LPTIME   FromTime();

   LPTIME   p_Time ;    /* указатель на соответвующую переменную */
};

inline TmsgTimeQuery :: TmsgTimeQuery(const UINT16 What)
   : TSamplerMessage(What)
{
   m_Size = sizeof(TmsgTimeQuery);
   p_Time = NULL ;
}

inline LPTIME TmsgTimeQuery :: FromTime()
{
   p_Time = NULL ;
   return (Throw() ? p_Time : NULL);
}


struct TmsgEnterTime : public TmsgTimeQuery
{
   TmsgEnterTime()
      : TmsgTimeQuery(scmdEnterTime)
   {}
};

struct TmsgEscapeTime : public TmsgTimeQuery
{
   TmsgEscapeTime()
      : TmsgTimeQuery(scmdEscapeTime)
   {}
};

/* ------------------- Сообщение ТочнаяНастройка -------------------- */

struct TmsgPrecise : TSamplerMessage
{
   TmsgPrecise(const TIME);

   TIME m_Time ;  /* Значение времени для точной настройки */
};

inline TmsgPrecise :: TmsgPrecise(const TIME tmPrec)
   : TSamplerMessage(scmdPrecise)
{
   m_Size = sizeof(TmsgPrecise);
   m_Time = tmPrec ;
}

/* ------------------- Сообщение ЗапросВерсии -------------------- */

struct TmsgVersion : TSamplerMessage
{
   TmsgVersion();

   LPSZ     m_Version ; /* Указатель на версию */
};

inline TmsgVersion :: TmsgVersion()
   : TSamplerMessage(scmdVersion)
{
   m_Size   = sizeof(TmsgVersion);
   m_Version= sVERSION ;
}

/* ------------------- Сообщение МасштабнаяНастройка -------------------- */

struct TmsgScale : TSamplerMessage
{
   TmsgScale(const Tscl);

   Tscl m_Scale ;  /* Значение времени для точной настройки */
};

inline TmsgScale :: TmsgScale(const Tscl tmScale)
   : TSamplerMessage(scmdScale)
{
   m_Size = sizeof(TmsgScale);
   m_Scale = tmScale ;
}

/* ===========[ Только для измерения в программах пользователя ]============ */

#ifndef NSAMPLER

#ifdef P5Type
    #ifdef PrType
        #undef P5Type
    #endif
#else
    #define PrType
#endif

/* ==========================[ methods ]============================== */

static TIME Now()
{
#ifdef PrType
   static __CONST UINT16 far* lpSystemTimer = __CAST(__CONST UINT16 far*,MK_FP(0x0000u,0x046Cu)) ;
   static UINT8 now[8], StOut;

   /* берем старшее слово показаний из младшего слова счетчика времени суток */
   *__CAST(UINT16*,&now[2]) = *lpSystemTimer ;
   *__CAST(UINT32*,&now[4]) = 0 ;
   /* чтение времени в fastnow */

   outportb(0x43,0xC2);    /* команда чтения счетчика       */
   StOut  = inportb(0x40);
   now[0] = inportb(0x40); /* младший байт счетчика         */
   now[1] = inportb(0x40); /* старший байт счетчика         */

   /* вычисляем правильное время по остатку счетчика */
   *__CAST(UINT16*,now) = ((0U - *__CAST(UINT16*,now))>>1)+(StOut&0x80 ? 0:0x8000);

   return (*__CAST(TIME*,now)) ;
#else
   static TIME nw, far* now=&nw;
   asm{
    db 66h; push AX
    db 66h; push DX
            push DS
            push SI
            lds  si,now
    db 0Fh,31h                      //  rdtsc
    db 66h; mov  [si],AX
    db 66h; mov  [si][4],DX
            pop  SI
            pop  DS
    db 66h; pop  DX
    db 66h; pop  AX
   }
   return nw;
#endif
}

struct TRemoteSampler
{
   public :

      TRemoteSampler();    /* конструктор объекта :)     */

      void OnEnter();      /* регистрация момента входа  */
      void OnEscape();     /* регистрация момента выхода */

      TIME EnterTime()  const ;  /* значение момента входа в секцию измерения    */
      TIME EscapeTime() const ;  /* значение момента выхода из секцию измерения  */
      TIME WalkTime()   const ;  /* продолжительность пребывания в секции измер. */

      int  Enable() const ;      /* разрешено измерение ? */
      void Enable(int) ;         /* разрешение/запрещение */

      void Break(const char*,const UINT16) ;
         /* проводим измерение с указанной строкой */

   private:

      int      m_Enable ;  /* не 0 если возможны измерения времени*/
      LPTIME   m_Enter  ;  /* время входа в процедуру измерения   */
      LPTIME   m_Escape ;  /* время выхода из процедуры измерения */

      friend void __attach_sampler(TRemoteSampler*);

}; /* TRemoteSampler */

/* -------------------- Функциии обеспечения -------------------- */

inline void TRemoteSampler :: OnEnter()
{
   const TIME tmFastTemp = Now();

   if (/* m_Enable &&*/ m_Enter )
      *m_Enter = tmFastTemp ;
}

inline void TRemoteSampler :: OnEscape()
{
   const TIME tmFastTemp = Now();

   if (/*m_Enable && */m_Escape)
      *m_Escape = tmFastTemp ;
}

inline TIME TRemoteSampler :: EnterTime() const
{
   return (m_Enter ? Time(m_Enter->dTh, m_Enter->dTl) : NTIME);
}

inline TIME TRemoteSampler :: EscapeTime() const
{
   return (m_Escape ? Time(m_Escape->dTh, m_Escape->dTl) : NTIME);
}

inline TIME TRemoteSampler :: WalkTime() const
{
   return (EnterTime()-EscapeTime()) ;
   /* WARNING : EnterTime()   -> время текущего входа    */
   /*           EscapeTime()  -> время предыдущего выхода*/
}

inline int TRemoteSampler :: Enable() const
{
   return m_Enable ;
}

inline void TRemoteSampler :: Enable(int iEnable)
{
   m_Enable = (iEnable != 0) ;
}

inline void TRemoteSampler :: Break(const char* szModule,const UINT16 iLine)
{
   /* входим в процедуру измерения */
   OnEnter() ;

   /* оформляем данные и вызываем Sampler */
   if (Enable())
   {
      TmsgMeasure QMeasure(szModule,iLine,WalkTime());

      if ( !QMeasure.Throw() )  /* Сдох SAMPLER ? */
         Enable(0);
   }

   /* выходим из процедуры измерения */
   OnEscape();
}

static void __show_error(const char* str)
{
   fprintf(stderr,"\n%s",str);
   exit(1);
}

static void __check_sampler()
{
#define __V(__t,__v)    (*__CAST(__CONST unsigned __t far*,__v))
   __CONST void far* pVector = __CAST(__CONST void far*,_dos_getvect(sINTID));
   TmsgVersion QVersion ;

   if (pVector == 0 || __V(char,pVector) == __IRET || __V(short,pVector) == __IRETD || !QVersion.Throw())
      __show_error("SAMPLER не установлен !");

   if (QVersion.m_Version == NULL)
   {
      fprintf (stderr,"\nТребуется SAMPLER %s и выше !",sVERSION);
      exit(1);
   }

#undef __V
} /* __check_sampler */

void __throw_sampler(const char* /*szModule*/,int /*iLine*/);

static void __solve_true_time(TRemoteSampler* pSampler,int nTimes)
{
   TIME tmBeg ;      /* время начала события */
   TIME tmEnd ;      /* время конца  события */
   TIME tmDelta ;    /* длительность события */
   TIME tmMinNow(-1);  /* минимальная задержка для Now        */
   TIME tmMinMeas(-1); /* минимальная задержка для измерения  */

   pSampler->Enable(0) ;

   for (int idx=0; idx < nTimes; idx ++)
   {
      /* погрешность функции измерения времени - теоретически маленькая */
      tmBeg = Now() ;
      tmEnd = Now() ;
      tmDelta = tmEnd - tmBeg;
      if (tmDelta < tmMinNow)
         tmMinNow = tmDelta ;

      /* погрешность функции измерения отсчета профиля - теоретически большая */
      tmBeg = Now() ;
      __throw_sampler(NULL,0) ;
      tmEnd = Now() ;
      tmDelta = (tmEnd - tmBeg) - (pSampler->EscapeTime() - pSampler->EnterTime());
      if (tmDelta < tmMinMeas )
         tmMinMeas = tmDelta ;
   }

   /* вызов для задания времени коррекции для программы на С++ */
   if (tmMinNow < tmMinMeas)
      tmDelta = tmMinMeas - tmMinNow ;
   else
      tmDelta = tmMinNow ;
      /* >>> влияние кэш-памяти и шустрый CpU - пограничный режим */
      /* <<< берем сумму задержек,  так как она позволит остаться */
      /*     хотя бы в порядке погрешности.                       */

   TmsgPrecise QPrecise(tmDelta);

   if ( !QPrecise.Throw() )
      __show_error("Не могу переслать время коррекции.");

   pSampler->Enable(1) ;
} /* __solve_true_time */

static void __solve_corr_scale()
{
   Tscl tmDelta;           /* масштаб */
#ifdef PrType
   asm{
    db 66h; mov word ptr [tmDelta], 34DCh; dw 12h
    };
//   tmDelta=1193180;
#else
   UINT32 Keep_EAX, Keep_EDX;
   asm{
    db 66h; push AX
    db 66h; push DX
            push ES
            push BX
            push CX

            xor  AX,AX
            mov  ES,AX
            mov  BX,046Ch
            mov  CX,ES:[BX]
            inc  CX}
   T1:asm{  cmp  CX,ES:[BX]
            ja   T1

    db 0Fh,31h                        // RDTSC
    db 66h; mov  word ptr Keep_EAX,AX
    db 66h; mov  word ptr Keep_EDX,DX
            inc  CX}
   T2:asm{  cmp  CX,ES:[BX]
            ja   T2

    db 0Fh,31h                        // RDTSC
    db 66h; sub  AX,word ptr Keep_EAX
    db 66h; sbb  DX,word ptr Keep_EDX

    db 66h; mov  BX,34DCh; dw 12h    // 1193180 Гц
    db 66h; mul  BX
            mov  AX,DX
    db 66h; ror  AX,16
    db 66h; mov  word ptr [tmDelta],AX

            pop  CX
            pop  BX
            pop  ES
    db 66h; pop  DX
    db 66h; pop  AX
   }
   printf("\nCPU=%lu",tmDelta);
#endif

   TmsgScale QScale(tmDelta);

   if ( !QScale.Throw() )
      __show_error("Не могу переслать масштаб коррекции.");
} /* __solve_corr_scale */

static void __attach_sampler(TRemoteSampler* pSampler)
{
   TmsgEnterTime  QEnter ;
   TmsgEscapeTime QEscape;

   pSampler -> m_Enter = QEnter.FromTime();
   pSampler -> m_Escape= QEscape.FromTime();

   if (pSampler -> m_Enter == NULL || pSampler->m_Escape == NULL)
      __show_error("Не могу связаться с SAMPLER'ом.");
}

#ifdef P5Type

static void __detect_rdtsc()
{
  int trubl=-1;
  asm{
     db 66h; push AX
     db 66h; push BX
     db 66h; push CX
     db 66h; push DX
     db 66h; pushf               // pushfd
     db 66h; pop  AX
     db 66h; or   AX,0h; dw 20h  // 200000h
     db 66h; push AX
     db 66h; popf                // popfd
     db 66h; pushf               // pushfd
     db 66h; pop  AX
     db 66h; test AX,0h; dw 20h  // 200000h
             jz   exit           // 1.CPUID not detect (bit 21 EFLAG)

     db 66h; xor  AX,AX
     db 0Fh,0A2h                 // CPUID
             cmp  AX,1h
             jb   exit
     db 66h; xor  AX,AX; inc AX
     db 0Fh,0A2h                 // CPUID
             test DX,10h
             jz   exit           // 2.RDTSC not detect  (bit 4 CPUID2)

             smsw AX             // mov  EAX,CR0.low
             test AX,1h
             jz   good           // 3.Protected Mode not detect(bit 0 CR0)
             mov  trubl,3
             jmp  exit
  };
good:
  trubl=0;
exit:
  asm{
     db 66h; pop  DX
     db 66h; pop  CX
     db 66h; pop  BX
     db 66h; pop  AX
  }

  if(trubl)
  {
    if(trubl==3)
       __show_error("Запущены программы защищенного режима (emm386, Windows, etc.).");
    if(trubl<0)
       __show_error("Класс процессора должен быть не ниже P5.");
  }
}

#endif   // P5Type

inline TRemoteSampler :: TRemoteSampler ()
{
   m_Enable = 0 ;
   m_Enter  = NULL ;
   m_Escape = NULL ;

   /* проверка присутствия Sampler'a */
   __check_sampler();
   /* цепляемся к Sampler'у */
   __attach_sampler(this);
#ifdef P5Type
   /* проверка возможности применения П. инструкций */
   __detect_rdtsc();
#endif
   /* вычисляем время коррекции Sampler'a */
   __solve_true_time(this,100);
   /* масштабирующий параметр*/
   __solve_corr_scale();
} /* TRemoteSampler */

/* -------------------- Измерение отсчета времени -------------------- */

/***********************************************/
#ifndef EXCLUDE_IMPLEMENTATION
   TRemoteSampler  __RemoteSampler;
#else
   extern TRemoteSampler  __RemoteSampler;
#endif
/*** Обработчик данных и связь с Sampler'ом ***/

static void __throw_sampler(const char* szModule,int iLine)
{
   __RemoteSampler.Break(szModule,iLine);
}

#define SAMPLE    __throw_sampler(__FILE__,__LINE__)
#define TIMING    __throw_sampler(__FILE__,__LINE__)

#endif // NSAMPLER

#endif // Sentry SAMPLER_H_USED
