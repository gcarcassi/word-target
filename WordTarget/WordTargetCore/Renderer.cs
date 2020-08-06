using System;
using System.Collections.Generic;
using System.Diagnostics.Tracing;
using System.Text;
using System.Linq;

namespace WordTargetCore
{
    public class Renderer
    {
        private static Dictionary<char, int> charsInCircle1 = new Dictionary<char, int>
        {
            { 'A', 5 },
            { 'B', 5 },
            { 'C', 5 },
            { 'D', 5 },
            { 'E', 5 },
            { 'F', 5 },
            { 'G', 5 },
            { 'H', 5 },
            { 'I', 12 },
            { 'J', 6 },
            { 'K', 5 },
            { 'L', 6 },
            { 'M', 4 },
            { 'N', 5 },
            { 'O', 5 },
            { 'P', 5 },
            { 'Q', 5 },
            { 'R', 5 },
            { 'S', 5 },
            { 'T', 5 },
            { 'U', 5 },
            { 'V', 5 },
            { 'W', 4 },
            { 'X', 5 },
            { 'Y', 5 },
            { 'Z', 6 },
            { ' ', 12 }
        };

        private static Dictionary<char, int> charsInCircle2 = new Dictionary<char, int>
        {
            { 'A', 24 },
            { 'B', 24 },
            { 'C', 22 },
            { 'D', 22 },
            { 'E', 24 },
            { 'F', 26 },
            { 'G', 21 },
            { 'H', 22 },
            { 'I', 59 },
            { 'J', 32 },
            { 'K', 24 },
            { 'L', 29 },
            { 'M', 19 },
            { 'N', 22 },
            { 'O', 21 },
            { 'P', 24 },
            { 'Q', 21 },
            { 'R', 22 },
            { 'S', 24 },
            { 'T', 26 },
            { 'U', 22 },
            { 'V', 24 },
            { 'W', 17 },
            { 'X', 24 },
            { 'Y', 24 },
            { 'Z', 26 },
            { ' ', 59 }
        };

        private static Dictionary<char, int> charsInCircle3 = new Dictionary<char, int>
        {
            { 'A', 44 },
            { 'B', 44 },
            { 'C', 41 },
            { 'D', 41 },
            { 'E', 44 },
            { 'F', 48 },
            { 'G', 38 },
            { 'H', 41 },
            { 'I', 105 },
            { 'J', 59 },
            { 'K', 44 },
            { 'L', 53 },
            { 'M', 35 },
            { 'N', 41 },
            { 'O', 38 },
            { 'P', 44 },
            { 'Q', 38 },
            { 'R', 41 },
            { 'S', 44 },
            { 'T', 48 },
            { 'U', 41 },
            { 'V', 44 },
            { 'W', 31 },
            { 'X', 44 },
            { 'Y', 44 },
            { 'Z', 48 },
            { ' ', 105 }
        };

        private static Dictionary<char, int> charsInCircle4 = new Dictionary<char, int>
        {
            { 'A', 62 },
            { 'B', 62 },
            { 'C', 57 },
            { 'D', 57 },
            { 'E', 62 },
            { 'F', 68 },
            { 'G', 53 },
            { 'H', 57 },
            { 'I', 149 },
            { 'J', 83 },
            { 'K', 62 },
            { 'L', 74 },
            { 'M', 49 },
            { 'N', 57 },
            { 'O', 53 },
            { 'P', 62 },
            { 'Q', 53 },
            { 'R', 57 },
            { 'S', 62 },
            { 'T', 68 },
            { 'U', 57 },
            { 'V', 62 },
            { 'W', 44 },
            { 'X', 62 },
            { 'Y', 62 },
            { 'Z', 68 },
            { ' ', 149 }
        };

        private static Dictionary<char, int> charsInCircle5 = new Dictionary<char, int>
        {
            { 'A', 81 },
            { 'B', 81 },
            { 'C', 75 },
            { 'D', 75 },
            { 'E', 81 },
            { 'F', 88 },
            { 'G', 69 },
            { 'H', 75 },
            { 'I', 195 },
            { 'J', 108 },
            { 'K', 81 },
            { 'L', 91 },
            { 'M', 65 },
            { 'N', 75 },
            { 'O', 69 },
            { 'P', 81 },
            { 'Q', 69 },
            { 'R', 75 },
            { 'S', 81 },
            { 'T', 88 },
            { 'U', 76 },
            { 'V', 81 },
            { 'W', 57 },
            { 'X', 81 },
            { 'Y', 81 },
            { 'Z', 88 },
            { ' ', 195 }
        };


        private static List<Dictionary<char, int>> charsInCircle = new List<Dictionary<char, int>>() { null, charsInCircle1, charsInCircle2, charsInCircle3, charsInCircle4, charsInCircle5 };


        private static Dictionary<char, int> pixelsByChar = new Dictionary<char, int>
        {
            { 'A', 17 },
            { 'B', 14 },
            { 'C', 17 },
            { 'D', 16 },
            { 'E', 15 },
            { 'F', 13 },
            { 'G', 18 },
            { 'H', 16 },
            { 'I', 5 },
            { 'J', 12 },
            { 'K', 15 },
            { 'L', 13 },
            { 'M', 18 },
            { 'N', 16 },
            { 'O', 18 },
            { 'P', 14 },
            { 'Q', 18 },
            { 'R', 16 },
            { 'S', 14 },
            { 'T', 15 },
            { 'U', 14 },
            { 'V', 15 },
            { 'W', 22 },
            { 'X', 14 },
            { 'Y', 16 },
            { 'Z', 14 }
        };

