using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;
using System.Threading;
using Newtonsoft.Json;
using System.Text.RegularExpressions;

namespace Rouge
{
    public class RougeDriver
    {
        private Socket socket;
        private IPAddress hosta;
        private IPEndPoint hostep;

        private RougeListener listener;
		
		private Thread driverThread;
		
		private String unProcessed;

        public RougeDriver(String host, int port, RougeListener listener, bool bEncode)
        {

            this.listener = listener;

            this.hosta = IPAddress.Parse("127.0.0.1");
            this.hostep = new IPEndPoint(this.hosta, 6611);
            this.socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp); 
			
			this.driverThread = new Thread(new ThreadStart(ThreadProc));
			
			this.unProcessed = "";
        }
		
		protected void ThreadProc()
    	{
			System.Text.Encoding enc = System.Text.Encoding.ASCII;
     		
			while(true) {
			
				byte[] receiveBuffer = new byte[this.socket.Available];
				int recv = this.socket.Receive(receiveBuffer);
				
				if(recv > 0) {
					String stringBuffer =  Regex.Replace( unProcessed + enc.GetString(receiveBuffer), @"\s", "" );
					String[] splitBuffer = stringBuffer.Split('|');
					
					for(int i = 0; i < splitBuffer.Length-1; i++) {
						
						RougeObject rougeObject = new RougeObject(splitBuffer[i]);
						
						if (this.listener != null) {
							this.listener.onMessage(rougeObject.getString("command"),
						                        rougeObject.getRougeObject("payload"));
						}
					}
					
					if (splitBuffer[splitBuffer.Length-1].Length > 0) {
						this.unProcessed = splitBuffer[splitBuffer.Length-1];
					} else {
						this.unProcessed = "";
					}
					
					Thread.Sleep(50);
				}
			}
    	}

        public void connect()
        {
            try
            {
                socket.Connect(hostep);
            }
            catch (SocketException e)
            {
                Console.WriteLine("Problem connecting to host");
                Console.WriteLine(e.ToString());
                socket.Close();
                return;
            }
			
			driverThread.Start();
			
			if (this.listener != null) {
				this.listener.onConnect();
			}
        }

        public void disconnect()
        {
            socket.Close();
			
			if (this.listener != null) {
				this.listener.onDisconnect();
			}
        }

        public void send(String command, RougeObject payload)
        {
            Dictionary<string, object> toSend = new Dictionary<string, object>();
            toSend.Add("command", command);
            toSend.Add("payload", payload.toDictionary());

            string stringToSend = JsonConvert.SerializeObject(toSend) + "|" + "\n";

            try
            {
                socket.Send(Encoding.ASCII.GetBytes(stringToSend));
            }
            catch (SocketException e)
            {
                Console.WriteLine("Problem sending data");
                Console.WriteLine(e.ToString());
                socket.Close();
                return;
            }

            Console.WriteLine("Sent {0}", stringToSend);
        }

    }
}
