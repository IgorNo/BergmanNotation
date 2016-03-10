package nov.math.ext;

import java.util.List;

public class TestBergman {

    public static void main(String[] args) {

        for (int i = 0; i < 30 ; i++) {
            BergmanNotation nBergmanNotation = new BergmanNotation(i);
            List<Byte> nBergman = nBergmanNotation.getnBergman();
            System.out.print("N= "+i+" "+"nBergman= " + nBergmanNotation);
            System.out.print(" = ");
            for (int j = 0; j < nBergman.size(); j++) {
                if (nBergman.get(j)==BergmanNotation.ONE){
                    System.out.print("F("+(Math.negateExact(j-nBergmanNotation.getnFraction()))+")+");
                }
            }
            System.out.print(" = ");
            long N=0;
            for (int j = 0; j < nBergman.size(); j++) {
                if (nBergman.get(j)==BergmanNotation.ONE){
                    long nFibonacci = MathExt.fibonacci( Math.negateExact(j-nBergmanNotation.getnFraction()) );
                    N += nFibonacci;
                    System.out.print("("+nFibonacci+")+");
                }
            }
            System.out.print(" = " + N + " toDecimal() = " + nBergmanNotation.toDecimal());

            System.out.println();

           if (i!=N) System.out.println("ERROR!");
        }

        for (double i = 0; i < 3 ; i += 0.2) {
            BergmanNotation nBergmanNotation = new BergmanNotation(i,15);
            List<Byte> nBergman = nBergmanNotation.getnBergman();
            System.out.print("N= "+i+" "+"nBergman= " + nBergmanNotation);
            System.out.print(" toDecimal() = " + nBergmanNotation.toDecimal());
            System.out.println();
        }

        int nMax=32;
        for (int i = 2; i <=nMax; i++) {
            for (int j = 2; j <= nMax; j++) {
                BergmanNotation nBergmanNotation1 = new BergmanNotation(i);
                BergmanNotation nBergmanNotation2 = new BergmanNotation(j);
                BergmanNotation nBergmanNotationSum = nBergmanNotation1.add(nBergmanNotation2);
                System.out.print(i+" + "+j+" = "+nBergmanNotation1+" + "+nBergmanNotation2+" = "+nBergmanNotationSum+
                        " toDecimal() = " + nBergmanNotationSum.toDecimal());
                System.out.println();
            }
        }

        int nMax1=10;
        for (int i = 1; i <=nMax1; i++) {
            for (int j = 1; j <= nMax1; j++) {
                BergmanNotation nBergmanNotation1 = new BergmanNotation(i);
                BergmanNotation nBergmanNotation2 = new BergmanNotation(j);
                BergmanNotation nBergmanNotationSum = nBergmanNotation1.multiply(nBergmanNotation2);
                System.out.print(i+" * "+j+" = "+nBergmanNotation1+" * "+nBergmanNotation2+" = "+nBergmanNotationSum+
                        " toDecimal() = " + nBergmanNotationSum.toDecimal());
                System.out.println();
            }
        }

    }
}
