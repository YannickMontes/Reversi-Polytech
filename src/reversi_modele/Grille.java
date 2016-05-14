package reversi_modele;

import java.util.ArrayList;
import reversi_vue.VueGrille;

/**
 * Cette classe contient la représentation modèle du plateau de jeu.
 * @author yannick
 */
public class Grille implements Cloneable
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
     * Variable contenant un chiffre qui représente l'algo à utiliser pour l'IA.
     */
    private int algoIA;
    
    /**
     * Variable representant la profondeur max de l'algo.
     */
    private int profondeur;
    
    /**
     * Conctructeur par défaut de la grille, il initialise une grille basique.
     * @param generation Indique si il faut ou non générer une grille de départ basique.
     */
    public Grille(boolean generation, int algo, int difficulty)
    {
        this.algoIA = algo;
        this.profondeur = difficulty;
        this.grille = new Case[HEIGHT_GRID][WIDTH_GRID];
        if(generation)
        {
            initGrille();
        }
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
                this.grille[i][j] = new Case(i,j);
            }
        }
        this.grille[3][3].setVal(CaseContent.BLANC);
        this.grille[4][4].setVal(CaseContent.BLANC);
        this.grille[4][3].setVal(CaseContent.NOIR);
        this.grille[3][4].setVal(CaseContent.NOIR);
    }
    
    /**
     * Cette fonction permet de créer une nouvelle case dans la grille 
     * en fonction de sa couleur.
     * @param ligne La ligne de la case
     * @param colonne La colonne de la case
     * @param val La couleur de la case
     */
    public void initCase(int ligne, int colonne, CaseContent val)
    {
        this.grille[ligne][colonne] = new Case(ligne, colonne, val);
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
     * Permet de savoir si la partie est terminée ou non.
     * @return True si la partie est finie, False sinon.
     */
    public boolean isFinished()
    {
        for(int i=0; i<WIDTH_GRID; i++)
        {
            for(int j=0; j< HEIGHT_GRID; j++)
            {
                if(this.grille[i][j].getVal()==CaseContent.VIDE)
                {
                    return false;
                }
            }
        }
        return true;
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
        if(ligne < 0 || ligne > HEIGHT_GRID-1 || colonne < 0 || colonne > WIDTH_GRID-1)
        {
            return;
        }
        
        //If the selected case is already occuped by a piece
        if(this.grille[ligne][colonne].getVal()!=CaseContent.VIDE)
        {
            return;
        }
        
        this.grille[ligne][colonne].setVal(color);
        boolean[] directions = this.getPlayableDirection(ligne, colonne, color, true);
        for(int i=0; i<directions.length; i++)
        {
            if(directions[i])
            {
                this.play(ligne, colonne, i, color, true);
            }
        }
//        this.play(ligne-1, colonne-1, 0, color, true);
//        this.play(ligne-1, colonne, 1, color, true);
//        this.play(ligne-1, colonne+1, 2, color, true);
//        this.play(ligne, colonne+1, 3, color, true);
//        this.play(ligne+1, colonne+1, 4, color, true);
//        this.play(ligne+1, colonne, 5, color, true);
//        this.play(ligne+1, colonne-1, 6, color, true);
//        this.play(ligne, colonne-1, 7, color, true);
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
        boolean playable = false;//Booléen correspondant a la jouabilité de la direction
        int lastline=-1, lastcol=-1;//Variables de sauvegarde en cas de jeu
        /*
            Le principe est le même pour chaque direction. On se place de cases
            à la suite dans la direction. En cas de limite en dehors du tableau, une 
            exception est levée. 
            On vérifie si la case est une case de la même couleur que le joueur:
                -Si c'est le cas on sauvegarde la case actuelle, et on revient a la case sélectionné par le joueur. 
                A partir de la, on met toutes les cases de la couleur du joueur, jusqu'a la case sauvegardé.
                -Si la case est vide, alors la direction n'est pas jouable
                -Si la case est de couleur adverse, on passe a la case suivante dans la même direction.
        */
        switch(direction)
        {
            case 0://Haut gauche
                try
                {
                    int j = colonne - 2;
                    for(int i=ligne-2; i>=0; i--, j--)
                    {
                        if(!playable)
                        {
                            if(this.grille[i][j].getVal()==color)
                            {
                                playable = true;
                                lastline=i;
                                lastcol=j;
                                i=ligne;
                                j=colonne;
                            }
                            else if(this.grille[i][j].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                i=-1;
                            }
                        }
                        else if(jeu && playable && (i>=lastline && j>=lastcol))
                        {
                            this.grille[i][j].setVal(color);
                        }
                        else
                        {
                            i=-1;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
            case 1://Haut
                try
                {
                    for(int i=ligne-2; i>=0; i--)
                    {
                        if(!playable)
                        {
                            if(this.grille[i][colonne].getVal()==color)
                            {
                                playable = true;
                                lastline=i;
                                i=ligne;
                            }
                            else if(this.grille[i][colonne].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                i=-1;
                            }
                        }
                        else if(jeu && playable && (i>=lastline))
                        {
                            this.grille[i][colonne].setVal(color);
                        }
                        else
                        {
                            i=-1;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
            case 2://Haut droite
                try
                {
                    int j = colonne + 2;
                    for(int i=ligne-2; i>=0; i--, j++)
                    {
                        if(!playable)
                        {
                            if(this.grille[i][j].getVal()==color)
                            {
                                playable = true;
                                lastline=i;
                                lastcol=j;
                                i=ligne;
                                j=colonne;
                            }
                            else if(this.grille[i][j].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                i=-1;
                            }
                        }
                        else if(jeu && playable && (i>=lastline && j<=lastcol))
                        {
                            this.grille[i][j].setVal(color);
                        }
                        else
                        {
                            i=-1;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
            case 3://Droite
                try
                {
                    for(int j=colonne+2; j<WIDTH_GRID; j++)
                    {
                        if(!playable)
                        {
                            if(this.grille[ligne][j].getVal()==color)
                            {
                                playable = true;
                                lastcol=j;
                                j=colonne;
                            }
                            else if(this.grille[ligne][j].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                j=WIDTH_GRID;
                            }
                        }
                        else if(jeu && playable && (j<=lastcol))
                        {
                            this.grille[ligne][j].setVal(color);
                        }
                        else
                        {
                            j=WIDTH_GRID;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
            case 4://Bas droit
                try
                {
                    int j = colonne + 2;
                    for(int i=ligne+2; i<HEIGHT_GRID; i++, j++)
                    {
                        if(!playable)
                        {
                            if(this.grille[i][j].getVal()==color)
                            {
                                playable = true;
                                lastline=i;
                                lastcol=j;
                                i=ligne;
                                j=colonne;
                            }
                            else if(this.grille[i][j].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                i=HEIGHT_GRID;
                            }
                        }
                        else if(jeu && playable && (i<=lastline && j<=lastcol))
                        {
                            this.grille[i][j].setVal(color);
                        }
                        else
                        {
                            i=HEIGHT_GRID;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
            case 5://Bas
                try
                {
                    for(int i=ligne+2; i<HEIGHT_GRID; i++)
                    {
                        if(!playable)
                        {
                            if(this.grille[i][colonne].getVal()==color)
                            {
                                playable = true;
                                lastline=i;
                                i=ligne;
                            }
                            else if(this.grille[i][colonne].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                i=HEIGHT_GRID;
                            }
                        }
                        else if(jeu && playable && (i<=lastline))
                        {
                            this.grille[i][colonne].setVal(color);
                        }
                        else
                        {
                            i=HEIGHT_GRID;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
            case 6://Bas gauche
                try
                {
                    int j = colonne - 2;
                    for(int i=ligne+2; i<HEIGHT_GRID; i++, j--)
                    {
                        if(!playable)
                        {
                            if(this.grille[i][j].getVal()==color)
                            {
                                playable = true;
                                lastline=i;
                                lastcol=j;
                                i=ligne;
                                j=colonne;
                            }
                            else if(this.grille[i][j].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                i=HEIGHT_GRID;
                            }
                        }
                        else if(jeu && playable && (i<=lastline && j>=lastcol))
                        {
                            this.grille[i][j].setVal(color);
                        }
                        else
                        {
                            i=HEIGHT_GRID;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
            case 7://Gauche
                try
                {
                    for(int j=colonne-2; j>=0; j--)
                    {
                        if(!playable)
                        {
                            if(this.grille[ligne][j].getVal()==color)
                            {
                                playable = true;
                                lastcol=j;
                                j=colonne;
                            }
                            else if(this.grille[ligne][j].getVal() == CaseContent.VIDE)
                            {
                                playable = false;
                                j=-1;
                            }
                        }
                        else if(jeu && playable && (j>=lastcol))
                        {
                            this.grille[ligne][j].setVal(color);
                        }
                        else
                        {
                            j=-1;
                        }
                    }
                }
                catch(Exception ex)
                {
                }
                break;
        }
        return playable;
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
     * @param adjacenteUniquement Booléen permettant de savoir si l'on veut connaitre
     * uniquement si le pion est adjacent à des pièces ennemies, ou si un coup est jouable.
     * @return Un tableau de booléen contenant les directions jouables.
     */
    public boolean[] getPlayableDirection(int ligne, int colonne, CaseContent color, boolean adjacenteUniquement)
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
        
        if(!adjacenteUniquement)
        {
            for(int i=0; i<playable.length; i++)
            {
                if(playable[i])
                {
                    playable[i] = this.play(ligne, colonne, i, color, false);
                }
            }
        }
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
        if(this.grille[ligne][colonne].getVal()!=CaseContent.VIDE)
        {
            return false;
        }
        boolean[] directions = this.getPlayableDirection(ligne, colonne, color, false);
        for(int i=0; i<directions.length; i++)
        {
            if(directions[i])
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Cette fonction fait partie de l'algorithme MinMax. C'est la fonction 
     * d'évaluation. Elle permet d'évaluer la grille dans une position donnée.
     * @param color La couleur du joueur
     * @return l'évaluation
     */
    public int evaluation(CaseContent color)
    {
        //On compte le nombre de billes de la couleur
        
        int score =0; 
        
        for(int i=0; i<HEIGHT_GRID; i++)
        {
            for(int j=0; j<WIDTH_GRID; j++)
            {
                if(this.grille[i][j].getVal()==color)
                {
                    score++;
                }
            }
        }
        
        return score;
        /*
        int score = 0;
        int[][] caseValue ={
            {8, -2, 3, 3, 3, 3, -2, 8},
            {-2, 1, 2, 2, 2, 2, 1, -2},
            {3, 2, 1, 1, 1, 1, 2, 3},
            {3, 2, 1, 1, 1, 1, 2, 3},
            {3, 2, 1, 1, 1, 1, 2, 3},
            {3, 2, 1, 1, 1, 1, 2, 3},
            {-2, 1, 2, 2, 2, 2, 1, -2},
            {8, -2, 3, 3, 3, 3, -2, 8}
        };
        
        for(int i=0; i<HEIGHT_GRID; i++)
        {
            for(int j=0; j<WIDTH_GRID; j++)
            {
                if(this.grille[i][j].getVal()==color)
                {
                    score += caseValue[i][j];
                }
            }
        }
        return score;
        */
    }
            
    
    public ArrayList<Case> getPossibleMooves(CaseContent color)
    {
        ArrayList<Case> moves = new ArrayList<>();
        for(int i=0; i<HEIGHT_GRID; i++)
        {
            for(int j=0; j<WIDTH_GRID; j++)
            {
                if(this.isPlayable(i, j, color))
                {
                    moves.add(this.grille[i][j]);
                }
            }
        }
        return moves;
    }
    
    public Object[] MinMax(Grille g, CaseContent color, int prof)
    {
        if(g.isFinished() || prof <= 0)
        {
            Object[] retour = new Object[2];
            retour[0] = g.evaluation(color);
            retour[1] = null;
            return retour;
        }
        
        int scoreMax;
        Case bestMoove=null;
        
        if(color != VueGrille.PLAYER_COLOR)//Ici on maximise, c'est le tour de l'IA
        {
            scoreMax = Integer.MIN_VALUE;
            ArrayList<Case> mooves = g.getPossibleMooves(color);
            for(Case c : mooves)
            {
                Grille tmp = g.clonage();
                tmp.executeTurn(color, c.getLigne(), c.getColonne());
                Object[] score = MinMax(tmp, VueGrille.PLAYER_COLOR, prof-1);
                if((int)score[0] > scoreMax)
                {
                    scoreMax = (int)score[0];
                    bestMoove = c;
                }
            }
        }
        else //Ici on minimise, c'est le tour du joueur
        {
            scoreMax = Integer.MAX_VALUE;
            ArrayList<Case> mooves = g.getPossibleMooves(color);
            for(Case c : mooves)
            {
                Grille tmp = g.clonage();
                tmp.executeTurn(color, c.getLigne(), c.getColonne());
                Object[] score = MinMax(tmp, color, prof-1);
                if((int)score[0] < scoreMax)
                {
                    scoreMax = (int)score[0];
                    bestMoove = c;
                }
            }
        }
        
        Object[] retour = new Object[2];
        retour[0] = scoreMax;
        retour[1] = bestMoove;
        return retour;
    }
    
    public Object[] AlphaBeta(Grille g, CaseContent color, int prof, int alpha, int beta)
    {
        if(g.isFinished() || prof <= 0)
        {
            Object[] retour = new Object[2];
            retour[0] = g.evaluation(color);
            retour[1] = null;
            return retour;
        }
        
        Case bestMoove = null;
        ArrayList<Case> mooves = g.getPossibleMooves(color);
        int meilleur_score = Integer.MIN_VALUE;
        for(Case c : mooves)
        {
            Grille tmp = g.clonage();
            tmp.executeTurn(color, c.getLigne(), c.getColonne());
            Object[] score;
            if(color == VueGrille.PLAYER_COLOR)
            {
                score = AlphaBeta(tmp, VueGrille.IA_COLOR, prof-1, -beta, -alpha);
            }
            else
            {
                score = AlphaBeta(tmp, VueGrille.PLAYER_COLOR, prof-1, -beta, -alpha);
            }
            int val = -1* (int)score[0];
            if(val >= meilleur_score)
            {
                meilleur_score = val;
                if(meilleur_score > alpha)
                {
                    alpha = meilleur_score;
                    bestMoove = c;
                    if(alpha >= beta)
                    {
                        Object[] retour = new Object[2];
                        retour[0] = meilleur_score;
                        retour[1] = c;
                    }
                }
            }
        }
        
        Object[] retour = new Object[2];
        retour[0] = meilleur_score;
        retour[1] = bestMoove;
        return retour;
    }
    
    /**
     * Permet d'obtenir un clone de la grille
     * @return La nouvelle grille, clonée.
     */
    public Grille clonage()
    {
        try
        {
            return (Grille) this.clone();
        } catch (CloneNotSupportedException ex)
        {
            System.err.println("Erreur lors du clonage.");
        }
        return null;
    }

    /**
     * Surcharge de la fonction clone
     * @return La grille clonnée
     * @throws CloneNotSupportedException 
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        Grille g = new Grille(false, this.algoIA, this.profondeur);
        for(int i=0; i<WIDTH_GRID; i++)
        {
            for(int j=0; j< HEIGHT_GRID; j++)
            {
                g.initCase(i, j, this.grille[i][j].getVal());
            }
        }
        
        return g;
    }

    public void IA_Turn()
    {
        Case tmp = null;
        if(this.algoIA == 0)
        {
            tmp = (Case) this.MinMax(this, VueGrille.IA_COLOR, this.profondeur)[1];
            
        }
        else
        {
            tmp = (Case) this.AlphaBeta(this, VueGrille.IA_COLOR, this.profondeur, Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
        }
        if(tmp!=null)
        {            
            this.executeTurn(VueGrille.IA_COLOR, tmp.getLigne(), tmp.getColonne());
        }
        VueGrille.NEXT_TURN = VueGrille.PLAYER_COLOR;
    }
    
    public boolean canPlay(CaseContent color)
    {
        if(this.getPossibleMooves(color).isEmpty())
        {
            return false;
        }
        return true;
    }
}
