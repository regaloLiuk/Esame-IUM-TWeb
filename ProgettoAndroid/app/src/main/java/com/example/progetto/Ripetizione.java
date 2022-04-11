package com.example.progetto;


public class Ripetizione implements Comparable<Ripetizione> {

    String prof;
    String mat;
    String gg;
    String ora;

    public Ripetizione(String prof,String mat,String gg,String ora){
        this.prof=prof;
        this.mat=mat;
        this.gg=gg;
        this.ora=ora;
    }

    public String getProf(){
        return this.prof;
    }

    public String getMat(){
        return this.mat;
    }

    public String getGG(){
        return this.gg;
    }

    public String getOra(){
        return this.ora;
    }

    @Override
    public String toString() {
        return this.prof+","+this.mat+","+this.gg+","+this.ora;
    }
    private int dayConvert(){
        int ris=-1;
        if(this.getGG().equalsIgnoreCase("lunedi"))
            ris=1;
        else if(this.getGG().equalsIgnoreCase("martedi"))
            ris=2;
        else if(this.getGG().equalsIgnoreCase("mercoledi"))
            ris=3;
        else if(this.getGG().equalsIgnoreCase("giovedi"))
            ris=4;
        else
            ris=5;
        return ris;
    }

    @Override
    public int compareTo(Ripetizione compare) {
        int ris=0;
        if(!this.getGG().equalsIgnoreCase(compare.getGG())){
            ris=this.dayConvert()-compare.dayConvert();
        }
        else
        if(!this.getOra().equalsIgnoreCase(compare.getOra()))
            ris=this.getOra().compareTo(compare.getOra());

        return ris;

    }

}
