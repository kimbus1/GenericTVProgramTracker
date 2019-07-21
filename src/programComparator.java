import java.util.Comparator;

public class programComparator implements Comparator<program> {

    @Override
    public int compare(program o1, program o2) {
        if (o1.airing && o2.airing){
            return o1.title.compareTo(o2.title);
        }
        if (o1.airing && !o2.airing){
            return -1;
        }
        if (!o1.airing && o2.airing){
            return 1;
        }
        return o1.title.compareTo(o2.title);
    }
}
