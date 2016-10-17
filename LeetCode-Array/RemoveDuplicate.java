// remove duplicates from sorted array
public class RemoveDuplicate {
    int[] array;
    int index = 0;
    
    public RemoveDuplicate(int[] array) {
        this.array = array;
    }
    
    public int solve() {
        int len = array.length;
        if(len == 0)
            return 0;
        
        for(int i = 1; i < len; i++) {
            if(array[index] != array[i])
                array[++index] = array[i]; 
        }
        return index + 1;
    }
    
    public String toString() {
        String s = "";
        for(int i = 0; i <= index; i++)
            s += array[i] + " ";
        return s;
    }
    
    public static void main(String[] args) {
        RemoveDuplicate rd = new RemoveDuplicate(new int[]{1, 1, 1, 2, 2, 3, 4, 5, 5});
        System.out.println(rd.solute());
        System.out.println(rd.toString());
    }
}
