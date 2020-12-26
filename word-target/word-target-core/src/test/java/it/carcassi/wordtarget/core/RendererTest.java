/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Matteo
 */
public class RendererTest {

    @Test
    public void testWordFracCircle1() {
        assertEquals(1.000, Renderer.fracForWord("STATE", 1), 0.001);
        assertEquals(0.600, Renderer.fracForWord("CAT", 1), 0.001);
        assertEquals(0.733, Renderer.fracForWord("BALL", 1), 0.001);
        assertEquals(0.903, Renderer.fracForWord("WON'T", 1), 0.001);
        assertEquals(0.883, Renderer.fracForWord("HO HO", 1), 0.001);
    }
    
    @Test
    public void testWordFracCircle2() {
        assertEquals(0.270, Renderer.fracForWord("TESTING", 2), 0.001);
        assertEquals(0.330, Renderer.fracForWord("ELEPHANT", 2), 0.001);
        assertEquals(0.360, Renderer.fracForWord("DOUGHNUT", 2), 0.001);
        assertEquals(0.125, Renderer.fracForWord("CAT", 2), 0.001);
        assertEquals(0.319, Renderer.fracForWord("BASEBALL", 2), 0.001);
        assertEquals(0.314, Renderer.fracForWord("COULDN'T", 2), 0.001);
        assertEquals(0.363, Renderer.fracForWord("TREE FROG", 2), 0.001);
    }

    @Test
    public void testPrepareWordsInCenter() {
        String expected = """
  <text x="0" y="0" dominant-baseline="middle" text-anchor="middle" class="text1">STARE</text>
""";
        assertEquals(expected, Renderer.layoutWordCenter("STARE"));
        assertThrows(RuntimeException.class, () -> {
            Renderer.layoutWordCenter("THISISAVERYLONGWORD");
        });
        assertThrows(RuntimeException.class, () -> {
            Renderer.layoutWordCenter("AAAAAA");
        });
    }

    private static WordTargetLayout createTextLayout(List<String> words, int circle, int startingAngle) {
        List<String> allWords = new ArrayList<>();
        if (circle != 5) {
            allWords.add("FIRST");
        }
        allWords.addAll(words);
        allWords.add("LAST");
        WordTargetLayout layout = new WordTargetLayout(allWords);
        if (circle != 5) {
            layout.setStartingAngle(circle, startingAngle);
        }
        for (String word : words) {
            if (circle == 5 && word == words.get(0)) {
                continue;
            }
            layout.assignWord(word, circle);
        }
        layout.calculateAnglesInCircle();
        return layout;
    }

    @Test
    public void testPrepareWordsInCircle2() {
        String expected = """
  <line transform="rotate(80 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
  <text transform="rotate(97 0,0)" class="text2"><textPath href="#circle2">STARE</textPath></text>
  <line transform="rotate(190 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
  <text transform="rotate(208 0,0)" class="text2"><textPath href="#circle2">RIGHT</textPath></text>
  <line transform="rotate(296 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
  <text transform="rotate(314 0,0)" class="text2"><textPath href="#circle2">UNCLEAR</textPath></text>
""";
        assertEquals(expected, Renderer.layoutWord(createTextLayout(List.of("STARE", "RIGHT", "UNCLEAR"), 2, 80), 2));
        assertThrows(RuntimeException.class, () -> {
            Renderer.layoutWord(createTextLayout(List.of("STARE", "RIGHT", "VERYBIGWORDTHATISTOOLONG"), 2, 80), 2);
        });
        assertThrows(RuntimeException.class, () -> {
            Renderer.layoutWord(createTextLayout(List.of("TOO", "MANY", "WORDS", "TO", "DISPLAY", "IN", "ONE", "CIRCLE"), 2, 80), 2);
        });
    }

    @Test
    public void testSvgForCircleWord2() {
        assertEquals("  <text transform=\"rotate(10 0,0)\" class=\"text2\"><textPath href=\"#circle2\">BASEBALL</textPath></text>", Renderer.svgForCircleWord(10, "BASEBALL", 2));
    }

