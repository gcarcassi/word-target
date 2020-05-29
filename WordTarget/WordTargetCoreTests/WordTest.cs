using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class WordTest
    {
        [TestMethod]
        public void WordCreation()
        {
            Word word = new Word("cat");
            Assert.AreEqual("cat", word.Text);
        }
    }
}
