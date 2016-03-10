package nov.math.ext;

import java.util.LinkedList;
import java.util.List;

public class BergmanNotation {

    public BergmanNotation() {
        nBergman.add(ZERO);
        nFraction = 1; // nBergman = 0,00
    }

    public BergmanNotation(int num) {
        this();
        for (int i = 0; i < num; i++) {
            inc();
        }
    }

    public BergmanNotation(double num, int nPrecision) {

        nFraction = 1;

        if (num ==  1.) {
            nBergman.add(ONE);
//            for (int i = 0; i < nPrecision; i++)
//                nBergman.addLast(ZERO);
        } else {
            double tau = TAU;
            while (tau < num) {
                ++nFraction;
                tau *= TAU;
            }

            num /= tau;
            for (int j = 0; j < nFraction + nPrecision; j++) {
                num *= TAU;
                nBergman.addLast((byte) num);
                if (num > 1.0) num -= 1;
            }
        }
        this.removeZero();
    }

    BergmanNotation(BergmanNotation that){
        nFraction = that.nFraction;
        for (byte x:that.nBergman) {
            nBergman.add(x);
        }
    }
    public double toDecimal(){
        double tauN = 1;
        double num = 0;
        for (int i = nFraction-1; i >= 0; i--) {
            num += nBergman.get(i) * tauN;
            tauN *= TAU;
        }

        tauN = 1/TAU;
        for (int i = nFraction; i < nBergman.size(); i++) {
            num += nBergman.get(i) * tauN;
            tauN /= TAU;
        }

        return num;
    }

//increment operation
   public void inc(){
        if (nBergman.get(nFraction-1) == ONE){
            unwrap(nFraction-1);
        }
         nBergman.set(nFraction-1, ONE);
        wrapAll();
    }

    public BergmanNotation add(BergmanNotation that){

        // вирівнюємо числа, якщо потрібно
        that.addZeroFirst(this.characteristic() - that.characteristic());
        this.addZeroFirst(that.characteristic() - this.characteristic());
        that.addZeroLast(this.precision() - that.precision());
        this.addZeroLast(that.precision() - this.precision());

        BergmanNotation sum = new BergmanNotation();
        LinkedList<Integer> transfer = new LinkedList<>();
        for (int i = 0; i < this.nBergman.size()-1; i++) {
            sum.nBergman.add(ZERO);
            sum.nFraction = this.nFraction;
        }
        int j=0;
        int k=0;
        if(this.nBergman.get(0)==1 && that.nBergman.get(0)==1){ // 100 + 100
            sum.nBergman.set(0,ZERO);
//            sum.nBergman.set(1,ZERO);
            j=1; sum.nFraction++;
            sum.nBergman.addFirst(ONE);
            transfer.addLast(3);
            k=2;
        }

        for (int i = k; i < nBergman.size(); i++) {
            if(this.nBergman.get(i)==1 && that.nBergman.get(i)==1){
                sum.nBergman.set(i-1+j,ONE);
                sum.nBergman.set(i+j,ZERO);
                if (i+1 < this.nBergman.size()){
  //                  sum.nBergman.set(i+1+j,ZERO);
                    transfer.addLast(i+2+j);
                } else { // unwrap at the end
                    sum.nBergman.addLast(ZERO);
                    sum.nBergman.addLast(ONE);
                }
            } else {
                if(this.nBergman.get(i)==0 && that.nBergman.get(i)==0) {
                    sum.nBergman.set(i + j, ZERO);
                } else {
                    sum.nBergman.set(i + j, ONE);
                }
            }
        }
        sumWrap(sum, transfer);
// якщо перенос вийшов за границі чисда, то додаємо необхідні розряди до суми
        if (transfer.size()>0){
            while(sum.nBergman.size()-1<transfer.getLast())
                sum.nBergman.addLast(ZERO);
        }

        for (int i = 0; i < transfer.size(); i++) {
            int transerPos = transfer.get(i);
            if (sum.nBergman.get(transerPos) == ONE){
                sum.nBergman.set(transerPos-1,ONE);
                sum.nBergman.set(transerPos,ZERO);
                if (transerPos+1 < sum.nBergman.size()) {
//                    sum.nBergman.set(transerPos + 1, ZERO);
                    transfer.set(i--, transerPos + 2);
                } else {
                    sum.nBergman.addLast(ZERO);
                    sum.nBergman.addLast(ONE);
                }
            } else {
                sum.nBergman.set(transerPos,ONE);
            }
            sumWrap(sum,transfer);
        }
        sum.wrapAll();

        this.removeZero();
        that.removeZero();
        sum.removeZero();

        return  sum;
    }

