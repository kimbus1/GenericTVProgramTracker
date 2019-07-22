import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//Maybe can create 4 csvs instead of 2 and split type between anime and TV
//TODO create methods to sanitise all types of inputs
class main {

    public static void main(String[] args) {
        //Loading Files
        ArrayList<program> programs = loadData("watching.csv");
        ArrayList<program> programsC = loadData("complete.csv");

        //Main Loop
        boolean editing = true;
        boolean complete = false;
        while (editing) {
            if(!complete) {
                programs.sort(new programComparator());
                for (int i = 0; i < programs.size(); i++) {
                    System.out.println("[" + i + "] " + programs.get(i));
                }
            } else {
                programsC.sort(new programComparator());
                for (int i = 0; i < programsC.size(); i++) {
                    System.out.println("[" + i + "] " + programsC.get(i));
                }
            }
            String choice;
            int choiceI;
            Scanner input = new Scanner(System.in);

            System.out.println("(i)ncrement progress, (m)ove to/from completed, (d)elete, (e)dit, (a)dd, (s)earch, s(w)itch to/from completed, sa(v)e and quit, (q)uit withouut saving ");
            choice = input.nextLine();
            choice.toLowerCase();
            switch (choice){
                case "a":
                    String title;
                    int epOn;
                    int epTot;
                    String airS;
                    boolean air = false;
                    try {
                        System.out.println("Enter title");
                        title = input.nextLine();
                        System.out.println("Enter episode currently on");
                        epOn = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter total episodes");
                        epTot = input.nextInt();
                        input.nextLine();
                    } catch (java.util.InputMismatchException e){
                        System.out.println("invalid choice");
                        input.nextLine();
                        input.nextLine();
                        continue;
                    }
                    System.out.println("Enter t if airing");
                    airS = input.nextLine();
                    if (airS.equals("t")){
                        air = true;
                    }
                    if (epOn > epTot){
                        epOn = epTot;
                    }
                    program p = new program(title, epOn, epTot, air);
                    if (!complete) {
                        programs.add(p);
                    } else {
                        programsC.add(p);
                    }
                    continue;
                case "s":
                    //search
                    continue;
                case "w":
                    complete = !complete;
                    continue;
                case "v":
                    save(programs, false);
                    save(programsC, true);
                case "q":
                    System.exit(0);
                case "i":
                case "m":
                case "d":
                case "e":
                    break;
                default: System.out.println("invalid choice");
                    input.nextLine();
                    continue;
            }

            System.out.println("Select an entry to edit");
            choiceI = input.nextInt();
            if (choiceI > programs.size() - 1 || choiceI < 0){
                System.out.println("invalid choice");
                input.nextLine();
                continue;
            }
            input.nextLine();
            System.out.println(programs.get(choiceI));
            input.nextLine();

            //Increment program number
            //Move to Completed
            //Delete
            //Edit(leave blank to ignore, set blank ints to 0, set last to true if airing was edited)
            //Search


        }
        //Shutdown

    }
    private static ArrayList<program> loadData (String file){
        //Method to load a string int int boolean csv file into memory as a program class array list
        ArrayList<program> programs = new ArrayList<>();
        File watchingFile = new File(file);
        if (!watchingFile.exists()) {
            try {
                watchingFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String watchingOut = null;
        BufferedReader watchingRead = null;

        try {
            watchingRead = new BufferedReader(new FileReader(watchingFile));
            while ((watchingOut = watchingRead.readLine()) != null) {
                //Cleaning title text
                String title = watchingOut.substring(0, watchingOut.indexOf(",", watchingOut.indexOf('"', 1))).replace('"', ' ').trim();
                String remaining = watchingOut.substring(watchingOut.indexOf(",", watchingOut.indexOf('"', 1)) + 1);
                int currentEp = Integer.parseInt(remaining.substring(0, remaining.indexOf(",")));
                int totalEp = Integer.parseInt(remaining.substring(remaining.indexOf(",") + 1, remaining.indexOf(",", remaining.indexOf(",") + 1)));
                Boolean airing = false;
                if (remaining.substring(remaining.lastIndexOf(",") + 1).toUpperCase().equals("TRUE")) {
                    airing = true;
                }
                programs.add(new program(title, currentEp, totalEp, airing));

            }
            watchingRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        programs.sort(new programComparator());
        return programs;
    }

    private static void save (ArrayList<program> programs, boolean complete){
        //Save CSV files
        try {
            String c = "watching.csv";
            if (complete){
                c = "complete.csv";
            }
            PrintWriter pw = new PrintWriter(c, "UTF-8");
            for (int i = 0; i < programs.size(); i++) {
                pw.println('"' + programs.get(i).title + '"' + "," + programs.get(i).currentEp + "," + programs.get(i).totalEp + "," + programs.get(i).airing);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}