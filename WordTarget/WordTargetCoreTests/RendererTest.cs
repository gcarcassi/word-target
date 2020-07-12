using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class RendererTest
    {
        [TestMethod]
        public void WordFrac()
        {
            Assert.AreEqual(0.2669, Renderer.FracForWord("TESTING", 2), 0.001);
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
