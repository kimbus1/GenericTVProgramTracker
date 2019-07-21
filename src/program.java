public class program {
    String name;
    int currentEp;
    int totalEp;
    boolean airing;

    public program(String name, int currentEp, int totalEp, boolean airing){
        this.name = name;
        this.currentEp = currentEp;
        this.totalEp = totalEp;
        this.airing = airing;
    }

    public void incrementEpisode(){
        if (currentEp < totalEp) {
            currentEp++;
        }
    }

    public void editData(String name, int currentEp, int totalEp, boolean airing, boolean airingEdited){
        name.trim();
        if (!name.isEmpty()){
            this.name = name;
        }
        if (currentEp > 0){
            this.currentEp = currentEp;
        }
        if (totalEp > 0){
            this.totalEp = totalEp;
        }
        if (airingEdited){
            this.airing = airing;
        }
    }
}
