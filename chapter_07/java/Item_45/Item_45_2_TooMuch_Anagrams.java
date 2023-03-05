package chapter_07.java.Item_45;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/*
 *  과도한 스트림 사용 - 따라 하지 말 것!
 */
public class Item_45_2_TooMuch_Anagrams {
    public static void main(String[] args) throws IOException{
        Path dictionary = Paths.get(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        try (Stream<String> words = Files.lines(dictionary)) {
            words.collect(
                groupingBy(word -> word.chars().sorted()
                        .collect(StringBuilder::new,
                            (sb, c) -> sb.append((char) c),
                            StringBuilder::append).toString()))
            .values().stream()
            .filter(group -> group.size() >= minGroupSize)
            .map(group -> group.size() + ": " + group)
            .forEach(System.out::println);
        }
    }
    
}
