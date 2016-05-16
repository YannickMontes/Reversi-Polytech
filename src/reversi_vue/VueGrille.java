package reversi_vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import reversi_modele.Case;
import reversi_modele.CaseContent;
import reversi_modele.Grille;

/**
 * Classe contenant le composant grille du jeu. Permet de représenter le plateau
 * de jeu.
 * @author yannick
 */
public class VueGrille extends JComponent implements MouseMotionListener, MouseListener
{
    /**
     * Attribut permettant de savoir quelle case le joueur visualise lorsqu'il cherche
     * ou jouer.
     */
    private Case focused;
    /**
     * Fênetre parente.
     */
    private MainWindow parent;

    /**
     * Constructeur de la classe
     * @param g La grille (modele)
     * @param playercolor La couleur choisie par le joueur
     * @param mw Le parent
     */
    public VueGrille(Grille g, CaseContent playercolor, MainWindow mw)
    {
        super();
        this.parent = mw;
        Grille.PLAYER_COLOR = playercolor;
        if(Grille.PLAYER_COLOR == CaseContent.NOIR)
        {
            Grille.IA_COLOR = CaseContent.BLANC;
        }
        else
        {
            Grille.IA_COLOR = CaseContent.NOIR;
        }
        focused = null;
        Grille.NEXT_TURN = CaseContent.NOIR;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }                   

    /**
     * Fonction de dessin surchargée
     * @param g Le pinceau
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(1));
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, 801,801);
        
        g2.setColor(Color.BLACK);
        
        for(int i=0; i<Grille.HEIGHT_GRID+1; i++)
        {
            g2.drawLine(i*(800/Grille.HEIGHT_GRID), 0, i*(800/Grille.HEIGHT_GRID), 800);
            g2.drawLine(0,i*(800/Grille.HEIGHT_GRID),800, i*(800/Grille.HEIGHT_GRID));
        }
        
        for(int i=0; i<Grille.HEIGHT_GRID; i++)
        {
            for(int j=0; j<Grille.WIDTH_GRID; j++)
            {
                if(this.parent.getGrille().getCase(i, j).getVal()!=CaseContent.VIDE && this.parent.getGrille().getCase(i,j)!=focused)
                {
                    g2.drawImage(this.parent.getGrille().getCase(i, j).getImage(), j*100, i*100, this);
                }
                else if(this.parent.getGrille().getCase(i, j) == focused)
                {
                    if(Grille.NEXT_TURN == CaseContent.NOIR)
                    {
                        g2.drawImage(Case.pionNoirFocus, j*100, i*100, this);
                    }
                    else
                    {
                        g2.drawImage(Case.pionBlancFocus, j*100, i*100, this);
                    }
                }
            }
        }
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(801,801);
    }                 

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    /**
     * Lorsque l'on bouge la souris
     * Permet de mettre a jour la variable focused
     * @param e 
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        if(Grille.NEXT_TURN == Grille.PLAYER_COLOR)
        {
            int i = e.getY()/100;
            int j = e.getX()/100;
            if((i>=0 && i<Grille.HEIGHT_GRID && j>=0 && j<Grille.WIDTH_GRID) && this.parent.getGrille().getCase(i, j).getVal()==CaseContent.VIDE)
            {
                if(this.parent.getGrille().isPlayable(e.getY()/100, e.getX()/100, Grille.NEXT_TURN))
                {
                    focused = this.parent.getGrille().getCase(i, j);
                }
                else
                {
                    focused = null;
                }
            }
            else
            {
                focused = null;
            }
        }
        else
        {
            focused = null;
            this.parent.getGrille().IA_Turn();
        }
        this.repaint();
        this.checkConditions();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    /**
     * Lorsqu'on relache le clic
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(Grille.NEXT_TURN == Grille.PLAYER_COLOR)
        {
            if(this.parent.getGrille().isPlayable(e.getY()/100, e.getX()/100, Grille.NEXT_TURN))
            {
                this.parent.getGrille().executeTurn(Grille.NEXT_TURN,e.getY()/100, e.getX()/100);
                if(Grille.NEXT_TURN == CaseContent.BLANC)
                {
                    Grille.NEXT_TURN = CaseContent.NOIR;
                }
                else
                {
                    Grille.NEXT_TURN = CaseContent.BLANC;
                }
                this.repaint();
            }   
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    /**
     * Fonction permettant d'afficher la fênetre de fin.
     */
    private void showEndWindow()
    {
        int scoreNoir = this.parent.getGrille().countPieces(CaseContent.NOIR);
        int scoreBlanc = this.parent.getGrille().countPieces(CaseContent.BLANC);
        String message, score;
        if(!this.parent.getGrille().isFinished())
        {
            message = "Aucune possibilité de jeu, fin de la partie!";
        }
        else
        {
            message = "Grille remplie, fin de la partie!";
        }
        if(scoreNoir > scoreBlanc)
        {
            if(Grille.PLAYER_COLOR == CaseContent.NOIR)
            {
                score = "\nBravo, vous avez gagné!\nScore:\nBlanc: "+scoreBlanc+", Noir: "+scoreNoir;
            }
            else
            {
                score = "\nDommage, vous avez perdu!\nScore:\nBlanc: "+scoreBlanc+", Noir: "+scoreNoir;
            }
        }
        else if (scoreNoir < scoreBlanc)
        {
            if(Grille.PLAYER_COLOR == CaseContent.BLANC)
            {
                score = "\nBravo, vous avez gagné!\nScore:\nBlanc: "+scoreBlanc+", Noir: "+scoreNoir;
            }
            else
            {
                score = "\nDommage, vous avez perdu!\nScore:\nBlanc: "+scoreBlanc+", Noir: "+scoreNoir;
            }
        }
        else
        {
            score = "\nMatch nul!\nScore:\nBlanc: "+scoreBlanc+", Noir: "+scoreNoir;
        }
        
        
        Object[] options = {"Oui",
                    "Non"};
        int resultat = JOptionPane.showOptionDialog(this,
            message + score + "\nSouhaitez-vous rejouer?",
            "Fin de la partie",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[1]);
        if(resultat == JOptionPane.YES_OPTION)
        {
            this.parent.replay();
        }
        else
        {
            System.exit(0);
        }
    }
    
    /**
     * Fonction vérifiant s'il faut afficher la fêntre de fin ou non.
     */
    private void checkConditions()
    {
        if(this.parent.getGrille().isFinished())
        {
            this.showEndWindow();
        }
        else
        {
            if(!this.parent.getGrille().canPlay(Grille.NEXT_TURN))
            {
                if(Grille.NEXT_TURN == Grille.PLAYER_COLOR)
                {
                    if(this.parent.getGrille().canPlay(Grille.IA_COLOR))
                    {
                        Grille.NEXT_TURN = Grille.IA_COLOR;
                    }
                    else
                    {
                        this.showEndWindow();
                    }
                }
                else
                {
                    if(this.parent.getGrille().canPlay(Grille.PLAYER_COLOR))
                    {
                        Grille.NEXT_TURN = Grille.PLAYER_COLOR;
                    }
                    else
                    {
                        this.showEndWindow();
                    }
                }
            }   
        }
    }
}
