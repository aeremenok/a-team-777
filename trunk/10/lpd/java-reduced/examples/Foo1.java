public class Foo1{
    int i;
    void foo(){}

    public void main(){
        int a = 0;
        this.i = 0;
        boolean b = 777;
        a = (1 + ( 2 * this.i));
        this.foo();
        // this.foo() = 0; ошибка
    }

}