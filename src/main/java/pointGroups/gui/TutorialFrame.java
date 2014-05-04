package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.TutorialHandler;
import pointGroups.util.PointGroupsUtility;

public class TutorialFrame 
  extends JFrame
  implements TutorialHandler
{
  protected static int defaultWidth = 600;
  protected static int defaultHeight = 400;
  
  protected JButton prevButton;
  protected JButton nextButton;
  protected JButton startButton;
  
  protected CardLayout layout;
  protected JPanel pages;
  
  private static final long serialVersionUID = 1467143052188689182L;
  
  public TutorialFrame() {
    this(defaultWidth,defaultHeight);
  }
  
  public TutorialFrame(int width, int height) {
    super("Tutorial");
    this.setSize(width, height);
    this.setResizable(false);
    this.setLocationRelativeTo(this.getParent());
      
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel();
    
    layout = new CardLayout();
    pages = new JPanel(layout);
    
    //pages, e.g. steps of the tutorial
    String dir_step1;
    String dir_step2;
    String dir_step3;
    
    try {
      dir_step1 = PointGroupsUtility.getImage("icon.png").toString();
      dir_step2 = PointGroupsUtility.getImage("icon.png").toString();
      dir_step3 = PointGroupsUtility.getImage("icon.png").toString();
    } catch(IOException e) {
        dir_step1 = "Users/Simon/Desktop/image-114470-galleryV9-fisq.jpg";
        dir_step2 = "Users/Simon/Desktop/image-114470-galleryV9-fisq.jpg";
        dir_step3 = "Users/Simon/Desktop/image-114470-galleryV9-fisq.jpg";
    }
    
    
    JPanel step1 = new TutorialPage(dir_step1, "Step 1: Choose a symmetry group\nYou can choose a symmetry group on the top left side. In the left column are all available groups listed. After selecting one of them you can choose in the right column one of its subgroups to work with. With the 3D|4D button you can switch between three and four dimensional space.");
    JPanel step2 = new TutorialPage(dir_step2, "Step 2: Pick a point\nDrag the spot to pick a point in the fundamental domain. The coordinates of the point will be displayed below this panel. The point will be mapped under all symmetries according to the symmetry group you chose before. The resulting point group will be shown on the main panel.\nAlternatively you can put specific coordinates manually in the displayed fields below or generate random coordinates.\nAdditionally you can scale the model by using the slider. ");
    JPanel step3 = new TutorialPage(dir_step3, "Cras mattis consectetur purus sit amet fermentum. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.");
    // adding pages
    pages.add(step1);
    pages.add(step2);
    pages.add(step3);

    startButton = new JButton("Start");
    prevButton = new JButton("<");
    nextButton = new JButton(">");
    
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        layout.first(pages);
      }      
    });
    
    prevButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        layout.previous(pages);
      }      
    });
    
    nextButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        layout.next(pages);
      }      
    });
    
    buttonPanel.add(startButton);
    buttonPanel.add(prevButton);
    buttonPanel.add(nextButton);
    
    mainPanel.add(pages, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    this.add(mainPanel);
    
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    EventDispatcher.get().addHandler(TutorialEvent.TYPE, this);
  }

  @Override
  public void onTutorialEvent(TutorialEvent event) {
    this.setVisible(true);
    this.toFront();
  }

}