    @Test
    public void testSvgForCircleSeparator2() {
        assertEquals("  <line transform=\"rotate(125 0,0)\" x1=\"-200\" x2=\"-100\" y1=\"0\" y2=\"0\" class=\"line2\"/>", Renderer.svgForCircleSeparator(125, 2));
    }

    @Test
    public void testWordFracCircle3() {
        assertEquals(0.125, Renderer.fracForWord("TENNIS", 3), 0.001);
        assertEquals(0.254, Renderer.fracForWord("ACCOUNTABLE", 3), 0.001);
        assertEquals(0.068, Renderer.fracForWord("CAT", 3), 0.001);
        assertEquals(0.174, Renderer.fracForWord("BASEBALL", 3), 0.001);
        assertEquals(0.170, Renderer.fracForWord("COULDN'T", 3), 0.001);
        assertEquals(0.198, Renderer.fracForWord("TREE FROG", 3), 0.001);
    }

    @Test
    public void testSvgForCircleSeparator3() {
        assertEquals("  <line transform=\"rotate(65 0,0)\" x1=\"-300\" x2=\"-200\" y1=\"0\" y2=\"0\" class=\"line3\"/>", Renderer.svgForCircleSeparator(65, 3));
    }

    @Test
    public void testSvgForCircleWord3() {
        assertEquals("  <text transform=\"rotate(10 0,0)\" class=\"text3\"><textPath href=\"#circle3\">BASEBALL</textPath></text>", Renderer.svgForCircleWord(10, "BASEBALL", 3));
    }

    @Test
    public void testPrepareWordsInCircle3() {
        String expected = """  
  <line transform="rotate(80 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(91 0,0)" class="text3"><textPath href="#circle3">SPARE</textPath></text>
  <line transform="rotate(145 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(157 0,0)" class="text3"><textPath href="#circle3">VALUE</textPath></text>
  <line transform="rotate(209 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(221 0,0)" class="text3"><textPath href="#circle3">POLARS</textPath></text>
  <line transform="rotate(282 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(294 0,0)" class="text3"><textPath href="#circle3">STATEN</textPath></text>
  <line transform="rotate(354 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(6 0,0)" class="text3"><textPath href="#circle3">VITAMIN A</textPath></text>
""";
        WordTargetLayout words = createTextLayout(List.of("SPARE", "VALUE", "POLARS", "STATEN", "VITAMIN A"), 3, 80);
        assertEquals(expected, Renderer.layoutWord(words, 3));
    }

    @Test
    public void testWordFracCircle4() {
        assertEquals(0.151, Renderer.fracForWord("CHOCOLATE", 4), 0.001);
        assertEquals(0.245, Renderer.fracForWord("BLOODTHIRSTINESS", 4), 0.001);
        assertEquals(0.048, Renderer.fracForWord("CAT", 4), 0.001);
        assertEquals(0.124, Renderer.fracForWord("BASEBALL", 4), 0.001);
        assertEquals(0.122, Renderer.fracForWord("COULDN'T", 4), 0.001);
        assertEquals(0.141, Renderer.fracForWord("TREE FROG", 4), 0.001);
    }

    @Test
    public void testSvgForCircleSeparator4() {
        assertEquals("  <line transform=\"rotate(65 0,0)\" x1=\"-400\" x2=\"-300\" y1=\"0\" y2=\"0\" class=\"line4\"/>", Renderer.svgForCircleSeparator(65, 4));
    }

    @Test
    public void testSvgForCircleWord4() {
        assertEquals("  <text transform=\"rotate(10 0,0)\" class=\"text4\"><textPath href=\"#circle4\">BASEBALL</textPath></text>", Renderer.svgForCircleWord(10, "BASEBALL", 4));
    }

