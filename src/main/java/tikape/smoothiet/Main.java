package tikape.smoothiet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) {
       
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        Map<String, Smoothie> smoothieMap = new HashMap<>();
        Map<String, RaakaAine> raakaAineMap = new HashMap<>();
        
        ArrayList<RaakaAine> ainekset = new ArrayList<>();
        ArrayList<Smoothie> smoothiet = new ArrayList<>();
        
        RaakaAine mustikka = new RaakaAine("mustikka");
        ainekset.add(mustikka);
        raakaAineMap.put("mustikka", mustikka);
        
        List<String> marjarahkasmoothiellelista = new ArrayList<>();
        marjarahkasmoothiellelista.add("1 dl");
        marjarahkasmoothiellelista.add(" ");
        
        RaakaAine rahka = new RaakaAine("rahka");
        ainekset.add(rahka);
        raakaAineMap.put("rahka", rahka);
        
        List<String> marjarahkasmoothiellelista2 = new ArrayList<>();
        marjarahkasmoothiellelista2.add("1 prk");
        marjarahkasmoothiellelista2.add(" ");
        
        RaakaAine banaani = new RaakaAine("banaani");
        ainekset.add(banaani);
        raakaAineMap.put("banaani", banaani);
        
        List<String> marjarahkasmoothiellelista3 = new ArrayList<>();
        marjarahkasmoothiellelista3.add("1 kpl");
        marjarahkasmoothiellelista3.add("Kuori ja pilko.");
        
        Smoothie smoothie1 = new Smoothie("Marja-rahkasmoothie");
        smoothiet.add(smoothie1);
        smoothieMap.put("Marja-rahkasmoothie", smoothie1);
        
        HashMap mapp1 = new HashMap<>();
        mapp1.put(mustikka, marjarahkasmoothiellelista);
        smoothie1.setMolemmat(mapp1);
        mustikka.setSmoothie(smoothie1);
        
        HashMap mapp2 = new HashMap<>();
        mapp2.put(rahka, marjarahkasmoothiellelista2);
        smoothie1.setMolemmat(mapp2);
        rahka.setSmoothie(smoothie1);
        
        HashMap mapp3 = new HashMap<>();
        mapp3.put(banaani, marjarahkasmoothiellelista3);
        smoothie1.setMolemmat(mapp3);
        banaani.setSmoothie(smoothie1);
        
        
        Spark.get("/", (req, res) -> {
            res.redirect("/smoothiearkisto");
            return "";
        });
     
        Spark.get("/raaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", ainekset);
            return new ModelAndView(map, "raaka-aineet");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothiearkisto", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothiet);
            return new ModelAndView(map, "smoothiearkisto");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("smoothiet/:nimi", (req, res) -> {
        
            HashMap map = new HashMap<>();
            map.put("nimi", req.params(":nimi"));
            
            Smoothie haluttuSmoothie = smoothieMap.get(req.params(":nimi"));
           // System.out.println(haluttuSmoothie.nimi);
            
            map.put("smoothie", haluttuSmoothie);
            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothienluominen", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothiet);
            map.put("raakaaineet", ainekset);
            return new ModelAndView(map, "smoothienluominen");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/aine/:nimi", (req, res) -> {
            String ainesosa = req.params(":nimi");
            HashMap map = new HashMap<>();
            map.put("nimi", ainesosa);
            map.put("smoothiet", raakaAineMap.get(ainesosa).getSmoothiet());
            return new ModelAndView(map, "aine");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/smoothienluominen", (req, res) -> {
            HashMap map = new HashMap<>();
            String smoothienNimi = req.queryParams("smoothienNimi");
            Smoothie luotuSmoothie = new Smoothie(smoothienNimi);
            smoothiet.add(luotuSmoothie);
            smoothieMap.put(smoothienNimi, luotuSmoothie);
         
            res.redirect("/smoothienluominen");
            return "";
          
        });

        Spark.post("/smoothienmuokkaus", (req, res) -> {
            String smoothienNimi = req.queryParams("valittusmoothie");
            String aineenNimi = req.queryParams("valitturaaka-aine");
            String aineenMaara = req.queryParams("maara");
            String aineenOhje = req.queryParams("ohje");
            
            RaakaAine raakaAine = raakaAineMap.get(aineenNimi);
            Smoothie smoothie = smoothieMap.get(smoothienNimi);
          
            String molemmista = aineenMaara + " " + aineenOhje;
            //uutta
            List<String> listaMolemmista = new ArrayList<>();
            listaMolemmista.add(aineenMaara);
            listaMolemmista.add(aineenOhje);
            HashMap mappi = new HashMap<>();
            mappi.put(raakaAine, listaMolemmista);
            smoothie.setMolemmat(mappi);
            //uutta
            //HashMap mappi = new HashMap<>(); vanhaa
            //mappi.put(raakaAine, molemmista); vanhaa
            //smoothie.setMolemmat(mappi); vanhaa
            raakaAine.setSmoothie(smoothie);
            
            res.redirect("/smoothienluominen");
            return "";
        });
        
        Spark.post("/raaka-aineet", (req, res) -> {
            String lisattava = req.queryParams("lisattava").toLowerCase();
            RaakaAine luotuRaakaAine = new RaakaAine(lisattava);
            raakaAineMap.put(lisattava, luotuRaakaAine);
            ainekset.add(luotuRaakaAine);
            res.redirect("/raaka-aineet");
            return "";
        });
    }
}

