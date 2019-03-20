package entity;

public class Fraction {

    String value;
    int numerator;
    int denominator;

    public Fraction(int numerator,int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public String getValue() {
        int gcf = gcd(this.denominator,this.numerator);
        this.denominator = this.denominator/gcf;
        this.numerator = this.numerator/gcf;
        this.value = numerator + "/" + denominator;
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    //辗转相除法求最大公因数
    private int gcd(int a,int b){
        if(a%b == 0)
            return b;
        else
            return gcd(b,a%b);
    }

    @Override
    public String toString() {
        int gcf = gcd(this.denominator,this.numerator);
        this.denominator = this.denominator/gcf;
        this.numerator = this.numerator/gcf;
        this.value = numerator + "/" + denominator;
        return this.getNumerator()+"/"+this.getDenominator();
    }
}
