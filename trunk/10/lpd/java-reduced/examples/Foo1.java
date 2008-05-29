public class Foo1 
{
    //public static void main(String[] args){ new Foo1().main(); }
    Foo1 instance;
  
    Foo1 get(){
        instance = this;
        return instance;
    }
  
    public void main()
    {
        Foo1 f = new Foo1();
        System.out.println(f.get());
    }

}