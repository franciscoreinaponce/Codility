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

        System.out.println("Result: " + isAStringBalanced("azABaabza"));
        System.out.println("Result: " + isAStringBalanced("TacoCat"));
        System.out.println("Result: " + isAStringBalanced("AcZCbaBz"));
        System.out.println("Result: " + isAStringBalanced("abcdefghijklmnopqrstuvwxyz"));

        // Some random tests
        System.out.println("Result: " + isAStringBalanced("mKAbBatAcZCbaBzD"));
        System.out.println("Result: " + isAStringBalanced("mKAbBatAcZCbaBzDaAK"));

    }

    private static int isAStringBalanced(String S) {
        System.out.println("\ninput: " + S);

        Set<Character> upperCaseChars = S.chars().filter(Character::isUpperCase).mapToObj(i -> (char) i).collect(Collectors.toSet());
        System.out.println("upperCaseChars: " + upperCaseChars);

        Set<Character> lowerCaseChars = S.chars().filter(Character::isLowerCase).mapToObj(i -> (char) i).collect(Collectors.toSet());
        System.out.println("lowerCaseChars:" + lowerCaseChars);

        Set<Character> upperCaseCharsNotRepeated = upperCaseChars.stream()
                .filter(c -> !lowerCaseChars.contains(Character.toLowerCase(c)))
                .collect(Collectors.toSet());

        Set<Character> lowerCaseCharsNotRepeated = lowerCaseChars.stream()
                .filter(i -> !upperCaseChars.contains(Character.toUpperCase(i)))
                .collect(Collectors.toSet());

        Set<Character> allCharsNotRepeated = Stream.of(upperCaseCharsNotRepeated, lowerCaseCharsNotRepeated)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
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

    private static Set<String> splitStrings(Set<String> strings, Character splitter) {
        Set<String> res = new HashSet<>();
        for (String ss : strings) {
            res.addAll(Arrays.stream(ss.split(splitter.toString())).collect(Collectors.toSet()));
        }
        return res;
    }

}
