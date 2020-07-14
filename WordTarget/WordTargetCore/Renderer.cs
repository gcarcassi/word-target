using System;
using System.Collections.Generic;
using System.Diagnostics.Tracing;
using System.Text;
using System.Threading;

namespace WordTargetCore
{
    public class Renderer
    {
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

        private static List<double> minFracBetweenWords = new List<double> { 0.0, 0.0, 20.0 / 360.0, 20.0 / 360.0 };

        public static string LayoutWord(List<string> words, int circle, int startingAngleDegrees)
        {
            List<double> fractions = new List<double>();
            double total = 0.0;
            foreach (string word in words)
            {
                double frac = FracForWord(word, circle);
                fractions.Add(frac);
                total += frac;
            }

            double spaceBetweenWords = (1 - total) / words.Count;

            if (spaceBetweenWords < minFracBetweenWords[circle])
            {
                throw new Exception("Words do not fit in the circle");
            }

            double circleSoFar = 0.0;
            StringBuilder str = new StringBuilder();
            for(int i = 0; i < words.Count; i++)
            {
                str.AppendLine(SvgForCircleSeparator(startingAngleDegrees + (int) (circleSoFar * 360), circle));
                circleSoFar += spaceBetweenWords / 2;
                str.AppendLine(SvgForCircleWord(startingAngleDegrees + (int) (circleSoFar * 360), words[i], circle));
                circleSoFar += fractions[i];
                circleSoFar += spaceBetweenWords / 2;
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
            if (circle == 2)
            {
                foreach (char ch in word)
                {
                    frac = frac + 1.0 / charsInCircle2[ch];
                }
            }
            if (circle == 3)
            {
                foreach (char ch in word)
                {
                    frac = frac + 1.0 / charsInCircle3[ch];
                }
            }

            return frac;
        }
    }
}
