#include <getopt.h>
#include <unistd.h>
#include <QtGui/QApplication>
#include <QtGui/QLabel>
#include <QtCore/QTextCodec>
#include "MainWindow.h"

#include "DeliveryNetwork.h"

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
  net.addRoute(a,b,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(b,a,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(b,c,CEdgeParameters::TRUCK,100,1);
  net.addRoute(b,d,CEdgeParameters::TRUCK,100,1);
  net.addRoute(d,a,CEdgeParameters::TRUCK,100,1);
  net.addRoute(a,b,CEdgeParameters::TRAIN,100,1);
  net.addRoute(a,e,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(a,f,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(a,g,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(a,h,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(a,i,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(h,i,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(h,j,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(h,k,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(h,b,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(h,e,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(h,d,CEdgeParameters::AIRLINES,100,1);
  net.addRoute(h,a,CEdgeParameters::AIRLINES,100,1);
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
