import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardButtonListener implements ActionListener {

    private BoardButton cell;

    public BoardButtonListener(BoardButton cell) {
        this.cell = cell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.cell.hasMine()){
            JOptionPane.showMessageDialog(null, "L'utente ha perso");
            //rivela
        }

    }
}
