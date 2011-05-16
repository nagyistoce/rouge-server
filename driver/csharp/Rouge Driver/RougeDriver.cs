using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;
using Newtonsoft.Json;

namespace Rouge
{
    public class RougeDriver
    {
        private Socket socket;
        private IPAddress hosta;
        private IPEndPoint hostep;

        private RougeListener listener;

        public RougeDriver(String host, int port, RougeListener listener, bool bEncode)
        {

            this.listener = listener;

            this.hosta = IPAddress.Parse("127.0.0.1");
            this.hostep = new IPEndPoint(this.hosta, 6611);
            this.socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);            
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
        }

        public void disconnect()
        {
            socket.Close();
        }

        public void send(String command, RougeObject payload)
        {
            Dictionary<string, object> toSend = new Dictionary<string, object>();
            toSend.Add("command", command);
            toSend.Add("payload", payload.toJSon());

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
