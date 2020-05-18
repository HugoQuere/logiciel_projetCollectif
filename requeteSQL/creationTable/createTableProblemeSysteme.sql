/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  d-dja
 * Created: 10 mai 2020
 */

CREATE TABLE PROBLEMESYSTEME(

    idProblemeSysteme int      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    idCategorieProbleme int NOT NULL,
    commentaire VARCHAR(255) NOT NULL,
    dateCreation date      NOT NULL,
    dateResolution date      ,

    PRIMARY KEY(idProblemeSysteme),
    FOREIGN KEY(idCategorieProbleme) REFERENCES CATEGORIEPROBLEME(idCategorieProbleme)

);