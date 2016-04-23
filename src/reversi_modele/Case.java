/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi_modele;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author yannick
 */
public class Case
{
    public static BufferedImage pionNoir;
    public static BufferedImage pionBlanc;
    public static BufferedImage pionNoirFocus;
    public static BufferedImage pionBlancFocus;
    private CaseContent val;
    
    
    public Case()
    {
        val = CaseContent.VIDE;
    }
    
    public Case(CaseContent val)
    {
        this.val = val;
    }
    
    public void initImages()
    {
        try
        {
            Case.pionBlanc = ImageIO.read(getClass().getResource("/reversi_images/0.png"));
            Case.pionNoir = ImageIO.read(getClass().getResource("/reversi_images/1.png"));
            Case.pionBlancFocus = ImageIO.read(getClass().getResource("/reversi_images/focus_blanc.png"));
            Case.pionNoirFocus = ImageIO.read(getClass().getResource("/reversi_images/focus_noir.png"));
            
        } catch (IOException ex)
        {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @Override
    public String toString()
    {
        return " "+this.val.toString()+" ";
    }
}
