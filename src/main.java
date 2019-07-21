import java.io.*;
import java.util.ArrayList;

//Maybe can create 4 csvs instead of 2 and split type between anime and TV
class main {

    public static void main(String[] args) {
        //Setup
        //Import CSV files and load watching CSV into arraylist

        ArrayList<program> programs = new ArrayList<>();
        File watchingFile = new File("watching.csv");
        if (!watchingFile.exists()){
            try {
                watchingFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String watchingOut = null;
        BufferedReader watchingRead;

        try {
            watchingRead = new BufferedReader(new FileReader( watchingFile));
            while ((watchingOut = watchingRead.readLine()) != null){
                //Cleaning title text
                String title = watchingOut.substring(0,watchingOut.indexOf(",", watchingOut.indexOf('"', 1))).replace('"', ' ').trim();
                String remaining = watchingOut.substring(watchingOut.indexOf(",", watchingOut.indexOf('"', 1))+1);
                int currentEp = Integer.parseInt(remaining.substring(0,remaining.indexOf(",")));
                int totalEp = Integer.parseInt(remaining.substring(remaining.indexOf(",")+1, remaining.indexOf(",",remaining.indexOf(",")+1)));
                Boolean airing = false;
                if (remaining.substring(remaining.lastIndexOf(",")+1).equals("TRUE")){
                    airing = true;
                }
                programs.add(new program(title,currentEp,totalEp,airing));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        programs.sort(new programComparator());
        for (int i = 0; i < programs.size(); i++){
            System.out.println(programs.get(i));
        }


        //Main Loop
        //Increment program number
        //Move to Completed
        //Delete
        //Add(title,episodeOn,EpisodeTotal,Airing)
        //Edit(leave blank to ignore, set blank ints to 0, set last to true if airing was edited)
        //Search
        //Switch to Completed View

        //Shutdown
        //Save all changes to CSV files, using "" around titles
    }
}