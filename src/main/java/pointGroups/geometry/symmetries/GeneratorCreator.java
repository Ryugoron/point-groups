package pointGroups.geometry.symmetries;

import pointGroups.geometry.Quaternion;

import java.util.ArrayList;
import java.util.List;

public class GeneratorCreator
{
  private final List<Quaternion> generators;
  private final List<Quaternion> rotationQuaterion;
  private final List<Quaternion> newQuaterion;
  private final double epsilon;
  
  /**
   * 
   * @param generators List of Quaternions
   * @param epsilon Epsilon-approx.
   */
  public GeneratorCreator(List<Quaternion> generators, double epsilon){
    this.generators = generators;
    this.epsilon = epsilon;
    rotationQuaterion = new ArrayList<Quaternion>();
    for(Quaternion q : generators){
      rotationQuaterion.add(q);
    }
    newQuaterion = new ArrayList<Quaternion>();    
  }
  
  public List<Quaternion> start(){
    // Testcounter
    int c = 0;
    int d = 0;
    int size = 0;
    
    Quaternion x;
    Quaternion y;    
    // Rememeber all quaternions
    List<Quaternion> equalQuaternions = new ArrayList<Quaternion>(); 
    for(int i = 0; i < generators.size()-1; i++){
      
      for (int j = i+1; j < generators.size(); j++){
        Quaternion r = generators.get(i).mult(generators.get(j));
        if(!containsApprox(rotationQuaterion, r)){
          newQuaterion.add(r);
        }   
        else{
          equalQuaternions.add(r);
        }
      }
    } 
    
    while(newQuaterion.size() > 0 && size < 100){
      x = newQuaterion.remove(0);
      rotationQuaterion.add(x);
      size++;
      for(Quaternion z : rotationQuaterion){
        y = x.mult(z);
        
        if(!containsApprox(rotationQuaterion, y)){
          if(!containsApprox(equalQuaternions, y)){
            newQuaterion.add(y);          
          }
          else{
            System.out.println("Kette zu dicht an vorherig berechneten Quat.");
            d++;
            equalQuaternions.add(y);
          }
        }
        else{
          c++;
          equalQuaternions.add(y);
        }
      }
     }
    System.out.println("unnötig gerechnet #"+c+" #Ketten: "+d);
    return rotationQuaterion;
  }
  
  private boolean containsApprox(List<Quaternion> list, Quaternion x){
    for(Quaternion y : list){
      if(equalApprox(x, y)){
        return true;
      }
    }
    return false;
  }

  private boolean equalApprox(Quaternion a, Quaternion b){
    double distance = Quaternion.distance(a, b);  
    return distance < epsilon;
  }
  
  
  public static void main(String args[]){
    // example 2I = <qi,qw>
    double s = (Math.sqrt(5)-1)/2; 
    double t = (Math.sqrt(5)+1)/2;
    Quaternion qi = new Quaternion(0, 0.5, s*0.5, t*0.5);
    Quaternion qw = new Quaternion(-0.5,0.5,0.5,0.5);
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(qw);
    gen.add(qi);    
    GeneratorCreator creator = new GeneratorCreator(gen, 0.5);
    List<Quaternion> result = creator.start();
    System.out.println("Size: "+result.size());  
  }  
}
