import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task2_BalancedString {

    /**
     * Write a function:
     * class Solution { public int solution(String S); }
     * that, given a string S of length N, returns the length of the
     * shortest balanced fragment of S. If S does not contain any
     * balanced fragments, the function should return -1.
     * <p>
     * Examples:
     * <p>
     * 1. Given S = "azABaabza", the function should return 5.
     * The shortest balanced fragment of S is "ABaab".
     * <p>
     * 2. Given S = "TacoCat", the function should return -1.
     * There is no balanced fragment.
     * <p>
     * 3. Given S = "AcZCbaBz", the function should return 8.
     * The shortest balanced fragment is the whole string.
     * <p>
     * 4. Given S = "abcdefghijklmnopqrstuvwxyz", the function should return -1.
     * <p>
     * Assume that:
     * <p>
     * 1. N is an integer within the range [1..200];
     * 2. string S consists only of letters ('a'-'z' and/or 'A'-'Z').
     */
    public static void main(String[] args) {

        System.out.println("Result: " + isStringBalanced("azABaabza"));
        System.out.println("Result: " + isStringBalanced("TacoCat"));
        System.out.println("Result: " + isStringBalanced("AcZCbaBz"));
        System.out.println("Result: " + isStringBalanced("abcdefghijklmnopqrstuvwxyz"));

        // Some random tests
        System.out.println("Result: " + isStringBalanced("mKAbBatAcZCbaBzD"));
        System.out.println("Result: " + isStringBalanced("mKAbBatAcZCbaBzDaAK"));

    }

    /**
     * Given a string S, returns the length of the shortest balanced fragment.
     * Fragments of size 1 are not taken into account.
     *
     * @param S input string to be processed
     * @return length of the shortest balanced fragment, -1 if none
     */
    private static int isStringBalanced(String S) {
        System.out.println("\ninput: " + S);

        Set<Character> allCharsNotRepeated = findCharsNotRepeated(S);
        System.out.println("charsNotRepeated: " + allCharsNotRepeated);

        Set<String> potentialResults = new HashSet<>(Collections.singleton(S));
        for (Character c : allCharsNotRepeated) {
            potentialResults = splitStrings(potentialResults, c);
        }

        System.out.println("potentialResults: " + potentialResults);
        return potentialResults.stream()
                .filter(element -> element.length() > 1)
                .sorted(Comparator.comparing(String::length))
                .map(String::length)
                .findFirst()
                .orElse(-1);
    }

    /**
     * Given a string, find those characters that appear only 1 time (keeping case-sensitive).
     * E.g.Given "atAcZCbaBzD", returns "t, D"
     *
     * @param input string to be processed
     * @return set containing the chars that are not repeated in the string
     */
    private static Set<Character> findCharsNotRepeated(String input) {
        Set<Character> upperCaseChars = input.chars().filter(Character::isUpperCase).mapToObj(i -> (char) i).collect(Collectors.toSet());
        System.out.println("upperCaseChars: " + upperCaseChars);

        Set<Character> lowerCaseChars = input.chars().filter(Character::isLowerCase).mapToObj(i -> (char) i).collect(Collectors.toSet());
        System.out.println("lowerCaseChars:" + lowerCaseChars);

        Set<Character> upperCaseCharsNotRepeated = upperCaseChars.stream()
                .filter(c -> !lowerCaseChars.contains(Character.toLowerCase(c)))
                .collect(Collectors.toSet());

        Set<Character> lowerCaseCharsNotRepeated = lowerCaseChars.stream()
                .filter(c -> !upperCaseChars.contains(Character.toUpperCase(c)))
                .collect(Collectors.toSet());

        return Stream.of(upperCaseCharsNotRepeated, lowerCaseCharsNotRepeated)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Given a set of string, divides each string stored in the set by the provided char,
     * keeping those that are not divisible.
     * E.g.Given "AbBatAcZCbaBzDa" and the splitter "t", returns "AbBa, AcZCbaBzDa"
     * E.g.Given "AbBa, AcZCbaBzDa" and the splitter "D", returns "AbBa, AcZCbaBz, a"
     *
     * @param stringSet set of string to be processed
     * @param splitter  char with which the strings will be divided
     * @return set of strings, it contains the strings that are not divisible
     * and the new ones generated after division
     */
    private static Set<String> splitStrings(Set<String> stringSet, Character splitter) {
        Set<String> result = new HashSet<>();
        for (String ss : stringSet) {
            result.addAll(Arrays.stream(ss.split(splitter.toString())).collect(Collectors.toSet()));
        }
        return result;
    }

}
