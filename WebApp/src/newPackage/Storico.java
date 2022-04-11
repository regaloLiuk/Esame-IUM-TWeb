package newPackage;

public class Storico {

    private int IDPROF;
    private String PROF;
    private String MATERIA;
    private String GIORNO;
    private int ORA;
    private int STATO;  //segnala se effettuata o no
    private String UTENTE;

    public Storico(String u, int idprof, String prof, String corso, String gg, int ora, int stato){
        IDPROF = idprof;
        PROF = prof;
        MATERIA = corso;
        GIORNO = gg;
        ORA = ora;
        STATO = stato;
        UTENTE = u;
    }
    public int getIdProf(){return IDPROF;}
    public String getProf(){return PROF;}
    public String getMateria(){return MATERIA;}
    public String getGiorno(){return GIORNO;}
    public int getOra(){return ORA;}
    public int getStato(){return STATO;}
    public String getUtente(){return UTENTE;}
    public String toString(){return "Utente: " + getUtente() + " Professore: " + getIdProf() + " " + getProf() + " Materia: " + getMateria() +
                                    " Giorno: : " + getGiorno() + " Ora: " + getOra() + " Stato: " + getStato() + "\n";
    }
}
