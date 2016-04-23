package reversi_modele;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe contient la représentation modèle du plateau de jeu.
 * @author yannick
 */
public class Grille
{
    /**
     * Pour plus de facilité, on définie deux variables publiques et statiques
     * pour la taille de la grille. A utiliser a chaque parcours de la grille.
     */
    public static final int HEIGHT_GRID = 8;
    public static final int WIDTH_GRID = 8;
    
    /**
     * Variable représentant le plateau en lui même.
     */
    private Case[][] grille;
    
    /**
     * Conctructeur par défaut de la grille, il initialise une grille basique.
     */
    public Grille()
    {
        this.grille = new Case[HEIGHT_GRID][WIDTH_GRID];
        initGrille();
    }
    
    /**
     * Fonction d'initialisation de la grille (création + mise en place des 4 
     * premiers pions).
     */
    public void initGrille()
    {
        for(int i=0; i<WIDTH_GRID; i++)
        {
            for(int j=0; j< HEIGHT_GRID; j++)
            {
                this.grille[i][j] = new Case();
            }
        }
        this.grille[3][3].setVal(CaseContent.BLANC);
        this.grille[4][4].setVal(CaseContent.BLANC);
        this.grille[4][3].setVal(CaseContent.NOIR);
        this.grille[3][4].setVal(CaseContent.NOIR);
    }
    
    /**
     * Permet d'obtenir une case de la grille.
     * @param ligne la ligne de la case voulue
     * @param colonne la colonne de la case voulue
     * @return la case situé à la ligne LIGNE et la colonne COLONNE
     */
    public Case getCase(int ligne, int colonne)
    {
        return this.grille[ligne][colonne];
    }
    