    @Test
    public void testPrepareWordsInCircle4() {
        String expected = """
  <line transform="rotate(80 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(85 0,0)" class="text4"><textPath href="#circle4">ANGLE</textPath></text>
  <line transform="rotate(120 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(125 0,0)" class="text4"><textPath href="#circle4">STAR</textPath></text>
  <line transform="rotate(153 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(158 0,0)" class="text4"><textPath href="#circle4">JOIN</textPath></text>
  <line transform="rotate(183 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(189 0,0)" class="text4"><textPath href="#circle4">CORE</textPath></text>
  <line transform="rotate(219 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(224 0,0)" class="text4"><textPath href="#circle4">STATION</textPath></text>
  <line transform="rotate(267 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(273 0,0)" class="text4"><textPath href="#circle4">POLAR</textPath></text>
  <line transform="rotate(307 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(313 0,0)" class="text4"><textPath href="#circle4">VAGUE</textPath></text>
  <line transform="rotate(348 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(354 0,0)" class="text4"><textPath href="#circle4">R</textPath></text>
  <line transform="rotate(5 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(10 0,0)" class="text4"><textPath href="#circle4">COORDINATES</textPath></text>
""";
        WordTargetLayout words = createTextLayout(List.of("ANGLE", "STAR", "JOIN", "CORE", "STATION", "POLAR", "VAGUE", "R", "COORDINATES"), 4, 80);
        assertEquals(expected, Renderer.layoutWord(words, 4));
    }

    @Test
    public void testWordFracCircle5() {
        assertEquals(0.101, Renderer.fracForWord("ENVELOPE", 5), 0.001);
        assertEquals(0.264, Renderer.fracForWord("OVERCOMMERCIALIZATIONS", 5), 0.001);
        assertEquals(0.037, Renderer.fracForWord("CAT", 5), 0.001);
        assertEquals(0.096, Renderer.fracForWord("BASEBALL", 5), 0.001);
        assertEquals(0.094, Renderer.fracForWord("COULDN'T", 5), 0.001);
        assertEquals(0.108, Renderer.fracForWord("TREE FROG", 5), 0.001);

    }

    @Test
    public void testSvgForCircleSeparator5() {
        assertEquals("  <line transform=\"rotate(65 0,0)\" x1=\"-500\" x2=\"-400\" y1=\"0\" y2=\"0\" class=\"line5\"/>", Renderer.svgForCircleSeparator(65, 5));
    }

    @Test
    public void testSvgForCircleWord5() {
        assertEquals("  <text transform=\"rotate(90 0,0)\" class=\"text5\"><textPath href=\"#circle5\">BASEBALL</textPath></text>", Renderer.svgForCircleWord(90, "BASEBALL", 5));
    }

    @Test
    public void testPrepareWordsInCircle5() {
        String expected = """  
  <line transform="rotate(25 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(29 0,0)" class="text5"><textPath href="#circle5">VARIABLE</textPath></text>
  <line transform="rotate(65 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(69 0,0)" class="text5"><textPath href="#circle5">SIGHT</textPath></text>
  <line transform="rotate(94 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(98 0,0)" class="text5"><textPath href="#circle5">NUCLEAR</textPath></text>
  <line transform="rotate(134 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(138 0,0)" class="text5"><textPath href="#circle5">SPACE</textPath></text>
  <line transform="rotate(164 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(168 0,0)" class="text5"><textPath href="#circle5">JOINT</textPath></text>
  <line transform="rotate(192 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(196 0,0)" class="text5"><textPath href="#circle5">POLARIS</textPath></text>
  <line transform="rotate(229 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(233 0,0)" class="text5"><textPath href="#circle5">VAGUE</textPath></text>
  <line transform="rotate(260 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(264 0,0)" class="text5"><textPath href="#circle5">COIN</textPath></text>
  <line transform="rotate(285 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(289 0,0)" class="text5"><textPath href="#circle5">CAROTENOIDS</textPath></text>
  <line transform="rotate(342 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(346 0,0)" class="text5"><textPath href="#circle5">ANKLE</textPath></text>
  <line transform="rotate(12 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(16 0,0)" class="text5"><textPath href="#circle5">S</textPath></text>
""";

        WordTargetLayout words = createTextLayout(List.of("VARIABLE", "SIGHT", "NUCLEAR", "SPACE", "JOINT", "POLARIS", "VAGUE", "COIN", "CAROTENOIDS", "ANKLE", "S"), 5, 80);
        assertEquals(expected, Renderer.layoutWord(words, 5));
    }

