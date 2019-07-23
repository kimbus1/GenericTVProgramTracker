public class program {
    String title;
    int currentEp;
    int totalEp;
    boolean airing;

    public program(String title, int currentEp, int totalEp, boolean airing){
        this.title = title;
        this.currentEp = currentEp;
        this.totalEp = totalEp;
        this.airing = airing;
    }

    public void incrementEpisode(){
        if (currentEp < totalEp) {
            currentEp++;
        }
    }

    public void editData(String title, int currentEp, int totalEp, boolean airing, boolean airingEdited){
        title.trim();
        if (!title.isEmpty()){
            this.title = title;
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

    public String toString(){
        if (airing){
            return title + " " + currentEp + "/" + totalEp + " Airing";
        }
        return title + " " + currentEp + "/" + totalEp;
    }

    public void invertAiring(){
        airing = !airing;
    }

    public String getTitle(){
        return title;
    }

    public void complete(){
        currentEp = totalEp;
    }
}
