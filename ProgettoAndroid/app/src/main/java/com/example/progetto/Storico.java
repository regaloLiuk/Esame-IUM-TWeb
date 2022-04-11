package com.example.progetto;


public class Storico implements Comparable<Storico> {
    String username;
    String prof;
    String mat;
    String gg;
    String ora;
    String stato;

    public Storico(String username,String prof,String mat,String gg,String ora,String stato){
        this.username=username;
        this.prof=prof;
        this.mat=mat;
        this.gg=gg;
        this.ora=ora;
        this.stato=stato;
    }


    public String getUsername(){
        return this.username;
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


    public String getStato(){
        return this.stato;
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
    public String toString() {
        return this.username+","+this.prof+","+this.mat+","+this.gg+","+this.ora+","+this.stato;
    }

    @Override
    public int compareTo(Storico compare) {
        int ris=0;
        if(Integer.parseInt(this.getStato())!=Integer.parseInt(compare.getStato())){
            return Integer.parseInt(this.getStato())-Integer.parseInt(compare.getStato());
        }
        if(!this.getStato().equals(compare.getStato())){
            if(this.getStato().equals("0"))
                ris=-1;
            else
                ris=1;
        }else{
            if(!this.getGG().equalsIgnoreCase(compare.getGG())){
                ris=this.dayConvert()-compare.dayConvert();
            }
            else
            if(!this.getOra().equalsIgnoreCase(compare.getOra()))
                ris=this.getOra().compareTo(compare.getOra());
        }
        return ris;

    }

}
