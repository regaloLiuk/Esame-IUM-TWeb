package newPackage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.jfr.DataAmount;

import javax.print.Doc;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); // per essere robusti rispetto a caratteri speciali (', etc)
        DAO.registerDriver();
        String var = request.getParameter("action");
        if(var == null){var = request.getParameter("var");}
        System.out.println(var);

        if(var.equals("loginWeb")){
            String account = request.getParameter("user");
            String password = request.getParameter("pwd");
            System.out.println(account + " " + password);
            Utente u = DAO.getUtenteWeb(account);
            System.out.println(u);
            if(u != null){
                if(!account.equals("") && !password.equals("")){
                    if (account.equals(u.getUser()) && password.equals(u.getPassword())) {
                        System.out.println(account + " " + password);
                        if(u.getRuolo()==1){
                            HttpSession s = request.getSession();
                            s.setAttribute("account", account);
                            out.print("admin");
                        }else{
                            HttpSession s = request.getSession();
                            s.setAttribute("account", account);
                            out.print("user");
                        }
                        System.out.println("accesso ok");
                    } else {
                        System.out.println("errore if 3");
                        out.print("error");
                    }
                }else{
                    System.out.println("errore if 2: no dati");
                    out.print("NoData");
                }
            }else{ System.out.println("errore if 1: nome o passaword sbagliati");
                out.print("error");}
        }
        else if(var.equals("ripetizioniWeb")){
            String prof = request.getParameter("prof");
            String mat = request.getParameter("mat");
            String ora = request.getParameter("ora");
            String gg = request.getParameter("gg");
            System.out.println(mat + " " + prof + " " + gg + " " + ora);
            response.setContentType("text/plain");
            ArrayList<Ripetizione> r = null;
            Gson g = new Gson();
            if (gg.equals("giorno") && mat.equals("materia") && prof.equals("professore") && ora.equals("ora")) {
                r = DAO.getRipetizioneWeb();
            } else if (gg.equals("giorno") && mat.equals("materia") && ora.equals("ora")) {
                r = DAO.getRipetizioneProfWeb(prof);
            }else if (gg.equals("giorno") && prof.equals("professore") && ora.equals("ora")) {
                r = DAO.getRipetizioneWeb(mat);
            } else if (prof.equals("professore") && ora.equals("ora")) {
                r = DAO.getRipetizioneWeb(mat, gg);
            } else if (gg.equals("giorno") && ora.equals("ora")){
                r = DAO.getRipetizioneWeb(mat, Integer.parseInt(prof));
            }else if(prof.equals("professore")){
                r = DAO.getRipetizioneWeb(mat,gg,Integer.parseInt(ora));
            }else {
                r = DAO.getRipetizioneWeb(mat, gg, Integer.parseInt(ora), Integer.parseInt(prof));
            }
            String m = "{\"Ripetizioni\":" + g.toJson(r) + "}";
            System.out.println(m);
            out.println(m);
            out.flush();
        }

        else if(var.equals("logout")){
            HttpSession s = request.getSession();
            System.out.println(s.getAttribute("account"));
            s.invalidate();
            System.out.println("ok");
            out.print("ok");
        }

        else if (var.equals("nomeUtente")){
            HttpSession s = request.getSession();
            out.print(s.getAttribute("account"));
        }

        else if(var.equals("storicoWeb")){
            response.setContentType("text/plain");
            HttpSession s = request.getSession();
            System.out.println(s.getAttribute("account"));
            Utente u = DAO.getUtenteWeb((String)s.getAttribute("account"));
            System.out.println(u);
            ArrayList<Storico> storico = null;
            if(u!=null){
                if(u.getRuolo()==1)
                    storico = DAO.getStoricoWeb();
                else
                    storico = DAO.getStoricoWeb(u.getUser());
                Gson g = new Gson();
                String m = "{\"Storico\":" + g.toJson(storico) + "}";
                System.out.println(m);
                out.println(m);
                out.flush();
            }else {
                out.println("error");
            }
        }

        else if(var.equals("datipersonali")){
            HttpSession s = request.getSession();
            Utente ut = DAO.getUtenteWeb((String)s.getAttribute("account"));
            Gson g = new Gson();
            String m = "{\"Utente\":" + g.toJson(ut) + "}";
            out.print(m);
        }
        else if(var.equals("corsi")){
            ArrayList<Corso> mat = DAO.getMaterieWeb();
            System.out.println(mat);
            Gson g = new Gson();
            String m = "{\"Corsi\":" + g.toJson(mat) + "}";
            System.out.println(m);
            out.print(m);
        }
        else if(var.equals("docenti")){
            ArrayList<Professore> mat = DAO.getProfWeb();
            System.out.println(mat);
            Gson g = new Gson();
            String m = "{\"Docenti\":" + g.toJson(mat) + "}";
            System.out.println(m);
            out.print(m);
        }
        else if(var.equals("insegnamenti")){
            ArrayList<Insegnamento> mat = DAO.getInsegnamentiWeb();
            System.out.println(mat);
            Gson g = new Gson();
            String m = "{\"Insegnamenti\":" + g.toJson(mat) + "}";
            System.out.println(m);
            out.print(m);
        }

        else if (var.equals("riserva")){
            HttpSession s = request.getSession();
            String ut = (String)s.getAttribute("account");
            String prof = request.getParameter("prof");
            String idprof = request.getParameter("idprof");
            String mat = request.getParameter("mat");
            String ora = request.getParameter("ora");
            String gg = request.getParameter("gg");
            System.out.println(ut + " " + prof + " " + idprof + " " + mat + " " + ora + " " + gg);

            if(DAO.prenotaWeb(Integer.parseInt(idprof), prof, mat, gg, Integer.parseInt(ora), ut)){
                System.out.println("prenotata lezione");
                out.print("ok");
            }else
                out.print("error");
        }

        else if(var.equals("elimina")){
            HttpSession s = request.getSession();
            String ut = (String)s.getAttribute("account");
            //String prof = request.getParameter("prof");
            String idprof = request.getParameter("prof");
            String mat = request.getParameter("mat");
            String ora = request.getParameter("ora");
            String gg = request.getParameter("gg");

            System.out.println(ut + " " + " " + idprof + " " + mat + " " + ora + " " + gg);

            if(DAO.removeLezioneWeb(ut, mat, Integer.parseInt(idprof), gg, Integer.parseInt(ora))){
                 System.out.println("eliminazione lezione");
                out.print("ok");
            }else
            out.print("error");
        }

        else if(var.equals("effettua")){
            HttpSession s = request.getSession();
            String ut = (String)s.getAttribute("account");
            String idprof = request.getParameter("prof");
            String mat = request.getParameter("mat");
            String ora = request.getParameter("ora");
            String gg = request.getParameter("gg");

            System.out.println(ut + " " + " " + idprof + " " + mat + " " + ora + " " + gg);

            if(DAO.effettuaWeb(Integer.parseInt(idprof), mat, gg, Integer.parseInt(ora), ut)){
                System.out.println("lezione effettuata");
                out.print("ok");
            }else
                out.print("error");
        }

        else if(var.equals("aggiungiProf")){
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String materia = request.getParameter("materia");
            System.out.println(nome + " " + cognome + " " + materia);
            if(!nome.equals("") && !cognome.equals("") && !materia.equals("")) {
                ArrayList<Professore> prof = DAO.getProf();
                int id = prof.size() + 1;
                if(DAO.addProfWeb(nome, cognome, id))
                    System.out.println("aggiunto nuovo professore");
                else
                    out.print("error");

                ArrayList<Corso> mat = DAO.getMaterieWeb();
                System.out.println(mat);
                Corso c = new Corso(materia);
                if (mat.contains(materia)) {
                    System.out.println("materia già presente");
                }else{
                    if(DAO.addCorsoWeb(materia))
                        System.out.println("aggiunta nuova materia");
                    /*else
                        out.print("error");*/
                }
                String professore = nome + " " + cognome;
                if(DAO.addInsegnamentoWeb(materia, professore, id))
                    System.out.println("inserito insegnamento");
                else
                    out.print("error");

                DAO.generaRipetizioniWeb(id, nome, cognome, materia);
                System.out.println("aggiunte ripetizioni");
                out.print("ok");
            }else
                out.print("noData");
        }

        else if(var.equals("aggiungiCorso")){
            String prof = request.getParameter("professore");
            String materia = request.getParameter("materia");

            System.out.println(prof + " "  + materia);

            if(!prof.equals("") && !materia.equals("")) {

                Professore p =  DAO.getProfWeb(Integer.parseInt(prof));
                String nome = p.getNome();
                String cognome = p.getCognome();
                String professore = nome + " " + cognome;

                ArrayList<Corso> mat = DAO.getMaterieWeb();
                System.out.println(mat);
                boolean tmp = false;
                int i=0;
                while(i<mat.size() && !tmp){
                    if(mat.get(i).getMateria().equals(materia))
                        tmp = true;
                    i++;
                }
                if (!tmp) {
                    if(DAO.addCorsoWeb(materia))
                        System.out.println("aggiunta nuova materia");
                }else{
                    System.out.println("materia già presente");
                }
                if(DAO.addInsegnamentoWeb(materia, professore, Integer.parseInt(prof)))
                    System.out.println("inserito insegnamento");
                else
                    out.print("error");
                DAO.generaRipetizioniWeb(Integer.parseInt(prof), nome, cognome, materia);
                System.out.println("aggiunte ripetizioni");
                out.print("ok");
            }else
                out.print("noData");
        }

        else if(var.equals("rimuoviProf")){
            String prof = request.getParameter("professore");
            System.out.println(prof);
            ArrayList<Corso> mat = DAO.getMaterie();
            if(!prof.equals("professore")) {
                if(DAO.removeProfWeb(Integer.parseInt(prof))){
                    for(int i=0; i<mat.size();i++){
                        ArrayList<Insegnamento> ins = DAO.getInsegnamentiWeb(mat.get(i).getMateria());
                        if(ins.size() == 0)
                            DAO.removeCorsoWeb(mat.get(i).getMateria());
                    }
                    out.print("ok");
                    System.out.println("eliminato professore");
                }else
                    out.print("error");
            }else
                out.print("noData");
        }

        else if(var.equals("rimuoviCorso")){
            String corso = request.getParameter("materia");
            System.out.println(corso);

            Scanner s = new Scanner(corso);
            String prof = s.next();
            String mat = s.next();
            System.out.println(prof + " - " + mat);

            ArrayList<Corso> materie = DAO.getMaterieWeb();
            ArrayList<Professore> professori = DAO.getProfWeb();

            if(!corso.equals("corso")){
                if(DAO.removeInsegnamentoWeb(mat, Integer.parseInt(prof))){
                    for(int i=0; i<materie.size();i++){
                        ArrayList<Insegnamento> ins = DAO.getInsegnamentiWeb(materie.get(i).getMateria());
                        if(ins.size() == 0)
                            DAO.removeCorsoWeb(materie.get(i).getMateria());
                    }
                    for(int i=0; i<professori.size();i++){
                        ArrayList<Insegnamento> ins = DAO.getInsegnamentiWeb(professori.get(i).getId());
                        if(ins.size() == 0)
                            DAO.removeProfWeb(professori.get(i).getId());
                    }
                    if (DAO.eliminaRipetizioniWeb(Integer.parseInt(prof), mat)){
                        if (DAO.removeStoricoWeb(mat, Integer.parseInt(prof)))
                            out.print("ok");
                    }
                }else
                    out.print("error");
            }else
                out.print("noData");

            ////AndroidApp/////////////////////////////////////////////////////////////////////////////////////////////////
        }else if(var.equals("login")){
            String account = request.getParameter("user");
            String password = request.getParameter("pwd");
            System.out.println(account + " " + password);
            Utente u = null;
            if (account != "" && password != "") {
                u = DAO.getUtente(account);
                if (account.equals(u.getUser()) && password.equals(u.getPassword())) {
                    System.out.println(account + " " + password);
                    System.out.println(u);
                    out.print("ok");
                    System.out.println("accesso ok");
                } else {
                    System.out.println("errore if 2");
                    out.print("error");
                }
            }else{
                System.out.println("errore 1 if");
                out.print("error");
            }
        }else if(var.equals("ripetizioni")){
            String prof = request.getParameter("prof");
            String mat = request.getParameter("mat");
            String ora = request.getParameter("ora");
            String gg = request.getParameter("gg");

            StringTokenizer tokenizer =new StringTokenizer(prof," ");

            System.out.println("mat: "+mat + " prof: " + prof + " gg: " + gg + " ora: " + ora);

            response.setContentType("text/plain");

            ArrayList<Ripetizione> r =  new ArrayList<Ripetizione>();
            Gson g = new Gson();
            String m = "";
            String nome="";
            String cognome="";


            if (gg.equals("giorno") && mat.equals("materia") && prof.equals("professore") && ora.equals("ora")) { //Nessuno
                r = DAO.getRipetizione();
            } else if (gg.equals("giorno") && prof.equals("professore") && ora.equals("ora")) { // materia
                r = DAO.getRipetizione(mat);
            }  else if (prof.equals("professore") && mat.equals("materia") && ora.equals("ora") ) {// GIORNO
                r = DAO.getRipetizioni(gg);
            } else if (prof.equals("professore") && ora.equals("ora")) { //Giorno materia
                r = DAO.getRipetizione(mat, gg);
            } else if(prof.equals("professore")){//Materia giorno ora
                r = DAO.getRipetizione(mat,gg,Integer.parseInt(ora));
            }else if (!gg.equals("giorno") && !mat.equals("materia") && !ora.equals("ora") && !prof.equals("professore")) {// Tutti
                r = DAO.getRipetizione(mat,gg,Integer.parseInt(ora),tokenizer.nextToken(),tokenizer.nextToken());
            }
            else if (gg.equals("giorno") && mat.equals("materia") && ora.equals("ora") ) {// professore
                r = DAO.getRipetizioni(tokenizer.nextToken(),tokenizer.nextToken());
            }

            else if (gg.equals("giorno") && ora.equals("ora")) { //Professore materia
                nome=tokenizer.nextToken();
                cognome=tokenizer.nextToken();
                System.out.println(nome+" "+cognome);
                r = DAO.getRipetizione(mat,nome,cognome);
            }
            else if (mat.equals("materia") && ora.equals("ora")) { //Professore giorno
                nome=tokenizer.nextToken();
                cognome=tokenizer.nextToken();
                r = DAO.getRipetizioni(nome,cognome,gg);
            }

            else if (ora.equals("ora")) { //Professore materia
                nome=tokenizer.nextToken();
                cognome=tokenizer.nextToken();

                r = DAO.getRipetizioni(mat,nome,cognome,gg);
            }
            else if (mat.equals("materia")) { //Professore giorno ORA
                nome=tokenizer.nextToken();
                cognome=tokenizer.nextToken();
                r = DAO.getRipetizioni(nome,cognome,gg,Integer.parseInt(ora));
            }


            m = "{\"Ripetizioni\":" + g.toJson(r) + "}";
            System.out.println(m);
            out.println(m);
            out.flush();
        }
        else if(var.equals("prenota")){
            System.out.println("Stiamo scherzando?");
            String prof ="";
            String ute ="";
            String mat ="";
            int ora =0;
            String gg ="";
            try{
                prof = request.getParameter("prof");
                mat = request.getParameter("mat");
                ute = request.getParameter("ute");
                ora = Integer.parseInt(request.getParameter("ora"));
                gg = request.getParameter("gg");
            }catch (Exception e){System.out.println(e);}

            System.out.println("----Provo a prenotare!");
            System.out.println("### "+prof+" ### "+mat+" ### "+ora+" ### "+gg);

            if(DAO.prenotazione(prof,mat,gg,ora,ute)){
                System.out.println("ok");
                out.println("ok");
                out.flush();
            }else{
                System.out.println("ko");
                out.println("ko");
                out.flush();
            }
            System.out.println("###Dopo averci provato!");
        }

        else if(var.equals("storico")){
            String ute="";
            try{
                ute = request.getParameter("ute");
            }catch (Exception e){System.out.println(e);}

            ArrayList<Storico> r = null;
            Gson g = new Gson();
            String m = "";

            r = DAO.getStorico(ute);

            m = "{\"Storico\":" + g.toJson(r) + "}";

            System.out.println(m);
            System.out.println("ok STORICO");
            out.println(m);
            out.flush();
        }
        else if(var.equals("disdici")){
            String ute="";
            try{
                ute = request.getParameter("ute");
            }catch (Exception e){System.out.println(e);}

            String prof = request.getParameter("prof");
            String mat = request.getParameter("mat");
            String ora = request.getParameter("ora");
            String gg = request.getParameter("gg");

            StringTokenizer tokenizer=new StringTokenizer(prof," ");

            DAO.disdici(ute,mat,tokenizer.nextToken(),tokenizer.nextToken(),gg,Integer.parseInt(ora));

            System.out.println("ok Disdetta "+prof+" "+mat+" "+ora+" "+gg);
            out.println("Prenotazione disdetta");
            out.flush();
        }
        else if(var.equals("effettuata")){
            String ute="";
            try{
                ute = request.getParameter("ute");
            }catch (Exception e){System.out.println(e);}

            String prof = request.getParameter("prof");
            String mat = request.getParameter("mat");
            String ora = request.getParameter("ora");
            String gg = request.getParameter("gg");

            StringTokenizer tokenizer=new StringTokenizer(prof," ");
            DAO.segnaEffettuata(ute,mat,tokenizer.nextToken(),tokenizer.nextToken(),gg,Integer.parseInt(ora));

            System.out.println("ok Effettuata "+prof+" "+mat+" "+ora+" "+gg);
            out.println("Prenotazione effettuata");
            out.flush();
        }
        else if(var.equals("prof")){
            ArrayList<Professore> r = null;
            Gson g = new Gson();
            String m = "";

            r = DAO.getProf();

            m = "{\"Professore\":" + g.toJson(r) + "}";


            System.out.println("ok PROF");
            out.println(m);

            out.println();
            out.flush();
        }
        else if(var.equals("mat")){
            ArrayList<Corso> r = null;
            Gson g = new Gson();
            String m = "";

            r = DAO.getMaterie();

            m = "{\"Materia\":" + g.toJson(r) + "}";


            System.out.println("ok Mat");
            out.println(m);

            out.println();
            out.flush();
        }

            out.close();
        }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
