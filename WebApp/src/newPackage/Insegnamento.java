package newPackage;

public class Insegnamento {
    private int IDPROF;
    private String PROF;
    private String MATERIA;

    public Insegnamento(int id, String prof, String corso){
        IDPROF=id;
        PROF=prof;
        MATERIA=corso;
    }

    public int getIDPROF(){return IDPROF;}
    public String getPROF(){return PROF;}
    public String getMATERIA(){return MATERIA;}

}
