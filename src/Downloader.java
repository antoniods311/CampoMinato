import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;
public class Downloader implements Runnable{
    private Scanner scan;
    private boolean running;
    private BoardButton[][] grid;
    public Downloader(BoardButton[][] grid, Scanner scan) throws IOException{
        this.grid = grid;
        this.scan = scan;
        running = false;
    }
    @Override
    public void run() {
        if(!running) {
            running = true;
            while(running) {
                String cmd = scan.nextLine();
                System.out.println("Bombe: "+ cmd);
                if(cmd.equals("done")) {
                    JOptionPane.showMessageDialog(null, "La partita può iniziare");
                    running = false;
                    continue;
                }
                String[] info = cmd.split(":");
                String riga = info[0];
                String colonna = info[1];
                String n = info[2];
                int rigaIndex = Integer.parseInt(riga);
                int colonnaIndex = Integer.parseInt(colonna);
                int value = Integer.parseInt(n);

                if(value == -1){
                    grid[rigaIndex][colonnaIndex].setMine(true);
                }else{
                    grid[rigaIndex][colonnaIndex].setAdjacentMinesCount(value);
                }

                /*for(int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        if(grid[i][j].hasMine()) {
                            JOptionPane.showMessageDialog(null, "L'utente ha perso");
                            break;
                        }
                        else grid[i][j].setAdjacentMinesCount(Integer.parseInt(n));
                    }
                }*/
            }
            //JOptionPane.showMessageDialog(null, "La partita può iniziare");
        }
    }
    public boolean isRunning() {
        return running;
    }
}
