
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	public static void main( String[] args ) {
		try {
			Socket socket = new Socket( "192.168.1.135", 11896 );
			
			DataInputStream in = new DataInputStream( socket.getInputStream() );
			
			if ( in.readUTF().equals( "State purpose." ) ) {
				new DataOutputStream( socket.getOutputStream() ).writeInt( 5 );
			}
			
			socket.close();
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
