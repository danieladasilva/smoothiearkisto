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
        //if (System.env("PORT") != null) {
        //    Spark.port(Integer.valueOf(System.getenv("PORT")));
        //}
        Map<String, Smoothie> smoothieMap = new HashMap<>();
        Map<String, RaakaAine> raakaAineMap = new HashMap<>();
        
        ArrayList<RaakaAine> ainekset = new ArrayList<>();
        ArrayList<Smoothie> smoothiet = new ArrayList<>();
        /*
        ainekset.add(new RaakaAine("banaani"));
        ainekset.add(new RaakaAine("mustikka"));
        RaakaAine mustikka = new RaakaAine("mustikka");
     
        HashMap marjarahkasmoothie = new HashMap<>();
        marjarahkasmoothie.put(new RaakaAine("villivadelma"), "2 dl");
        marjarahkasmoothie.put(mustikka, "1 dl");
        
        smoothiet.add(new Smoothie("Marja-rahkasmoothie",
                                    marjarahkasmoothie));
        smoothiet.add(new Smoothie("Vihersmoothie"));
        */
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
            System.out.println(haluttuSmoothie.nimi);
            
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

