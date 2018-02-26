
package tikape.smoothiet;

import java.util.HashMap;
import java.util.List;

public class Smoothie {
    String nimi;
    HashMap<RaakaAine, String> ainesMaaratMap = new HashMap<>();
    HashMap<RaakaAine, String> ohjeRaakaAineelleMap = new HashMap<>();
    
    //HashMap<RaakaAine, String> molemmat = new HashMap<>();
    HashMap<RaakaAine, List<String>> molemmat = new HashMap<>();
    
    public Smoothie(String nimi) {
        this.nimi = nimi;
    }
    
    public Smoothie() {
        this.nimi = "perus smoothie";
    }

    public void setAinesMaaratMap(HashMap<RaakaAine, String> ainesMaaratMap) {
        this.ainesMaaratMap = ainesMaaratMap;
    }

    public void setOhjeRaakaAineelleMap(HashMap<RaakaAine, String> ohjeRaakaAineelleMap) {
        this.ohjeRaakaAineelleMap = ohjeRaakaAineelleMap;
    }

    public HashMap<RaakaAine, String> getAinesMaaratMap() {
        return ainesMaaratMap;
    }

    public HashMap<RaakaAine, String> getOhjeRaakaAineelleMap() {
        return ohjeRaakaAineelleMap;
    }
    
    
    public Smoothie(String nimi, HashMap<RaakaAine, String> ainekset) {
        this.nimi = nimi;
        this.ainesMaaratMap = ainekset;
    }

    public String getNimi() {
        return nimi;
    }
    
    public String ToString() {
        /*
        for (int i = 0; i<ainekset.size(); i++) {
            System.out.println(ainekset.keySet() + ainekset.get(i));
        }
        */  
        return nimi;
    }
    
    public HashMap getAinekset() {
        return ainesMaaratMap;
    }
    
    public void setMolemmat(HashMap<RaakaAine, List<String>> molemmat) {
        
        this.molemmat.putAll(molemmat);
    }
    
    public HashMap<RaakaAine, List<String>> getMolemmat(){
        return molemmat;
    }
    /*
    public void setMolemmat(HashMap<RaakaAine, String> molemmat) {
        molemmat.
        this.molemmat.putAll(molemmat);
    }
 Ä/
    public HashMap<RaakaAine, String> getMolemmat(){
        return molemmat;
    }
    /*
    public HashMap getMolemmat()  {
        HashMap mappp  =new HashMap<>();
        for(RaakaAine aine : ainesMaaratMap.keySet()){
            mappp.put(aine, ainesMaaratMap.get(aine) + " " + ohjeRaakaAineelleMap.get(aine));
        }
        return mappp;
    }
    */

}