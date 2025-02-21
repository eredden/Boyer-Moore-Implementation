// Main.java - Entry-point for the program. Simple implementation of the Boyer-Moore algorithm.
// Created on 02/20/2025 by Evan Redden for Computer Science 201: Data Structures and Algorithms.

import java.util.Scanner; // Required for taking in user input.

public class Main {
    // Generates a list of indexes for each ASCII character in a string.
    public static int[] getCharacterIndexes(String string) {
      int sublen = string.length();
      int characterCount = 256;

      // Create a table for all ASCII characters.
      int[] characterIndexes = new int[characterCount];

      for (int index = 0; index < characterCount; index++) {
          characterIndexes[index] = sublen;
      }

      // For characters in the substring, mark their indexes.
      for (int index = 0; index < sublen - 1; index++) {
          // We get the ASCII oridinal of the character here.
          int charIndex = (int) string.charAt(index);

          characterIndexes[charIndex] = sublen - 1 - index;
      }

      return characterIndexes; 
    }

    // Checks whether two strings are equivalent or not in reverse order.
    public static boolean checkParity(String string1, String string2) {
        int index = string1.length() - 1;

        while (string1.charAt(index) == string2.charAt(index)) {
            if (index == 0) {
              return true;
            }

            index -= 1;
        }

        return false;
    }

    // Searches for a substring inside of a string using the Boyer-Moore algorithm.
    public static boolean searchString(String string, String substring) {
        int[] characterIndexes = getCharacterIndexes(substring);
        int skip = 0;
        int strlen = string.length();
        int sublen = substring.length();

        while (strlen - skip >= sublen) {
            if (checkParity(string.substring(skip, skip + sublen), substring)) {
                return true;
            }

            // If a bad character is found, skip past that using the offset from the 
            // character indexes table that we made during the pre-processing.
            int bad_char = (int) string.charAt(skip + sublen - 1);

            skip += characterIndexes[bad_char];
        }

        return false;
    }

    // Continously prompt the user to select an option until the user provides a valid integer.
    public static int promptForOptionSelection(Scanner listener) {
        int optionCount = 3;

        System.out.println("\nBOYER-MOORE U.S. STATE SEARCH INTERFACE:");
        System.out.println("1. Print all U.S. states.");
        System.out.println("2. Print all U.S. states containing a given substring.");
        System.out.println("3. Exit the program.\n");

        while (true) {
            try {
                System.out.print("Select an option (1-" + optionCount + "): ");

                int selection = Integer.parseInt(
                  listener.nextLine()
                );

                if (selection >= 1 && selection <= optionCount) {
                  return selection;
                }
            }

            catch (NumberFormatException err) {
                // Do nothing if a int cast error is encountered.
                // This is done so that the user does not see the error.
                ;
            }

            System.out.println("Invalid input. Select an integer value between 1 and " + optionCount + ".\n");
        }
    }

    public static void main(String[] args) {
        Scanner listener = new Scanner(System.in);
        String[] states = {
          "alaska",
          "alabama",
          "arkansas",
          "arizona",
          "california",
          "colorado",
          "connecticut",
          "delaware",
          "florida",
          "georgia",
          "hawaii",
          "iowa",
          "idaho",
          "illinois",
          "indiana",
          "kansas",
          "kentucky",
          "louisiana",
          "massachusetts",
          "maryland",
          "maine",
          "michigan",
          "minnesota",
          "missouri",
          "mississippi",
          "montana",
          "north carolina",
          "north dakota",
          "nebraska",
          "new hampshire",
          "new jersey",
          "new mexico",
          "nevada",
          "new york",
          "ohio",
          "oklahoma",
          "oregon",
          "pennsylvania",
          "rhode island",
          "south carolina",
          "south dakota",
          "tennessee",
          "texas",
          "utah",
          "virginia",
          "vermont",
          "washington",
          "wisconsin",
          "west virginia",
          "wyoming"
        };

        while (true) {
            switch (promptForOptionSelection(listener)) {
                // Print out all U.S. states.
                case 1:
                    for (int i = 0; i < states.length; i++) {
                        System.out.println(states[i]);
                    }

                    break;

                // Print all of the U.S. states containing a given substring.
                case 2:
                    System.out.print("Enter a substring to search for: ");
                    String substring = listener.nextLine();

                    for (int i = 0; i < states.length; i++) {
                        if (searchString(states[i], substring)) {
                            System.out.println(states[i]);
                        }
                    }

                    break;

                // Exit the program.
                case 3:
                    System.out.println("Have a great day!\n");

                    listener.close();
                    return;
            }
        }
    }
}