package reversi_vue;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import reversi_modele.CaseContent;
import reversi_modele.Grille;

/**
 * Classe représentant la fênetre principale du jeu.
 * @author yannick
 */
public class MainWindow extends JFrame
{
    /**
     * Attribut contenant le plateau de jeu de manière modele.
     */
    private Grille plateau;
    /**
     * Panneau principal ou l'on met tout les composants.
     */
    private JPanel principalPane;
    /**
     * Panneau de jeu, ou l'on met la grille de jeu
     */
    private JPanel gamePanel;
    /**
     * Panneau d'options
     */
    private VueOption optionsPane;
    /**
     * Variable contenant le plateau de jeu, de manière vue.
     */
    private VueGrille gridComponent;
    
    /**
     * Constructeur de base
     * @param name Nom de la fênetre
     */
    public MainWindow(String name)
    {
        super(name);
        this.init();
    }
    
    /**
     * Fonction d'initialisation
     */
    private void init()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.initGraph();
    }
    
    //GETTER
    public Grille getGrille()
    {
        return this.plateau;
    }
    
    /**
     * Initialisation graphique de la fênetre
     */
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
    
    /**
     * Fonction permettant de lancer le jeu
     * @param color La couleur du joueur
     * @param algo L'algo choisi pour l'IA
     * @param difficulty La difficulté choisie
     */
    public void launchGame(CaseContent color, int algo, int difficulty)
    {
        this.gamePanel.removeAll();
        this.plateau = new Grille(true, algo, difficulty);
        this.gridComponent = new VueGrille(this.plateau, color, this);
        this.gamePanel.add(this.gridComponent);
        this.pack();
        this.repaint();
    }

    /**
     * Fonction permettant de relancer une partie.
     */
    public void replay()
    {
        this.gamePanel.removeAll();
        this.plateau = null;
        this.gridComponent = null;
        this.pack();
    }
}