    @Test
    public void RenderWordTarget() {
        String expected = """
  <svg width="500" height="500" viewBox="-550 -550 1100 1100" xmlns="http://www.w3.org/2000/svg">
  <style>
    .text1 { fill: black; font: 50px sans-serif; }
    .text2 { fill: white; font: 50px sans-serif; }
    .line2 { stroke: white; stroke-width:5; }
    .text3 { fill: black; font: 50px sans-serif; }
    .line3 { stroke: black; stroke-width:5; }
    .text4 { fill: white; font: 50px sans-serif; }
    .line4 { stroke: white; stroke-width:5; }
    .text5 { fill: black; font: 50px sans-serif; }
    .line5 { stroke: black; stroke-width:5; }
  </style>

  <circle cx="0" cy="0" r="505" fill="black" />
  <circle cx="0" cy="0" r="500" fill="white" />
  <circle cx="0" cy="0" r="400" fill="black" />
  <circle cx="0" cy="0" r="300" fill="white" />
  <circle cx="0" cy="0" r="200" fill="black" />
  <circle cx="0" cy="0" r="100" fill="white" />
  <line x1="-415" y1="-415" x2="-390" y2="-390" stroke="black" stroke-width="20"/>
  <polygon points="-375 -415, -415 -375, -360 -360" />

  <path id="circle2" fill="transparent"
      d="
      M 0 0
      m -130, 0
      a 130,130 0 1,1 260,0
      a 130,130 0 1,1 -260,0
      "
  />
  <path id="circle3" fill="transparent"
      d="
      M 0 0
      m -230, 0
      a 230,230 0 1,1 460,0
      a 230,230 0 1,1 -460,0
      "
  />
  <path id="circle4" fill="transparent"
      d="
      M 0 0
      m -330, 0
      a 330,330 0 1,1 660,0
      a 330,330 0 1,1 -660,0
      "
  />
  <path id="circle5" fill="transparent"
      d="
      M 0 0
      m -430, 0
      a 430,430 0 1,1 860,0
      a 430,430 0 1,1 -860,0
      "
  />

  <!-- First circle -->
  <text x="0" y="0" dominant-baseline="middle" text-anchor="middle" class="text1">STATE</text>

  <!-- Second circle -->
  <line transform="rotate(80 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
  <text transform="rotate(92 0,0)" class="text2"><textPath href="#circle2">STARE</textPath></text>
  <line transform="rotate(181 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
  <text transform="rotate(194 0,0)" class="text2"><textPath href="#circle2">STAR</textPath></text>
  <line transform="rotate(267 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
  <text transform="rotate(280 0,0)" class="text2"><textPath href="#circle2">JOIN</textPath></text>
  <line transform="rotate(344 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
  <text transform="rotate(357 0,0)" class="text2"><textPath href="#circle2">RIGHT</textPath></text>

  <!-- Third circle -->
  <line transform="rotate(0 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(6 0,0)" class="text3"><textPath href="#circle3">SPARE</textPath></text>
  <line transform="rotate(55 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(62 0,0)" class="text3"><textPath href="#circle3">CORN</textPath></text>
  <line transform="rotate(105 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(112 0,0)" class="text3"><textPath href="#circle3">VALUE</textPath></text>
  <line transform="rotate(159 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(165 0,0)" class="text3"><textPath href="#circle3">POLARS</textPath></text>
  <line transform="rotate(222 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(229 0,0)" class="text3"><textPath href="#circle3">STATEN</textPath></text>
  <line transform="rotate(284 0,0)" x1="-300" x2="-200" y1="0" y2="0" class="line3"/>
  <text transform="rotate(291 0,0)" class="text3"><textPath href="#circle3">VITAMIN A</textPath></text>

  <!-- Fourth circle -->
  <line transform="rotate(5 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(11 0,0)" class="text4"><textPath href="#circle4">ANGLE</textPath></text>
  <line transform="rotate(48 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(55 0,0)" class="text4"><textPath href="#circle4">UNCLEAR</textPath></text>
  <line transform="rotate(103 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(110 0,0)" class="text4"><textPath href="#circle4">STATIN</textPath></text>
  <line transform="rotate(148 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(155 0,0)" class="text4"><textPath href="#circle4">STATION</textPath></text>
  <line transform="rotate(199 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(206 0,0)" class="text4"><textPath href="#circle4">POLAR</textPath></text>
  <line transform="rotate(243 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(249 0,0)" class="text4"><textPath href="#circle4">VAGUE</textPath></text>
  <line transform="rotate(287 0,0)" x1="-400" x2="-300" y1="0" y2="0" class="line4"/>
  <text transform="rotate(294 0,0)" class="text4"><textPath href="#circle4">COORDINATES</textPath></text>

  <!-- Fifth circle -->
  <line transform="rotate(24 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(28 0,0)" class="text5"><textPath href="#circle5">VARIABLE</textPath></text>
  <line transform="rotate(66 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(71 0,0)" class="text5"><textPath href="#circle5">SIGHT</textPath></text>
  <line transform="rotate(96 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(101 0,0)" class="text5"><textPath href="#circle5">NUCLEAR</textPath></text>
  <line transform="rotate(138 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(143 0,0)" class="text5"><textPath href="#circle5">SPACE</textPath></text>
  <line transform="rotate(170 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(175 0,0)" class="text5"><textPath href="#circle5">JOINT</textPath></text>
  <line transform="rotate(199 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(204 0,0)" class="text5"><textPath href="#circle5">POLARIS</textPath></text>
  <line transform="rotate(238 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(243 0,0)" class="text5"><textPath href="#circle5">COIN</textPath></text>
  <line transform="rotate(264 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(269 0,0)" class="text5"><textPath href="#circle5">CAROTENOIDS</textPath></text>
  <line transform="rotate(323 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(328 0,0)" class="text5"><textPath href="#circle5">ANKLE</textPath></text>
  <line transform="rotate(355 0,0)" x1="-500" x2="-400" y1="0" y2="0" class="line5"/>
  <text transform="rotate(359 0,0)" class="text5"><textPath href="#circle5">CORE</textPath></text>

</svg>
""";
        WordTargetLayout wordTargetLayout = new WordTargetLayout(List.of("VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE"));
        wordTargetLayout.assignWord("STARE", 2);
        wordTargetLayout.assignWord("STAR", 2);
        wordTargetLayout.assignWord("JOIN", 2);
        wordTargetLayout.assignWord("RIGHT", 2);
        wordTargetLayout.assignWord("SPARE", 3);
        wordTargetLayout.assignWord("CORN", 3);
        wordTargetLayout.assignWord("VALUE", 3);
        wordTargetLayout.assignWord("POLARS", 3);
        wordTargetLayout.assignWord("STATEN", 3);
        wordTargetLayout.assignWord("VITAMIN A", 3);
        wordTargetLayout.assignWord("ANGLE", 4);
        wordTargetLayout.assignWord("UNCLEAR", 4);
        wordTargetLayout.assignWord("STATIN", 4);
        wordTargetLayout.assignWord("STATION", 4);
        wordTargetLayout.assignWord("POLAR", 4);
        wordTargetLayout.assignWord("VAGUE", 4);
        wordTargetLayout.assignWord("COORDINATES", 4);
        wordTargetLayout.assignWord("SIGHT", 5);
        wordTargetLayout.assignWord("NUCLEAR", 5);
        wordTargetLayout.assignWord("SPACE", 5);
        wordTargetLayout.assignWord("JOINT", 5);
        wordTargetLayout.assignWord("POLARIS", 5);
        wordTargetLayout.assignWord("COIN", 5);
        wordTargetLayout.assignWord("CAROTENOIDS", 5);
        wordTargetLayout.assignWord("ANKLE", 5);
        wordTargetLayout.assignWord("CORE", 5);
        wordTargetLayout.setStartingAngle(2, 80);
        wordTargetLayout.setStartingAngle(3, 0);
        wordTargetLayout.setStartingAngle(4, 5);
        String wordTarget = Renderer.renderWordTarget(wordTargetLayout);

        //System.IO.File.WriteAllText(Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\WordTargetOutput.svg", wordTarget);
        assertEquals(expected, wordTarget);
    }
}
