public class Foo1{
    int i;
    void foo(){}

    public void main(){
        int a;
        a = 0;
        i = 888;
        this.i = 0;
        Foo1 foo = new Foo1();
        a = (1 + ( 2 * this.i));
        this.foo();
        System.out.print("");
        // this.foo() = 0; ������
    }

}