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

        [TestMethod]
        public void VerifyStringConversion()
        {
            Word word = new Word("cat");
            Assert.AreEqual("cat", word.ToString());
        }

        [TestMethod]
        public void ValidWordEquivalency()
        {
            Word word1 = new Word("cat");
            Word word2 = new Word("cat");
            Assert.AreEqual(word1, word2);
            Assert.AreEqual(word1.GetHashCode(), word2.GetHashCode());
        }
    }
}
