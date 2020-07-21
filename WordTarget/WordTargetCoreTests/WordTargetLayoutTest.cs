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

        // Assign word to a circle: error if it's not in the list or if it's first/last word; remove from other circles if there;
        // add to the target circle at the end of the list; calcualte the fraction and add it to the list of fractions for the circle

        // Calculate empty space for circle: add up all the fractions, add min space for separator, return what's left
        
        // Is circle full? Check that there is no more space in the circle

        // Where is the word? Return the circle or null if not assigned

        // Get previous and next words

        // Get words that are not assigned

    }
}
