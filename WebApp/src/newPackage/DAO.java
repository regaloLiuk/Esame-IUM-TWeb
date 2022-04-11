package newPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DAO {
    private final static String url = "jdbc:mysql://localhost:3306/ripetizioni?autoReconnect=true&useSSL=false";
    private final static String USER = "root";
    private final static String PWD = "";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    /////ANDROID/////
    /**OPERAZIONI SU PROFESSORI*/

    public static ArrayList<Professore> getProf() {
        ArrayList<Professore> doc = new ArrayList<Professore>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PROFESSORE");
            while (rs.next()) {
                Professore d = new Professore(rs.getString("nome"), rs.getString("cognome"), rs.getInt("id"));
                doc.add(d);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return doc;
    }
    public static void addProf(String nome, String cognome, int id) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO PROFESSORE (NOME, COGNOME, ID) VALUES('" + nome + "','" + cognome + "'," + id + ")");
            st.close();
            conn.close();
            System.out.println("aggiunto docente: " + nome);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    public static void removeProf(int id) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM PROFESSORE WHERE  ID = '"+ id+"'");
            st.close();
            conn.close();
            System.out.println("rimosso docente con id: " + id);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    /**OPERAZIONI SU UTENTE*/

    public static ArrayList<Utente> getUtenti() {
        ArrayList<Utente> utenti = new ArrayList<Utente>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTE");
            while (rs.next()) {
                Utente u = new Utente(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getInt("RUOLO"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"));
                utenti.add(u);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return utenti;
    }
    public static void addUtente(String user, String password, int ruolo) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO UTENTE (USERNAME, PASSWORD, RUOLO) VALUES('" + user + "','" + password + "',"  + ruolo + ")");
            st.close();
            conn.close();
            System.out.println("aggiunto Utente: " + user);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    public static void removeUtente(String user) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM UTENTE WHERE  USERNAME = '"+ user+"'");
            st.close();
            conn.close();
            System.out.println("rimosso user: " + user);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    public static Utente getUtente(String user) {
        Utente u = null;
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTE WHERE  USERNAME = '"+ user+"'");
            rs.next();
            u = new Utente(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getInt("RUOLO"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"));
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return u;
        }finally {
            return u;
        }
    }

    /**OPERAZIONI SU MATERIA*/
    public static ArrayList<Corso> getMaterie() {
        ArrayList<Corso> corsi = new ArrayList<Corso>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CORSO");
            while (rs.next()) {
                Corso c = new Corso(rs.getString("MATERIA"));
                corsi.add(c);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return corsi;
    }
    public static void addCorso(String materia) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO CORSO (MATERIA) VALUES('" + materia +"')");
            st.close();
            conn.close();
            System.out.println("aggiunto corso di: " + materia);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    public static void removeCorso(String materia) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM CORSO WHERE  MATERIA = '"+ materia +"'");
            st.close();
            conn.close();
            System.out.println("rimosso corso di: " + materia);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    /**OPERAZIONI SU RIPETIZIONI*/

    public static void addRipetizione(int prof, String materia, String gg, int ora){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO RIPETIZIONE (IDPROF, MATERIA, GIORNO, ORA, STATO)" +
                    " VALUES('" + prof + "','" + materia + "','" + gg + "','" + ora +"'," + 0 + ")");
            st.close();
            conn.close();
            System.out.println("aggiunto corso di: " + materia);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    //ricerca per stato
    public static ArrayList<Ripetizione> getRipetizione() {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE RIPETIZIONE.STATO = 0");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per materia
    public static ArrayList<Ripetizione> getRipetizione(String materia) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    // ricerca per per materia-giorno
    public static ArrayList<Ripetizione> getRipetizione(String materia, String gg) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND GIORNO = '" + gg + "' AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per materia-professore
    public static ArrayList<Ripetizione> getRipetizione(String materia, String nome,String cognome) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND NOME = '" + nome + "' AND COGNOME = '" + cognome + "' AND MATERIA = '" + materia + "'");;
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }

    //ricerca per giorno
    public static ArrayList<Ripetizione> getRipetizioni(String gg) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND GIORNO = '" + gg + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per materia-professore-giorno
    public static ArrayList<Ripetizione> getRipetizioni(String materia, String nome,String cognome,String gg) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND NOME = '" + nome + "' AND COGNOME = '" + cognome + "' AND MATERIA = '" + materia + "'AND GIORNO = '" + gg + "'");;
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per professore-giorno
    public static ArrayList<Ripetizione> getRipetizioni(String nome,String cognome,String gg) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND NOME = '" + nome + "' AND COGNOME = '" + cognome + "'AND GIORNO = '" + gg + "'");;
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per professore-giorno-ora
    public static ArrayList<Ripetizione> getRipetizioni(String nome,String cognome,String gg,int ora) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND NOME = '" + nome + "' AND COGNOME = '" + cognome + "'AND GIORNO = '" + gg + "' AND ORA = '"+ora+"'");;
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per professore
    public static ArrayList<Ripetizione> getRipetizioni(String nome,String cognome) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND NOME = '" + nome + "' AND COGNOME = '" + cognome + "'");;
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per materia-giorno-ora
    public static ArrayList<Ripetizione> getRipetizione(String materia, String gg, int ora) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND GIORNO = '" + gg + "' AND ORA = '" + ora + "' AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca completa
    public static ArrayList<Ripetizione> getRipetizione(String materia, String gg, int ora, String nome, String cognome) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND GIORNO = '" + gg + "' AND ORA = '" + ora + "' AND NOME = '" + nome + "' AND COGNOME = '" + cognome + "' AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }

    //  PRENOTAZIONE RIPETIZIONE
    public static boolean prenotazione(String prof, String materia, String gg, int ora, String ut) {
        try {

            StringTokenizer tokenizer=new StringTokenizer(prof," ");
            int id=getId(tokenizer.nextToken(),tokenizer.nextToken());
            System.out.println("id:"+id);
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT STATO FROM RIPETIZIONE WHERE IDPROF = '" + id + "'AND MATERIA = '" + materia +
                    "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();

            int ris = rs.getInt("STATO");
            if (ris == 0) {

                st.executeUpdate("UPDATE RIPETIZIONE SET STATO = 1 WHERE IDPROF = '" + id + "'AND MATERIA = '" + materia +
                        "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
                addStorico(ut, id, prof, materia, gg, ora);
                System.out.println("prenotata lezione di: " + materia + "per il giorno: " + gg + " alle ore: " + ora);

            }else{
                System.out.println("ERRORE RIPETIZIONE NON DISPONIBILE");
                st.close();
                rs.close();
                conn.close();
                return false;
            }
            System.out.println("--------1");
            st.close();
            System.out.println("--------2");
            rs.close();
            System.out.println("--------3");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return true;
    }

    public static void libera(String nome,String cognome, String materia, String gg, int ora) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            conn.setAutoCommit(false);

            int prof=getId(nome,cognome);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT STATO FROM RIPETIZIONE WHERE IDPROF = '" + prof + "'AND MATERIA = '" + materia +
                    "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();
            int ris = rs.getInt("STATO");

            if (ris == 1) {
                st.executeUpdate("UPDATE RIPETIZIONE SET STATO = 0 WHERE IDPROF = '" + prof + "'AND MATERIA = '" + materia +
                        "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            }else{
                System.out.println("ERRORE RIPETIZIONE NON OCCUPATA");
            }
            st.close();
            rs.close();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    /**OPERAZIONI SU STORICO*/
    public static ArrayList<Storico> getStorico(String u){
        ArrayList<Storico> storico = new ArrayList<Storico>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            System.out.println("//////////////////////////"+u);
            ResultSet rs = st.executeQuery("SELECT * FROM STORICO JOIN PROFESSORE ON(IDPROF=ID) WHERE USERNAME = '" + u + "'");
            while (rs.next()) {
                Storico s = new Storico(rs.getString("USERNAME"), rs.getInt("IDPROF"), rs.getString("PROFESSORE"), rs.getString("MATERIA"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                storico.add(s);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return storico;
    }

    public static void removeLezione(String ut, String mat, String nome,String cognome, String gg, int ora){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            conn.setAutoCommit(false);
            int prof=getId(nome,cognome);
            ResultSet rs = st.executeQuery("SELECT STATO FROM STORICO WHERE USERNAME = '" + ut + "' AND IDPROF = '" + prof + "' AND MATERIA = '" + mat +
                    "' AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();
            int ris = rs.getInt("STATO");
            if (ris == 0) {
                st.executeUpdate("DELETE FROM STORICO WHERE USERNAME = '" + ut + "' AND IDPROF = '" + prof + "' AND MATERIA = '" + mat +
                        "' AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
                System.out.println("Rimossa lezione di: " + mat + " del il giorno: " + gg + " alle ore: " + ora);
                libera(nome,cognome,mat,gg,ora);
            }else{
                System.out.println("ERRORE RIPETIZIONE GIA EFFETTUATA");
            }
            rs.close();
            st.close();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static void addStorico(String ut, int id, String prof,String mat, String gg, int ora){
        try{
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO STORICO (USERNAME, IDPROF, PROFESSORE ,MATERIA, GIORNO, ORA, STATO) " +
                    "VALUES('" + ut + "','" + id + "','"+ prof +"','"  + mat + "','" + gg + "','" + ora + "','" + 0 + "')");
            st.close();
            conn.close();
            System.out.println("aggiunta ripetizione a utente: " + ut);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    public static void disdici(String ut, String mat, String nome, String cognome, String gg, int ora){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            conn.setAutoCommit(false);
            int prof=getId(nome,cognome);
            ResultSet rs = st.executeQuery("SELECT STATO FROM STORICO WHERE USERNAME = '" + ut + "' AND IDPROF = '" + prof +  "' AND MATERIA = '" + mat +
                    "' AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();
            int ris = rs.getInt("STATO");
            if (ris == 0) {
                st.executeUpdate("DELETE FROM STORICO  WHERE USERNAME = '" + ut + "' AND IDPROF = '" + prof + "' AND MATERIA = '" + mat +
                        "' AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
                System.out.println("Rimossa lezione di: " + mat + " del il giorno: " + gg + " alle ore: " + ora);
                libera(nome,cognome,mat,gg,ora);
            }else{
                System.out.println("ERRORE RIPETIZIONE GIA EFFETTUATA");
            }
            rs.close();
            st.close();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }

    }


    public static void segnaEffettuata(String ut, String mat, String nome,String cognome, String gg, int ora){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            conn.setAutoCommit(false);
            int prof=getId(nome,cognome);
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE STORICO SET STATO = 1 WHERE IDPROF = '" + prof + "'AND MATERIA = '" + mat +
                    "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            System.out.println("Effettuata la lezione di: " + mat + " del il giorno: " + gg + " alle ore: " + ora);
            st.close();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }

    }

    public static int getId(String nome,String cognome){
        int result=-1;
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();


            ResultSet rs = st.executeQuery("SELECT ID FROM PROFESSORE WHERE NOME = '" + nome + "' AND COGNOME = '" + cognome + "'");
            if(rs==null)
                rs = st.executeQuery("SELECT ID FROM PROFESSORE WHERE NOME = '" + nome + "' AND COGNOME = '" + cognome + "'");

            rs.first();

            result=rs.getInt("id");

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return result;
    }

    //////////////////WEB///////////////////////////////////////////////////////////////////////////////////////////////

    /**OPERAZIONI SU PROFESSORI*/

    public static ArrayList<Professore> getProfWeb() {
        ArrayList<Professore> doc = new ArrayList<Professore>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PROFESSORE");
            while (rs.next()) {
                Professore d = new Professore(rs.getString("nome"), rs.getString("cognome"), rs.getInt("id"));
                doc.add(d);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return doc;
    }

    public static Professore getProfWeb(int id) {
        Professore doc = null;
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PROFESSORE WHERE ID = '" + id + "'");
            rs.next();
            doc = new Professore(rs.getString("nome"), rs.getString("cognome"), rs.getInt("id"));
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return doc;
    }

    public static Boolean addProfWeb(String nome, String cognome, int id) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO PROFESSORE (NOME, COGNOME, ID) VALUES('" + nome + "','" + cognome + "'," + id + ")");
            st.close();
            conn.close();
            System.out.println("aggiunto docente: " + nome);
            return  true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return  false;
        }
    }
    public static boolean removeProfWeb(int id) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM PROFESSORE WHERE  ID = '"+ id+"'");
            st.close();
            conn.close();
            System.out.println("rimosso docente con id: " + id);
            return true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

    /**OPERAZIONI SU UTENTE*/

    public static ArrayList<Utente> getUtentiWeb() {
        ArrayList<Utente> utenti = new ArrayList<Utente>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTE");
            while (rs.next()) {
                Utente u = new Utente(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getInt("RUOLO"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"));
                utenti.add(u);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return utenti;
    }
    public static boolean addUtenteWeb(String user, String password, int ruolo, String nome, String cognome, String mail) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            if(ruolo == 0)
                st.executeUpdate("INSERT INTO UTENTE (USERNAME, PASSWORD, RUOLO, NOME, COGNOME, EMAIL) VALUES('" + user + "', '" + password + "', '" + ruolo + "', '" + nome + "', '" + cognome + "', '" + mail + "')");
            else
                st.executeUpdate("INSERT INTO UTENTE (USERNAME, PASSWORD, RUOLO) VALUES('" + user + "', '" + password + "', '"  + ruolo + "')");
            st.close();
            conn.close();
            System.out.println("aggiunto Utente: " + user);
            return  true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return  false;
        }
    }
    public static void removeUtenteWeb(String user) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM UTENTE WHERE  USERNAME = '"+ user+"'");
            st.close();
            conn.close();
            System.out.println("rimosso user: " + user);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    public static Utente getUtenteWeb(String user) {
        Utente u = null;
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTE WHERE  USERNAME = '"+ user+"'");
            rs.next();
            u = new Utente(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getInt("RUOLO"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("EMAIL"));
            st.close();
            conn.close();
        } catch (SQLException e) {
            //System.out.println("Errore: " + e.getMessage());
            return u;
        }finally {
            return u;
        }
    }

    /**OPERAZIONI SU MATERIA*/
    public static ArrayList<Corso> getMaterieWeb() {
        ArrayList<Corso> corsi = new ArrayList<Corso>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CORSO");
            while (rs.next()) {
                Corso c = new Corso(rs.getString("MATERIA"));
                corsi.add(c);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return corsi;
    }
    public static boolean addCorsoWeb(String materia) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO CORSO (MATERIA) VALUES('"+ materia +"')");
            st.close();
            conn.close();
            System.out.println("aggiunto corso di: " + materia);
            return  true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return  false;
        }
    }
    public static boolean removeCorsoWeb(String materia) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM CORSO WHERE  MATERIA = '"+ materia +"'");
            st.close();
            conn.close();
            System.out.println("rimosso corso di: " + materia);
            return true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }


    /**OPERAZIONI INSEGNAMENTI*/

    public static ArrayList<Insegnamento> getInsegnamentiWeb() {
        ArrayList<Insegnamento> insegnamento = new ArrayList<Insegnamento>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM INSEGNAMENTO");
            while (rs.next()) {
                Insegnamento i = new Insegnamento(rs.getInt("IDPROF"), rs.getString("PROFESSORE"), rs.getString("MATERIA"));
                insegnamento.add(i);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return insegnamento;
    }
    public static ArrayList<Insegnamento> getInsegnamentiWeb(String mat) {
        ArrayList<Insegnamento> insegnamento = new ArrayList<Insegnamento>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM INSEGNAMENTO WHERE MATERIA = '" + mat + "'");
            while (rs.next()) {
                Insegnamento i = new Insegnamento(rs.getInt("IDPROF"), rs.getString("PROFESSORE"), rs.getString("MATERIA"));
                insegnamento.add(i);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return insegnamento;
    }

    public static ArrayList<Insegnamento> getInsegnamentiWeb(int prof) {
        ArrayList<Insegnamento> insegnamento = new ArrayList<Insegnamento>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM INSEGNAMENTO WHERE IDPROF = '" + prof + "'");
            while (rs.next()) {
                Insegnamento i = new Insegnamento(rs.getInt("IDPROF"), rs.getString("PROFESSORE"), rs.getString("MATERIA"));
                insegnamento.add(i);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return insegnamento;
    }

    public static boolean addInsegnamentoWeb(String materia, String prof, int idprof) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO INSEGNAMENTO (IDPROF, PROFESSORE, MATERIA) VALUES('" + idprof + "', '" + prof + "', '" + materia +"')");
            st.close();
            conn.close();
            System.out.println("aggiunto corso di: " + materia + " insegnato da:" + prof);
            return  true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return  false;
        }
    }

    public static boolean removeInsegnamentoWeb(String materia, int prof) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM INSEGNAMENTO WHERE  MATERIA = '"+ materia +"' AND IDPROF = '" + prof +"'");
            System.out.println("rimosso insegnamento di: " + materia);
            st.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

    /**OPERAZIONI SU RIPETIZIONI*/

    public static void generaRipetizioniWeb(int idprof, String nomeProf, String cognomeprof, String materia){
        String prof = nomeProf + " " + cognomeprof;
        String[] gg = {"lunedi","martedi","mercoledi","giovedi","venerdi"};
        for(int i=0; i<gg.length; i++){
            for(int j=15; j<20; j++){
                addRipetizioneWeb(idprof, prof, materia, i, gg[i], j);/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        }
    }

    public static boolean eliminaRipetizioniWeb(int prof, String mat){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM RIPETIZIONE WHERE  MATERIA = '"+ mat +"' AND IDPROF = '" + prof +"'");
            System.out.println("rimosse ripetizioni di: " + mat + " del prof: " + prof);
            st.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

    public static void addRipetizioneWeb(int idprof, String prof, String materia, int i, String gg, int ora){////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO RIPETIZIONE (IDPROF, PROFESSORE, MATERIA, I, GIORNO, ORA, STATO)" +
                    " VALUES('" + idprof + "','" + prof + "','" + materia + "','" + i + "','" + gg + "','" + ora +"'," + 0 + ")");
            st.close();
            conn.close();
            System.out.println("aggiunto corso di: " + materia);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    //ricerca per stato
    public static ArrayList<Ripetizione> getRipetizioneWeb() {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE RIPETIZIONE.STATO = 0");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per materia
    public static ArrayList<Ripetizione> getRipetizioneWeb(String materia) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per professore
    public static ArrayList<Ripetizione> getRipetizioneProfWeb(String prof) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND IDPROF = '" + prof+ "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    // ricerca per per materia-giorno
    public static ArrayList<Ripetizione> getRipetizioneWeb(String materia, String gg) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND GIORNO = '" + gg + "' AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per materia-professore
    public static ArrayList<Ripetizione> getRipetizioneWeb(String materia, int prof) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND IDPROF = '" + prof + "' AND MATERIA = '" + materia + "'");;
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca per materia-giorno-ora
    public static ArrayList<Ripetizione> getRipetizioneWeb(String materia, String gg, int ora) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND GIORNO = '" + gg + "' AND ORA = '" + ora + "' AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }
    //ricerca completa
    public static ArrayList<Ripetizione> getRipetizioneWeb(String materia, String gg, int ora, int prof) {
        ArrayList<Ripetizione> rip = new ArrayList<Ripetizione>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOME, COGNOME, MATERIA, GIORNO, ORA, IDPROF, I, STATO FROM RIPETIZIONE JOIN PROFESSORE ON (IDPROF = ID) WHERE STATO = 0 AND GIORNO = '" + gg + "' AND ORA = '" + ora + "' AND IDPROF = '" + prof + "' AND MATERIA = '" + materia + "'");
            while (rs.next()) {
                String s = rs.getString("NOME") + " " + rs.getString("COGNOME");
                Ripetizione r = new Ripetizione(s, rs.getInt("IDPROF"), rs.getString("MATERIA"), rs.getInt("I"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                rip.add(r);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return rip;
    }

    //  PRENOTAZIONE RIPETIZIONE
    public static boolean prenotaWeb(int idprof, String prof, String materia, String gg, int ora, String ut){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT STATO FROM RIPETIZIONE WHERE IDPROF = '" + idprof + "'AND MATERIA = '" + materia +
                    "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();
            int ris = rs.getInt("STATO");
            if (ris == 0) {
                st.executeUpdate("UPDATE RIPETIZIONE SET STATO = 1 WHERE IDPROF = '" + idprof + "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
                addStoricoWeb(ut, idprof, prof, materia, gg, ora);
                System.out.println("prenotata lezione di: " + materia + "per il giorno: " + gg + " alle ore: " + ora);
                st.close();
                rs.close();
                conn.close();
                return true;
            }else{
                System.out.println("ERRORE RIPETIZIONE NON DISPONIBILE");
                st.close();
                rs.close();
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }
    public static void liberaWeb(int prof, String materia, String gg, int ora) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT STATO FROM RIPETIZIONE WHERE IDPROF = '" + prof + "'AND MATERIA = '" + materia +
                    "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();
            int ris = rs.getInt("STATO");
            if (ris == 1) {
                st.executeUpdate("UPDATE RIPETIZIONE SET STATO = 0 WHERE IDPROF = '" + prof + "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            }else{
                System.out.println("ERRORE RIPETIZIONE NON OCCUPATA");
            }
            st.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    /**OPERAZIONI SU STORICO*/
    public static ArrayList<Storico> getStoricoWeb(String u){
        ArrayList<Storico> storico = new ArrayList<Storico>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM STORICO WHERE USERNAME = '" + u + "'");
            while (rs.next()) {
                Storico s = new Storico(rs.getString("USERNAME"), rs.getInt("IDPROF"), rs.getString("PROFESSORE"), rs.getString("MATERIA"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                storico.add(s);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return storico;
    }

    public static ArrayList<Storico> getStoricoWeb(){
        ArrayList<Storico> storico = new ArrayList<Storico>();
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM STORICO");
            while (rs.next()) {
                Storico s = new Storico(rs.getString("USERNAME"), rs.getInt("IDPROF"), rs.getString("PROFESSORE"), rs.getString("MATERIA"),
                        rs.getString("GIORNO"), rs.getInt("ORA"), rs.getInt("STATO"));
                storico.add(s);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return storico;
    }


    public static boolean removeLezioneWeb(String ut, String mat, int prof, String gg, int ora){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT STATO FROM STORICO WHERE USERNAME = '" + ut + "' AND IDPROF = '" + prof + "' AND MATERIA = '" + mat +
                    "' AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();
            int ris = rs.getInt("STATO");
            if (ris == 0) {
                st.executeUpdate("DELETE FROM STORICO WHERE USERNAME = '" + ut + "' AND IDPROF = '" + prof + "' AND MATERIA = '" + mat +
                        "' AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
                System.out.println("Rimossa lezione di: " + mat + " del il giorno: " + gg + " alle ore: " + ora);
                liberaWeb(prof,mat,gg,ora);
                rs.close();
                st.close();
                conn.close();
                return true;
            }else{
                System.out.println("ERRORE RIPETIZIONE GIA EFFETTUATA");
                rs.close();
                st.close();
                conn.close();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

    public static boolean effettuaWeb(int idprof, String materia, String gg, int ora, String ut){
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT STATO FROM STORICO WHERE IDPROF = '" + idprof + "'AND MATERIA = '" + materia +
                    "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
            rs.first();
            int ris = rs.getInt("STATO");
            if (ris == 0) {
                st.executeUpdate("UPDATE STORICO SET STATO = 1 WHERE IDPROF = '" + idprof + "'AND MATERIA = '" + materia +
                        "'AND GIORNO = '" + gg + "' AND ORA = '" + ora + "'");
                System.out.println("effettuata lezione di: " + materia + "per il giorno: " + gg + " alle ore: " + ora);
                st.close();
                rs.close();
                conn.close();
                return true;
            }else{
                System.out.println("ERRORE RIPETIZIONE GIA EFFETTUATA");
                st.close();
                rs.close();
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

    public static void addStoricoWeb(String ut, int idprof, String prof, String mat, String gg, int ora){
        try{
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO STORICO (USERNAME, IDPROF, PROFESSORE, MATERIA, GIORNO, ORA, STATO) " +
                    "VALUES('" + ut + "','" + idprof + "','" + prof + "','"   + mat + "','" + gg + "','" + ora + "','" + 0 + "')");
            st.close();
            conn.close();
            System.out.println("aggiunta ripetizione a utente: " + ut);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static boolean removeStoricoWeb(String materia, int prof) {
        try {
            Connection conn = DriverManager.getConnection(url, USER, PWD); // apertura della connessione verso il DB
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM STORICO WHERE  MATERIA = '"+ materia +"' AND IDPROF = '" + prof +"' AND STATO = 0");
            System.out.println("rimosso insegnamento di: " + materia);
            st.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

}
