package RSA;

import java.math.BigInteger;

public class RSABase {

    protected String message;
    protected BigInteger encryptedMessage;

    public static BigInteger mul_mod(BigInteger a, BigInteger b, BigInteger N, BigInteger k){
        BigInteger bitmask = BigInteger.ONE.shiftLeft(N.bitLength()).subtract(BigInteger.ONE);
        BigInteger t = a.multiply(b);
        BigInteger m = t.and(bitmask).multiply(k).and(bitmask);
        BigInteger ans = (t.add(m.multiply(N))).shiftRight(N.bitLength());
        if(ans.compareTo(N) > 0){
            ans = ans.subtract(N);
        }
        return ans;
    }

    public static BigInteger exp_mod(BigInteger M, BigInteger E, BigInteger N){
        BigInteger R = BigInteger.ONE.shiftLeft(N.bitLength());
        BigInteger temp = M.multiply(R).remainder(N);
        BigInteger R_prime = R.modInverse(N);
        BigInteger k = R.multiply(R_prime).subtract(BigInteger.ONE).divide(N);
        BigInteger ans = R.remainder(N);
        while(!E.equals(BigInteger.ZERO)){
            if(E.and(BigInteger.ONE).equals(BigInteger.ONE)){
                ans = mul_mod(temp, ans, N, k);
            }
            temp = mul_mod(temp, temp, N, k);
            E = E.shiftRight(1);
        }
        ans = ans.multiply(R_prime).remainder(N);
        return ans;
    }

    public static BigInteger encodeStringToBigInteger(String s){
        BigInteger ans = BigInteger.ZERO;
        for(char c: s.toCharArray()){
            ans = BigInteger.valueOf(256).multiply(ans);
            ans = ans.add(BigInteger.valueOf((int)c));
        }
        return ans;
    }

    public static String decodeStringFromBigInteger(BigInteger n){
        StringBuilder sb = new StringBuilder();
        while(n.compareTo(BigInteger.ZERO) > 0){
            char c = (char)n.and(BigInteger.ONE.shiftLeft(8).subtract(BigInteger.ONE)).intValue();
            n = n.shiftRight(8);
            sb.insert(0, c);
        }
        return sb.toString();
    }
}
