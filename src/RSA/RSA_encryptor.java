package RSA;

import java.math.BigInteger;

public class RSA_encryptor {

    private final String message;

    public RSA_encryptor(String message) throws RuntimeException{
        this.message = message;
        if(message.length() > 256){
            throw new RuntimeException("Length of the message must be less than 256, shrink it and try again!");
        }
    }

    public BigInteger mul_mod(BigInteger a, BigInteger b, BigInteger N, BigInteger k){
        BigInteger bitmask = BigInteger.ONE.shiftLeft(N.bitLength()).subtract(BigInteger.ONE);
        BigInteger t = a.multiply(b);
        BigInteger m = t.and(bitmask).multiply(k).and(bitmask);
        BigInteger ans = (t.add(m.multiply(N))).shiftRight(N.bitLength());
        if(ans.compareTo(N) > 0){
            ans = ans.subtract(N);
        }
        return ans;
    }

    public BigInteger exp_mod(BigInteger M, BigInteger E, BigInteger N){//N is a big prime number, thus is odd
        System.out.println(M.toString() + ' ' + E.toString() + ' ' + N.toString());
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
        System.out.println(ans.toString());
        return ans;
    }

    public BigInteger encrypt(BigInteger E, BigInteger N){
        BigInteger messageAsInt = BigInteger.ZERO;
        for(char c: this.message.toCharArray()){
            messageAsInt = BigInteger.valueOf(256).multiply(messageAsInt);
            messageAsInt = messageAsInt.add(BigInteger.valueOf((int)c));
        }
        BigInteger c = exp_mod(messageAsInt, E, N);
        System.out.println(c.toString());
        return c;
    }
}