    public BergmanNotation  multiply(BergmanNotation that){
        BergmanNotation mult = new BergmanNotation(that);
        BergmanNotation sum = new BergmanNotation();

        int shift = nBergman.size()-1;
        for (int i = nBergman.size()-1; i >= 0; i--) {
            if (nBergman.get(i)==ONE){
                mult.shiftRight(shift-i);
                sum = sum.add(mult);
                shift = i;
            }
        }
        sum.shiftLeft(nBergman.size()-nFraction);
        sum.removeZero();
        return sum;
    }


private  void shiftLeft(int nPos){
    if (nFraction-nPos < 0){
        addZeroFirst(nPos-nFraction+1);
        nFraction = 1;
    } else{
        nFraction -= nPos;
    }

}
    private void shiftRight(int nPos){
        if (nFraction+nPos > nBergman.size()){
            addZeroLast(nFraction+nPos-nBergman.size());
            nFraction = nBergman.size();
        } else{
            nFraction += nPos;
        }

    }
    private  void removeZero(){

        while (nFraction!=1 && nBergman.getFirst()==ZERO){
            nBergman.removeFirst();
            --nFraction;
        }
        while (nFraction!=nBergman.size() && nBergman.getLast()==ZERO){
            nBergman.removeLast();
        }
    }
    private void sumWrap(BergmanNotation sum, LinkedList<Integer> transfer){
        int sumSize = sum.nBergman.size();
        sum.wrapAll();

        int diff = sum.nBergman.size() - sumSize;
        if (diff>0){
            for (int i = 0; i < transfer.size(); i++) {
                int nPosNew = transfer.get(i) + diff;
                transfer.set(i,nPosNew);
            }
        }

    }
    private void addZeroFirst(int n){
        for (int i = 0; i < n; i++) { // before  101,001
            nBergman.addFirst(ZERO);  // after 00101,001
            ++nFraction;
        }
    }

    private void addZeroLast(int n){
        for (int i = 0; i < n; i++) {// before 101,001
            nBergman.addLast(ZERO);   // after 101,00100
        }
    }
    private void unwrap(int nPos){
        if (nPos == nBergman.size()-1 ){ // крайня права позиція
            nBergman.set(nPos,ZERO);
            nBergman.addLast(ONE);
            nBergman.addLast(ONE);
        } else {
            if (nBergman.get(nPos + 1) == ONE) {
                unwrap(nPos+1); // 101,10 -> 101,011
            }
            nBergman.set(nPos + 1, ONE); // 100,111

            if (nBergman.get(nPos + 2) == ONE) { // 101,01 -> 101,0011
                unwrap(nPos+2);
            }
            nBergman.set(nPos + 2, ONE);
        }
    }

    public void wrapAll() {
        boolean isWrap;
        do {
            isWrap = false;
            for (int i = 1; i < nBergman.size(); i++) {
                if (nBergman.get(i) == ONE && nBergman.get(i-1) == ONE){
                    isWrap = true;
                    nBergman.set(i,ZERO);
                    nBergman.set(i-1,ZERO);
                    if (i==1){
                        nBergman.addFirst(ONE);
                        nFraction++;
                    } else {
                        nBergman.set(i-2, ONE);
                    }
                }
            }
        } while (isWrap);
    }

    public List<Byte> getnBergman() {
        return nBergman;
    }

    public int getnFraction() {
        return nFraction;
    }

    public int characteristic() {return nFraction - 1;}

    public int precision() {return nBergman.size() - nFraction;}

    @Override
    public String toString() {
        String strBergman ="";
        for (int i = 0; i < nFraction ; i++) {
            strBergman += nBergman.get(i);
        }
        if (nFraction != nBergman.size())
            strBergman += ",";
        for (int i = nFraction; i < nBergman.size(); i++) {
            strBergman += nBergman.get(i);
        }

        return  strBergman;
    }

    private LinkedList<Byte> nBergman = new LinkedList<>();;
    int nFraction; // позиція з якої починається дробна частина числа

    public static final byte ZERO = 0;
    public static final byte ONE = 1;
    public static final double TAU = (1+Math.sqrt(5))/2;
}
