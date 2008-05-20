#include <getopt.h>
#include <unistd.h>
#include <QtGui/QApplication>
#include <QtGui/QLabel>
#include <QtCore/QTextCodec>
#include "MainWindow.h"

#include "DeliveryNetwork.h"

#include "AircraftLink.h"

void printUsage()
{
  printf("  Супер пупер программа по ООТ \n"
         "  -h  --help\n");
}

int main(int argc, char *argv[])
{
  option opts[]=
  {
    {"help",optional_argument,NULL,'h'},
    {0}
  };
  
  CDeliveryNetwork &net = CDeliveryNetwork::getInstance();
  CCity a("�����-���������",9000);
  CCity b("������",10000);
  CCity c("��������",1000);
  CCity d("������������",1000);
  CCity e("���������",1000);
  CCity f("������",1000);
  CCity g("��������",1000);
  CCity h("�����",1000);
  CCity i("������",1000);
  CCity j("������-���",1000);
  CCity k("�������",1000);
  CCity l("�����������",1000);
  CCity m("��������",1000);
  CCity n("������������",1000);
  CCity o("����",1000);
  CCity p("�����������",1000);
  CCity r("�����������-��-�����",1000);
  net.addCity(a);
  net.addCity(b);
  net.addCity(c);
  net.addCity(d);
  net.addCity(e);
  net.addCity(f);
  net.addCity(f);
  net.addCity(h);
  net.addCity(i);
  net.addCity(j);
  net.addCity(k);
  net.addCity(l);
  net.addCity(m);
  net.addCity(n);
  net.addCity(o);
  net.addCity(p);
  net.addCity(r);
  net.addRoute(a,b, new CPulkovoLink(100,1));
  net.addRoute(a,c, new CPulkovoLink(100,1));
  net.addRoute(a,d, new CPulkovoLink(100,1));
  net.addRoute(a,e, new CPulkovoLink(100,1));
  net.addRoute(a,f, new CPulkovoLink(100,1));
  net.addRoute(a,g, new CPulkovoLink(100,1));
  net.addRoute(f,h, new CPulkovoLink(100,1));
  net.addRoute(a,i, new CPulkovoLink(100,1));
  net.addRoute(a,j, new CPulkovoLink(100,1));
  net.addRoute(j,k, new CPulkovoLink(100,1));
  net.addRoute(d,h, new CPulkovoLink(100,1));
  net.addRoute(i,k, new CPulkovoLink(100,1));
  net.addRoute(i,b, new CPulkovoLink(100,1));
  net.addRoute(i,f, new CPulkovoLink(100,1));
  net.addRoute(i,e, new CPulkovoLink(100,1));
  net.addRoute(b,l, new CPulkovoLink(100,1));
  net.addRoute(b,m, new CPulkovoLink(100,1));
  net.addRoute(b,n, new CPulkovoLink(100,1));
  net.addRoute(b,o, new CPulkovoLink(100,1));
  net.addRoute(b,p, new CPulkovoLink(100,1));
  net.addRoute(b,r, new CPulkovoLink(100,1));
  net.addRoute(a,r, new CPulkovoLink(100,1));
  net.addRoute(f,p, new CPulkovoLink(100,1));
  net.addRoute(p,k, new CPulkovoLink(100,1));
  net.addRoute(d,n, new CPulkovoLink(100,1));
  net.addRoute(e,o, new CPulkovoLink(100,1));
  net.addRoute(i,e, new CPulkovoLink(100,1));
  net.addRoute(a,p, new CPulkovoLink(100,1));
  net.addRoute(b,p, new CPulkovoLink(100,1));
  net.addRoute(h,r, new CPulkovoLink(100,1));
  net.addRoute(d,m, new CPulkovoLink(100,1));
  int indx,opt; 

  const char *pConfig=NULL;

  while((opt=getopt_long(argc,argv,"hc:",opts,&indx))!=-1)
  {
    switch(opt)
    {
      case 'c':
      pConfig=optarg;
      break;

      case 'h':
      printUsage();
      return 0;
    }
  }

  

  QTextCodec *rusCodec = QTextCodec::codecForName("KOI8-R");
  QTextCodec::setCodecForTr(rusCodec);

  QApplication app(argc, argv);

  CMainWindow mainFrame;
  
  mainFrame.showMaximized();
  
  return app.exec();
}; 

