/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_vue;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    private JPanel gamePanel;
    private VueOption optionsPane;
    private VueGrille gridComponent;
    private boolean inGame;
    
    public MainWindow(String name, Grille g)
    {
        super(name);
        this.inGame = false;
        this.plateau = g;
        this.init();
        /* while(!plateau.isFinished())
        {
            if(VueGrille.NEXT_TURN!=VueGrille.PLAYER_COLOR)
            {
                long current_time = System.currentTimeMillis();
                Case tmp = (Case)plateau.AlphaBeta(plateau, VueGrille.NEXT_TURN, 8, Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
                System.out.println("Temps d'exécution de l'algo: "+(System.currentTimeMillis()-current_time)+" millisecondes");
                plateau.executeTurn(VueGrille.NEXT_TURN, tmp.getLigne(), tmp.getColonne());
                gridComponent.repaint();
                VueGrille.NEXT_TURN = VueGrille.PLAYER_COLOR;
            }
            /* IA CONTRE IA
            else
            {
                Case tmp = (Case)plateau.AlphaBeta(plateau, VueGrille.NEXT_TURN, 4, Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
                plateau.executeTurn(VueGrille.NEXT_TURN, tmp.getLigne(), tmp.getColonne());
                gridComponent.repaint();
                VueGrille.NEXT_TURN = CaseContent.BLANC;
            }
            try
            {
                Thread.sleep(1);
            } catch (InterruptedException ex)
            {
            }
        }*/
    }
    
    private void init()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.initGraph();
    }
    
    private void initGraph()
    {
        this.setSize(1102, 802);
        this.setMinimumSize(new Dimension(1102,802));
        this.setMaximumSize(new Dimension(1102,802));
        
        this.principalPane = new JPanel();
        this.principalPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        this.gamePanel = new JPanel();
        this.gamePanel.setSize(801,801);
        c.gridx = 0;
        c.gridy = 0;
        this.principalPane.add(this.gamePanel, c);

        this.optionsPane = new VueOption(this);
        this.optionsPane.setSize(301,801);
        c.gridx = 1;
        this.principalPane.add(this.optionsPane,c);
        
        this.add(this.principalPane);
        
        this.pack();
    }
    
    public void launchGame(CaseContent color, int algo, int difficulty)
    {
        this.gamePanel.removeAll();
        this.plateau = new Grille(true, algo, difficulty);
        this.gridComponent = new VueGrille(this.plateau, color);
        this.gamePanel.add(this.gridComponent);
        this.pack();
        this.inGame = true;
        this.repaint();
    }
}
