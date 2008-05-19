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
  printf("  п║я┐п©п╣я─ п©я┐п©п╣я─ п©я─п╬пЁя─п╟п╪п╪п╟ п©п╬ п·п·п╒ \n"
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
  CCity a("Санкт-Петербург",9000);
  CCity b("Москва",10000);
  CCity c("Бобруйск",1000);
  CCity d("Екатеринбург",1000);
  CCity e("Волгоград",1000);
  CCity f("Казань",1000);
  CCity g("Новгород",1000);
  CCity h("Пермь",1000);
  CCity i("Якутск",1000);
  CCity j("Йошкар-Ола",1000);
  CCity k("Воркута",1000);
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

