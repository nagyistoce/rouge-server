/*
 * Copyright [2011] [ADInfo, Alexandre Denault]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
		
		private string unProcessed;
		
		private bool bEncode;

        public RougeDriver(string host, int port, RougeListener listener, bool bEncode)
        {

            this.listener = listener;

            this.hosta = IPAddress.Parse(host);
            this.hostep = new IPEndPoint(this.hosta, port);
            this.socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp); 
			
			this.driverThread = new Thread(new ThreadStart(ThreadProc));
			
			this.unProcessed = "";
			
			this.bEncode = bEncode;
        }
		
		protected void ThreadProc()
    	{
			System.Text.Encoding enc = System.Text.Encoding.ASCII;
     		
			while(true) {
			
				byte[] receiveBuffer = new byte[this.socket.Available];
				int recv = this.socket.Receive(receiveBuffer);
				
				if(recv > 0) {
					
					if (bEncode) {
						
						//Console.WriteLine("Received {0}", enc.GetString(receiveBuffer));
						
						List<IDictionary<string,object>> l = BEncode.Decode(receiveBuffer);
						
						foreach(IDictionary<string,object> d in l) {
							RougeObject rougeObject = new RougeObject(d);
							
							if (this.listener != null) {
								this.listener.onMessage(rougeObject.getString("command"),
							                        rougeObject.getRougeObject("payload"));
							}                                 
						}
						
					} else {
						string stringBuffer =  Regex.Replace( unProcessed + enc.GetString(receiveBuffer), @"\s", "" );
						string[] splitBuffer = stringBuffer.Split('|');
						
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

        public void send(string command, RougeObject payload)
        {
            Dictionary<string, object> toSend = new Dictionary<string, object>();
            toSend.Add("command", command);
            toSend.Add("payload", payload.toDictionary());
			
			byte[] byteArray = null;
			
			if(bEncode) {
				
				byteArray = BEncode.Encode(toSend);
				
			} else {

	            string stringToSend = JsonConvert.SerializeObject(toSend) + "|" + "\n";
				byteArray = Encoding.ASCII.GetBytes(stringToSend);
			}
			
			if (byteArray != null) {
		        try
	            {
	                socket.Send(byteArray);
					//Console.WriteLine("Sent {0}", System.Text.Encoding.ASCII.GetString(byteArray));
	            }
	            catch (SocketException e)
	            {
	                Console.WriteLine("Problem sending data");
	                Console.WriteLine(e.ToString());
	                socket.Close();
	                return;
	            }
			}
        }

    }
}
