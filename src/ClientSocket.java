import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class ClientSocket {
	private final static int PORT = 8090;
	private static String ip; // внешний IP адресс
	
	public String mesServer;
	public static volatile boolean b = true;
	public static volatile PrintWriter out;

	public static void main(String[] args) throws IOException, UnknownHostException {

		 InetAddress inetC = InetAddress.getLocalHost(); // создание InetAddress дл€ получени€ локального IP
		// получение внешнего ip
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		try(BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()))) {
			ip = in.readLine(); //получение внешнего IP
			
			}
		 //InetAddress inetC = InetAddress.getByName(ip);
			System.out.println(inetC.getHostAddress()+"\n"+"\t¬н. IP:"+ip+"\n"+"\t"); // выводим состо€ние ip
	
	

		// Socket socketC = new Socket(inetC, PORT); // соединение через InetAddress
			Socket socketC = new Socket(ip,PORT); //через ip и порт 
		//	socketC.connect(new InetSocketAddress(ip,PORT)); // если хочеш другое соединение из ранее созданной переменной
			
		BufferedReader in = new BufferedReader(new InputStreamReader(socketC.getInputStream()));
		out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socketC.getOutputStream())),true);
		
		ThreadClientOut mythread1 = new ThreadClientOut();
		new Thread(mythread1).start();
		
		try {
			//in = new BufferedReader(new InputStreamReader(socketC.getInputStream()));???
			
			String mesServer;
			while(b) {
				
				mesServer = in.readLine();
				
				if(mesServer.contains("END")) { // проверка строки на подстроку
					socketC.close();
					b = false;
					mythread1.disactiv();
					System.out.println("—оединение зовершенно —ервером.");
					break;
				}
				if(socketC.isClosed()) {
					b = false;
					System.out.println("—оединение завершено 2.");
					break;
				}
				
				System.out.println(mesServer);
				
				
			}
			
			
		}finally {
			out.close();
			in.close();
			System.out.println("«авершение");
			socketC.close(); }
		

	}

}
