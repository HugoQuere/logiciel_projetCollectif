/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hugo
 * Created: 15 mars 2020
 */

CREATE TABLE NID(

    idNid int      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    numNid  int    NOT NULL,
    idEnclos int    NOT NULL,
    zone     int    NOT NULL,

    PRIMARY KEY(idNid),
    FOREIGN KEY(idEnclos) REFERENCES ENCLOS(idEnclos),

    CONSTRAINT CHK_Zone CHECK (zone>=1 AND zone<=6)  /* on vérifie que la zone est compris entre 1 et 6 */

);
