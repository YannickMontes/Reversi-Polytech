/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_modele;

/**
 *
 * @author yannick
 */
public class Grille
{
    public final int HEIGHT_GRID = 8;
    public final int WIDTH_GRID = 8;
    
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
        this.grille[3][3].setVal(0);
        this.grille[4][4].setVal(0);
        this.grille[4][3].setVal(1);
        this.grille[3][4].setVal(1);
    }
    
    public Case getCase(int ligne, int colonne)
    {
        return this.grille[ligne][colonne];
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
}
