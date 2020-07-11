﻿using System;
using System.Collections.Generic;
using System.Diagnostics.Tracing;
using System.Text;

namespace WordTargetCore
{
    public class Renderer
    {
        private static Dictionary<char, int> charsInCircle2 = new Dictionary<char, int>
        {
            { 'A', 24 },
            { 'B', 24 },
            { 'C', 24 },
            { 'D', 24 },
            { 'E', 24 },
            { 'F', 24 },
            { 'G', 24 },
            { 'H', 24 },
            { 'I', 59 },
            { 'J', 24 },
            { 'K', 24 },
            { 'L', 24 },
            { 'M', 24 },
            { 'N', 24 },
            { 'O', 24 },
            { 'P', 24 },
            { 'Q', 24 },
            { 'R', 24 },
            { 'S', 24 },
            { 'T', 24 },
            { 'U', 24 },
            { 'V', 24 },
            { 'W', 24 },
            { 'X', 24 },
            { 'Y', 24 },
            { 'Z', 24 }
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

        public static double FracForWord(string word, int circle)
        {
            word = word.ToUpper();
            double frac = 0;
            foreach (char ch in word)
            {
                frac = frac + 1.0 / charsInCircle2[ch];
            }
            return frac;
        }
    }
}
