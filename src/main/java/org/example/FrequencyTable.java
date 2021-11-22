package org.example;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.App.getSymbols;

@Data
public class FrequencyTable {

    String text;
    HashMap<String, Integer> frequency;

    public FrequencyTable(String text) {
        this.text = text;
        List<String> symbols = getSymbols(text);
        this.frequency = new HashMap<>();

        for (String symbol : symbols) {
            if(frequency.containsKey(symbol)) {
                Integer newFrequency = frequency.get(symbol) + 1;
                frequency.put(symbol, newFrequency);
            } else {
                frequency.put(symbol, 1);
            }
        }
    }

    public String mostFrequented() {
        Optional<Integer> max = getBiggestNumbersOfOccur();
        List<String> collect = frequency.entrySet().stream().filter(stringIntegerEntry -> stringIntegerEntry.getValue().equals(max.get()))
                .map(Map.Entry::getKey).collect(Collectors.toList());

        return collect.get(0);
    }

    public double biggestFrequency() {
        Optional<Integer> max = getBiggestNumbersOfOccur();
        List<String> collect = frequency.entrySet().stream().filter(stringIntegerEntry -> stringIntegerEntry.getValue().equals(max.get()))
                .map(Map.Entry::getKey).collect(Collectors.toList());

        return max.map(v -> ((double) v)/text.length()).orElse(0.0);
    }

    private Optional<Integer> getBiggestNumbersOfOccur() {
        return frequency.values().stream().max(Integer::compareTo);
    }
}
