import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
public class Frame extends JFrame{
    private JPanel central;
    private JPanel nord;
    private JPanel sud;
    private JPanel address;
    private JPanel port;
    private JTextField ip;
    private JTextField porta;
    private JButton connect;
    private JButton disconnect;
    private JButton start;
    private JButton stop;
    private JButton rivela;
    private BoardButton[][] grid;
    public Frame() {
        setTitle("Capone Franco 1884168");
        ip = new JTextField(20);
        ip.setText("localhost");
        porta = new JTextField(20);
        porta.setText("4400");
        connect = new JButton("Connect");
        disconnect = new JButton("Disconnect");
        start = new JButton("Start");
        stop = new JButton("Stop");
        rivela = new JButton("Rivela");
        address = new JPanel(new FlowLayout());
        address.add(new JLabel("Server Address"));
        address.add(ip);
        port = new JPanel(new FlowLayout());
        port.add(new JLabel("Porta"));
        port.add(porta);
        nord = new JPanel(new FlowLayout());
        nord.add(address);
        nord.add(port);
        nord.add(connect);
        nord.add(disconnect);
        sud = new JPanel(new FlowLayout());
        sud.add(start);
        sud.add(stop);
        sud.add(rivela);
        grid = new BoardButton[10][10];
        central = new JPanel(new GridLayout(10,10));
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                grid[i][j] = new BoardButton();
                grid[i][j].addActionListener(new BoardButtonListener(grid[i][j]));
                central.add(grid[i][j]);
            }
        }
        this.getContentPane().add(central, BorderLayout.CENTER);
        this.getContentPane().add(sud, BorderLayout.SOUTH);
        this.getContentPane().add(nord, BorderLayout.NORTH);
        ActionListener listener = new Listener(this, grid, ip, porta);
        connect.setActionCommand(Listener.CONNECT);
        connect.addActionListener(listener);
        disconnect.setActionCommand(Listener.DISCONNECT);
        disconnect.addActionListener(listener);
        start.setActionCommand(Listener.START);
        start.addActionListener(listener);
        stop.setActionCommand(Listener.STOP);
        stop.addActionListener(listener);
        rivela.setActionCommand(Listener.RIVELA);
        rivela.addActionListener(listener);
        setLocation(200,100);
        setButtons(false,false);
        this.setVisible(true);
    }
    public void setButtons(boolean connected, boolean transmitting) {
        if(connected) {
            connect.setEnabled(false);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            if(transmitting) {
                disconnect.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
            else {
                stop.setEnabled(false);
                rivela.setEnabled(true);
                start.setEnabled(true);
                disconnect.setEnabled(true);
            }
        }
        else {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            connect.setEnabled(true);
            rivela.setEnabled(true);
            disconnect.setEnabled(false);
            start.setEnabled(false);
            stop.setEnabled(false);
        }
    }

    public void clearCells(){
        // azzera celle
    }
}
