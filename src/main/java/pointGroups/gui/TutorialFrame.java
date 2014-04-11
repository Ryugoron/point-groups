package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.TutorialHandler;

public class TutorialFrame 
  extends JFrame
  implements TutorialHandler
{
  protected static int defaultWidth = 600;
  protected static int defaultHeight = 400;
  
  protected JButton prevButton;
  protected JButton nextButton;
  
  protected CardLayout layout;
  protected JPanel pages;
  
  private static final long serialVersionUID = 1467143052188689182L;
  
  public TutorialFrame() {
    this(defaultWidth,defaultHeight);
  }
  
  public TutorialFrame(int width, int height) {
    super("Tutorial");
    this.setSize(width, height);
      
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel();
    
    layout = new CardLayout();
    pages = new JPanel(layout);
    
    //pages, e.g. steps of the tutorial
    JPanel step1 = new JPanel();
    step1.setBackground(Color.RED);
    JPanel step2 = new JPanel();
    step2.setBackground(Color.BLUE);
    JPanel step3 = new JPanel();
    step3.setBackground(Color.GREEN);
    
    // adding pages
    pages.add(step1);
    pages.add(step2);
    pages.add(step3);
    
    prevButton = new JButton("<");
    nextButton = new JButton(">");
    
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
