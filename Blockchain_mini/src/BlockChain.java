import java.util.LinkedList;

public class BlockChain extends LinkedList<Block>{

    public static final int difficulty = 5;

    public Boolean isChainValid() {
        Block currentBlock,previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        for(int i=1; i < this.size(); i++) {
            currentBlock = this.get(i);
            previousBlock = this.get(i-1);
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
            byte[] dec=currentBlock.data.getBytes();

        }
        return true;
    }
    public void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        this.add(newBlock);
    }
}
