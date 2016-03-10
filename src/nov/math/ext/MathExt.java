package nov.math.ext;

import java.util.List;
import java.util.Scanner;

public class MathExt {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int typeOper=0;
        do {
            System.out.print("Enter Number1: ");
            double n1 = in.nextDouble();
            BergmanNotation nbn1 = new BergmanNotation(n1,20);
            System.out.print("Enter Number2: ");
            double n2 = in.nextDouble();
            BergmanNotation nbn2 = new BergmanNotation(n2,20);
            System.out.print("Enter Type of Operation (1-add, 2-multiply, 0-exit): ");
            typeOper = in.nextInt();

            BergmanNotation nbnRes;
            switch (typeOper){
                case 1:
                    nbnRes = nbn1.add(nbn2);
                    System.out.print(n1+" + "+n2+" = "+(n1+n2)+" = "+nbn1+" + "+nbn2+" = "+nbnRes+
                            " ResultToDecimal = " + nbnRes.toDecimal());
                    break;
                case 2:
                    nbnRes = nbn1.multiply(nbn2);
                    System.out.print(n1+" * "+n2+" = "+(n1*n2)+" = "+nbn1+" * "+nbn2+" = "+nbnRes+
                            " ResultToDecimal = " + nbnRes.toDecimal());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Undefined Type of Operation repeat enter: ");
                    break;
            }
            System.out.println();
        } while (typeOper>0);

    }

    public static BergmanNotation bergman(int num){
        BergmanNotation nBergman = new BergmanNotation(num);
        return nBergman;
    }

// Матриця Р = | 1 1 | - початкова матриця
//             | 1 0 |
//
// Матриця А = | a b | - тимчасова матриця яка використовується в алгоритмі піднесення до степені
//             | c d | - ініціалізується матрицею Р
//
// Вектор  R = | rc rd | - вектор результату ( друга строка матриці Р^n) ініціалізується як друга строка матриці Р


    static long  fibonacci(int n)
    {
        int n1 = n;
        if ( n<0) n=-n;

        long a = 1, ta,
                b = 1, tb,
                c = 1, rc = 0,  tc,
                d = 0, rd = 1;

        while (n>0)
        {
            if ((n & 1) == 1)    // Якщо ступінь непарна
            {
                // Множимо вектор R на матрицю A
                tc = rc;
                rc = rc*a + rd*c;
                rd = tc*b + rd*d;
            }

            // Множимо матрицю A на саму себя
            ta = a; tb = b; tc = c;
            a = a*a  + b*c;
            b = ta*b + b*d;
            c = c*ta + d*c;
            d = tc*tb+ d*d;

            n >>= 1;  // Зменшуємо ступінь вдвічі

        }
        if (n1<0){
            if (n1%2 == 0) {
                rc = -rc;
            }
        }
        return rc;
    }
}
