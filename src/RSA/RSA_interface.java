package RSA;

import java.math.BigInteger;
import java.util.Scanner;

public class RSA_interface {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("TODO");
        RSA_encryptor encryptor = new RSA_encryptor("T");
        encryptor.encrypt(BigInteger.valueOf(13), BigInteger.valueOf(65537));
    }
}
