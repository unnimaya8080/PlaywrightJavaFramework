package tests;

public class D extends E{

    int a;

    public D(int a)
    {
        super(a); //This will invoke the parent (tests.E) constructor and pass the value 3 to it
        this.a=a;
    }

    public int add()
    {
//        a=a+1;
        return a+1;
    }

    public int minus()
    {
//        a=a-1;
        return a-1;
    }
}
