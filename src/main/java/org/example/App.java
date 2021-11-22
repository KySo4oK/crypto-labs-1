package org.example;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    static List<String> alphabet = getAllSymbols();

    private static List<String> getAllSymbols() {
        List<String> strings = IntStream.range(32, 128)
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .collect(Collectors.toList());
        strings.addAll(List.of("\n", "\t", "\r"));
        return strings;
    }

    public static void main(String[] args) throws DecoderException {
        String data = "7958401743454e1756174552475256435e59501a5c524e176f786517545e475f5245191772195019175e4317445f58425b531743565c521756174443455e595017d5b7ab5f525b5b58174058455b53d5b7aa175659531b17505e41525917435f52175c524e175e4417d5b7ab5c524ed5b7aa1b174f584517435f5217515e454443175b524343524517d5b7ab5fd5b7aa17405e435f17d5b7ab5cd5b7aa1b17435f5259174f584517d5b7ab52d5b7aa17405e435f17d5b7ab52d5b7aa1b17435f525917d5b7ab5bd5b7aa17405e435f17d5b7ab4ed5b7aa1b1756595317435f5259174f58451759524f4317545f564517d5b7ab5bd5b7aa17405e435f17d5b7ab5cd5b7aa175650565e591b17435f525917d5b7ab58d5b7aa17405e435f17d5b7ab52d5b7aa1756595317445817585919176e5842175a564e17424452175659175e5953524f1758511754585e59545e53525954521b177f565a5a5e595017535e4443565954521b177c56445e445c5e17524f565a5e5956435e58591b17444356435e44435e54565b17435244434417584517405f564352415245175a52435f5853174e5842175152525b174058425b5317445f584017435f52175552444317455244425b4319";

        String text = fromHex(data);

        System.out.println(frequencyMax(text));

        tryw1(text);
    }

    public static double frequencyMax(String text) {
        List<String> symbols = getSymbols(text);
        HashMap<String, Integer> frequency = new HashMap<>();

        for (String symbol : symbols) {
            if(frequency.containsKey(symbol)) {
                Integer newFrequency = frequency.get(symbol) + 1;
                frequency.put(symbol, newFrequency);
            } else {
                frequency.put(symbol, 1);
            }
        }
        Optional<Integer> max = frequency.values().stream().max(Integer::compareTo);
        List<String> collect = frequency.entrySet().stream().filter(stringIntegerEntry -> stringIntegerEntry.getValue().equals(max.get()))
                .map(Map.Entry::getKey).collect(Collectors.toList());

        System.out.println(collect + " " +  max.get());
        return max.map(v -> ((double) v)/symbols.size()).orElse(0.0);
    }

    private static List<String> getSymbols(String text) {
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

    private static void tryw1(String d) {
        System.out.println(d);
        String key = xor(String.valueOf(d.charAt(0)), String.valueOf(d.charAt(1)));
        System.out.println("key - " + key);

        List<String> elements = getSymbols(d);

        Map<String, String> toReplace = new HashMap<>();
        for (String s : alphabet) {
            String xor = xor(s, key);
            System.out.println(xor + " " + s);
            try {
                toReplace.put(xor, s);
            } catch (Exception ignored){

            }
        }

        String result = elements.stream()
                .map(toReplace::get)
                .collect(Collectors.joining());

        System.out.println(result);

    }

    public static String xor(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < a.length(); i++)
            sb.append((char)(a.charAt(i) ^ b.charAt(i % b.length())));
        return sb.toString();
    }
}
