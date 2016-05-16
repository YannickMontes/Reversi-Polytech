package reversi;

import reversi_modele.Case;
import reversi_vue.MainWindow;

/**
 * Main
 * @author yannick
 */
public class Reversi
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        Case.initImages();
        MainWindow mw = new MainWindow("Reversi");
    }
    
}
