package creos.simgsg.api.utils;

public final class OArrays {
    private OArrays(){}

    public static <T> boolean contains(T[] array, T toSearch) {
        if(array == null || toSearch == null || array.length == 0) {
            return false;
        }

        for(T e: array) {
            if(toSearch.equals(e)) {
                return true;
            }
        }

        return false;
    }

}
