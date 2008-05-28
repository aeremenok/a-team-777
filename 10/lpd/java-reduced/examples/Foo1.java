public class Foo1 
{
    int i;
    int foo(){
        i = 7;
        System.out.println(i);
    }
    //public static void main(String[] args){ new Foo1().main(); }
    
    public void main()
    {
        Foo1 f = new Foo1();
        int i = f.foo();

        System.out.println(i);
    }

}