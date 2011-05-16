using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Rouge;

namespace Rouge_Tester
{
    class Program
    {
        static void Main(string[] args)
        {
            //testJSonConversion();

            RougeObject rougeObject = new RougeObject();
            rougeObject.putString("username", "bob");
            rougeObject.putString("password", "password");

            RougeDriver driver = new RougeDriver("127.0.0.1", 6611, null, false);
            driver.connect();
            driver.send("login", rougeObject);

            while (true) ;
        }

        static void testJSonConversion()
        {
            RougeObject rougeObject = new RougeObject();
            rougeObject.putString("username", "bob");
            rougeObject.putString("password", "secret");
            rougeObject.putInt("int", 1);
            rougeObject.putFloat("float", 0.1f);
            rougeObject.putBoolean("boolean", true);

            string jSon = rougeObject.toJSon();

            Console.WriteLine("Rouge Object is {0}", jSon);

            RougeObject jSonRouge = new RougeObject(jSon);

            Console.WriteLine("After Transformation {0}", jSonRouge.toJSon());
        }
    }
}
