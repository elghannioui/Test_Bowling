import java.util.ArrayList;
import java.lang.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("please enter a valid sequence of rolls for one line separated by turns EX:(X 56 -7 8/ 67 -/ 68 X X X)");
        String input = scanner.nextLine();
        String[] values = input.split(" ");
        ArrayList<String> listframes = new ArrayList<String>(10);
        for (String s : values) {
            if (listframes.size() < 12) {
                listframes.add(s);
            }else
                System.out.println("not a valid sequence, please enter a valid sequence");
        }
        ArrayList<String> numberList = getNumberList();
        if (listframes.size() >= 10) {
            int score = getscore(listframes, numberList);
            System.out.println("the number of rolles you played is " + getNumberRoles(input));
            System.out.println("the number of frames you played is " + getNumberFrames(listframes));
            System.out.println("the number of Strikes you played is " + getNumberStrike(listframes));
            System.out.println("the number of Spares you played is " + getNumberSpare(listframes));
            System.out.println("your score is "+score);
        }else
            System.out.println("line not completed, please enter a valid sequence");
    }

    // function to calculate a number of valid rolls
    public static int getNumberRoles(String sequence) {
        sequence = sequence.replaceAll("\\s", "");
        return sequence.length();
    }

    // function to calculate a number of valid frames
    public static String getNumberFrames(ArrayList<String> listframes) {
        if (listframes.size() >= 10) {
            return "10";
        }
        else return "Not a valid number of frames for a completed game";
    }

    // function to calculate a number of strikes during one line
    public static int getNumberStrike(ArrayList<String> listframes) {
        int numberOfStrikes = 0;
        for (String frame : listframes)
            if (frame.equals("X"))
                numberOfStrikes += 1;
        return numberOfStrikes;
    }

    // function to calculate a number of spares during one line
    public static int getNumberSpare(ArrayList<String> listframes) {
        int numberOfSpare = 0;
        for (String frame : listframes)
            if (!frame.equals("X") && frame.endsWith("/"))
                numberOfSpare += 1;
        return numberOfSpare;
    }

    // fuction to define a list of possible value numbers during a roll
    public static ArrayList<String> getNumberList() {
        ArrayList<String> numberList = new ArrayList<String>();
        numberList.add("1");
        numberList.add("2");
        numberList.add("3");
        numberList.add("4");
        numberList.add("5");
        numberList.add("6");
        numberList.add("7");
        numberList.add("8");
        numberList.add("9");
        return numberList;
    }

    // function to calculate the score of one line
    public static int getscore(ArrayList<String> listframes, ArrayList<String> listbonus) {
        int score = 0;
        ArrayList<String> numberList = getNumberList();
        for (int i = 0; i < 10; i++) {
            // Strike case
            if (listframes.get(i).equals("X")) {
                    if (listframes.get(i+1).equals("X")) {
                        if (listframes.get(i+2).equals("X")) {
                            score += 30;
                        } else if (numberList.contains(Character.toString(listframes.get(i + 2).charAt(0)))) {
                            score += 20 + Integer.parseInt(Character.toString(listframes.get(i + 2).charAt(0)));
                        } else if (Character.toString(listframes.get(2).charAt(0)).equals("-")) {
                            score += 20;
                        } else
                            System.out.println("frames not valid");

                    } else if (numberList.contains(Character.toString(listframes.get(i + 1).charAt(0)))) {
                        int valueFirstRoll = Integer.parseInt(Character.toString(listframes.get(i + 1).charAt(0)));
                        if (numberList.contains(Character.toString(listframes.get(i + 1).charAt(1)))) {
                            int valueSecondRoll = Integer.parseInt(Character.toString(listframes.get(i + 1).charAt(1)));
                            score += 10 + valueFirstRoll + valueSecondRoll;
                        } else if (Character.toString(listframes.get(i + 1).charAt(1)).equals("/")) {
                            score += 20;
                        } else if (Character.toString(listframes.get(i + 1).charAt(1)).equals("-")) {
                            score += 10 + valueFirstRoll;

                        } else
                            System.out.println("roll not valid");

                    } else if (Character.toString(listframes.get(i + 1).charAt(0)).equals("-")) {
                        if (Character.toString(listframes.get(i + 1).charAt(1)).equals("-")) {
                            score += 10;
                        } else if (numberList.contains(Character.toString(listframes.get(i + 1).charAt(1)))) {
                            score += 10 + Integer.parseInt(Character.toString(listframes.get(i + 1).charAt(1)));
                        } else if (Character.toString(listframes.get(i + 1).charAt(1)).equals("/")) {
                            score += 20;
                        } else
                            System.out.println("not a valid roll");

                    } else
                        System.out.println("not a valid frame");

            } else if (Character.toString(listframes.get(i).charAt(0)).equals("-")) {
                if (Character.toString(listframes.get(i).charAt(1)).equals("-")) {
                    score += 0;
                    // Spare case after a miss
                } else if (Character.toString(listframes.get(i).charAt(1)).equals("/")) {
                        if (listframes.get(i + 1).equals("X")) {
                            score += 20;
                        } else if (Character.toString(listframes.get(i + 1).charAt(0)).equals("-")) {
                            score += 10;
                        } else if (numberList.contains(Character.toString(listframes.get(i + 1).charAt(0)))) {
                            score += 10 + Integer.parseInt(Character.toString(listframes.get(i + 1).charAt(0)));
                        } else
                            System.out.println("not a valid roll");
                } else if (numberList.contains(Character.toString(listframes.get(i).charAt(1)))) {
                    score += Integer.parseInt(Character.toString(listframes.get(i).charAt(1)));
                } else
                    System.out.println("not a valid roll");
            } else if (numberList.contains(Character.toString(listframes.get(i).charAt(0)))) {
                int valueFirstRoll = Integer.parseInt(Character.toString(listframes.get(i).charAt(0)));
                if (Character.toString(listframes.get(i).charAt(1)).equals("-")) {
                    score += valueFirstRoll;
                    // Spare case after knocking some pins
                } else if (Character.toString(listframes.get(i).charAt(1)).equals("/")) {
                        if (listframes.get(i + 1).equals("X")) {
                            score += 20;
                        } else if (Character.toString(listframes.get(i + 1).charAt(0)).equals("-")) {
                            score += 10;
                        } else if (numberList.contains(Character.toString(listframes.get(i + 1).charAt(0)))) {
                            score += 10 + Integer.parseInt(Character.toString(listframes.get(i + 1).charAt(0)));
                        } else
                            System.out.println("not a valid roll");
                } else if (numberList.contains(Character.toString(listframes.get(i).charAt(1)))) {
                    score += valueFirstRoll + Integer.parseInt(Character.toString(listframes.get(i).charAt(1)));
                } else
                    System.out.println("not a valid roll");
            } else
                System.out.println("not valid frame");
        }
        return score;
    }
}
