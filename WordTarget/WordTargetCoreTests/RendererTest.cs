using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;

namespace WordTargetCore
{
    [TestClass]
    public class RendererTest
    {
        [TestMethod]
        public void WordFrac()
        {
            //Assert.AreEqual(0.2669, Renderer.FracForWord("TESTING", 2), 0.001);
            Assert.AreEqual(0.33, Renderer.FracForWord("ELEPHANT", 2), 0.001);
            Assert.AreEqual(0.36, Renderer.FracForWord("DOUGHNUT", 2), 0.001);
            Assert.AreEqual(0.125, Renderer.FracForWord("CAT", 2), 0.001);
            Assert.AreEqual(0.319, Renderer.FracForWord("BASEBALL", 2), 0.001);
        }

        [TestMethod]
        public void PrepareWordsInCenter()
        {
            string expected = @"  <text x=""0"" y=""0"" dominant-baseline=""middle"" text-anchor=""middle"" class=""text1"">STARE</text>
";
            Assert.AreEqual(expected, Renderer.LayoutWordCenter("STARE"));
            Assert.ThrowsException<System.Exception>(() => Renderer.LayoutWordCenter("THISISAVERYLONGWORD"));
            Assert.ThrowsException<System.Exception>(() => Renderer.LayoutWordCenter("AAAAAA"));
        }

        [TestMethod]
        public void PrepareWordsInCircle2()
        {
            string expected = @"  <line transform=""rotate(80 0,0)"" x1=""-200"" x2=""-100"" y1=""0"" y2=""0"" class=""line2""/>
  <text transform=""rotate(97 0,0)"" class=""text2""><textPath href=""#circle2"">STARE</textPath></text>
  <line transform=""rotate(190 0,0)"" x1=""-200"" x2=""-100"" y1=""0"" y2=""0"" class=""line2""/>
  <text transform=""rotate(208 0,0)"" class=""text2""><textPath href=""#circle2"">RIGHT</textPath></text>
  <line transform=""rotate(296 0,0)"" x1=""-200"" x2=""-100"" y1=""0"" y2=""0"" class=""line2""/>
  <text transform=""rotate(314 0,0)"" class=""text2""><textPath href=""#circle2"">UNCLEAR</textPath></text>
";
            List<string> words = new List<string> { "STARE", "RIGHT", "UNCLEAR" };
            Assert.AreEqual(expected, Renderer.LayoutWord(words, 2, 80));
            Assert.ThrowsException<System.Exception>(() => Renderer.LayoutWord(new List<string> { "STARE", "RIGHT", "VERYBIGWORDTHATISTOOLONG" }, 2, 80));
            Assert.ThrowsException<System.Exception>(() => Renderer.LayoutWord(new List<string> { "TOO", "MANY", "WORDS", "TO", "DISPLAY", "IN", "ONE", "CIRCLE" }, 2, 80));
        }

        [TestMethod]
        public void SvgForCircleWord2()
        {
            Assert.AreEqual("  <text transform=\"rotate(10 0,0)\" class=\"text2\"><textPath href=\"#circle2\">BASEBALL</textPath></text>", Renderer.SvgForCircleWord(10, "BASEBALL", 2));
        }

        [TestMethod]
        public void SvgForCircleSeparator2()
        {
            Assert.AreEqual("  <line transform=\"rotate(125 0,0)\" x1=\"-200\" x2=\"-100\" y1=\"0\" y2=\"0\" class=\"line2\"/>", Renderer.SvgForCircleSeparator(125, 2));
        }

        [TestMethod]
        public void WordFracCircle3()
        {
            Assert.AreEqual(0.125, Renderer.FracForWord("TENNIS", 3), 0.001);
            Assert.AreEqual(0.254, Renderer.FracForWord("ACCOUNTABLE", 3), 0.001);
            Assert.AreEqual(0.068, Renderer.FracForWord("CAT", 3), 0.001);
            Assert.AreEqual(0.174, Renderer.FracForWord("BASEBALL", 3), 0.001);
        }

