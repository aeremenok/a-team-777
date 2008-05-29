public class Foo1 
{
    //public static void main(String[] args){ new Foo1().main(); }
    Foo1 instance;
    int i;
  
    Foo1 get(){
        this.instance = this;
        i = 1;
        return instance;
    }
  
    public void main()
    {
        Foo1 f = new Foo1();
        System.out.println(f.get().i);
    }

}