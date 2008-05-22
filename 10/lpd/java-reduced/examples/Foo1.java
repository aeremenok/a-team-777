public class Foo1{
    int i;
    void bar(){}
    
    void foo(boolean c)
    {
        float i = 0.11;
        if(!c){
            System.out.println(i);
            i = (i + 0.1);
        }
        else
        {
            System.out.println("6325645asdhfdsha");
        }

    }

    //public static void main(String[] args){ new Foo1().main(); }
    public void main()
    {
        Foo1 foo = new Foo1();
        foo.foo(true);
    }

}