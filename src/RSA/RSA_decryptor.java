package RSA;

import java.math.BigInteger;

public class RSA_decryptor extends RSABase {

    public RSA_decryptor(BigInteger encryptedMessage){
        this.encryptedMessage = encryptedMessage;
    }

    public String decrypt(keyPair keyDict){
        BigInteger D = keyDict.getD();
        BigInteger N = keyDict.getN();
        BigInteger m = exp_mod(encryptedMessage, D, N);
        message = decodeStringFromBigInteger(m);
        return message;
    }
}
