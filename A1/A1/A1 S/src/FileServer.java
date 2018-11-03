
/* This is the Java code for the server program */
import java.io.*;
import java.net.*;

class FileServer {
	
	public static void main(String argv[]) throws Exception {
		
		String fileName;
		FileOutputStream fos;
		DataOutputStream outToFile;
		String lineFromClient;
		DataInputStream inFromClient;
		DataOutputStream  outToClient;
		Socket connectionSocket;

		int bytesInBuffer;
		
		// create welcoming socket
		ServerSocket welcomeSocket = new ServerSocket(7890);

		
		while(true) {
			
			// wait on welcoming socket accept() method for client contact to create and
			// return new socket connecting to client
			System.out.println("Server is ready to be connected");
			connectionSocket = welcomeSocket.accept();

			
			// create input stream attached to client socket 
			inFromClient = new DataInputStream (connectionSocket.getInputStream());

			
			// create output stream attached to client socket
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			
			// read file name from client
			//int j = inFromClient.readInt();
			int j = inFromClient.readInt();
			byte[] buffer = new byte[j];
			int i = inFromClient.read(buffer,0,buffer.length);
			fileName = new String(buffer,0,j);
			//System.out.println("reached");
			try {
				// create file by file name and output stream attached to file
				fos = new FileOutputStream(fileName);
				outToFile = new DataOutputStream(fos);
			} catch (Exception e) {
				// any error happens
				System.out.println(e);
				connectionSocket.close();
				return;
			}
			
			// read "line:"+line from client and write line to file until receive "done"
			// indicating file end
//			while ((lineFromClient = inFromClient.readLine()) != null) {
//				if (lineFromClient.equals("done")) break;
//				outToFile.writeBytes(lineFromClient.substring("line:".length())
//+"\r\n");
//			}
			while((bytesInBuffer = inFromClient.read(buffer))>=0) {
				outToFile.write(buffer,0,bytesInBuffer);
			}
			
			
			
			// close file and socket
			fos.close();
			outToFile.close();
			connectionSocket.close();


		}
	}
}
