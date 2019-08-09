import java.util.Comparator;

public class ProgramComparator implements Comparator<Program> {

    @Override
    public int compare(Program o1, Program o2) {
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
