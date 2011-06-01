using System;
namespace Rouge
{
	[Serializable()]
	public class BEncodeException : System.Exception
	{
		
		public BEncodeException() : base() { }
	    public BEncodeException(string message) : base(message) { }
    	public BEncodeException(string message, System.Exception inner) : base(message, inner) { }

    	// A constructor is needed for serialization when an
    	// exception propagates from a remoting server to the client. 
    	protected BEncodeException(System.Runtime.Serialization.SerializationInfo info,
        	System.Runtime.Serialization.StreamingContext context) { }
	}
}

