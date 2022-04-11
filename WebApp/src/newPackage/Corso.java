package newPackage;

public class Corso {
    private String materia;

    public Corso(String materia){
        this.materia = materia;
    }
    public String getMateria(){
        return materia;
    }
    public String toString(){
        return "Nome: " + getMateria();
    }
}
