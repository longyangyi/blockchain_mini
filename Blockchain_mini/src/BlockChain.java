import java.util.LinkedList;

public class BlockChain{
    public static LinkedList<Block> blockchain = new LinkedList<Block>();
    public static int difficulty = 5;
    public static void main(String[] args) {
        System.out.println("正在创建第一个区块链....... ");
        addBlock(new Block("我是第一个区块链", "0"));//创世块

        System.out.println("正在创建第二个区块链....... ");
        addBlock(new Block("我是第二个区块链",blockchain.get(blockchain.size()-1).hash));

        System.out.println("正在创建第三个区块链.......");
        addBlock(new Block("我是第三个区块链",blockchain.get(blockchain.size()-1).hash));

        System.out.println("区块链是否有效的: " + isChainValid());

        String blockchainJson = StringUtil.getJson(blockchain);
        System.out.println(blockchainJson);
    }
    public static Boolean isChainValid() {
        Block currentBlock,previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("这个区块还没有被开采。。。");
                return false;
            }
        }
        return true;
    }
    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
}
