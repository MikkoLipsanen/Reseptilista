/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.Comparator;

/**
 *
 * @author Mikko
 */
public class AnnosRaakaAine {
    
    private Integer raaka_aine_id;
    private Integer annos_id;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public AnnosRaakaAine(Integer raaka_aine_id, Integer annos_id, Integer jarjestys, String maara, String ohje) {
        this.raaka_aine_id = raaka_aine_id;
        this.annos_id = annos_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getRaakaAineId() {
        return raaka_aine_id;
    }

    public void setRaakaAineId(Integer raaka_aine_id) {
        this.raaka_aine_id = raaka_aine_id;
    }
    
     public Integer getAnnosId() {
        return annos_id;
    }

    public void setAnnosId(Integer annos_id) {
        this.annos_id = annos_id;
    }
    
    public Integer getJarjestys(){
        return jarjestys;
    }
    
     public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }
    
    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
    public static Comparator<AnnosRaakaAine> araComp = new Comparator<AnnosRaakaAine>() {

	public int compare(AnnosRaakaAine a1, AnnosRaakaAine a2) {

	   int jarjestys1 = a1.getJarjestys();
	   int jarjestys2 = a2.getJarjestys();

	   return jarjestys1-jarjestys2;
        
    }};
}
            
