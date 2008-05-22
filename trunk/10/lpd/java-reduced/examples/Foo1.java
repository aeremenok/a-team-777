public class Foo1{
    int i;
    void bar(){}
    
    void foo()
    {
        int a;
        a = 0;
        i = 888;
        this.i = 0;
        Foo1 foo = new Foo1();
        a = (1 + ( 2 - this.i));
        this.bar();
        System.out.print("sdfasdf");
        // this.foo() = 0; ошибка        
    }

    //public static void main(String[] args){ new Foo1().main(); }
    public void main()
    {
        Foo1 foo = new Foo1();
        foo.foo();
    }

}