/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import reversi_modele.CaseContent;

/**
 *
 * @author yannick
 */
public class VueOption extends JPanel implements ActionListener
{
    private JLabel titre;
    private JLabel choixCouleur;
    private MainWindow parent;
    private JRadioButton blanc;
    private JRadioButton noir;
    private JButton jouer;
    private ButtonGroup group;
    
    public VueOption(MainWindow mw)
    {
        this.setSize(300,800);
        this.parent = mw;
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 3;
        
        this.titre = new JLabel("Reversi");
        c.gridx = 1;
        c.gridy = 0;
        this.add(titre, c);
        
        this.choixCouleur = new JLabel("Choissisez votre couleur:");
        c.gridx = 1;
        c.gridy = 1;
        this.add(choixCouleur, c);
        
        this.blanc = new JRadioButton("Blanc");
        c.gridx=0; 
        c.gridy=2;
        this.add(blanc, c);
        
        this.noir = new JRadioButton("Noir");
        this.noir.setSelected(true);
        c.gridx=3; 
        c.gridy=2;
        this.add(noir, c);
        
        this.jouer = new JButton("Jouer");
        c.gridx=1;
        c.gridy=3;
        this.add(jouer, c);
        
        this.jouer.addActionListener(this);
        
        this.group = new ButtonGroup();
        this.group.add(this.noir);
        this.group.add(this.blanc);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==this.jouer)
        {
            if(this.noir.isSelected())
            {
                this.parent.launchGame(CaseContent.NOIR);
            }
            else
            {
                this.parent.launchGame(CaseContent.BLANC);
            }
        }
    }
}
