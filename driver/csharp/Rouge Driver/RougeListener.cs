using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Rouge
{
    public interface RougeListener
    {
  
		void onMessage(String command, RougeObject payload);
        void onConnect();
        void onDisconnect();

    }
}
