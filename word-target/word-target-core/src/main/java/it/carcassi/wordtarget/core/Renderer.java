/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Matteo
 */
public class Renderer {

    private static final Map<Character, Integer> charsInCircle1 = Map.ofEntries(
            entry('A', 5),
            entry('B', 5),
            entry('C', 5),
            entry('D', 5),
            entry('E', 5),
            entry('F', 5),
            entry('G', 5),
            entry('H', 5),
            entry('I', 12),
            entry('J', 6),
            entry('K', 5),
            entry('L', 6),
            entry('M', 4),
            entry('N', 5),
            entry('O', 5),
            entry('P', 5),
            entry('Q', 5),
            entry('R', 5),
            entry('S', 5),
            entry('T', 5),
            entry('U', 5),
            entry('V', 5),
            entry('W', 4),
            entry('X', 5),
            entry('Y', 5),
            entry('Z', 6),
            entry(' ', 12)
    );

    private static final Map<Character, Integer> charsInCircle2 = Map.ofEntries(
            entry('A', 24),
            entry('B', 24),
            entry('C', 22),
            entry('D', 22),
            entry('E', 24),
            entry('F', 26),
            entry('G', 21),
            entry('H', 22),
            entry('I', 59),
            entry('J', 32),
            entry('K', 24),
            entry('L', 29),
            entry('M', 19),
            entry('N', 22),
            entry('O', 21),
            entry('P', 24),
            entry('Q', 21),
            entry('R', 22),
            entry('S', 24),
            entry('T', 26),
            entry('U', 22),
            entry('V', 24),
            entry('W', 17),
            entry('X', 24),
            entry('Y', 24),
            entry('Z', 26),
            entry(' ', 59)
    );

    private static final Map<Character, Integer> charsInCircle3 = Map.ofEntries(
            entry('A', 44),
            entry('B', 44),
            entry('C', 41),
            entry('D', 41),
            entry('E', 44),
            entry('F', 48),
            entry('G', 38),
            entry('H', 41),
            entry('I', 105),
            entry('J', 59),
            entry('K', 44),
            entry('L', 53),
            entry('M', 35),
            entry('N', 41),
            entry('O', 38),
            entry('P', 44),
            entry('Q', 38),
            entry('R', 41),
            entry('S', 44),
            entry('T', 48),
            entry('U', 41),
            entry('V', 44),
            entry('W', 31),
            entry('X', 44),
            entry('Y', 44),
            entry('Z', 48),
            entry(' ', 105)
    );
    ;

        private static final Map<Character, Integer> charsInCircle4 = Map.ofEntries(
            entry('A', 62),
            entry('B', 62),
            entry('C', 57),
            entry('D', 57),
            entry('E', 62),
            entry('F', 68),
            entry('G', 53),
            entry('H', 57),
            entry('I', 149),
            entry('J', 83),
            entry('K', 62),
            entry('L', 74),
            entry('M', 49),
            entry('N', 57),
            entry('O', 53),
            entry('P', 62),
            entry('Q', 53),
            entry('R', 57),
            entry('S', 62),
            entry('T', 68),
            entry('U', 57),
            entry('V', 62),
            entry('W', 44),
            entry('X', 62),
            entry('Y', 62),
            entry('Z', 68),
            entry(' ', 149)
    );

    private static final Map<Character, Integer> charsInCircle5 = Map.ofEntries(
            entry('A', 81),
            entry('B', 81),
            entry('C', 75),
            entry('D', 75),
            entry('E', 81),
            entry('F', 88),
            entry('G', 69),
            entry('H', 75),
            entry('I', 195),
            entry('J', 108),
            entry('K', 81),
            entry('L', 91),
            entry('M', 65),
            entry('N', 75),
            entry('O', 69),
            entry('P', 81),
            entry('Q', 69),
            entry('R', 75),
            entry('S', 81),
            entry('T', 88),
            entry('U', 76),
            entry('V', 81),
            entry('W', 57),
            entry('X', 81),
            entry('Y', 81),
            entry('Z', 88),
            entry(' ', 195)
    );

    private static final List<Map<Character, Integer>> charsInCircle = List.of(new HashMap<>(), charsInCircle1, charsInCircle2, charsInCircle3, charsInCircle4, charsInCircle5);

    private static final List<Double> minFracBetweenWords = List.of(0.0, 0.0, 20.0 / 360.0, 12.0 / 360.0, 10.0 / 360.0, 6.0 / 360.0);

    public static String layoutWordCenter(String word) {
        double frac = fracForWord(word, 1);
        if (frac > 1) {
            throw new RuntimeException("Word does not fit in center");
        }
        StringBuilder str = new StringBuilder();
        str.append("  <text x=\"0\" y=\"0\" dominant-baseline=\"middle\" text-anchor=\"middle\" class=\"text1\">")
                .append(word)
                .append("</text>\n");
        return str.toString();

    }