        private static List<double> minFracBetweenWords = new List<double> { 0.0, 0.0, 20.0 / 360.0, 12.0 / 360.0, 10.0 / 360.0, 6.0 / 360.0 };

        public static string LayoutWordCenter(string word)
        {
            double frac = FracForWord(word, 1);
            if (frac > 1)
            {
                throw new Exception("Word does not fit in center");
            }
            StringBuilder str = new StringBuilder();
            str.Append("  <text x=\"0\" y=\"0\" dominant-baseline=\"middle\" text-anchor=\"middle\" class=\"text1\">")
                .Append(word)
                .AppendLine("</text>");
            return str.ToString();

        }

        public static string LayoutWord(WordTargetLayout layout, int circle)
        {

            StringBuilder str = new StringBuilder();
            List<int> angleList = layout.GetLayoutInCircle(layout, circle);
            for (int i = 0; i < angleList.Count; i++)
            {
                if(i % 2 == 0)
                {
                    str.AppendLine(SvgForCircleSeparator(angleList[i], circle));
                }
                else
                {
                    str.AppendLine(SvgForCircleWord(angleList[i], layout.GetWordsInCircle(circle)[(i-1)/2], circle));
                }
            }

            return str.ToString();
        }

        public static string SvgForCircleWord(int angleDegrees, string word, int circle)
        {
            StringBuilder str = new StringBuilder();
            str.Append("  <text transform=\"rotate(")
                .Append(angleDegrees)
                .Append(" 0,0)\" class=\"text")
                .Append(circle)
                .Append("\"><textPath href=\"#circle")
                .Append(circle)
                .Append("\">")
                .Append(word)
                .Append("</textPath></text>");
            return str.ToString();
        }

        public static string SvgForCircleSeparator(int angleDegrees, int circle)
        {
            int x1 = circle * -100;
            int x2 = (circle - 1) * -100;
            //     <line transform="rotate(125 0,0)" x1="-200" x2="-100" y1="0" y2="0" class="line2"/>
            StringBuilder str = new StringBuilder();
            str.Append("  <line transform=\"rotate(")
                .Append(angleDegrees)
                .Append(" 0,0)\" x1=\"")
                .Append(x1)
                .Append("\" x2=\"")
                .Append(x2)
                .Append("\" y1=\"0\" y2=\"0\" class=\"line")
                .Append(circle)
                .Append("\"/>");
            return str.ToString();
        }

        public static double FracForWord(string word, int circle)
        {
            word = word.ToUpper();
            double frac = 0;
            foreach (char ch in word)
            {
                frac = frac + 1.0 / charsInCircle[circle][ch];
            }

            return frac;
        }

        public static string RenderWordTarget(WordTargetLayout layout)
        {
            StringBuilder str = new StringBuilder();
            str.Append(@"<svg width=""500"" height=""500"" viewBox=""-700 -700 1400 1400"" xmlns=""http://www.w3.org/2000/svg"">
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

  <circle cx=""0"" cy=""0"" r=""505"" fill=""black"" />
  <circle cx=""0"" cy=""0"" r=""500"" fill=""white"" />
  <circle cx=""0"" cy=""0"" r=""400"" fill=""black"" />
  <circle cx=""0"" cy=""0"" r=""300"" fill=""white"" />
  <circle cx=""0"" cy=""0"" r=""200"" fill=""black"" />
  <circle cx=""0"" cy=""0"" r=""100"" fill=""white"" />
  <line x1=""-415"" y1=""-415"" x2=""-390"" y2=""-390"" stroke=""black"" stroke-width=""20""/>
  <polygon points=""-375 -415, -415 -375, -360 -360"" />

  <path id=""circle2"" fill=""transparent""
      d=""
      M 0 0
      m -130, 0
      a 130,130 0 1,1 260,0
      a 130,130 0 1,1 -260,0
      ""
  />
  <path id=""circle3"" fill=""transparent""
      d=""
      M 0 0
      m -230, 0
      a 230,230 0 1,1 460,0
      a 230,230 0 1,1 -460,0
      ""
  />
  <path id=""circle4"" fill=""transparent""
      d=""
      M 0 0
      m -330, 0
      a 330,330 0 1,1 660,0
      a 330,330 0 1,1 -660,0
      ""
  />
  <path id=""circle5"" fill=""transparent""
      d=""
      M 0 0
      m -430, 0
      a 430,430 0 1,1 860,0
      a 430,430 0 1,1 -860,0
      ""
  />

  <!-- First circle -->
");
            str.Append(LayoutWordCenter(layout.WordInCenter));
            str.Append(@"
  <!-- Second circle -->
");
            str.Append(LayoutWord(layout, 2));
            str.Append(@"
  <!-- Third circle -->
");
            str.Append(LayoutWord(layout, 3));
            str.Append(@"
  <!-- Fourth circle -->
");
            str.Append(LayoutWord(layout, 4));
            str.Append(@"
  <!-- Fifth circle -->
");
            str.Append(LayoutWord(layout, 5));
            str.Append(@"
</svg>
");

            return str.ToString();
        }
    }
}
