import java.util.Scanner;

public class ThreadClientOut implements Runnable {
	Scanner scan = new Scanner(System.in);
	public String nic_name = "Илья";
	public boolean activ;
	
	public ThreadClientOut() {
		this.activ = true;
	}
	
	public void disactiv() {
		activ = false;
	}
	
	public void run() {
		try {
			while(ClientSocket.b) {
				
				ClientSocket.out.println(nic_name+" :\n\t"+scan.nextLine());
			}
		}finally {
			scan.close();
		}
	}

}
