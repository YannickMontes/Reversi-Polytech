/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_vue;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import reversi_modele.Case;
import reversi_modele.CaseContent;
import reversi_modele.Grille;

/**
 *
 * @author yannick
 */
public class MainWindow extends JFrame
{
    private Grille plateau;
    private JPanel principalPane;
    private VueGrille gridComponent;
    
    public MainWindow(String name, Grille g)
    {
        super(name);
        this.plateau = g;
        this.init();
        while(!plateau.isFinished())
        {
            if(VueGrille.NEXT_TURN!=Grille.PLAYER_COLOR)
            {
                Case tmp = (Case)plateau.MinMax(g, CaseContent.NOIR, 4)[1];
                plateau.executeTurn(CaseContent.NOIR, tmp.getLigne(), tmp.getColonne());
                gridComponent.repaint();
                VueGrille.NEXT_TURN = Grille.PLAYER_COLOR;
            }
            try
            {
                Thread.sleep(500);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void init()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.initGraph();
    }
    
    private void initGraph()
    {
        this.principalPane = new JPanel();
        this.gridComponent = new VueGrille(plateau);
        this.principalPane.add(this.gridComponent);
        this.add(this.principalPane);
        this.pack();
    }
}
