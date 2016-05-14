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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import reversi_modele.Case;
import reversi_modele.CaseContent;
import reversi_modele.Grille;

/**
 *
 * @author yannick
 */
public class VueGrille extends JComponent implements MouseMotionListener, MouseListener
{
    private Grille grille;
    public static CaseContent NEXT_TURN;
    public static CaseContent PLAYER_COLOR;
    public static CaseContent IA_COLOR;
    private Case focused;

    public VueGrille(Grille g, CaseContent playercolor)
    {
        super();
        VueGrille.PLAYER_COLOR = playercolor;
        if(VueGrille.PLAYER_COLOR == CaseContent.NOIR)
        {
            VueGrille.IA_COLOR = CaseContent.BLANC;
        }
        else
        {
            VueGrille.IA_COLOR = CaseContent.NOIR;
        }
        focused = null;
        this.NEXT_TURN = CaseContent.NOIR;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
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
                if(this.grille.getCase(i, j).getVal()!=CaseContent.VIDE && this.grille.getCase(i,j)!=focused)
                {
                    g2.drawImage(this.grille.getCase(i, j).getImage(), j*100, i*100, this);
                }
                else if(this.grille.getCase(i, j) == focused)
                {
                    if(NEXT_TURN == CaseContent.NOIR)
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

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if(VueGrille.NEXT_TURN == VueGrille.PLAYER_COLOR)
        {
            int i = e.getY()/100;
            int j = e.getX()/100;
            if((i>=0 && i<Grille.HEIGHT_GRID && j>=0 && j<Grille.WIDTH_GRID) && this.grille.getCase(i, j).getVal()==CaseContent.VIDE)
            {
                if(this.grille.isPlayable(e.getY()/100, e.getX()/100, NEXT_TURN))
                {
                    focused = this.grille.getCase(i, j);
                }
                else
                {
                    focused = null;
                }
                this.repaint();
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

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(this.grille.isPlayable(e.getY()/100, e.getX()/100, NEXT_TURN))
        {
            this.grille.executeTurn(NEXT_TURN,e.getY()/100, e.getX()/100);
            if(NEXT_TURN == CaseContent.BLANC)
            {
                NEXT_TURN = CaseContent.NOIR;
            }
            else
            {
                NEXT_TURN = CaseContent.BLANC;
            }
            this.repaint();
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
}
