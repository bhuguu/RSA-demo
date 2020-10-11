package RSA;

import java.math.BigInteger;

public class RSA_encryptor extends RSABase {

    public RSA_encryptor(String message) throws RuntimeException{
        this.message = message;
        if(message.length() > 256){
            throw new RuntimeException("Length of the message must be less than 256, shrink it and try again!");
        }
    }

    public BigInteger encrypt(keyPair keydict){
        BigInteger E = keydict.getE();
        BigInteger N = keydict.getN();
        BigInteger messageAsInt = encodeStringToBigInteger(this.message);
        return exp_mod(messageAsInt, E, N);
    }
}
