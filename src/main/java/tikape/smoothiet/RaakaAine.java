
package tikape.smoothiet;

import java.util.ArrayList;
import java.util.List;

public class RaakaAine {
    String nimi;
    List<Smoothie> list = new ArrayList<>();
    
    public RaakaAine(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }
    
    public String toString() {
        return nimi;
    }
    
    public void setSmoothie(Smoothie smoothie){
        this.list.add(smoothie);
    }
    
    public List getSmoothiet(){
        return list;
    }
    
}
