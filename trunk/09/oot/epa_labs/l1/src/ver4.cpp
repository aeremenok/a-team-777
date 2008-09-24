// ver4.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "conio.h"
#include "iostream.h"
#include "CShape.h"
#include "CSquare.h"
#include "CDiamond.h"
#include "CText.h"
#include "CTextInSquare.h"
#include "IIterator.h"
#include "TIterator.h"
#include "TStack.h"
#include "Exceptions.h"


/**
 * Simple figure test
 */
void runTestFig()
{
	
	CShape* c = new CSquare(10,10,1); 
	c->setX(5);
	c->setY(5);
	cout << endl << "[MAIN] 1. X,Y changed to 5 - checking:" << *c << endl << endl;

	CDiamond d = CDiamond(10,10,5,90);
	d.setL(20);
	cout << endl << "[MAIN] 2. L changed to 20 - checking d:" << d<< endl<< endl;

	CShape* c2 = new CTextInSquare("This is text!!!", 10,10,5);
	c2->setX(0);
	c2->setY(0);
	cout << endl << "[MAIN] 3. X,Y set to 0 - checking c2:" << *c2<< endl<< endl;

    CShape * c3 = c;
	if(c3 == c) cout << "[MAIN] 4. Test compare ok."<< endl; else cout << "[MAIN] 4. Test compare FAILED."<< endl;
	if(c2 == c) cout << "[MAIN] 5. Test compare FAILED."<< endl; else cout << "[MAIN] 5. Test compare ok."<< endl;

	
	cout << "[MAIN] ==== DELETING ==== "<< endl << "CSquare:";
	
	delete c;
	cout <<endl << "CTextInSquare:"; 
	delete c2;
}


void runStackTest(){

TStack<CShape>* myS = new TStack<CShape>();

CShape* c1 = NULL;
try
{
	c1 = myS->pop();
}
catch (StackEmptyException* e)
{
	cout<<"[MAIN] Got StackEmptyException!" <<endl;
    e->printException();
}

CShape*q1 = new CSquare(10,10,1);
myS->push(q1);
CShape*q2 = new CSquare(20,20,2);
myS->push(q2);
CShape*q3 = new CSquare(30,30,3);
myS->push(q3);
myS->push(new CSquare(40,40,4));
myS->push(new CSquare(50,50,5));

CShape* c = NULL;
try
{
	c = new CSquare(60,60,1);
    myS->push(c);
}
catch (StackFullException* e)
{
	cout<<"[MAIN] Got StackFullException!" <<endl;
    e->printException();
}
if(c != NULL) delete c;


c1 = myS->pop();
cout << "[MAIN] Pop shape is " << *c1 << endl;
delete c1;

c1 = myS->pop();
cout << "[MAIN] Pop shape is " << *c1 << endl;
delete c1;

myS->clear();
delete q1;
delete q2;
delete q3;
delete myS;
}

void printMainMenu(){
	cout<< endl << " ===== MAIN MENU ==== " << endl
		<< " 1 - simple figure test" << endl 
		<< " 2 - simple stack test" << endl 
		<< " 3 - manual stack test" << endl 
		<< " 4 - exit" << endl ;
}

void printManualMenu(){
	cout<< endl << " ===== MANUAL MENU ==== " << endl
		<< " 1 - PRINT " << endl 
		<< " 2 - POP " << endl 
		<< " 3 - PUSH " << endl 
		<< " 4 - CLEAR " << endl 
		<< " 5 - back to main menu " << endl ;
}


void runManualTests(){
	TStack<CShape>* myS = new TStack<CShape>();
	char h;
	
	do{
		printManualMenu();


		cin >> h;
		switch(h){

		case '1' : 
			cout << "[runManualTests] STACK is: "; cout << (*myS);
			
			break;

		case '2':
			{		
				cout<<"[runManualTests] POP:" <<endl;
				CShape* c1 = NULL;
				try
				{
					c1 = myS->pop();
				}
				catch (StackEmptyException* e)
				{
					cout<<"[runManualTests] Got StackEmptyException!" <<endl;
					e->printException();						
				}
				if(c1 != NULL) {
					cout<<"[runManualTests] GOT:" << c1 << endl << "[runManualTests] Deleting... "<< endl ;
					delete c1;
				} else {
					cout<<"[runManualTests] GOT nothing!" << endl ;
				}
			}
			
			
			break;

		case '3':
				cout<<"[runManualTests] PUSH:" <<endl;
				
				
				try
				{
					CShape* c = NULL;
					double x,y,l,u;
					std::string str1;
					char strbuf[BUFSIZ];
					char f;

					cout << endl << 
						" Creating CShape: " << endl <<
						" 1 - CSquare " << endl <<
						" 2 - CDiamond" << endl <<
						" 3 - CText   " << endl <<
						" 4 - CTextInSquare" << endl;
					cin >> f;

						
					switch(f){
						case '1' :
							cout << "Enter x,y,l";
							cin >> x >> y >> l;
							c = new CSquare(x,y,l);
							break;

						case '2' :
							cout << "Enter x,y,l,u";
							cin >> x >> y >> l >>u;
							c = new CDiamond(x,y,l,u);
							break;
												
						case '3' :
							cout << "Enter x,y,l";
							cin >> x >> y >> strbuf;
							str1 = ""; str1.append(strbuf);
							c = new CText(str1,x,y);
							break;
						
						case '4' :
							cout << "Enter str,x,y,l";						
							cin >> strbuf >> x >> y >> l;
							str1 = ""; str1.append(strbuf);
							c = new CTextInSquare(str1, x,y,l);
							break;
						default:
							cout << "Incorrect input!";
					};
						
					if(c != NULL) myS->push(c);
				}
				catch (StackFullException* e)
				{
					cout<<"[MAIN] Got StackFullException!" <<endl;
					e->printException();
				}
				
			break;
			
		case '4':

			myS -> clear();			
			
			break;

		case '5':
			break;

		default :
			cout << "Incorrect input!";
		
		}
	
	}while(h != '5');

}



int main(int argc, char* argv[])
{
	
	//
	
	
	char h;
	
	do{
		printMainMenu();


		cin >> h;
		switch(h){

		case '1' : 
			cout << "[MAIN] ==== TESTING SCHAPES ==== "<< endl<< endl;
			runTestFig();
			cout << endl << "[MAIN] ==== END TESTING SCHAPES ==== ";
			break;

		case '2':
			cout << "[MAIN] ==== TESTING STACK ==== "<< endl<< endl;
			runStackTest();
			cout << endl << "[MAIN] ==== END TESTING STACK ==== ";
			break;

		case '3':
			
			runManualTests();
			
			break;

		case '4':
			break;

		default :
			cout << "Incorrect input!";
		
		}

	
	}while(h != '4');

	return 0;
}