//duplicates are allowed at most twice, O(n), S(1)
public class RemoveDuplicateII {
    int[] array;
    int index = 2;
    
    public RemoveDuplicateII(int[] array) {
        this.array = array;
    }
    
    public int solve() {
        int len =array.length;
        if(len <= 2)
            return index;
        for(int i = 2; i < len; i++) {
            if(array[i] != array[index - 2])
                array[index++] = array[i];
        }
        return index;
    }
    
    public String toString() {
        String s = "";
        for(int i = 0; i < index; i++)
            s += array[i] + " ";
        return s;
    }
    
    public static void main(String[] args) {
        RemoveDuplicateII rd = new RemoveDuplicateII(new int[]{1, 1, 1, 1, 2, 2, 3, 4, 5, 5, 5, 6, 7, 7});
        System.out.println(rd.solute());
        System.out.println(rd.toString());
    }
}
