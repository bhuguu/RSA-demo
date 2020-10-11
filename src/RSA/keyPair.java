package RSA;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class keyPair {

    private final BigInteger E;
    private final BigInteger N;
    private final BigInteger D;

    private final Random rdm;

    public keyPair(){
        this.rdm = new Random();
        BigInteger P = BigInteger.probablePrime(1024, rdm);
        BigInteger Q = BigInteger.probablePrime(1024, rdm);
        this.N = P.multiply(Q);
        BigInteger phi = P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE));
        this.E = BigInteger.probablePrime(20, rdm);
        this.D = this.E.modInverse(phi);
    }

    public void encodeKeysToFile(File dir) throws IOException{
        Writer writer = new FileWriter(dir.getName() + "/keys.txt");
        writer.write(E.toString() + "\r\n");
        writer.write(N.toString() + "\r\n");
        writer.write(D.toString());
        writer.close();
    }

    public Map<String, BigInteger> decodeKeysFromFile(File dir) throws IOException{
        Map<String, BigInteger> paramDict = new HashMap<>();
        Reader reader = new FileReader(dir.getName() + "/keys.txt");
        BufferedReader bfReader = new BufferedReader(reader);
        paramDict.put("E", new BigInteger(bfReader.readLine()));
        paramDict.put("N", new BigInteger(bfReader.readLine()));
        paramDict.put("D", new BigInteger(bfReader.readLine()));
        return paramDict;
    }

    public BigInteger getE() {
        return E;
    }

    public BigInteger getN() {
        return N;
    }

    public BigInteger getD() {
        return D;
    }

    public keyPair(File dir) throws IOException{
        this.rdm = new Random();
        Map<String, BigInteger> paramDict = decodeKeysFromFile(dir);
        this.E = paramDict.get("E");
        this.N = paramDict.get("N");
        this.D = paramDict.get("D");
    }

    public keyPair(String encodedKeyString){
        this.rdm = new Random();
        BigInteger keyBigInteger;
        try {
            keyBigInteger = new BigInteger(encodedKeyString);
        }
        catch (NumberFormatException e){
            System.out.println("The key you get from receiver may not be right, please use the right key and try again.");
            System.out.println("Your key: " + encodedKeyString);
            throw e;
        }
        String keyString = RSABase.decodeStringFromBigInteger(keyBigInteger);
        String[] keys = keyString.split("N=");
        try {
            this.E = new BigInteger(keys[0].substring(2));
            this.N = new BigInteger(keys[1]);
        }
        catch (NumberFormatException e){
            System.out.println("The key you get from receiver may not be right, please use the right key and try again.");
            throw e;
        }
        this.D = BigInteger.valueOf(-1);
    }

    public void printPublicKey (File dir) throws IOException{
        String s = "E=" + this.E.toString() + "N=" + this.N.toString();
        String keyString = RSABase.encodeStringToBigInteger(s).toString();
        System.out.println(keyString);
        encodeKeysToFile(dir);
    }
}
