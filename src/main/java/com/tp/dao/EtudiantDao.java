package com.tp.dao;

import com.tp.beans.Etudiant;

public interface EtudiantDao {
    void creer( Etudiant etudiant ) throws DAOException;

    Etudiant[] afficher(  ) throws DAOException;

    void supprimer( Long mat ) throws DAOException;

    Etudiant trouver( Long mat ) throws DAOException;

    /*    ici on a besoin :
            - d'ajouter un nouvel etudiant x) "done" => creer
            - supprimer un etudiant
            - afficher la liste des etudiants
     */
}
