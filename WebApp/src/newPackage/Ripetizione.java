package newPackage;
import java.util.ArrayList;

public class Ripetizione {
    private String PROF;
    private int IDPROF;
    private String MATERIA;
    private int I;
    private String GIORNO;
    private int ORA;
    private int STATO;  //segnala disponibilità ripetizione

    public Ripetizione(String prof, int idprof, String corso, int i, String gg, int ora, int stato){
        PROF = prof;
        IDPROF = idprof;
        MATERIA = corso;
        I = i;
        GIORNO = gg;
        ORA = ora;
        STATO = stato;
    }


    public String getProf(){return PROF;}
    public int getIdProf(){return IDPROF;}
    public String getMateria(){return MATERIA;}
    public String getGiorno(){return GIORNO;}
    public int getOra(){return ORA;}
    public int getStato(){return STATO;}
    public String toString(){return "Professore: " + getIdProf() + " " + getProf() + " Materia: " + getMateria() +
                                    " Giorno: " + getGiorno() + " Ora: " + getOra() + "\n";
    }


}
