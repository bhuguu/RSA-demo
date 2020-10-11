package RSA;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RSA_interface {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input 1 to encrypt, 2 to decrypt, 3 to generate keys, \"exit\" to exit:");
            String cmd = scanner.nextLine();
            if (cmd.toLowerCase().equals("exit")) {
                break;
            }
            int type = cmdToInt(cmd);
            processWithTypeNum(type);
        }
        System.out.println("Thanks for using, see you next time!");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    private static int cmdToInt(String cmd){
        int type;
        try {
            type = Integer.parseInt(cmd);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, try again.");
            return -1;
        }
        if (type < 1 || type > 4) {
            System.out.println("Invalid input, try again.");
            return -1;
        }
        return type;
    }

    private static File getLegalDirectory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input directory you use to store your keys, empty input for default:");
        File dir = new File("./");
        while(true) {
            String path = scanner.nextLine();
            if (!path.isEmpty()) {
                dir = new File(path);
            }
            if (!dir.isDirectory()) {
                System.out.println("Oops! You may have a typo in your input, check it and try again!");
                continue;
            }
            break;
        }
        return dir;
    }

    private static keyPair findKey(File dir) throws IOException {
        keyPair keyDict;
        try {
            keyDict = new keyPair(dir);
        } catch (IOException e) {
            System.out.println("No available keys.txt file found or current file is broken.");
            throw e;
        }
        return keyDict;
    }

    private static void encryptProcess() throws NumberFormatException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input a message you want to encrypt(no more than 128 characters):");
        String message = scanner.nextLine();
        System.out.println("Input public key you get from receiver:");
        String encodedKeysString = scanner.nextLine();
        keyPair keyDict;
        keyDict = new keyPair(encodedKeysString);
        RSA_encryptor encryptor = new RSA_encryptor(message);
        System.out.println("The following line is encrypted information:\r\n" + encryptor.encrypt(keyDict).toString());
    }

    private static void decryptProcess(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input encrypted message:");
        String encryptedMessage = scanner.nextLine();
        File dir = getLegalDirectory();
        keyPair keyDict;
        try {
            keyDict = findKey(dir);
        }
        catch (IOException e){
            System.out.println("Due to key file lost, decryption process terminated.");
            return;
        }
        RSA_decryptor decryptor = new RSA_decryptor(new BigInteger(encryptedMessage));
        String message = decryptor.decrypt(keyDict);
        System.out.println("Original message: " + message);
    }
    private static void generateKeys()throws IOException{
        File dir = getLegalDirectory();
        keyPair keyDict;
        try{
            keyDict = findKey(dir);
        }
        catch (IOException e){
            keyDict = new keyPair();
        }
        keyDict.printPublicKey(dir);
    }

    private static void processWithTypeNum(int type) {
        try {
            switch (type) {
                case 1:
                    encryptProcess();
                    break;
                case 2:
                    decryptProcess();
                    break;
                case 3:
                    generateKeys();
                default:
                    break;
            }
        }
        catch (NumberFormatException ignored){}
        catch (IOException e2){
            System.out.println("File write failed, check your disk space and try again.");
        }
    }
}
