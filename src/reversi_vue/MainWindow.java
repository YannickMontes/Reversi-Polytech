/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
    private JPanel gamePanel;
    private VueOption optionsPane;
    private VueGrille gridComponent;
    
    public MainWindow(String name, Grille g)
    {
        super(name);
        this.plateau = g;
        this.init();
//        while(!plateau.isFinished())
//        {
//            if(VueGrille.NEXT_TURN!=Grille.PLAYER_COLOR)
//            {
//                Case tmp = (Case)plateau.MinMax(g, CaseContent.NOIR, 4)[1];
//                plateau.executeTurn(CaseContent.NOIR, tmp.getLigne(), tmp.getColonne());
//                gridComponent.repaint();
//                VueGrille.NEXT_TURN = Grille.PLAYER_COLOR;
//            }
//            try
//            {
//                Thread.sleep(500);
//            } catch (InterruptedException ex)
//            {
//                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
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
        this.principalPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        this.gamePanel = new JPanel();
        this.gamePanel.setSize(801,801);
        c.gridx = 0;
        c.gridy = 0;
        this.principalPane.add(this.gamePanel, c);

        this.optionsPane = new VueOption(this);
        c.gridx = 1;
        this.principalPane.add(this.optionsPane,c);
        
        this.add(this.principalPane);
        
        this.pack();
    }
    
    public void launchGame(CaseContent color)
    {
        this.gamePanel.removeAll();
        this.plateau = new Grille(true);
        this.gridComponent = new VueGrille(this.plateau, color);
        this.gamePanel.add(this.gridComponent);
        this.pack();
    }
}
