/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hugo
 * Created: 1 mai 2020
 */

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hugo
 * Created: 15 mars 2020
 */

CREATE TABLE PONTE(

    idPonte     int  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    idPalmipede int  NOT NULL,
    idNid      int  NOT NULL,
    datePonte   date NOT NULL,
    heureDebutPonte time NOT NULL,
    heureFinPonte   time NOT NULL,
    PrecenseOeuf boolean NOT NULL,
    OeufCollecte boolean NOT NULL,

    PRIMARY KEY(idPonte),
    FOREIGN KEY(idPalmipede) REFERENCES PALMIPEDE(idPalmipede),
    FOREIGN KEY(idNid) REFERENCES NID(idNid),

    CONSTRAINT CHK_Heure CHECK (heureDebutPonte<heureFinPonte)  /* on vérifie que la heure de début de ponte et bien inférieur à celle de fin de ponte */

);