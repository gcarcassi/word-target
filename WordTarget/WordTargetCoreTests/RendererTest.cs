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


    }
}
