package reversi_vue;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import reversi_modele.CaseContent;

/**
 *
 * @author yannick
 */
public class VueOption extends JPanel implements ActionListener
{
    private JLabel titreLabel;
    private JLabel choixCouleurLabel;
    private JLabel algoLabel;
    private JLabel difficultyLabel;
    private MainWindow parent;
    private JRadioButton blanc;
    private JRadioButton noir;
    private JRadioButton facile;
    private JRadioButton moyen;
    private JRadioButton difficile;
    private JRadioButton minMax;
    private JRadioButton alphaBeta;
    private ButtonGroup algoChoice;
    private ButtonGroup difficulty;
    private JButton jouer;
    private ButtonGroup groupColor;
    
    public VueOption(MainWindow mw)
    {
        this.setSize(300,401);
        this.parent = mw;
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,0,0,0);
        
        this.titreLabel = new JLabel("Reversi");
        this.titreLabel.setFont(new Font(this.titreLabel.getFont().getName(),Font.BOLD, 48));
        this.titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        this.add(titreLabel, c);
        
        this.choixCouleurLabel = new JLabel("Choissisez votre couleur:");
        this.choixCouleurLabel.setFont(new Font(this.choixCouleurLabel.getFont().getName(),Font.PLAIN, 20));
        c.gridx = 0;
        c.gridy = 1;
        this.add(choixCouleurLabel, c);
        
        this.blanc = new JRadioButton("Blanc");
        c.gridwidth = 2;
        c.gridx=0; 
        c.gridy=2;
        this.add(blanc, c);
        
        this.noir = new JRadioButton("Noir");
        this.noir.setSelected(true);
        c.gridx=2; 
        c.gridy=2;
        this.add(noir, c);
        
        this.algoLabel = new JLabel("Algorithme de l'IA: ");
        this.algoLabel.setFont(new Font(this.algoLabel.getFont().getName(),Font.PLAIN, 20));
        c.gridwidth=4;
        c.gridx = 0;
        c.gridy = 3;
        this.add(algoLabel, c);
        
        this.minMax = new JRadioButton("MinMax");
        this.minMax.setSelected(true);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        this.add(minMax,c);
        
        this.alphaBeta = new JRadioButton("Alpha-Beta");
        c.gridx=2;
        c.gridy=4;
        this.add(alphaBeta, c);
        
        this.difficultyLabel = new JLabel("Difficulté: ");
        this.difficultyLabel.setFont(new Font(this.titreLabel.getFont().getName(),Font.PLAIN, 20));
        c.gridwidth=4;
        c.gridx = 0;
        c.gridy = 5;
        this.add(difficultyLabel, c);
        
        this.facile = new JRadioButton("Facile");
        this.facile.setSelected(true);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 6;
        this.add(facile,c);
        
        this.moyen = new JRadioButton("Moyen");
        c.gridx = 1;
        c.gridy = 6;
        this.add(moyen,c);
        
        this.difficile = new JRadioButton("Difficile");
        c.gridx = 3;
        c.gridy = 6;
        this.add(difficile,c);
        
        this.jouer = new JButton("Jouer");
        c.gridwidth = 2;
        c.gridx=1;
        c.gridy=7;
        this.add(jouer, c);
        
        this.jouer.addActionListener(this);
        
        this.difficulty = new ButtonGroup();
        this.difficulty.add(facile);
        this.difficulty.add(moyen);
        this.difficulty.add(difficile);
        
        this.algoChoice = new ButtonGroup();
        this.algoChoice.add(minMax);
        this.algoChoice.add(alphaBeta);
        
        this.groupColor = new ButtonGroup();
        this.groupColor.add(this.noir);
        this.groupColor.add(this.blanc);
        this.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==this.jouer)
        {
            int difficulty = 0;
            CaseContent color = CaseContent.VIDE;
            int algorithm = 0;
            if(this.facile.isSelected())
            {
                difficulty = 4;
            }
            else if(this.moyen.isSelected())
            {
                difficulty = 6;
            }
            else
            {
                difficulty = 8;
            }
            if(this.noir.isSelected())
            {
                color = CaseContent.NOIR;
            }
            else
            {
                color = CaseContent.BLANC;
            }
            if(this.minMax.isSelected())
            {
                algorithm = 0;
            }
            else
            {
                algorithm = 1;
            }
            this.parent.launchGame(color ,algorithm, difficulty);
        }
    }
}
