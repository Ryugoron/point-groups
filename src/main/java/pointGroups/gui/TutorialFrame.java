package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
      
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel();
    
    layout = new CardLayout();
    pages = new JPanel(layout);
    
    //pages, e.g. steps of the tutorial

    JPanel step1 = new TutorialPage("/Users/Simon/Desktop/image-114470-galleryV9-fisq.jpg", "Nulla vitae elit libero, a pharetra augue. Maecenas faucibus mollis interdum. Donec ullamcorper nulla non metus auctor fringilla. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Cras mattis consectetur purus sit amet fermentum.");
    JPanel step2 = new TutorialPage("/Users/Simon/Desktop/image-114470-galleryV9-fisq.jpg", "Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Aenean lacinia bibendum nulla sed consectetur.");
    JPanel step3 = new TutorialPage("/Users/Simon/Desktop/image-114470-galleryV9-fisq.jpg", "Cras mattis consectetur purus sit amet fermentum. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.");
    
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
