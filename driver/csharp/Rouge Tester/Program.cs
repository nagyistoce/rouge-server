using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Rouge;

namespace Rouge_Tester
{
    class Program : RougeListener
    {
		
		private RougeDriver driver;
		
		public Program() {

            driver = new RougeDriver("127.0.0.1", 6611, this, false);
            driver.connect();
			
		}
		
		public void onMessage(String command, RougeObject payload) {
			
			Console.WriteLine("Received {0} {1}", command, payload.toJson());
			
			if (command.Equals("login")) 
			{
				RougeObject rougeObject = new RougeObject();
            	rougeObject.putString("name", "csroom2");
       			driver.send("room.create", rougeObject);
			} 
			else if (command.Equals("room.create")) 
			{
				RougeObject rougeObject = new RougeObject();
				rougeObject.putString("name", "csroom2");
            	rougeObject.putString("message", "hello");
       			driver.send("room.say", rougeObject);
			}
		}
		
       public void onConnect() {
			
			Console.WriteLine("Connected");
			
			RougeObject rougeObject = new RougeObject();
            rougeObject.putString("username", "bob");
            rougeObject.putString("password", "password");
			
			driver.send("login", rougeObject);
		}
		
        public void onDisconnect() {
			
			Console.WriteLine("Disconnected");
		}
		
        static void Main(string[] args)
        {
            //testJSonConversion();

            new Program();

            while (true) ;
        }

        static void testJSonConversion()
        {
            RougeObject rougeObject = new RougeObject();
            rougeObject.putString("username", "cs");
            rougeObject.putString("password", "secret");
            rougeObject.putInt("int", 1);
            rougeObject.putFloat("float", 0.1f);
            rougeObject.putBoolean("boolean", true);

            //string jSon = rougeObject.toJSon();

            //Console.WriteLine("Rouge Object is {0}", jSon);

            //valueRougeObject jSonRouge = new RougeObject(jSon);

            //Console.WriteLine("After Transformation {0}", jSonRouge.toJSon());
        }
    }
}
