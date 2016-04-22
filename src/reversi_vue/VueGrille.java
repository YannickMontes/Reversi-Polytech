/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import reversi_modele.CaseContent;
import reversi_modele.Grille;

/**
 *
 * @author yannick
 */
public class VueGrille extends JComponent
{
    private Grille grille;

    public VueGrille(Grille g)
    {
        super();
        this.grille = g;
    }                   

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
                if(this.grille.getCase(i, j).getVal()!=CaseContent.VIDE)
                {
                    g2.drawImage(this.grille.getCase(i, j).getImage(), j*100, i*100, this);
                }
            }
        }
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(801,801);
    }                 
}
