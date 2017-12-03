package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.dao.AnnosDao;
import tikape.runko.dao.AnnosRaakaAineDao;
import tikape.runko.dao.RaakaAineDao;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;


public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:reseptit.db");
        database.init();

        AnnosDao annosDao = new AnnosDao(database);
        AnnosRaakaAineDao araDao = new AnnosRaakaAineDao(database);
        RaakaAineDao raDao = new RaakaAineDao(database);

        Spark.get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());

        Spark.get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer annosId = Integer.parseInt(req.params(":id"));
            map.put("annos", annosDao.findOne(annosId));
            map.put("annosRaakaAine", araDao.raakaAineetAnnokseen(annosId));
            
            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineet", raDao.findAll());
            
            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/ainekset", (req, res) -> {
            RaakaAine aine = new RaakaAine(-1, req.queryParams("nimi"));
            raDao.saveOrUpdate(aine);
            
            res.redirect("/ainekset");
            return "";
        });
        
        Spark.post("/raakaAineet/:id", (req, res) -> {
            Integer raakaAineId = Integer.parseInt(req.params(":id"));
            raDao.delete(raakaAineId);
            
            res.redirect("/ainekset");
            return "";
            });
        
        Spark.get("/resepti", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("raakaAineet", raDao.findAll());
            
            return new ModelAndView(map, "resepti");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/annokset/:id", (req, res) -> {
            Integer annosId = Integer.parseInt(req.params(":id"));
            annosDao.delete(annosId);
            
            res.redirect("/resepti");
            return "";
            });
        
        Spark.post("/annos", (req, res) -> {
            Annos annos = new Annos(-1, req.queryParams("nimi"));
            annosDao.saveOrUpdate(annos);
            
            res.redirect("/resepti");
            return "";
        });
        
        Spark.post("/resepti", (req, res) -> {
            Integer annosId = Integer.parseInt(req.queryParams("annosId"));
            Integer raakaAineId = Integer.parseInt(req.queryParams("raakaAineId"));
            Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            
            AnnosRaakaAine annosRA = new AnnosRaakaAine(raakaAineId, annosId, jarjestys, req.queryParams("maara"), req.queryParams("ohje"));
            araDao.saveOrUpdate(annosRA);
            
            res.redirect("/resepti");
            return "";
        });
        
        }       
    }
    

