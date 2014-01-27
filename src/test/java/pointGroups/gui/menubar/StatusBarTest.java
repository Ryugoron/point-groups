package pointGroups.gui.menubar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import pointGroups.gui.StatusBar;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;

public class StatusBarTest{
public static void main(String[] args){
    final EventDispatcher dispatcher = new EventDispatcher();
    
    final double[] coord3D = {1.2, 2.4, 0.0};
    final double[] coord4D = {1.2, 2.4, 0.0,-4.5};
    
   
    final ShowFundamentalDomainEvent visEvent = new ShowFundamentalDomainEvent(true);    
    final ShowFundamentalDomainEvent invisEvent = new ShowFundamentalDomainEvent(false);


    
    JFrame main = new JFrame();
    main.getContentPane().setLayout(new BorderLayout());
    StatusBar statusBar = new StatusBar(dispatcher);
    main.getContentPane().add(BorderLayout.SOUTH, statusBar);
    main.setVisible(true);
    JButton testButton = new JButton("Test1");
    JButton testButton2 = new JButton("Test2");
    
    main.add(BorderLayout.WEST,testButton);
    main.add(BorderLayout.EAST,testButton2);
    ActionListener l1 = new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        dispatcher.fireEvent(visEvent);
        
      }
    };
    
    ActionListener l2 = new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        dispatcher.fireEvent(invisEvent);
        
      }
    };
    
    testButton.addActionListener(l1);
    testButton2.addActionListener(l2);

   }
}