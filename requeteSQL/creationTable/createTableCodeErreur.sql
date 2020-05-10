/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  d-dja
 * Created: 10 mai 2020
 */

CREATE TABLE CODEERREUR(

    idCodeErreur int      NOT NULL,         /* 1            | 2                | 3            | 4             | 5              | ..*/
    description VARCHAR(255)      NOT NULL, /* Cage Pleine  | Cage maintenance | Faible ponte | Trop de ponte | Erreur systeme | ..*/
    idCategorieProbleme int NOT NULL,       /* 1 (Nid)      | 1 (Nid)          | 2 (Palmipede)| 2 (Palmipede) | 3 (Systeme)*/

    PRIMARY KEY(idCodeErreur),
    FOREIGN KEY(idCategorieProbleme) REFERENCES CATEGORIEPROBLEME(idCategorieProbleme),

    CONSTRAINT CHK_CodeErreur_Categorie CHECK (CASE WHEN idCodeErreur = 1 OR idCodeErreur = 2 THEN idCategorieProbleme = 1 
                                                    WHEN idCodeErreur = 3 OR idCodeErreur = 4 THEN idCategorieProbleme = 2  
                                                    ELSE idCategorieProbleme = 3 END) /*Certains type de problème (idCategorieProbleme) 
                                                                                    ne peuvent pas être associé à certains code erreur*/
);