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
            string expected = @" <line transform=""rotate(80 0,0)"" x1=""-300"" x2=""-200"" y1=""0"" y2=""0"" class=""line3""/>
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
            List<string> words = new List<string> { "SPARE", "VALUE", "POLARS", "STATEN", "VITAMIN A"};
            Assert.AreEqual(expected, Renderer.LayoutWord(words, 3, 80));
        }

    }
}
