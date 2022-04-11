package newPackage;

public class Utente {
    private String user;
    private String password;
    private int ruolo;
    private String nome;
    private String cognome;
    private String email;

    public Utente (String user, String password, int ruolo, String nome, String cognome, String email){
        this.user = user;
        this.password = password;
        this.ruolo = ruolo;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
    public String getUser(){
        return user;
    }
    public String getPassword(){
        return password;
    }
    public int getRuolo() {
        return ruolo;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getEmail() {
        return email;
    }
    public String toString(){
        return "User: " + getUser() + " Password: " + getPassword() + " Ruolo: " + getRuolo() + " Nome: " + getNome() + " Cognome: " + getCognome() + " Email: " + getEmail();
    }
}