    /**
     * Permet d'exécuter un tour de jeu en fonction d'une couleur, et d'un pion
     * posé sur la case ligne colonne
     * @param color la couleur du joueur
     * @param ligne la ligne de la case
     * @param colonne la colonne de la case
     */
    public void executeTurn(CaseContent color, int ligne, int colonne)
    {
        //If the selected case isn't in the grid
        if(ligne < 0 || ligne > 7 || colonne < 0 || colonne > 7)
        {
            try {
                throw new Exception("Case sélectionné en dehors des limites de la grille.");
            } catch (Exception ex) {
                Logger.getLogger(Grille.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //If the selected case is already occuped by a piece
        if(this.grille[ligne][colonne].getVal()!=CaseContent.VIDE)
        {
            try {
                throw new Exception("Case déjà occupé.");
            } catch (Exception ex) {
                Logger.getLogger(Grille.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.grille[ligne][colonne].setVal(color);
        this.play(ligne-1, colonne-1, 0, color, true);
        this.play(ligne-1, colonne, 1, color, true);
        this.play(ligne-1, colonne+1, 2, color, true);
        this.play(ligne, colonne+1, 3, color, true);
        this.play(ligne+1, colonne+1, 4, color, true);
        this.play(ligne+1, colonne, 5, color, true);
        this.play(ligne+1, colonne-1, 6, color, true);
        this.play(ligne, colonne-1, 7, color, true);
    }
    
    /**
     * ToString surcharge pour afficher en console la grille.
     * @return Un string avec la grille.
     */
    @Override
    public String toString()
    {
        String s = "";
        for(int i=0; i<WIDTH_GRID; i++)
        {
            for(int j=0; j< HEIGHT_GRID; j++)
            {
                s += this.grille[i][j].toString();
            }
            s+="\n";
        }
        return s;
    }

    /**
     * Permet de savoir si une direction est jouable. Joue le coup selon les 
     * paramètres.
     * ATTENTION:
     * Direction représente la direction qui va être testé pour savoir si le coup
     * est jouable dans cette direction.
     * 0: Haut gauche
     * 1: Haut
     * 2: Haut droit 
     * 3: Droite
     * 4: Bas droit
     * 5: Bas
     * 6: Bas gauche
     * 7: Gauche
     * @param ligne Ligne de la premiere case adjacente a la case jouée par le joueur
     * @param colonne Colonne de la premiere case adjacente a la case jouée par le joueur
     * @param direction La direction qu'on veut vérifier
     * @param color La couleur du joueur en question
     * @param jeu Un booléen qui décide si l'on doit jouer le coup ou juste vérifier
     * @return True si le coup est jouable, faux sinon.
     */
    private boolean play(int ligne, int colonne, int direction, CaseContent color, boolean jeu)
    {
        //On vérifie que la case ne soit pas hors limite.
        if(ligne < 0 || ligne > HEIGHT_GRID-1 || colonne < 0 || colonne > WIDTH_GRID -1)
        {
            return false;
        }
        //Si la case est de la couleur de la couleur du joueur
        //On vérifie que la suivante dans la direction soit vide pour pouvoir jouer. 
        //Le cas échéant, cette direction n'est pas jouable.
        if(this.grille[ligne][colonne].getVal() == color)
        {
            switch(direction)
            {
                case 0:
                    if(ligne > 0 && colonne > 0)
                    {
                        return this.grille[ligne-1][colonne-1].getVal()==CaseContent.VIDE;
                    }
                    break;
                case 1:
                    if(ligne > 0)
                    {
                        return this.grille[ligne-1][colonne].getVal()==CaseContent.VIDE;
                    }
                    break;
                case 2:
                    if(ligne > 0 && colonne < WIDTH_GRID-1)
                    {
                        return this.grille[ligne-1][colonne+1].getVal()==CaseContent.VIDE;
                    }
                    break;
                case 3:
                    if(colonne < WIDTH_GRID-1)
                    {
                        return this.grille[ligne][colonne+1].getVal()==CaseContent.VIDE;
                    }
                    break;
                case 4:
                    if(ligne < HEIGHT_GRID-1 && colonne < WIDTH_GRID-1)
                    {
                        return this.grille[ligne+1][colonne+1].getVal()==CaseContent.VIDE;
                    }
                    break;
                case 5:
                    if(ligne < HEIGHT_GRID-1)
                    {
                        return this.grille[ligne+1][colonne].getVal()==CaseContent.VIDE;
                    }
                    break;
                case 6:
                    if(ligne < HEIGHT_GRID-1 && colonne > 0)
                    {
                        return this.grille[ligne+1][colonne-1].getVal()==CaseContent.VIDE;
                    }
                    break;
                case 7:
                    if(colonne > 0)
                    {
                        return this.grille[ligne][colonne-1].getVal()==CaseContent.VIDE;
                    }
                    break;
            }
            return true;
        }
        //Sinon, si la case n'est pas de la même couleur que le joueur
        //On rapelle la fonction avec la case suivante.
        switch(direction)
        {
            case 0:
                if(ligne > 0 && colonne > 0)
                {
                    if(this.play(ligne-1, colonne-1, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return (this.grille[ligne][colonne].getVal() == color);
                }
            case 1:
                if(ligne > 0)
                {
                    if(this.play(ligne-1, colonne, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return (this.grille[ligne][colonne].getVal() == color);
                }
            case 2:
                if(ligne > 0 && colonne < WIDTH_GRID-1)
                {
                    if(this.play(ligne-1, colonne+1, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return (this.grille[ligne][colonne].getVal() == color);
                }
            case 3:
                if(colonne < 7)
                {
                    if(this.play(ligne, colonne+1, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            case 4:
                if(ligne < HEIGHT_GRID-1 && colonne < WIDTH_GRID-1)
                {
                    if(this.play(ligne+1, colonne+1, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return (this.grille[ligne][colonne].getVal() == color);
                }
            case 5:
                if(ligne < HEIGHT_GRID-1)
                {
                    if(this.play(ligne+1, colonne, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return (this.grille[ligne][colonne].getVal() == color);
                }
            case 6:
                if(ligne < HEIGHT_GRID-1 && colonne > 0 )
                {
                    if(this.play(ligne+1, colonne-1, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return (this.grille[ligne][colonne].getVal() == color);
                }
            case 7:
                if(colonne > 0)
                {
                    if(this.play(ligne, colonne-1, direction, color, jeu))
                    {
                        if(jeu)
                        {
                            this.grille[ligne][colonne].setVal(color);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return (this.grille[ligne][colonne].getVal() == color);
                }
        }
        return false;
    }

    /**
     * Permet d'obtenir les directions jouables en fonction d'une case et d'une
     * couleur.
     * ATTENTION:
     * Dans le tableau,
     * 0: Haut gauche
     * 1: Haut
     * 2: Haut droit 
     * 3: Droite
     * 4: Bas droit
     * 5: Bas
     * 6: Bas gauche
     * 7: Gauche
     * @param ligne La ligne de la case
     * @param colonne La colonne de la case
     * @param color La couleur du joueur
     * @return Un tableau de booléen contenant les directions jouables.
     */
    public boolean[] getPlayableDirection(int ligne, int colonne, CaseContent color)
    {
        //On regarde tout d'abord si la case actuelle n'est pas une case isolée.
        boolean[] playable = new boolean[8];
        for(int i=0; i<playable.length; i++)
        {
            playable[i] = false;
        }
        try
        {
            if(this.grille[ligne-1][colonne-1].getVal()!=CaseContent.VIDE && this.grille[ligne-1][colonne-1].getVal()!=color)//Haut gauche
            {
                playable[0] = true;
            }
        }catch(Exception ex){}
        try
        {
            if(this.grille[ligne-1][colonne].getVal()!=CaseContent.VIDE && this.grille[ligne-1][colonne].getVal()!=color)//Haut
            {
                playable[1] = true;
            }
        }catch(Exception ex){}
        try
        {
            if(this.grille[ligne-1][colonne+1].getVal()!=CaseContent.VIDE && this.grille[ligne-1][colonne+1].getVal()!=color)//Haut droit
            {
                playable[2] = true;
            }
        }catch(Exception ex){}
        try
        {
            if(this.grille[ligne][colonne-1].getVal()!=CaseContent.VIDE && this.grille[ligne][colonne-1].getVal()!=color)//Gauche
            {
                playable[7] = true;
            }
        }catch(Exception ex){}
        try
        {
            if(this.grille[ligne][colonne+1].getVal()!=CaseContent.VIDE && this.grille[ligne][colonne+1].getVal()!=color)//Droite
            {
                playable[3] = true;
            }
        }catch(Exception ex){}
        try
        {
            if(this.grille[ligne+1][colonne-1].getVal()!=CaseContent.VIDE && this.grille[ligne+1][colonne-1].getVal()!=color)//Bas gauche
            {
                playable[6] = true;
            }
        }catch(Exception ex){}
        try
        {
            if(this.grille[ligne+1][colonne].getVal()!=CaseContent.VIDE && this.grille[ligne+1][colonne].getVal()!=color)//Bas 
            {
                playable[5] = true;
            }
        }catch(Exception ex){}
        try
        {
            if(this.grille[ligne+1][colonne+1].getVal()!=CaseContent.VIDE && this.grille[ligne+1][colonne+1].getVal()!=color)//Bas droite
            {
                playable[4] = true;
            }
        }catch(Exception ex){}
        
        return playable;
    }
    
    /**
     * Cette fonction permet de savoir si la case passée en paramètre permet
     * de joueur un coup, selon la couleur du joueur.
     * @param ligne La ligne de la case
     * @param colonne La colonne de la case
     * @param color La couleur du joueur
     * @return True si la case possède au moins une direction jouable, False sinon.
     */
    public boolean isPlayable(int ligne, int colonne, CaseContent color)
    {
        boolean[] directions = this.getPlayableDirection(ligne, colonne, color);
        for(int i=0; i<directions.length; i++)
        {
            if(directions[i])
            {
                directions[i] = this.play(ligne, colonne, i, color, false);
            }
        }
        for(int i=0; i<directions.length; i++)
        {
            if(directions[i])
            {
                return true;
            }
        }
        return false;
    }
}
