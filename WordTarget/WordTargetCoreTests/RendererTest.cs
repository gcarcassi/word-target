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

    }
}
