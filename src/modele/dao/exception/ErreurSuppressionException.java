/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao.exception;

/**
 *
 * @author mhverron
 */
public class ErreurSuppressionException extends Exception {

    /**
     * Creates a new instance of
     * <code>ErreurSuppressionException</code> without detail message.
     */
    public ErreurSuppressionException() {
    }

    /**
     * Constructs an instance of
     * <code>ErreurSuppressionException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public ErreurSuppressionException(String msg) {
        super(msg);
    }
}
