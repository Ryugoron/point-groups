package pointGroups.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pointGroups.gui.event.Event;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinateEvent;
import pointGroups.gui.event.types.ChangeCoordinateHandler;
import pointGroups.gui.event.types.RunEvent;
import pointGroups.gui.event.types.RunHandler;
import pointGroups.gui.event.types.ShowCoordinateEvent;
import pointGroups.gui.event.types.ShowCoordinateHandler;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;
import pointGroups.gui.event.types.ShowFundamentalDomainHandler;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.ShowLogHandler;
import pointGroups.gui.event.types.ShowNextEvent;
import pointGroups.gui.event.types.ShowNextHandler;
import pointGroups.gui.event.types.ShowPreviousEvent;
import pointGroups.gui.event.types.ShowPreviousHandler;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.TutorialHandler;

public class StatusBar extends JPanel implements ChangeCoordinateHandler, RunHandler, ShowCoordinateHandler, ShowFundamentalDomainHandler, ShowLogHandler, ShowNextHandler, ShowPreviousHandler, Symmetry3DChooseHandler,TutorialHandler 
{
  /**
   * 
   */
  private static final long serialVersionUID = 5642754641792078341L;
  private JLabel statusLabel;
  
  
  
  public StatusBar(EventDispatcher dispatcher){   
    // GUI layout
    setLayout(new BorderLayout(2, 2));
    statusLabel = new JLabel("");
    statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
    statusLabel.setForeground(Color.black);
    add(BorderLayout.CENTER, statusLabel);
    JLabel dummyLabel = new JLabel(" ");
    dummyLabel.setBorder(BorderFactory.createLoweredBevelBorder());
    add(BorderLayout.EAST, dummyLabel);
    
    // register events
    //TODO: correct?!
    dispatcher.addHandler(ChangeCoordinateEvent.TYPE, this);
    dispatcher.addHandler(RunEvent.TYPE, this);
    dispatcher.addHandler(ShowCoordinateEvent.TYPE, this);
    dispatcher.addHandler(ShowFundamentalDomainEvent.TYPE, this);
    dispatcher.addHandler(ShowLogEvent.TYPE, this);
    dispatcher.addHandler(ShowNextEvent.TYPE, this);
    dispatcher.addHandler(ShowPreviousEvent.TYPE, this);
    dispatcher.addHandler(Symmetry3DChooseEvent.TYPE, this);
    dispatcher.addHandler(TutorialEvent.TYPE, this);
  }
  
  public void setStatus(String status){
    statusLabel.setText(status);
  }
  
  public void removeStatusmessage(){
    setStatus("");
  }
  
  @Override
  public void onChangeCoordinateEvent(ChangeCoordinateEvent event) {
    setStatus("Coordinate choosen");    
  }

  @Override
  public void onRunEvent(RunEvent event) {
    StringBuilder s = new StringBuilder(200); 
    s.append("Run with point (");
    for(double d : event.getCoordinates()){
      s.append(d+", ");
    }
    s.delete(s.length()-2, s.length()-1);
    s.append(")");
    setStatus(s.toString());    
  }

  @Override
  public void onTutorialEvent(TutorialEvent event) {
    setStatus("Show Tutorial");
  }

  @Override
  public void onSymmetry3DChooseEvent(Symmetry3DChooseEvent event) {
    //TODO: toString method for a Symmetrygroup
    setStatus("Symmetry choosed: "+event.getSymmetry3D().getClass().getName());
    
  }

  @Override
  public void onPreviousEvent(ShowPreviousEvent event) {
    setStatus("Show previous point");
    
  }

  @Override
  public void onNextEvent(ShowNextEvent event) {
    setStatus("Show next point");
    
  }

  @Override
  public void onShowLogEvent(ShowLogEvent event) {
    setStatus("Show Logfile");
  }

  @Override
  public void onShowFundamentalDomainEvent(ShowFundamentalDomainEvent event) {
    if(event.getVisible()){
      setStatus("Show Fundamental domain");
    }
    else{
      setStatus("Fundamental domain is not visible");
    }
  }

  @Override
  public void onShowCoordinateEvent(ShowCoordinateEvent event) {
    if(event.getVisible()){
      setStatus("Show coordinatesystem");
    }
    else{
      setStatus("Coordinatesystem is not visible");
    }
  }  
}
