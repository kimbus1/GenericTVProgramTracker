import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

//Maybe can create 4 csvs instead of 2 and split type between anime and TV
class main {
    static public int numResults;

    public static void main(String[] args) {
        //Loading Files
        ArrayList<Program> programs = loadData("watching.csv");
        ArrayList<Program> programsC = loadData("complete.csv");

        //Main Loop
        boolean editing = true;
        boolean complete = false;
        boolean airSec = true;
        while (editing) {
            airSec = true;
            if (!complete) {
                programs.sort(new ProgramComparator());
                for (int i = 0; i < programs.size(); i++) {
                    if (i == 0){
                        System.out.println("-----------------------------------------------------------");
                    }
                    if (!programs.get(i).airing && airSec){
                        airSec = false;
                        System.out.println("-----------------------------------------------------------");
                    }
                    System.out.println("[" + i + "] " + programs.get(i));
                }
            } else {
                programsC.sort(new ProgramComparator());
                for (int i = 0; i < programsC.size(); i++) {
                    System.out.println("[" + i + "] " + programsC.get(i));
                }
            }
            String choice;
            int choiceI;
            Scanner input = new Scanner(System.in);

            System.out.println("(i)ncrement progress, (m)ove to/from completed, (d)elete, (e)dit, (a)dd, (s)earch, s(w)itch view to/from completed, sa(v)e, save and (q)uit, quit wit(h)ouut saving ");
            choice = input.nextLine();
            choice.toLowerCase();
            switch (choice) {
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
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("invalid choice");
                        input.nextLine();
                        input.nextLine();
                        continue;
                    }
                    System.out.println("Enter t if airing");
                    airS = input.nextLine();
                    if (airS.equals("t")) {
                        air = true;
                    }
                    if (epOn > epTot) {
                        epOn = epTot;
                    }
                    Program p = new Program(title, epOn, epTot, air);
                    if (!complete) {
                        programs.add(p);
                    } else {
                        programsC.add(p);
                    }
                    continue;
                case "s":
                    System.out.println("Enter Query");
                    String q = input.nextLine();
                    System.out.println("Searching for " + q);
                    System.out.println("Results:\n-----------------------------------------------------------");
                    if (!complete){
                        System.out.println(findProgram(q, programs));
                    } else {
                        System.out.println(findProgram(q, programsC));
                    }
                    System.out.println();
                    System.out.println(numResults + " Results");
                    input.nextLine();
                    continue;
                case "w":
                    complete = !complete;
                    continue;
                case "v":
                    System.out.println("Saving");
                    save(programs, false);
                    save(programsC, true);
                    System.out.println("Complete");
                    input.nextLine();
                    System.out.println();
                    continue;
                case "q":
                    System.out.println("Saving");
                    save(programs, false);
                    save(programsC, true);
                case "h":
                    System.out.println("Quitting");
                    System.exit(0);
                case "i":
                case "m":
                case "d":
                case "e":
                    break;
                default:
                    System.out.println("invalid choice");
                    input.nextLine();
                    continue;
            }

            System.out.println("Select an entry");
            try {
                choiceI = input.nextInt();
                int i = programsC.size();
                if (!complete) {
                    i = programs.size();
                }
                if (choiceI > i - 1 || choiceI < 0) {
                    System.out.println("invalid choice");
                    input.nextLine();
                    input.nextLine();
                    continue;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("invalid choice");
                input.nextLine();
                input.nextLine();
                continue;
            }

            switch (choice) {
                case "i":
                    if (!complete) {
                        programs.get(choiceI).incrementEpisode();
                        System.out.println("Incremented " + programs.get(choiceI).getTitle());
                        input.nextLine();
                        if (programs.get(choiceI).currentEp == programs.get(choiceI).totalEp){
                            String c2;
                            System.out.println("Do you wish to move " + programs.get(choiceI).getTitle() + " to completed Programs? y/n");
                            c2 = input.nextLine();
                            if (c2.toLowerCase().equals("y")){
                                programs.get(choiceI).complete();
                                programsC.add(programs.get(choiceI));
                                programs.remove(choiceI);
                                System.out.println("Moved");
                            } else {
                                System.out.println("Ignored");
                            }
                        }
                        input.nextLine();
                    } else {
                        programsC.get(choiceI).incrementEpisode();
                        System.out.println("Incremented " + programsC.get(choiceI).getTitle());
                        input.nextLine();
                        input.nextLine();
                    }
                    continue;
                case "m":
                    if (!complete) {
                        programs.get(choiceI).complete();
                        programsC.add(programs.get(choiceI));
                        programs.remove(choiceI);
                    } else {
                        programs.add(programsC.get(choiceI));
                        programsC.remove(choiceI);
                    }
                    continue;
                case "d":
                    if (!complete) {
                        System.out.println("Deleted " + programs.get(choiceI).getTitle());
                        programs.remove(choiceI);
                        input.nextLine();
                        input.nextLine();
                    } else {
                        System.out.println("Deleted " + programsC.get(choiceI).getTitle());
                        programsC.remove(choiceI);
                        input.nextLine();
                        input.nextLine();
                    }
                    continue;
                case "e":
                    String prN = programsC.get(choiceI).getTitle();
                    if (!complete){
                        prN = programs.get(choiceI).getTitle();
                    }
                    System.out.println("Editing " + prN + ", leave blank to ignore field");
                    String t;
                    int c;
                    String cS;
                    int tl;
                    String tlS;
                    boolean a = false;
                    boolean e;
                    String ec;
                    input.nextLine();
                    System.out.println("New title, leave blank to ignore field");
                    t = input.nextLine();
                    try {
                        System.out.println("New current ep, leave blank to ignore field");
                        cS = input.nextLine();
                        if (cS.equals("")){
                            c = -1;
                        } else {
                            c = Integer.parseInt(cS);
                        }
                        System.out.println("New total ep, leave blank to ignore field");
                        tlS = input.nextLine();
                        if (tlS.equals("")){
                            tl = -1;
                        } else {
                            tl = Integer.parseInt(tlS);
                        }
                        System.out.println("New airing, use t for airing, anything else for finished, leave blank to ignore field");
                        ec = input.nextLine();
                        ec.toLowerCase();
                        if (ec.equals("")){
                            e = false;
                        } else if (ec.equals("t")){
                            a = true;
                            e = true;
                        } else {
                            a = false;
                            e = true;
                        }
                    } catch (java.util.InputMismatchException|java.lang.NumberFormatException ee) {
                        System.out.println("invalid choice");
                        input.nextLine();
                        continue;
                    }
                    if (!complete){
                        programs.get(choiceI).editData(t,c,tl,a,e);
                        System.out.println("Edited Entry " + programs.get(choiceI));
                    } else {
                        programsC.get(choiceI).editData(t,c,tl,a,e);
                        System.out.println("Edited Entry " + programsC.get(choiceI));
                    }
                    input.nextLine();
            }
        }
    }

    private static ArrayList<Program> loadData(String file) {
        //Method to load a string int int boolean csv file into memory as a Program class array list
        ArrayList<Program> programs = new ArrayList<>();
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
                programs.add(new Program(title, currentEp, totalEp, airing));

            }
            watchingRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        programs.sort(new ProgramComparator());
        return programs;
    }

    private static void save(ArrayList<Program> programs, boolean complete) {
        //Save CSV files
        try {
            String c = "watching.csv";
            if (complete) {
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
    private static String findProgram(String search, ArrayList<Program> programs){
        numResults = 0;
        String results = "";
        for (int i = 0; i < programs.size(); i++) {
            if (Pattern.compile(Pattern.quote(search), Pattern.CASE_INSENSITIVE).matcher(programs.get(i).getTitle()).find()) {
                results = results + "\n[" + i + "] " + programs.get(i).getTitle();
                numResults++;
            }
        }
        return results;
    }

}