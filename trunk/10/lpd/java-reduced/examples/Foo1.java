public 
class Bar{
    void bar(){
        System.out.println("asdfas");
    }    
}

public class Foo1 extends Bar{
    void bar(){
        super.bar();//System.out.println("78978454");
    }    
    
    void foo(boolean d)
    {
    }

    //public static void main(String[] args){ new Foo1().main(); }
    
    public void main()
    {
        Foo1 foo1 = new Foo1();
        Foo1 foo2 = foo1;
        boolean b = foo1.equals(foo2);
        System.out.println(b);
    }

}