/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  d-dja
 * Created: 10 mai 2020
 */

INSERT INTO CODEERREUR (idCodeErreur, description,idCategorieProbleme)
    VALUES
        (1, 'Nid Plein',1), /*(..,..,Nid)*/
        (2, 'Nid besoin de maintenance',1),/*(..,..,Nid)*/
        (3, 'Faible ponte',2),      /*(..,..,Palmipede)*/
        (4, 'Trop de ponte',2),     /*(..,..,Palmipede)*/
        (5, 'Erreur systeme',3);    /*(..,..,Systeme)*/
        /*(5, 'Erreur systeme',2);    /*(..,..,Palmipede) /!\ Erreur systeme en type palmipede*/