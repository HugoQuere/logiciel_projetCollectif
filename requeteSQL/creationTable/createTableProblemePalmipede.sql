/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  d-dja
 * Created: 10 mai 2020
 */

CREATE TABLE PROBLEMEPALMIPEDE(

    idProblemePalmipede int      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    idPalmipede int NOT NULL,
    idCategorieProbleme int NOT NULL,
    commentaire VARCHAR(255) NOT NULL,
    dateCreation date      NOT NULL,
    dateResolution date      ,

    PRIMARY KEY(idProblemePalmipede),
    FOREIGN KEY(idPalmipede) REFERENCES PALMIPEDE(idPalmipede),
    FOREIGN KEY(idCategorieProbleme) REFERENCES CATEGORIEPROBLEME(idCategorieProbleme)

);