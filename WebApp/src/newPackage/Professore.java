package newPackage;

public class Professore {
        private String NOME;
        private String COGNOME;
        private int ID;

        public Professore(String nome, String cognome, int id){
            NOME=nome;
            COGNOME=cognome;
            ID=id;
        }
        public String getNome(){
            return NOME;
        }
        public String getCognome(){
            return COGNOME;
        }
        public int getId(){
            return ID;
        }

        public String toString(){
            return "Nome: " + getNome() + " Cognome: " + getCognome() + " Id: " + getId();
        }
}
