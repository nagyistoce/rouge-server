package ca.qc.adinfo.rouge.bencode;

import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import ca.qc.adinfo.rouge.data.RougeArray;
import ca.qc.adinfo.rouge.data.RougeObject;

public class BEncodeCompare {
	
	public static void main(String[] args) {
		
		RougeObject ro = new RougeObject();
		
		compare(ro);
		
		ro.put("username", "password");
		
		compare(ro);
		
		ro.put("a", "value1");
		ro.put("b", "value2");
		
		compare(ro);
		
		ro.put("c", 1234);
		ro.put("d", 99);
		
		compare(ro);
		
		RougeArray array = new RougeArray();
		
		array.add("abc");
		array.add(44);
		array.add(45);
		
		ro.put("e", array);
		
		compare(ro);
			
	}
	
	public static void compare(RougeObject ro) {
		
		byte[] bCode = BEncoder.bencode(ro);
		String jSon = ro.toJSON().toString();
		
		System.out.println(new String(bCode));
	
		System.out.println("JSon length " + jSon.length() + " BEncode length " + bCode.length + " reduction of " + (float)bCode.length/(float)jSon.length() );
		
	}

}
