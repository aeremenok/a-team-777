public class Foo1 
{
    int foo(){
        int i = 0;
        return 0;
    }
    //public static void main(String[] args){ new Foo1().main(); }
    
    public void main()
    {
        Foo1 f = new Foo1();
        int b = f.foo();

        System.out.println(b);
    }

}