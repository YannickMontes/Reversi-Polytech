/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import reversi_modele.Grille;
import reversi_vue.MainWindow;

/**
 *
 * @author yannick
 */
public class Reversi
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        Grille g = new Grille();
        g.getCase(0, 0).initImages();
        MainWindow mw = new MainWindow("Reversi", g);
    }
    
}
