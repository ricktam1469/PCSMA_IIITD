package pcsma_server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 extends Thread {

	private ServerSocket sckt;
        DataInputStream dstream;
        FileOutputStream fstrm;
        String filepath="D:\\AnalysisData.csv";
        boolean inf=true;
	public Server1(int port) {
		try {
			sckt = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (inf) {
			try {
				Socket Sok = sckt.accept();
				saveFile(Sok);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveFile(Socket ServerSock) throws IOException {
		dstream = new DataInputStream(ServerSock.getInputStream());
		fstrm = new FileOutputStream(filepath);
		byte[] serverByte = new byte[4096];

		int file_size = 20000; //Fixing the file size
		int read = 0;
		int byteRead = 0;
		
		while((read = dstream.read(serverByte, 0, Math.min(serverByte.length, file_size))) > 0) {
		        byteRead += read;
			file_size -= read;
                        System.out.println("Reading " + byteRead + " Bytes.");
                        System.out.println("File Saved in "+filepath);
			fstrm.write(serverByte, 0, read);

		}

		fstrm.close();
		dstream.close();
	}

	public static void main(String[] args) {
		Server1 svr = new Server1(4007);
		svr.start();
	}

}
