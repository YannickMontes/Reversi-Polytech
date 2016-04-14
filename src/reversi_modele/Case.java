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
public class Case
{
    private int val;
    
    public Case()
    {
        val = -1;
    }
    
    public Case(int val)
    {
        this.val = val;
    }
    
    public int getVal()
    {
        return this.val;
    }
    
    public void setVal(int val)
    {
        this.val = val;
    }

    @Override
    public String toString()
    {
        return Integer.toString(this.val);
    }
}
