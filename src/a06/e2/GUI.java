package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;

public class GUI extends JFrame {
    
    private final List<List<JButton>> cells;
    private final Model model;
    
    public GUI(int size) {
        model = new ModelImpl(size);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton fire = new JButton("Fire");
        main.add(BorderLayout.SOUTH, fire);
        
        fire.addActionListener(e -> {
                if (model.fire()) {
                    this.updateView();
                } else {
                    System.exit(0);
                }
            }
        );
         
        cells = new ArrayList<>(size);
        for (int i=0; i<size; i++){
            List<JButton> jList = new ArrayList<>(size);
            cells.add(jList);
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                panel.add(jb);
                jList.add(jb);
                jb.setEnabled(false);
            }
        }

        this.updateView();
        this.setVisible(true);
    }    

    private void updateView() {
        var values = model.getAllValues();
        for (List<JButton> list : this.cells) {
            int x = this.cells.indexOf(list);
            for (JButton button : list) {
                int y = this.cells.get(x).indexOf(button);
                button.setText(values.get(y).get(x) != 0 ? Integer.toString(values.get(y).get(x)) : "");
            }
        }
    }
}