        [TestMethod]
        public void SvgForCircleSeparator3()
        {
            Assert.AreEqual("  <line transform=\"rotate(65 0,0)\" x1=\"-300\" x2=\"-200\" y1=\"0\" y2=\"0\" class=\"line3\"/>", Renderer.SvgForCircleSeparator(65, 3));
        }

        [TestMethod]
        public void SvgForCircleWord3()
        {
            Assert.AreEqual("  <text transform=\"rotate(10 0,0)\" class=\"text3\"><textPath href=\"#circle3\">BASEBALL</textPath></text>", Renderer.SvgForCircleWord(10, "BASEBALL", 3));
        }

        [TestMethod]
        public void PrepareWordsInCircle3()
        {
            string expected = @"  <line transform=""rotate(80 0,0)"" x1=""-300"" x2=""-200"" y1=""0"" y2=""0"" class=""line3""/>
  <text transform=""rotate(91 0,0)"" class=""text3""><textPath href=""#circle3"">SPARE</textPath></text>
  <line transform=""rotate(145 0,0)"" x1=""-300"" x2=""-200"" y1=""0"" y2=""0"" class=""line3""/>
  <text transform=""rotate(157 0,0)"" class=""text3""><textPath href=""#circle3"">VALUE</textPath></text>
  <line transform=""rotate(209 0,0)"" x1=""-300"" x2=""-200"" y1=""0"" y2=""0"" class=""line3""/>
  <text transform=""rotate(221 0,0)"" class=""text3""><textPath href=""#circle3"">POLARS</textPath></text>
  <line transform=""rotate(282 0,0)"" x1=""-300"" x2=""-200"" y1=""0"" y2=""0"" class=""line3""/>
  <text transform=""rotate(294 0,0)"" class=""text3""><textPath href=""#circle3"">STATEN</textPath></text>
  <line transform=""rotate(354 0,0)"" x1=""-300"" x2=""-200"" y1=""0"" y2=""0"" class=""line3""/>
  <text transform=""rotate(366 0,0)"" class=""text3""><textPath href=""#circle3"">VITAMIN A</textPath></text>
";
            List<string> words = new List<string> { "SPARE", "VALUE", "POLARS", "STATEN", "VITAMIN A" };
            Assert.AreEqual(expected, Renderer.LayoutWord(words, 3, 80));
        }

        [TestMethod]
        public void WordFracCircle4()
        {
            Assert.AreEqual(0.151, Renderer.FracForWord("CHOCOLATE", 4), 0.001);
            Assert.AreEqual(0.245, Renderer.FracForWord("BLOODTHIRSTINESS", 4), 0.001);
            Assert.AreEqual(0.048, Renderer.FracForWord("CAT", 4), 0.001);
            Assert.AreEqual(0.124, Renderer.FracForWord("BASEBALL", 4), 0.001);
        }

        [TestMethod]
        public void SvgForCircleSeparator4()
        {
            Assert.AreEqual("  <line transform=\"rotate(65 0,0)\" x1=\"-400\" x2=\"-300\" y1=\"0\" y2=\"0\" class=\"line4\"/>", Renderer.SvgForCircleSeparator(65, 4));
        }

        [TestMethod]
        public void SvgForCircleWord4()
        {
            Assert.AreEqual("  <text transform=\"rotate(10 0,0)\" class=\"text4\"><textPath href=\"#circle4\">BASEBALL</textPath></text>", Renderer.SvgForCircleWord(10, "BASEBALL", 4));
        }

