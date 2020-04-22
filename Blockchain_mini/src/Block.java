import java.util.Date;

public class Block {

    public String hash;
    public String previousHash; //上一个区块的hash值
    public String data; //每个区块存放的信息，这里我们存放的是一串字符串
    private long timeStamp; //时间戳
    private int nonce;//挖矿者的工作量证明

    //构造
    public Block(String data,String previousHash ) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }


    //基于区块头计算哈希值
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data);//区块头
        //根据previousHash、data和timeStamp等产生唯一hash
        return calculatedhash;
    }
    //挖矿
    public void mineBlock(int difficulty) {
        //目标值，difficulty越大，下面计算量越大
        String target = StringUtil.getDificultyString(difficulty);
        //difficulty如果为5，那么target则为 00000
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("创建区块:" + hash);
    }
}

