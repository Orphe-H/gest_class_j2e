package com.tp.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tp.beans.Etudiant;
import com.tp.dao.DAOFactory;
import com.tp.dao.EtudiantDao;
import com.tp.forms.NewEtuForm;



@WebServlet(name = "Create_etu")
public class Create_etu extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/inscription.jsp";

    private EtudiantDao     etudiantDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.etudiantDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getEtudiantDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        NewEtuForm form = new NewEtuForm( etudiantDao );

        /* Traitement de la requête et récupération du bean en résultant */
        Etudiant etudiant = form.ajouterEtudiant( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, etudiant );

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
