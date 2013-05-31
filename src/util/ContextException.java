package util;

/**
 * Thrown for syntax errors.
 * Contains the context in which the error arised.
 */
public class ContextException extends Exception {
    private int line = -1;
    private String context = "";


    public ContextException() {
        super();
    }

    public ContextException(Throwable e) {
        super(e);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }
}
