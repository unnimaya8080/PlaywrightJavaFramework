package tests;

import org.testng.annotations.Test;

public class Ctest {

    @Test
    public void Calculate()
    {
        D d = new D(3);  //create object of class tests.D and pass the value 3 to the constructor of class tests.D
//        int a=3;
        System.out.println(d.add());
        System.out.println(d.minus());
        System.out.println("multiplyThree "+d.multiplyThree());


    }
}