    public static String layoutWord(WordTargetLayout layout, int circle) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < layout.getWordsInCircle(circle).size(); i++) {
            str.append(svgForCircleSeparator(layout.getAnglesOfSeparators(circle).get(i), circle) + "\n");
            str.append(svgForCircleWord(layout.getAnglesOfWords(circle).get(i), layout.getWordsInCircle(circle).get(i), circle) + "\n");
        }
        return str.toString();
    }

    public static String svgForCircleWord(int angleDegrees, String word, int circle) {
        StringBuilder str = new StringBuilder();
        str.append("  <text transform=\"rotate(")
                .append(angleDegrees)
                .append(" 0,0)\" class=\"text")
                .append(circle)
                .append("\"><textPath href=\"#circle")
                .append(circle)
                .append("\">")
                .append(word)
                .append("</textPath></text>");
        return str.toString();
    }

    public static String svgForCircleSeparator(int angleDegrees, int circle) {
        int x1 = circle * -100;
        int x2 = (circle - 1) * -100;
        //     <line transform="rotate(125 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
        StringBuilder str = new StringBuilder();
        str.append("  <line transform=\"rotate(")
                .append(angleDegrees)
                .append(" 0,0)\" x1=\"")
                .append(x1)
                .append("\" x2=\"")
                .append(x2)
                .append("\" y1=\"0\" y2=\"0\" class=\"line")
                .append(circle)
                .append("\"/>");
        return str.toString();
    }

    public static double fracForWord(String word, int circle) {
        word = word.toUpperCase();
        double frac = 0;
        for (char ch : word.toCharArray()) {
            frac = frac + 1.0 / charsInCircle.get(circle).get(ch);
        }
        return frac;
    }

    public static String renderWordTarget(WordTargetLayout layout) {
        layout.calculateAnglesInCircle();
        StringBuilder str = new StringBuilder();
        str.append("""
  <svg width="500" height="500" viewBox="-700 -700 1400 1400" xmlns="http://www.w3.org/2000/svg">
  <style>
    .text1 { fill: black; font: 50px sans-serif; }
    .text2 { fill: white; font: 50px sans-serif; }
    .line2 { stroke: white; stroke - width:5; }
    .text3 { fill: black; font: 50px sans-serif; }
    .line3 { stroke: black; stroke - width:5; }
    .text4 { fill: white; font: 50px sans-serif; }
    .line4 { stroke: white; stroke - width:5; }
    .text5 { fill: black; font: 50px sans-serif; }
    .line5 { stroke: black; stroke - width:5; }
  </style> 
  <circle cx = "0" cy = "0" r = "505" fill = "black" />
  <circle cx = "0" cy = "0" r = "500" fill = "white" />
  <circle cx = "0" cy = "0" r = "400" fill = "black" />
  <circle cx = "0" cy = "0" r = "300" fill = "white" />
  <circle cx = "0" cy = "0" r = "200" fill = "black" />
  <circle cx = "0" cy = "0" r = "100" fill = "white" />
  <line x1 = "-415" y1 = "-415" x2 = "-390" y2 = "-390 stroke = "black" stroke-width = "20"/>
  <polygon points = "-375 -415, -415 -375, -360 -360" />

  <path id = "circle2" fill = "transparent"
      d = "
      M 0 0
      m -130, 0
      a 130,130 0 1,1 260,0
      a 130,130 0 1,1 -260,0
      "
  />
  <path id = "circle3" fill = "transparent"
      d = "
      M 0 0
      m - 230, 0
      a 230,230 0 1,1 460,0
      a 230,230 0 1,1 -460,0
      "
  />
  <path id = "circle4" fill = "transparent"
      d = "
      M 0 0
      m - 330, 0
      a 330,330 0 1,1 660,0
      a 330,330 0 1,1 -660,0
      "
  />
  <path id = "circle5" fill = "transparent"
      d = "
      M 0 0
      m - 430, 0
      a 430,430 0 1,1 860,0
      a 430,430 0 1,1 -860,0
      "
  />

  <!-- First circle -->
");
            str.append(LayoutWordCenter(layout.WordInCenter));
            str.append(@"
  <!-- Second circle -->
");
            str.append(LayoutWord(layout, 2));
            str.append(@"
  <!-- Third circle -->
");
            str.append(LayoutWord(layout, 3));
            str.append(@"
  <!-- Fourth circle -->
");
            str.append(LayoutWord(layout, 4));
            str.append(@"
  <!-- Fifth circle -->
");
            str.append(LayoutWord(layout, 5));
            str.append(@"
</svg >
        ");
                   """);

        return str.toString();
    }
}
