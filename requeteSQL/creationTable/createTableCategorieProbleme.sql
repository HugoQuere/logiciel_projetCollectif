/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  d-dja
 * Created: 10 mai 2020
 */

CREATE TABLE CATEGORIEPROBLEME(
    idCategorieProbleme int      NOT NULL,  /* 1   | 2         | 3      */
    typeProbleme VARCHAR(255)      NOT NULL,/* Nid | Palmipede | Systeme*/

    PRIMARY KEY(idCategorieProbleme)
);