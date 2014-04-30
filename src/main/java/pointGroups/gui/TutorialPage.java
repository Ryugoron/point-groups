package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TutorialPage
  extends JPanel
{
  private static final long serialVersionUID = -6464765447996926961L;
  
  protected String imageFilePath;
  protected String discriptionFilePath;
  
  protected ImageComponent image;
  protected JTextArea discription;
  
  
  public TutorialPage(String imageFilePath, String discriptionFilePath){
    
    this.setLayout(new BorderLayout());
    
    // initialize discription component
    discription = new JTextArea(discriptionFilePath);
    discription.setColumns(15);
    discription.setLineWrap(true);
    discription.setWrapStyleWord(true);
    
    // initialize image component
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(imageFilePath));     
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    image = new ImageComponent(img);
    this.add(image);
    this.add(discription,BorderLayout.WEST);
  }
 
  
  
  
  class ImageComponent extends JComponent
  {
    private static final long serialVersionUID = -4574598357058691747L;
    
    private BufferedImage image;

    public ImageComponent(BufferedImage image)
    {
      this.image = image;
      setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
      repaint();
      invalidate();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
      
      Dimension par = this.getParent().getSize();
      Dimension text = discription.getSize();
      if (image != null)
        g.drawImage(image.getScaledInstance(par.width - text.width, par.height, Image.SCALE_DEFAULT), 0, 0, this);
    }
  }
  
}
