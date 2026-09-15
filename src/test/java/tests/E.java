package tests;

public class E {

    int a;

    public E(int a)  //Value 3 is passed to variable a
    {
        this.a=a;  //to make the value 3 (a) accessibile to the metjods of class tests.E, it is passed to the instance variable
    }
    public int multiplyTwo()
    {
        return a*2;
    }
    public int multiplyThree()
    {
        return a*3;
    }


}
