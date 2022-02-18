import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ContentTile 
{

    public Color color;
    public Image image = null;
    public int x, y;

    public ContentTile(int x, int y)
    {

        this.x = x;
        this.y = y;

        color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

    }

    public ContentTile(int x, int y, String imgPath)
    {

        this.x = x;
        this.y = y;

        color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

        try
        {

            image = ImageIO.read(new File(imgPath));

        }
        catch(IOException ie)
        {

            System.out.println("DID NOT WORK");
            image = null;

        }

    }

}
