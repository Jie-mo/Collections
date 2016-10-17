// assume no duplicate exists in the array, S(1), O(logN)
public class SearchInRotatedSortedArray {
    int[] array;
    int index = -1;
    public SearchInRotatedSortedArray(int[] array) {
        this.array = array;
    }
    
    public int solve(int target) {
        int end = array.length - 1;
        int start = 0, mid = 0;
        if(end < 0 || array == null || (end == 0 && array[end] != target))
            return index;
        
        while(start <= end) {
            mid = (end + start) >> 1;
            if(array[mid] == target) {
                return index;
            } 
            
            if(array[start] < array[mid]) {
                if(target < array[mid] && target >= array[start])
                    end = mid - 1;
                else 
                    start = mid + 1;
            } else {
                if(array[mid] < target && target <= array[end])
                    start = mid + 1;
                else
                    end = mid - 1;
            }
        }
        return index;
    }
    
    public String toString() {
        String s = "";
        for(int i = 0; i < index; i++)
            s += array[i] + " ";
        return s;
    }
}
