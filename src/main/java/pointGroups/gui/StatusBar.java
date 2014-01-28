package pointGroups.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinate3DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate3DPointHandler;
import pointGroups.gui.event.types.ChangeCoordinate4DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate4DPointHandler;
import pointGroups.gui.event.types.Schlegel3DComputeEvent;
import pointGroups.gui.event.types.Schlegel3DComputeHandler;
import pointGroups.gui.event.types.Schlegel4DComputeEvent;
import pointGroups.gui.event.types.Schlegel4DComputeHandler;
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
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.TutorialHandler;

public class StatusBar extends JPanel implements ChangeCoordinate4DPointHandler,ChangeCoordinate3DPointHandler, Schlegel3DComputeHandler,Schlegel4DComputeHandler , ShowCoordinateHandler, ShowFundamentalDomainHandler, ShowLogHandler, ShowNextHandler, ShowPreviousHandler, Symmetry3DChooseHandler, Symmetry4DChooseHandler, TutorialHandler 
{
  /**
   * 
   */
  private static final long serialVersionUID = 5642754641792078341L;
  private JLabel statusLabel;
  
  
  
  public StatusBar(EventDispatcher dispatcher){   
    // GUI layout
    setLayout(new BorderLayout(2, 2));
    statusLabel = new JLabel(" ");
    statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
    statusLabel.setForeground(Color.black);
    add(BorderLayout.CENTER, statusLabel);
    
    // register events
    dispatcher.addHandler(ChangeCoordinate3DPointEvent.TYPE, this);
    dispatcher.addHandler(ChangeCoordinate4DPointEvent.TYPE, this);
    dispatcher.addHandler(Schlegel3DComputeEvent.TYPE, this);
    dispatcher.addHandler(Schlegel4DComputeEvent.TYPE, this);
    dispatcher.addHandler(ShowCoordinateEvent.TYPE, this);
    dispatcher.addHandler(ShowFundamentalDomainEvent.TYPE, this);
    dispatcher.addHandler(ShowLogEvent.TYPE, this);
    dispatcher.addHandler(ShowNextEvent.TYPE, this);
    dispatcher.addHandler(ShowPreviousEvent.TYPE, this);
    dispatcher.addHandler(Symmetry3DChooseEvent.TYPE, this);
    dispatcher.addHandler(TutorialEvent.TYPE, this);
  }
  
  public void setStatus(String status){
    Calendar now = Calendar.getInstance();
    int hour = now.get(Calendar.HOUR_OF_DAY);
    int minute = now.get(Calendar.MINUTE);
    if(minute < 10){
      statusLabel.setText("["+hour+":0"+minute+"] "+status);

    }
    statusLabel.setText("["+hour+":"+minute+"] "+status);
  }
  
  public void removeStatusmessage(){
    setStatus(" ");
  }
  
  

 

  @Override
  public void onTutorialEvent(TutorialEvent event) {
    setStatus("Show Tutorial");
  }

  @Override
  public void onSymmetry3DChooseEvent(Symmetry3DChooseEvent event) {
    setStatus("Symmetry choosed: "+event.getSymmetry3D().getName());
    
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
      setStatus("Fundamental domain is hidden");
    }
  }

  @Override
  public void onShowCoordinateEvent(ShowCoordinateEvent event) {
    if(event.getVisible()){
      setStatus("Show coordinatesystem");
    }
    else{
      setStatus("Coordinatesystem is hidden");
    }
  }

  @Override
  public void onSchlegel4DComputeEvent(Schlegel4DComputeEvent event) {
   showComputeInfo(event.getPickedPoint().getComponents(),event.getSymmetry4D().getName(),event.getSubgroup().intern());
    
  }

  @Override
  public void onSchlegel3DComputeEvent(Schlegel3DComputeEvent event) {    
    showComputeInfo(event.getPickedPoint().getComponents(),event.getSymmetry3D().getName(),event.getSubgroup().intern());
  }
  
  private void showComputeInfo(double[] coords, String symgroup, String subgroup){
    StringBuilder s = new StringBuilder(300); 
    s.append("Run with point (");
    for(double d : coords){
      s.append(d+", ");
    }
    s.delete(s.length()-2, s.length()-1);
    s.append(") Symmetry group: ");
    s.append(symgroup);
    s.append(" Subroup: ");
    s.append(subgroup);
    
    setStatus(s.toString()); 
  }

  @Override
  public void
      onChangeCoordinate3DPointEvent(ChangeCoordinate3DPointEvent event) {
    setStatus("Coordinate choosen");   
  }

  @Override
  public void
      onChangeCoordinate4DPointEvent(ChangeCoordinate4DPointEvent event) {
    setStatus("Coordinate choosen"); 
    
  }

  @Override
  public void onSymmetry4DChooseEvent(Symmetry4DChooseEvent event) {
    setStatus("Symmetry choosed: "+event.getSymmetry4D().getName());
    
  }  
}
