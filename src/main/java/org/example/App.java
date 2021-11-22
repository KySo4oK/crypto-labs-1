package org.example;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    static List<String> alphabet = getAllSymbols();

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
        double max = 0.0;
        double heightMax = 1;
        for (int height = 1; height < 8; height++) {
            double frequency = maxFreqByHeight(data, height);
            if (frequency > max) {
                max = frequency;
                heightMax = height;
            }
        }
        System.out.println(max + " | " + heightMax);

        System.out.println(maxFreqByHeight1(data, ((int) heightMax)));
    }

    private static double maxFreqByHeight(String data, int height) {
        List<String> dataList = getSymbols(data);
        double max = 0.0;
        for (int stage = 0; stage < height; stage++) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i <= dataList.size() / height + 1; i++) {
                int index = i * height + stage;
                if (dataList.size() > index) {
                    result.append(dataList.get(index));
                }
            }
            FrequencyTable table = new FrequencyTable(result.toString());
            double v = table.biggestFrequency();
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    private static String maxFreqByHeight1(String data, int height) {
        List<String> dataList = getSymbols(data);
        List<String> resultLines = new ArrayList<>();
        for (int stage = 0; stage < height; stage++) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i <= dataList.size() / height + 1; i++) {
                int index = i * height + stage;
                if (dataList.size() > index) {
                    result.append(dataList.get(index));
                }
            }
            String text = result.toString();
            FrequencyTable table = new FrequencyTable(text);
            String mostFrequented = table.mostFrequented();
            String key = xor(mostFrequented, " ");
            System.out.println(key);
            resultLines.add(getSymbols(text)
                    .stream()
                    .map(s -> xor(key, s))
                    .collect(Collectors.joining()));
        }
        StringBuilder result = new StringBuilder();

        for (int h = 0; h < resultLines.get(0).length(); h++) {
            for (String resultLine : resultLines) {
                if(resultLine.length() > h) {
                    result.append(resultLine.charAt(h));
                }
            }
        }

        return result.toString();
    }

    private static void task1() throws DecoderException {
        String data = "7958401743454e1756174552475256435e59501a5c524e176f786517545e475f5245191772195019175e4317445f58425b531743565c521756174443455e595017d5b7ab5f525b5b58174058455b53d5b7aa175659531b17505e41525917435f52175c524e175e4417d5b7ab5c524ed5b7aa1b174f584517435f5217515e454443175b524343524517d5b7ab5fd5b7aa17405e435f17d5b7ab5cd5b7aa1b17435f5259174f584517d5b7ab52d5b7aa17405e435f17d5b7ab52d5b7aa1b17435f525917d5b7ab5bd5b7aa17405e435f17d5b7ab4ed5b7aa1b1756595317435f5259174f58451759524f4317545f564517d5b7ab5bd5b7aa17405e435f17d5b7ab5cd5b7aa175650565e591b17435f525917d5b7ab58d5b7aa17405e435f17d5b7ab52d5b7aa1756595317445817585919176e5842175a564e17424452175659175e5953524f1758511754585e59545e53525954521b177f565a5a5e595017535e4443565954521b177c56445e445c5e17524f565a5e5956435e58591b17444356435e44435e54565b17435244434417584517405f564352415245175a52435f5853174e5842175152525b174058425b5317445f584017435f52175552444317455244425b4319";

        String text = fromHex(data);

        String x = new FrequencyTable(text).mostFrequented();
        System.out.println(x);

        tryw1(text, x);
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

    private static void tryw1(String d, String x) {
        System.out.println(d);
//        String key = xor(String.valueOf(d.charAt(0)), String.valueOf(d.charAt(1)));
        String key = xor(" ", x);
        System.out.println(key);
        List<String> elements = getSymbols(d);

        Map<String, String> toReplace = new HashMap<>();
        for (String s : alphabet) {
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
