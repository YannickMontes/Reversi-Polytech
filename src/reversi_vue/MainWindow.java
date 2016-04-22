/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_vue;

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
    private VueGrille gridComponent;
    private CaseContent nextTurn;
    
    public MainWindow(String name, Grille g)
    {
        super(name);
        this.plateau = g;
        this.nextTurn = CaseContent.NOIR;
        this.init();
        System.out.println(this.plateau);
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
