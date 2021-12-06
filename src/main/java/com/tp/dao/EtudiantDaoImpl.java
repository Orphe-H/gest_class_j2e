package com.tp.dao;

import com.tp.beans.Etudiant;
import static com.tp.dao.DAOUtilitaire.*;

import java.sql.*;

public class EtudiantDaoImpl implements EtudiantDao {
    private static final String SQL_INSERT = "INSERT INTO etudiant (nom, prenom) VALUES (?, ?)";
    private DAOFactory          daoFactory;

    public EtudiantDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public void creer(Etudiant etudiant) throws IllegalArgumentException, DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, etudiant.getNom(), etudiant.getPrenom() );
            int statut = preparedStatement.executeUpdate();

            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de l'enregistrement de l'etudiant, aucune ligne ajoutée dans la table." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                etudiant.setMat( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de l'enregistrement de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    @Override
    public Etudiant[] afficher() throws DAOException {
        return new Etudiant[0];
    }

    @Override
    public void supprimer(Long mat) throws DAOException {

    }

    @Override
    public Etudiant trouver(Long mat) throws DAOException {
        return null;
    }


                            /*  MAPPING D'UN RESULTSET */
    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des etudiants (un
     * ResultSet) et un bean Etudiant.
     */
    private static Etudiant map( ResultSet resultSet ) throws SQLException {
        Etudiant etudiant = new Etudiant();
        etudiant.setMat( resultSet.getLong( "mat" ) );
        etudiant.setNom( resultSet.getString( "nom" ) );
        etudiant.setPrenom( resultSet.getString( "prenom" ) );
        return etudiant;
    }

}
