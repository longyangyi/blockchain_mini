import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

public class Main {

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, SignatureException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "SunEC");
        SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");
        kpg.initialize(256, randomGen);

        KeyPair keyPair = kpg.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥: " + publicKey);

        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("私钥: " + privateKey);

        String[] message_list = {"hello", "world", "test"};

        Signature ecdsa = Signature.getInstance("SHA256withECDSA");

        BlockChain bc= new BlockChain();

        for(int i=0;i<message_list.length;i++) {
            ecdsa.initSign(privateKey);

            ecdsa.update(message_list[i].getBytes("UTF-8"));

            byte[] sign = ecdsa.sign();

            String sign_string = new BigInteger(1, sign).toString(16);
            //System.out.println("Signature: " + sign_string);


            if(i==0) {
                System.out.println("正在创建创世区块....... ");
                bc.addBlock(new Block(sign_string, "0"));//创世块
            }else {
                System.out.println("正在创建第" + (i+1) +"个区块....... ");
                bc.addBlock(new Block(sign_string,bc.get(bc.size()-1).hash));
            }

        }

        System.out.println("区块链是否有效: " + bc.isChainValid());

        System.out.println(StringUtil.getJson(bc));




        System.out.println("验证区块中的数字签名: ");
        ecdsa.initVerify(publicKey);
        for(int i=0;i<message_list.length;i++) {
            ecdsa.update(message_list[i].getBytes("UTF-8"));
            boolean verifyResult = ecdsa.verify(new BigInteger(bc.get(i).data, 16).toByteArray());
            System.out.println("验证第" + (i+1) +"个区块中数字签名: " + verifyResult);
        }

    }
}
