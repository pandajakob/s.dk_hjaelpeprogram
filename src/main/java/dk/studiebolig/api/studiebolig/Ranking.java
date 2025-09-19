package dk.studiebolig.api.studiebolig;


public class Ranking {
    int A = 0;
    int B = 0;
    int C = 0;

    public void incrementA() {
        this.A++;
    }
    public void incrementB() {
        this.B++;
    }
    public void incrementC() {
        this.C++;
    }

    public int getA() {
        return A;
    }
    public int getB() {
        return B;
    }
    public int getC() {
        return C;
    }
}

