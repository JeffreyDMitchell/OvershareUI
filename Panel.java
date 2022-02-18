import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;

public class Panel extends JPanel implements ActionListener, MouseListener
{

    Timer t;

    JLabel status;
    JTextField pfSearch;
    
    ArrayList<ContentTile> content = new ArrayList<ContentTile>();

    Point[] mousePos = new Point[2];

    Image pfp;

    boolean mousePressed = false;

    double camX = 0, camY = 0, camXVel = 0, camYVel = 0;

    int TILESIZE = 125, SCREENWIDTH = 1920, SCREENHEIGHT = 1080;


    public Panel()
    {

        Timer t = new Timer(16, this);

        status = new JLabel("this is a test");
        pfSearch = new JTextField("Search             ");

        add(pfSearch);

        setFocusable(true);
        addMouseListener(this);

        setBackground(new Color(0x546E81));

        try{

            pfp = ImageIO.read(new File("./Resources/pfp.jpg"));

        }
        catch(IOException ioe)
        {

            System.out.println("cannot read pfp");

        }

        for(int i = 0; i < mousePos.length; i++)
            mousePos[i] = MouseInfo.getPointerInfo().getLocation();

        //content.add(new ContentTile((int) (((SCREENWIDTH / 2.0) - (((TILESIZE + 10) * 10 - 10)/ 2.0))), 0, "./Resources/Dog.png"));

        for(int i = 0; i < 10; i++)
        {

            for(int j = 0; j < 10; j++)
            {

                content.add(new ContentTile((int) (((SCREENWIDTH / 2.0) - (((TILESIZE + 10) * 10 - 10)/ 2.0)) + i * (TILESIZE + 10)), j * (TILESIZE + 10), "./Resources/" +((int) (Math.random() * 24))+ ".jpg"));

            }

        }


        t.start();

    }

    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);

        g.setColor(new Color(0xBED1DF));
        g.fillRect(SCREENWIDTH / 2 - 450, 0, 900, SCREENHEIGHT);

        g.drawImage(pfp, 105, 105, 300, 300, null);
        g.setColor(Color.BLACK);
        g.drawOval(105, 105, 300, 300);
        g.setColor(Color.WHITE);
        g.drawOval(104, 104, 302, 302);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
        g.drawString("First Last", 255 - (int) (g.getFontMetrics().stringWidth("First Last") / 2.0), 450);

        g.setColor(Color.GRAY);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("@username", 255 - (int) (g.getFontMetrics().stringWidth("@username") / 2.0), 480);

        g.setColor(Color.WHITE);
        g.drawString("Followers ___ | Following ___", 255 - (int) (g.getFontMetrics().stringWidth("Followers xxx | Following xxx") / 2.0), 520);

        for(ContentTile ct: content)
        {

            double adjustedX = (((ct.x + camX) < 0 ? ((TILESIZE + 10) * 10) + ((ct.x + camX) % ((TILESIZE + 10) * 10)) : (ct.x + camX)) % ((TILESIZE + 10) * 10)) + 400, adjustedY = (((ct.y + camY) < 0 ? ((TILESIZE + 10) * 10) + ((ct.y + camY) % ((TILESIZE + 10) * 10)) : (ct.y + camY)) % ((TILESIZE + 10) * 10));

            //double scale = 1 / (Math.min(Math.sqrt(Math.pow(adjustedX - 960, 2) + Math.pow(adjustedY - 540, 2)), 500) - 500) + 1;

            //double scale = 1 - (Math.sqrt(Math.pow(adjustedX - 960, 2) + Math.pow(adjustedY - 540, 2)) / 500);

            double scale = 1 - (Math.pow(1.012, Math.sqrt(Math.pow(adjustedX - 960, 2) + Math.pow(adjustedY - 540, 2))) / 200.0);

            //scale = 1;

            if(scale >= .01)
            {

                g.setColor(ct.color);
                g.fillRect((int) (adjustedX - (TILESIZE * scale / 2.0)), (int) (adjustedY - (TILESIZE * scale / 2.0)), (int) (TILESIZE * scale), (int) (TILESIZE * scale));

                if(ct.image != null)
                {

                    g.drawImage(ct.image, (int) (adjustedX - (TILESIZE * scale / 2.0)), (int) (adjustedY - (TILESIZE * scale / 2.0)), (int) (TILESIZE * scale), (int) (TILESIZE * scale), null);

                }

                g.setColor(Color.BLACK);
                g.drawRect((int) (adjustedX - (TILESIZE * scale / 2.0)), (int) (adjustedY - (TILESIZE * scale / 2.0)), (int) (TILESIZE * scale), (int) (TILESIZE * scale));
                g.setColor(Color.WHITE);
                g.drawRect((int) (adjustedX - (TILESIZE * scale / 2.0)) - 1, (int) (adjustedY - (TILESIZE * scale / 2.0)) - 1, (int) (TILESIZE * scale) + 2, (int) (TILESIZE * scale) + 2);
                
            }

        }
            
    }

    public void actionPerformed(ActionEvent ae)
    {

        for(int i = mousePos.length - 1; i > 0; i--)
            mousePos[i] = mousePos[i - 1];

        mousePos[0] = MouseInfo.getPointerInfo().getLocation();

        if(mousePressed)
        {

            camX += mousePos[0].x - mousePos[1].x;
            camY += mousePos[0].y - mousePos[1].y;

        }

        camX += camXVel;
        camY += camYVel;

        camXVel *= .95;
        camYVel *= .95;

        repaint();

    }

    public void mouseClicked(MouseEvent e) 
    {

        

    }

    public void mouseEntered(MouseEvent e) 
    {

        

    }

    public void mouseExited(MouseEvent e) 
    {
     
        
    }

    public void mousePressed(MouseEvent e) 
    {

        mousePressed = true;
        camXVel = 0;
        camYVel = 0;

    }

    public void mouseReleased(MouseEvent e) 
    {

        mousePressed = false;

        camXVel = (mousePos[0].x - mousePos[mousePos.length - 1].x) / 2.0;
        camYVel = (mousePos[0].y - mousePos[mousePos.length - 1].y) / 2.0;
        
    }

}
