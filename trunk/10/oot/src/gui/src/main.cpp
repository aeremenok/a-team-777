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

