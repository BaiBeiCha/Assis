package org.baibei.assis.obj;

public class KeyStroke {

    public final int keyCode;
    public final boolean shift;

    public KeyStroke(int keyCode, boolean shift) {
        this.keyCode = keyCode;
        this.shift = shift;
    }

    public String toString() {
        return (char) (keyCode) + " : " + shift;
    }
}
