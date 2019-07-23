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
            if (!complete) {
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

            System.out.println("(i)ncrement progress, (m)ove to/from completed, (d)elete, (e)dit, (a)dd, (s)earch, s(w)itch to/from completed, sa(v)e, save and (q)uit, quit wit(h)ouut saving ");
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
                    System.out.println("Saving");
                    save(programs, false);
                    save(programsC, true);
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

            System.out.println("Select an entry to edit");
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
                        programs.get(choiceI).invertAiring();
                        programsC.add(programs.get(choiceI));
                        programs.remove(choiceI);
                    } else {
                        programsC.get(choiceI).invertAiring();
                        programsC.get(choiceI).complete();
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
                        System.out.println("New Entry " + programs.get(choiceI));
                    } else {
                        programsC.get(choiceI).editData(t,c,tl,a,e);
                        System.out.println("New Entry " + programsC.get(choiceI));
                    }
                    input.nextLine();


            }

            //Search


        }
        //Shutdown

    }

    private static ArrayList<program> loadData(String file) {
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

    private static void save(ArrayList<program> programs, boolean complete) {
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
}