        [TestMethod]
        public void PrepareWordsInCircle4()
        {
            string expected = @"  <line transform=""rotate(80 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(85 0,0)"" class=""text4""><textPath href=""#circle4"">ANGLE</textPath></text>
  <line transform=""rotate(120 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(125 0,0)"" class=""text4""><textPath href=""#circle4"">STAR</textPath></text>
  <line transform=""rotate(153 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(158 0,0)"" class=""text4""><textPath href=""#circle4"">JOIN</textPath></text>
  <line transform=""rotate(183 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(189 0,0)"" class=""text4""><textPath href=""#circle4"">CORE</textPath></text>
  <line transform=""rotate(219 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(224 0,0)"" class=""text4""><textPath href=""#circle4"">STATION</textPath></text>
  <line transform=""rotate(267 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(273 0,0)"" class=""text4""><textPath href=""#circle4"">POLAR</textPath></text>
  <line transform=""rotate(307 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(313 0,0)"" class=""text4""><textPath href=""#circle4"">VAGUE</textPath></text>
  <line transform=""rotate(348 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(354 0,0)"" class=""text4""><textPath href=""#circle4"">R</textPath></text>
  <line transform=""rotate(365 0,0)"" x1=""-400"" x2=""-300"" y1=""0"" y2=""0"" class=""line4""/>
  <text transform=""rotate(370 0,0)"" class=""text4""><textPath href=""#circle4"">COORDINATES</textPath></text>
";
            List<string> words = new List<string> { "ANGLE", "STAR", "JOIN", "CORE", "STATION", "POLAR", "VAGUE", "R", "COORDINATES" };
            Assert.AreEqual(expected, Renderer.LayoutWord(words, 4, 80));
        }

        [TestMethod]
        public void WordFracCircle5()
        {
            Assert.AreEqual(0.101, Renderer.FracForWord("ENVELOPE", 5), 0.001);
            Assert.AreEqual(0.264, Renderer.FracForWord("OVERCOMMERCIALIZATIONS", 5), 0.001);
            Assert.AreEqual(0.037, Renderer.FracForWord("CAT", 5), 0.001);
            Assert.AreEqual(0.096, Renderer.FracForWord("BASEBALL", 5), 0.001);
        }

        [TestMethod]
        public void SvgForCircleSeparator5()
        {
            Assert.AreEqual("  <line transform=\"rotate(65 0,0)\" x1=\"-500\" x2=\"-400\" y1=\"0\" y2=\"0\" class=\"line5\"/>", Renderer.SvgForCircleSeparator(65, 5));
        }

        [TestMethod]
        public void SvgForCircleWord5()
        {
            Assert.AreEqual("  <text transform=\"rotate(90 0,0)\" class=\"text5\"><textPath href=\"#circle5\">BASEBALL</textPath></text>", Renderer.SvgForCircleWord(90, "BASEBALL", 5));
        }

        [TestMethod]
        public void PrepareWordsInCircle5()
        {
            string expected = @"  <line transform=""rotate(80 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(84 0,0)"" class=""text5""><textPath href=""#circle5"">VARIABLE</textPath></text>
  <line transform=""rotate(120 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(124 0,0)"" class=""text5""><textPath href=""#circle5"">SIGHT</textPath></text>
  <line transform=""rotate(149 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(153 0,0)"" class=""text5""><textPath href=""#circle5"">NUCLEAR</textPath></text>
  <line transform=""rotate(189 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(193 0,0)"" class=""text5""><textPath href=""#circle5"">SPACE</textPath></text>
  <line transform=""rotate(219 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(223 0,0)"" class=""text5""><textPath href=""#circle5"">JOINT</textPath></text>
  <line transform=""rotate(247 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(251 0,0)"" class=""text5""><textPath href=""#circle5"">POLARIS</textPath></text>
  <line transform=""rotate(284 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(288 0,0)"" class=""text5""><textPath href=""#circle5"">VAGUE</textPath></text>
  <line transform=""rotate(315 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(319 0,0)"" class=""text5""><textPath href=""#circle5"">COIN</textPath></text>
  <line transform=""rotate(340 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(344 0,0)"" class=""text5""><textPath href=""#circle5"">CAROTENOIDS</textPath></text>
  <line transform=""rotate(397 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(401 0,0)"" class=""text5""><textPath href=""#circle5"">ANKLE</textPath></text>
  <line transform=""rotate(427 0,0)"" x1=""-500"" x2=""-400"" y1=""0"" y2=""0"" class=""line5""/>
  <text transform=""rotate(431 0,0)"" class=""text5""><textPath href=""#circle5"">S</textPath></text>
";

            List<string> words = new List<string> { "VARIABLE", "SIGHT", "NUCLEAR", "SPACE", "JOINT", "POLARIS", "VAGUE", "COIN", "CAROTENOIDS", "ANKLE", "S" };
            Assert.AreEqual(expected, Renderer.LayoutWord(words, 5, 80));
        }
    }
}
