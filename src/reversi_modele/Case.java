package reversi_modele;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Cette classe permet de gérer le contenu d'une case de la grille.
 * @author yannick
 */
public class Case
{
    /**
     * Ces variables statiques servent a stocker les images de chaque pions.
     * Elles sont initialisées qu'une seule fois.
     */
    public static BufferedImage pionNoir;
    public static BufferedImage pionBlanc;
    public static BufferedImage pionNoirFocus;
    public static BufferedImage pionBlancFocus;
    
    /**
     * Cet attribut représente la ligne sur le plateau ou est situé le pion
     */
    private int ligne;
    /**
     * Cet attribut représente la colonne sur le plateau ou est situé le pion
     */
    private int colonne;
    /**
     * Cet attribute contient la couleur du pion.
     */
    private CaseContent val;
    
    /**
     * Constructeur de base, selon une ligne est une colonne. Initialise la
     * couleur à VIDE.
     * @param ligne Ligne de la case
     * @param colonne Colonne de la case
     */
    public Case(int ligne, int colonne)
    {
        this.ligne = ligne;
        this.colonne = colonne;
        this.val = CaseContent.VIDE;
    }
    
    /**
     * Constructeur de base, selon une ligne est une colonne. Initialise la
     * couleur à val.
     * @param ligne Ligne de la case
     * @param colonne Colonne de la case
     * @param val Couleur de la case
     */
    public Case(int ligne, int colonne, CaseContent val)
    {
        this.ligne = ligne;
        this.colonne = colonne;
        this.val = val;
    }
    
    /**
     * Permet d'initaliser les variables contenant les images.
     */
    public static void initImages()
    {
        try
        {
            Case.pionBlanc = ImageIO.read(Case.class.getResource("/reversi_images/0.png"));
            Case.pionNoir = ImageIO.read(Case.class.getResource("/reversi_images/1.png"));
            Case.pionBlancFocus = ImageIO.read(Case.class.getResource("/reversi_images/focus_blanc.png"));
            Case.pionNoirFocus = ImageIO.read(Case.class.getResource("/reversi_images/focus_noir.png"));
            
        } catch (IOException ex)
        {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //GETTERS & SETTERS
    public int getLigne()
    {
        return ligne;
    }

    public int getColonne()
    {
        return colonne;
    }
    
    public CaseContent getVal()
    {
        return this.val;
    }
    
    public void setVal(CaseContent val)
    {
        this.val = val;
    }
    
    public BufferedImage getImage()
    {
        if(null != this.val)
        switch (this.val)
        {
            case BLANC:
                return pionBlanc;
            case NOIR:
                return pionNoir;
            default:
                break;
        }
        return null;
    }

    /**
     * Fonction toString surchargée
     * @return La valeur de la case dans un string
     */
    @Override
    public String toString()
    {
        return " "+this.val.toString()+" ";
    }
}
