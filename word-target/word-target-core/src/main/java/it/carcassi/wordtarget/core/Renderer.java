/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matteo
 */
public class Renderer {

    private static final Map<Character, Integer> charsInCircle1 = new HashMap<>();
    {
        charsInCircle1.put('A', 5);
        charsInCircle1.put('B', 5);
        charsInCircle1.put('C', 5);
        charsInCircle1.put('D', 5);
        charsInCircle1.put('E', 5);
        charsInCircle1.put('F', 5);
        charsInCircle1.put('G', 5);
        charsInCircle1.put('H', 5);
        charsInCircle1.put('I', 12);
        charsInCircle1.put('J', 6);
        charsInCircle1.put('K', 5);
        charsInCircle1.put('L', 6);
        charsInCircle1.put('M', 4);
        charsInCircle1.put('N', 5);
        charsInCircle1.put('O', 5);
        charsInCircle1.put('P', 5);
        charsInCircle1.put('Q', 5);
        charsInCircle1.put('R', 5);
        charsInCircle1.put('S', 5);
        charsInCircle1.put('T', 5);
        charsInCircle1.put('U', 5);
        charsInCircle1.put('V', 5);
        charsInCircle1.put('W', 4);
        charsInCircle1.put('X', 5);
        charsInCircle1.put('Y', 5);
        charsInCircle1.put('Z', 6);
        charsInCircle1.put(' ', 12);
    }
    ;

        private static final Map<Character, Integer> charsInCircle2 = new HashMap<>();
    {
        charsInCircle2.put('A', 24);
        charsInCircle2.put('B', 24);
        charsInCircle2.put('C', 22);
        charsInCircle2.put('D', 22);
        charsInCircle2.put('E', 24);
        charsInCircle2.put('F', 26);
        charsInCircle2.put('G', 21);
        charsInCircle2.put('H', 22);
        charsInCircle2.put('I', 59);
        charsInCircle2.put('J', 32);
        charsInCircle2.put('K', 24);
        charsInCircle2.put('L', 29);
        charsInCircle2.put('M', 19);
        charsInCircle2.put('N', 22);
        charsInCircle2.put('O', 21);
        charsInCircle2.put('P', 24);
        charsInCircle2.put('Q', 21);
        charsInCircle2.put('R', 22);
        charsInCircle2.put('S', 24);
        charsInCircle2.put('T', 26);
        charsInCircle2.put('U', 22);
        charsInCircle2.put('V', 24);
        charsInCircle2.put('W', 17);
        charsInCircle2.put('X', 24);
        charsInCircle2.put('Y', 24);
        charsInCircle2.put('Z', 26);
        charsInCircle2.put(' ', 59);
    }
    ;

        private static final Map<Character, Integer> charsInCircle3 = new HashMap<>();
    {
        charsInCircle3.put('A', 44);
        charsInCircle3.put('B', 44);
        charsInCircle3.put('C', 41);
        charsInCircle3.put('D', 41);
        charsInCircle3.put('E', 44);
        charsInCircle3.put('F', 48);
        charsInCircle3.put('G', 38);
        charsInCircle3.put('H', 41);
        charsInCircle3.put('I', 105);
        charsInCircle3.put('J', 59);
        charsInCircle3.put('K', 44);
        charsInCircle3.put('L', 53);
        charsInCircle3.put('M', 35);
        charsInCircle3.put('N', 41);
        charsInCircle3.put('O', 38);
        charsInCircle3.put('P', 44);
        charsInCircle3.put('Q', 38);
        charsInCircle3.put('R', 41);
        charsInCircle3.put('S', 44);
        charsInCircle3.put('T', 48);
        charsInCircle3.put('U', 41);
        charsInCircle3.put('V', 44);
        charsInCircle3.put('W', 31);
        charsInCircle3.put('X', 44);
        charsInCircle3.put('Y', 44);
        charsInCircle3.put('Z', 48);
        charsInCircle3.put(' ', 105);
    }
    ;

        private static final Map<Character, Integer> charsInCircle4 = new HashMap<>();
    {
        charsInCircle4.put('A', 62);
        charsInCircle4.put('B', 62);
        charsInCircle4.put('C', 57);
        charsInCircle4.put('D', 57);
        charsInCircle4.put('E', 62);
        charsInCircle4.put('F', 68);
        charsInCircle4.put('G', 53);
        charsInCircle4.put('H', 57);
        charsInCircle4.put('I', 149);
        charsInCircle4.put('J', 83);
        charsInCircle4.put('K', 62);
        charsInCircle4.put('L', 74);
        charsInCircle4.put('M', 49);
        charsInCircle4.put('N', 57);
        charsInCircle4.put('O', 53);
        charsInCircle4.put('P', 62);
        charsInCircle4.put('Q', 53);
        charsInCircle4.put('R', 57);
        charsInCircle4.put('S', 62);
        charsInCircle4.put('T', 68);
        charsInCircle4.put('U', 57);
        charsInCircle4.put('V', 62);
        charsInCircle4.put('W', 44);
        charsInCircle4.put('X', 62);
        charsInCircle4.put('Y', 62);
        charsInCircle4.put('Z', 68);
        charsInCircle4.put(' ', 149);
    }
    ;

        private static final Map<Character, Integer> charsInCircle5 = new HashMap<>();
    {
        charsInCircle5.put('A', 81);
        charsInCircle5.put('B', 81);
        charsInCircle5.put('C', 75);
        charsInCircle5.put('D', 75);
        charsInCircle5.put('E', 81);
        charsInCircle5.put('F', 88);
        charsInCircle5.put('G', 69);
        charsInCircle5.put('H', 75);
        charsInCircle5.put('I', 195);
        charsInCircle5.put('J', 108);
        charsInCircle5.put('K', 81);
        charsInCircle5.put('L', 91);
        charsInCircle5.put('M', 65);
        charsInCircle5.put('N', 75);
        charsInCircle5.put('O', 69);
        charsInCircle5.put('P', 81);
        charsInCircle5.put('Q', 69);
        charsInCircle5.put('R', 75);
        charsInCircle5.put('S', 81);
        charsInCircle5.put('T', 88);
        charsInCircle5.put('U', 76);
        charsInCircle5.put('V', 81);
        charsInCircle5.put('W', 57);
        charsInCircle5.put('X', 81);
        charsInCircle5.put('Y', 81);
        charsInCircle5.put('Z', 88);
        charsInCircle5.put(' ', 195);
    }
    ;

    private static final List<Map<Character, Integer>> charsInCircle = List.of(null, charsInCircle1, charsInCircle2, charsInCircle3, charsInCircle4, charsInCircle5);

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
        for (Character ch : word.toCharArray()) {
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

