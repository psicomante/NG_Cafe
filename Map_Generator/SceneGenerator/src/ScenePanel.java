
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author toby
 */
public class ScenePanel extends javax.swing.JPanel {

    private MenuPannello menuPanel;

    /**
     * Creates new form ScenePanel
     */
    public ScenePanel() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 789, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 897, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // call superclass's paintComponent

        Graphics2D g2 = (Graphics2D) g; // cast g to Graphics2D

        if (s != null) {
            s.drawScene(g2);
        }
    }

    Scene s;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    void init(MenuPannello menuPanel) {
        s = new Scene(10, 10, this.getWidth(), this.getHeight());
        this.menuPanel = menuPanel;
    }

    void resizeScene(int num_x, int num_y) {
        s.resize(num_x, num_y);
        repaint();
    }

    void exportScene(String text) {
        String sceneFile = s.exportScene();
        String historyFile = s.exportHistory();
        //System.out.println(textFile);
        try {
            String nome = this.menuPanel.getNomeFile();
            if (nome.length() > 0 && nome != null) {
                Files.write(Paths.get("./" + nome + ".clp"), sceneFile.getBytes());
                this.menuPanel.printMsg("File creato \n" + Paths.get("./" + nome + ".clp"));

                if (historyFile.length() > 0) //scrivo il file della history solo se sono  
                {                               //sono state aggiunte persone alla scena
                    Files.write(Paths.get("./" + nome + "_history.clp"), historyFile.getBytes());
                    this.menuPanel.printMsg("File creato \n" + Paths.get("./" + nome + "_history.clp"));
                }
                loader.salva_info_mappa(s, nome);
            } else {
                this.menuPanel.errorMsg("Inserire un nome valido");
            }
        } catch (IOException ex) {
            Logger.getLogger(ScenePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void click(int x, int y, int state) {

        String result = s.click(x, y, state);
        if (result.equals("success")) {
            repaint();
        } else {
            menuPanel.errorMsg(result);
        }
    }

    void updateScene(Scene s) {
        s.setSizeScreen(this.getWidth(), this.getHeight());
        s.resize(s.num_x, s.num_y);
        s.loadImages();
        this.s = s;
        this.repaint();
    }
}
