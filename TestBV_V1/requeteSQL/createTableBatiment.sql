/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hugo
 * Created: 15 mars 2020
 */

CREATE TABLE BATIMENT(
    idBatiment int           NOT NULL   GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nomBatiment VARCHAR(255) NOT NULL,

    PRIMARY KEY(idBatiment) 
);