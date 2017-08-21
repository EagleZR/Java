package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main( String[] args ) {

		int port = 11896;
		
		try {
			ServerSocket socket = new ServerSocket( port );
			
			Socket connection = socket.accept();
			
			DataInputStream in = new DataInputStream(connection.getInputStream() );
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			
			out.writeUTF( "State purpose." );
			System.out.println( in.readInt() );
			
			socket.close();
			
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
