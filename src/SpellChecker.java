

import java.util.*;

public class SpellChecker {
    private Set<String> lexicon;
    private Scanner scanner;

    public SpellChecker(List<String> lexicon) {
        this.lexicon = new HashSet<String>(lexicon);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the spell checker program!\n");

        while (true) {
            System.out.println("Enter a word to check (or 'exit' to quit):");

            // Check whether there is more input to read
            if (!scanner.hasNextLine()) {
                break;
            }

            String word = scanner.nextLine().toLowerCase();

            if (word.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            List<String> results = Collections.singletonList(spellChecker(Collections.singletonList(word)));
            if (results.isEmpty()) {
                System.out.println("No spelling suggestions found.");
            } else {
                System.out.println("Spelling suggestions:");
                for (String suggestion : results) {
                    System.out.println("- " + suggestion);
                }
            }
        }

        scanner.close();
    }

    private String spellChecker(List<String> words) {
        String bestSuggestion = null;
        int bestFrequency = 0;

        for (String a : words) {
            if (lexicon.contains(a)) {
                return a;
            }

            // check permutations of the input string
            for (String perm : generatePermutations(a)) {
                int frequency = frequencyInLexicon(perm);
                if (frequency > bestFrequency) {
                    bestSuggestion = perm;
                    bestFrequency = frequency;
                }
            }

            // delete one character
            for (int i = 0; i < a.length(); i++) {
                String candidate = a.substring(0, i) + a.substring(i + 1);
                int frequency = frequencyInLexicon(candidate);
                if (frequency > bestFrequency) {
                    bestSuggestion = candidate;
                    bestFrequency = frequency;
                }
            }

            // swap adjacent characters
            for (int i = 0; i < a.length() - 1; i++) {
                String candidate = a.substring(0, i) + a.charAt(i + 1) + a.charAt(i) + a.substring(i + 2);
                int frequency = frequencyInLexicon(candidate);
                if (frequency > bestFrequency) {
                    bestSuggestion = candidate;
                    bestFrequency = frequency;
                }
            }

            // insert one character
            for (int i = 0; i <= a.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    String candidate = a.substring(0, i) + c + a.substring(i);
                    int frequency = frequencyInLexicon(candidate);
                    if (frequency > bestFrequency) {
                        bestSuggestion = candidate;
                        bestFrequency = frequency;
                    }
                }
            }

            // replace one character
            for (int i = 0; i < a.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    String candidate = a.substring(0, i) + c + a.substring(i + 1);
                    int frequency = frequencyInLexicon(candidate);
                    if (frequency > bestFrequency) {
                        bestSuggestion = candidate;
                        bestFrequency = frequency;
                    }
                }
            }
        }

        return bestSuggestion;
    }

    private int frequencyInLexicon(String word) {
        int frequency = 0;
        for (String w : lexicon) {
            if (w.equals(word)) {
                frequency++;
            }
        }
        return frequency;
    }

    private Set<String> generatePermutations(String str) {
        Set<String> perms = new HashSet<String>();
        if (str.length() == 0) {
            perms.add("");
            return perms;
        }
        char first = str.charAt(0);
        String rest = str.substring(1);
        for (String perm : generatePermutations(rest)) {
            for (int i = 0; i <= perm.length(); i++) {
                String newPerm = perm.substring(0, i) + first + perm.substring(i);
                perms.add(newPerm);
            }
        }
        return perms;
    }

    public static void main(String[] args) {
        List<String> lexicon = Arrays.asList("adidas", "skechers", "vans", "converse", "nike");
        SpellChecker spellChecker = new SpellChecker(lexicon);
        spellChecker.start();
    }
}

