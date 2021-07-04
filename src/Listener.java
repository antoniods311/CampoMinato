import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
public class Listener implements ActionListener{
    public static final String CONNECT = "connect", DISCONNECT = "disconnect", START = "start", STOP = "stop", RIVELA = "rivela";
    private JTextField ip;
    private JTextField porta;
    private boolean connected = false, transmitting = false;
    private Downloader downloader = null;
    private PrintWriter netPw;
    private Scanner scan;
    private Socket sock;
    private Frame frame;
    private BoardButton[][] grid;
    public Listener(Frame frame, BoardButton[][] grid, JTextField ip, JTextField porta) {
        this.frame = frame;
        this.grid = grid;
        this.ip = ip;
        this.porta = porta;
    }
    private void setupConnection()throws UnknownHostException, IOException{
        sock = new Socket(ip.getText(), Integer.parseInt(porta.getText()));
        OutputStream os = sock.getOutputStream();
        netPw = new PrintWriter(new OutputStreamWriter(os));
        scan = new Scanner(sock.getInputStream());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.equals(Listener.CONNECT)) {
            try {
                setupConnection();
                connected = true;
                System.out.println("Connected");
            }catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Impossiblie connettersi al server: \n" + e1.getMessage());
                e1.printStackTrace();
                return;
            }
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++) {
                    grid[i][j].reset();
                    //grid[i][j].repaint();
                }
            }
            JOptionPane.showMessageDialog(null, "Connessione stabilita");
        }
        else if(cmd.equals(Listener.START)) {
            try {
                for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        grid[i][j].reset();
                        //grid[i][j].repaint();
                    }
                }
                downloader = new Downloader(grid, scan);
            }catch(IOException e1) {
                JOptionPane.showMessageDialog(null, "Impossiblie creare il file: \n" + e1.getMessage());
                e1.printStackTrace();
            }
            transmitting = true;
            netPw.println(cmd);
            netPw.flush();
            Thread t = new Thread(downloader);
            t.start();
            JOptionPane.showMessageDialog(null, "Download avviato");
        }
        else if(cmd.equals(Listener.STOP)) {
            netPw.println(cmd);
            netPw.flush();
            transmitting = false;
            JOptionPane.showMessageDialog(null, "Download fermato");
        }
        else if(cmd.equals(Listener.DISCONNECT)) {
            netPw.println(cmd);
            netPw.flush();
            netPw.close();
            scan.close();
            connected = false;
            try {
                sock.close();
            }catch (IOException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Connessione chiusa");
        }
        else if(cmd.equals(Listener.RIVELA)) {
            for(int i=0; i<10; i++){
                for(int j = 0; j < 10; j++) {
                    grid[i][j].reveal();
                }
            }
        }
        frame.setButtons(connected, transmitting);
    }
}
