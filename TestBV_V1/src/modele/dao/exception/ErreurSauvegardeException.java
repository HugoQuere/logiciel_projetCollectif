/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.exception;

/**
 *
 * @author mhverron
 */
public class ErreurSauvegardeException extends Exception {

    /**
     * Creates a new instance of
     * <code>ErreurSauvegardeException</code> without detail message.
     */
    public ErreurSauvegardeException() {
    }

    /**
     * Constructs an instance of
     * <code>ErreurSauvegardeException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ErreurSauvegardeException(String msg) {
        super(msg);
    }
}
