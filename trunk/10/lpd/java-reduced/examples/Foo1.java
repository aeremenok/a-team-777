public 
interface Bar2{
    void bar();
}
public 
interface Bar1 extends Bar2 {
    void bar();
}

public 
interface Bar extends Bar1{
    void bar();
}

public class A{
    void aaa(){
        System.out.println("aaa");
    }
}

public class Foo1 
extends A
implements Bar{
    void bar(){
        System.out.println("78978454");
    }    
    
    void foo(boolean d)
    {
        bar();
    }

    //public static void main(String[] args){ new Foo1().main(); }
    
    public void main()
    {
        Foo1 foo1 = new Foo1();
        A foo2 = foo1;
        boolean b = !foo1.equals(foo2);
        foo2.bar();
        foo2.aaa(); // xxx работает, а не должно. бага
        System.out.println(b);
    }

}