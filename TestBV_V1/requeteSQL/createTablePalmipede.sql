/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hugo
 * Created: 15 mars 2020
 */

CREATE TABLE PALMIPEDE(

    idPalmipede int      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    RFID        int      NOT NULL,
    dateEntree date      NOT NULL,
    dateSortie date      ,
    idEnclos    int      NOT NULL,

    PRIMARY KEY(idPalmipede),
    FOREIGN KEY(idEnclos) REFERENCES ENCLOS(idEnclos)

);
