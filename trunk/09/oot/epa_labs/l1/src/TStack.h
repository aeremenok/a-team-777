#ifndef _TSTACK_H
#define _TSTACK_H

#include <ostream.h>
#include <string>
//using namespace std;

#define MAX_STACK 5

//////////////////////////////////////////////////////////////////////////
template<class T> class CItemStack{
	
private:

	T* item;
	
public:

	CItemStack(){
		item = NULL;
	};

	void set(T* i){
		item = i;
	};

	T* get(){
		return item;
	}

	void clear(){
		if(item == NULL) return;
		//delete item;
		item = NULL;
	}

};


template<class T> class TStack 
{
private:

	CItemStack<T>* stackstart;

	int cur; //for pop/push


    
    template<class T> class InternalIterator : public IIterator<T>
    {
    private:
        
		CItemStack<T>* stackstart;

		int cur;
        int max;

    public:
        
        virtual T* first()
        {
			cur = 0;
            return stackstart->get();
        }

        
        virtual T* next()
        {
            T* res =  stackstart[cur++].get();
            return res;
        }

		
        virtual bool hasNext()
        {
            return  ( cur < max );
        }

        
        InternalIterator(CItemStack<T>* stackstartNew, int m)
        {
            stackstart = stackstartNew;            
			cur = 0;
			max = m;
            cout<<"[InternalIterator] Created.\n";
        }
    };




public:
    
    void clear()
    {
        cout<<"\n[TStack] Clearing...\n";
        
		int i = cur;
		for(;i>0;i--)
		{
			stackstart[i].clear();
		}

        cout<<"[TStack] Cleared.\n";
    }

    void push(T* newE)
    {
		if(cur == MAX_STACK) throw new StackFullException("Stack is full!");
        cout<<"[TStack] Push element:" << (*newE);       
		
		stackstart[cur] . set(newE);
		cur++;
		cout<<"[TStack] Element pushed.\n";
    };

    
    T* pop()
    {
		if(cur == 0)  throw new StackEmptyException("Stack has no elements!");
        cout<<"[TStack] Pop element: " << (*(stackstart[cur-1] . get() ))<< endl ;
		cur--;
		return (stackstart[cur] . get());
    };

    // получить итератор для обхода графа
    IIterator<T>*  getIterator() const
    {
		IIterator<T>* t = new InternalIterator<T>(stackstart, cur);
        //return new InternalIterator<T>(stackstart, cur);
		return t;
    }

    TStack() 
    {
        stackstart = new  CItemStack<T> [MAX_STACK];
		cur = 0;
        cout<<"[TStack] Created.\n";
    };

	~TStack()
	{
		clear();
		cout<<"[TStack] Destroyed.\n";
	}

    friend ostream_withassign& operator<<(ostream_withassign& o, const TStack<T>& rhs);
};


template <class T> ostream_withassign& operator<<( ostream_withassign& o, const TStack<T>& rhs )
{
	o << endl << "[TStack] contains:" << endl;

    IIterator<T>* iter = new TIterator<T>(&rhs);
    int i = 0;
    while (iter->hasNext())
    {
        o<<"Element #"<<++i<<endl;
        T* current = iter->next();
        o << (*current) << endl;
    }
    if (i == 0)
    {
        o << "[TStack] nothing! " << endl;
    }else{
		o << "[TStack] ==== end." << endl;
	}
    return o;
}   

#endif 
