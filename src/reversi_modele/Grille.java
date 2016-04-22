/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_modele;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yannick
 */
public class Grille
{
    public static final int HEIGHT_GRID = 8;
    public static final int WIDTH_GRID = 8;
    
    private Case[][] grille;
    
    public Grille()
    {
        this.grille = new Case[HEIGHT_GRID][WIDTH_GRID];
        initGrille();
    }
    
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
    
    public Case getCase(int ligne, int colonne)
    {
        return this.grille[ligne][colonne];
    }
    
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
        this.majGrid(ligne-1, colonne-1, 0, color);
        this.majGrid(ligne-1, colonne, 1, color);
        this.majGrid(ligne-1, colonne+1, 2, color);
        this.majGrid(ligne, colonne+1, 3, color);
        this.majGrid(ligne+1, colonne+1, 4, color);
        this.majGrid(ligne+1, colonne, 5, color);
        this.majGrid(ligne+1, colonne-1, 6, color);
        this.majGrid(ligne, colonne-1, 7, color);
    }

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

    private boolean majGrid(int ligne, int colonne, int direction, CaseContent color)
    {
        if(ligne < 0 || ligne > HEIGHT_GRID-1 || colonne < 0 || colonne > WIDTH_GRID -1)
        {
            return false;
        }
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
        switch(direction)
        {
            case 0:
                if(ligne > 0 && colonne > 0)
                {
                    if(this.majGrid(ligne-1, colonne-1, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
                    if(this.majGrid(ligne-1, colonne, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
                    if(this.majGrid(ligne-1, colonne+1, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
                    if(this.majGrid(ligne, colonne+1, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
                    if(this.majGrid(ligne+1, colonne+1, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
                    if(this.majGrid(ligne+1, colonne, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
                    if(this.majGrid(ligne+1, colonne-1, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
                    if(this.majGrid(ligne, colonne-1, direction, color))
                    {
                        this.grille[ligne][colonne].setVal(color);
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
}
