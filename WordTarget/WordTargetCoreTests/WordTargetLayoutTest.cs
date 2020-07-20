using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;

namespace WordTargetCore
{
    [TestClass]
    public class WordTargetLayoutTest
    {
        [TestMethod]
        public void LinkCreation()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE" });
            Assert.AreEqual(1, layout.WordsInCircle5.Count);
            Assert.AreEqual("VARIABLE", layout.WordsInCircle5[0]);
            Assert.AreEqual(0, layout.WordsInCircle4.Count);
            Assert.AreEqual(0, layout.WordsInCircle3.Count);
            Assert.AreEqual(0, layout.WordsInCircle2.Count);
            Assert.AreEqual("STATE", layout.WordInCenter);
        }

    }
}
