package org.example;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.valueOf;

public class App {
    public static final int MAX_VALUE_FOR_KEY = 100;
    static List<String> allSymbols = getAllSymbols();

    public static List<String> getAllSymbols() {
        List<String> strings = IntStream.range(32, 128)
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .collect(Collectors.toList());
        strings.addAll(List.of("\n", "\t", "\r"));
        return strings;
    }

    public static void main(String[] args) throws DecoderException {
//        task1();
        task2();
    }

    private static void task2() {
        String data = "G0IFOFVMLRAPI1QJbEQDbFEYOFEPJxAfI10JbEMFIUAAKRAfOVIfOFkYOUQFI15ML1kcJFUeYhA4IxAeKVQZL1VMOFgJbFMDIUAAKUgFOElMI1ZMOFgFPxADIlVMO1VMO1kAIBAZP1VMI14ANRAZPEAJPlMNP1VMIFUYOFUePxxMP19MOFgJbFsJNUMcLVMJbFkfbF8CIElMfgZNbGQDbFcJOBAYJFkfbF8CKRAeJVcEOBANOUQDIVEYJVMNIFwVbEkDORAbJVwAbEAeI1INLlwVbF4JKVRMOF9MOUMJbEMDIVVMP18eOBADKhALKV4JOFkPbFEAK18eJUQEIRBEO1gFL1hMO18eJ1UIbEQEKRAOKUMYbFwNP0RMNVUNPhlAbEMFIUUALUQJKBANIl4JLVwFIldMI0JMK0INKFkJIkRMKFUfL1UCOB5MH1UeJV8ZP1wVYBAbPlkYKRAFOBAeJVcEOBACI0dAbEkDORAbJVwAbF4JKVRMJURMOF9MKFUPJUAEKUJMOFgJbF4JNERMI14JbFEfbEcJIFxCbHIJLUJMJV5MIVkCKBxMOFgJPlVLPxACIxAfPFEPKUNCbDoEOEQcPwpDY1QDL0NCK18DK1wJYlMDIR8II1MZIVUCOB8IYwEkFQcoIB1ZJUQ1CAMvE1cHOVUuOkYuCkA4eHMJL3c8JWJffHIfDWIAGEA9Y1UIJURTOUMccUMELUIFIlc=";
        byIndex(data);
    }

    private static void byIndex(String data) {
        List<String> symbols = getSymbols(data);
        int imax = 0;
        int max = 0;
        for (int i = 1; i < MAX_VALUE_FOR_KEY; i++) {
            int matches = checkMatches(shift(symbols, i), symbols);
            if(max < matches) {
                max = matches;
                imax = i;
            }
        }
        System.out.println("matches - " + imax + " | " + max);

        for (int i = 0; i < imax; i++) {
            StringBuilder flat = new StringBuilder();
            for (int j = 0; j < symbols.size(); j++) {
                if(j % max == i) {
                    flat.append(symbols.get(j));
                }
            }
            String flatStr = flat.toString();
            FrequencyTable frequencyTable = new FrequencyTable(flatStr);

            tryXorWithGuessingMostFrequented(frequencyTable, i);
        }
        trySolve(data, "goBfjEuiEjap", imax);
    }

    private static void trySolve(String data, String key, int imax) {
        System.out.println();
        for (int i = 0; i < data.length(); i++) {
            System.out.print(xor(valueOf(key.charAt(i % imax)), valueOf(data.charAt(i))));
        }
    }

    private static void tryXorWithGuessingMostFrequented(FrequencyTable table, int i) {
        String mostFrequented = table.mostFrequented();
        System.out.println(i + ": " + mostFrequented);
        System.out.println();
        Stream.of(" ", "t", "a", "i", "n", "o", "s", "h", "r", "d", "l", "u", "c", "m", "f", "w", "y", "g", "p", "b", "v", "k", "q", "j", "x", "z")
                        .forEach(s -> System.out.println(xor(mostFrequented, s) + "(" + s + ")"));
    }

    private static int checkMatches(List<String> shifted, List<String> symbols) {
        int count = 0;
        for (int i = 0; i < shifted.size(); i++) {
            if (shifted.get(i).equals(symbols.get(i))) {
                count++;
            }
        }
        return count;
    }

    private static List<String> shift(List<String> symbols, int n) {
        List<String> result = new ArrayList<>(symbols);
        for (int i = 0; i < n; i++) {
            String remove = result.remove(result.size() - 1);
            result.add(0, remove);
        }
        return result;
    }

    private static void task1() throws DecoderException {
        String data = "7958401743454e1756174552475256435e59501a5c524e176f786517545e475f5245191772195019175e4317445f58425b531743565c521756174443455e595017d5b7ab5f525b5b58174058455b53d5b7aa175659531b17505e41525917435f52175c524e175e4417d5b7ab5c524ed5b7aa1b174f584517435f5217515e454443175b524343524517d5b7ab5fd5b7aa17405e435f17d5b7ab5cd5b7aa1b17435f5259174f584517d5b7ab52d5b7aa17405e435f17d5b7ab52d5b7aa1b17435f525917d5b7ab5bd5b7aa17405e435f17d5b7ab4ed5b7aa1b1756595317435f5259174f58451759524f4317545f564517d5b7ab5bd5b7aa17405e435f17d5b7ab5cd5b7aa175650565e591b17435f525917d5b7ab58d5b7aa17405e435f17d5b7ab52d5b7aa1756595317445817585919176e5842175a564e17424452175659175e5953524f1758511754585e59545e53525954521b177f565a5a5e595017535e4443565954521b177c56445e445c5e17524f565a5e5956435e58591b17444356435e44435e54565b17435244434417584517405f564352415245175a52435f5853174e5842175152525b174058425b5317445f584017435f52175552444317455244425b4319";

        String text = fromHex(data);

        String x = new FrequencyTable(text).mostFrequented();

        tryWithSpaceAsMostFrequented(text, x);
    }

    static List<String> getSymbols(String text) {
        return text.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .toList();
    }

    public static String fromHex(String s) throws DecoderException {
        byte[] bytes = Hex.decodeHex(s.toCharArray());
        String x = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(x);
        return x;
    }

    private static void tryWithSpaceAsMostFrequented(String d, String x) {
        System.out.println(d);
        String key = xor(" ", x);
        System.out.println(key);
        List<String> elements = getSymbols(d);

        Map<String, String> toReplace = new HashMap<>();
        for (String s : allSymbols) {
            String xor = xor(s, key);
            System.out.println(xor + " " + s);
            try {
                toReplace.put(xor, s);
            } catch (Exception ignored) {

            }
        }

        String result = elements.stream()
                .map(el -> Optional.ofNullable(toReplace.get(el)).orElse(el))
                .collect(Collectors.joining());

        System.out.println(result);

    }

    public static String xor(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++)
            sb.append((char) (a.charAt(i) ^ b.charAt(i % b.length())));
        return sb.toString();
    }
}
