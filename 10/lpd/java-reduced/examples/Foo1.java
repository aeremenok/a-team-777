public class Foo1{
    int i;
    void bar(){}
    
    void foo(boolean c)
    {
        int i = 0;
        while(c){
            System.out.println(i);
            i = (i + 1);
        }

    }

    //public static void main(String[] args){ new Foo1().main(); }
    public void main()
    {
        Foo1 foo = new Foo1();
        foo.foo(true);
    }

}