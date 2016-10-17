//If duplicates are allowed, e.g. 1,3,1,1,1, when array[mid] = array[start], 
//we still can't decide whether target falls at some position between mid and start or not,
//cuz there would be some elements existing between mid and start
//if(array[mid] == array[start]) start++;
public class SearchInRotatedSortedArrayII {
    int[] array;
    public SearchInRotatedSortedArrayII(int[] array) {
        this.array = array;
    }
    
    public boolean solve(int target) {
//        for(int i = 0; i < array.length; i++) {
//            if(array[i] == target)
//                return true;
//        }
//        return false;
        
        int end = array.length - 1;
        int start = 0, mid = 0;
        if(end < 0 || array == null || (end == 0 && array[end] != target))
            return false;
        
        while(start <= end) {
            mid = (end + start) >> 1;
            if(array[mid] == target) {
                return true;
            } 
            
            if(array[start] < array[mid]) {
                if(target < array[mid] && target >= array[start])
                    end = mid - 1;
                else 
                    start = mid + 1;
            } else if(array[start] > array[mid]) {
                if(array[mid] < target && target <= array[end])
                    start = mid + 1;
                else
                    end = mid - 1;
            } else {
                start++;
            }
        }
        return false;
    }
}
