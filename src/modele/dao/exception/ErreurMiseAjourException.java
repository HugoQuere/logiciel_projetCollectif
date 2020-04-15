/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.exception;

/**
 *
 * @author mhverron
 */
public class ErreurMiseAjourException extends Exception {

    /**
     * Creates a new instance of
     * <code>ErreurMiseAjourException</code> without detail message.
     */
    public ErreurMiseAjourException() {
    }

    /**
     * Constructs an instance of
     * <code>ErreurMiseAjourException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ErreurMiseAjourException(String msg) {
        super(msg);
    }
}
