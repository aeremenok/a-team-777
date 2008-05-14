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
  net.addRoute(a,b,CCostType::AIRLINES,100);
  net.addRoute(b,c,CCostType::TRUCK,100);
  net.addRoute(b,d,CCostType::TRUCK,100);
  net.addRoute(e,a,CCostType::TRUCK,100);
  net.addRoute(d,f,CCostType::TRUCK,100);
  net.addRoute(f,h,CCostType::TRUCK,100);
  net.addRoute(h,i,CCostType::TRUCK,100);
  net.addRoute(i,j,CCostType::TRUCK,100);
  net.addRoute(j,e,CCostType::TRUCK,100);
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

