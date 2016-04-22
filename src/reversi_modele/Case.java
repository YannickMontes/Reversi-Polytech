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
    private static BufferedImage pionNoir;
    private static BufferedImage pionBlanc;
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
        if(this.val == CaseContent.BLANC)
        {
            return pionBlanc;
        }
        else if(this.val == CaseContent.NOIR)
        {
            return pionNoir;
        }
        return null;
    }

    @Override
    public String toString()
    {
        return " "+this.val.toString()+" ";
    }
}
