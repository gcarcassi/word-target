using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
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

        // Assign word to a circle: error if it's not in the list or if it's first/last word;
        [TestMethod]
        public void AssignWord()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            layout.AssignWord("VALUE", 3);
            layout.AssignWord("UNCLEAR", 4);
            Assert.ThrowsException<System.Exception>(() => layout.AssignWord("NUCLEAR", 2));
            Assert.ThrowsException<System.Exception>(() => layout.AssignWord("VARIABLE", 3));
            Assert.ThrowsException<System.Exception>(() => layout.AssignWord("STATE", 1));
            Assert.ThrowsException<System.Exception>(() => layout.AssignWord("VAGUE", 1));
            Assert.ThrowsException<System.Exception>(() => layout.AssignWord("VALUE", 4));
            Assert.AreEqual("STATE", layout.WordInCenter);
            Assert.AreEqual(1, layout.WordsInCircle3.Count);
            Assert.AreEqual(1, layout.WordsInCircle4.Count);
            Assert.AreEqual("VALUE", layout.WordsInCircle3[0]);
            Assert.AreEqual("UNCLEAR", layout.WordsInCircle4[0]);


        }

        // Remove from other circles if there;
        [TestMethod]
        public void RemoveWord()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "UNCLEAR", "STATE" });
            layout.AssignWord("VALUE", 3);
            layout.AssignWord("UNCLEAR", 4);
            Assert.ThrowsException<System.Exception>(() => layout.RemoveWord("NUCLEAR", 2));
            Assert.ThrowsException<System.Exception>(() => layout.RemoveWord("UNCLEAR", 3));
            Assert.ThrowsException<System.Exception>(() => layout.RemoveWord("VARIABLE", 5));
            Assert.ThrowsException<System.Exception>(() => layout.RemoveWord("STATE", 1));
            layout.RemoveWord("VALUE", 3);
            Assert.AreEqual(0, layout.WordsInCircle3.Count);
            Assert.AreEqual(1, layout.WordsInCircle4.Count);
            Assert.AreEqual("UNCLEAR", layout.WordsInCircle4[0]);
        }

        // add to the target circle at the end of the list; calcualte the fraction and add it to the list of fractions for the circle
        [TestMethod]
        public void FracsWhenAssigningWords()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "UNCLEAR", "STATE" });
            Assert.AreEqual(0, layout.FracsInCircle3.Count);
            layout.AssignWord("VALUE", 3);
            Assert.AreEqual(1, layout.FracsInCircle3.Count);
            Assert.AreEqual(0.111, layout.FracsInCircle3[0], 0.001);
            layout.AssignWord("UNCLEAR", 3);
            Assert.AreEqual(2, layout.FracsInCircle3.Count);
            Assert.AreEqual(0.162, layout.FracsInCircle3[1], 0.001);
        }

        [TestMethod]
        public void FracsWhenRemovingWords()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            layout.AssignWord("VALUE", 3);
            layout.AssignWord("UNCLEAR", 3);
            Assert.AreEqual(2, layout.FracsInCircle3.Count);
            layout.RemoveWord("UNCLEAR", 3);
            Assert.AreEqual(1, layout.FracsInCircle3.Count);
            layout.RemoveWord("VALUE", 3);
            Assert.AreEqual(0, layout.FracsInCircle3.Count);
        }

        // Calculate empty space for circle: add up all the fractions, add min space for separator, return what's left
        [TestMethod]
        public void EmptySpace()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            layout.AssignWord("VALUE", 3);
            layout.AssignWord("UNCLEAR", 3);
            Assert.AreEqual(0.660, layout.GetEmptySpace(3), 0.001);
            Assert.AreEqual(1.0, layout.GetEmptySpace(4));
            Assert.AreEqual(0.892, layout.GetEmptySpace(5), 0.001);
        }

        // Is circle full? Check that there is no more space in the circle
        [TestMethod]
        public void IsCircleFull()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "STATE" });
            layout.AssignWord("VALUE", 3);
            layout.AssignWord("VAGUE", 3);
            layout.AssignWord("UNCLEAR", 3);
            layout.AssignWord("NUCLEAR", 3);
            Assert.AreEqual(false, layout.IsCircleFull(3));
            layout.AssignWord("CORE", 3);
            layout.AssignWord("CORN", 3);
            layout.AssignWord("COIN", 3);
            layout.AssignWord("JOIN", 3);

            Assert.AreEqual(true, layout.IsCircleFull(3));

        }

        // Where is the word? Return the circle or null if not assigned
        [TestMethod]
        public void FindWord()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            layout.AssignWord("VALUE", 3);
            layout.AssignWord("UNCLEAR", 4);
            layout.AssignWord("VAGUE", 2);
            Assert.AreEqual(2, layout.FindWord("VAGUE"));
            Assert.AreEqual(3, layout.FindWord("VALUE"));
            Assert.AreEqual(4, layout.FindWord("UNCLEAR"));
            Assert.AreEqual(5, layout.FindWord("VARIABLE"));
            Assert.AreEqual(1, layout.FindWord("STATE"));
            Assert.AreEqual(null, layout.FindWord("NUCLEAR"));
        }

        // Get previous and next words
        [TestMethod]
        public void GetNextWord()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            Assert.AreEqual("VALUE", layout.GetNextWord("VARIABLE"));
            Assert.AreEqual("VAGUE", layout.GetNextWord("VALUE"));
            Assert.AreEqual(null, layout.GetNextWord("STATE"));
        }

        [TestMethod]
        public void GetPreviousWord()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            Assert.AreEqual(null, layout.GetPreviousWord("VARIABLE"));
            Assert.AreEqual("VARIABLE", layout.GetPreviousWord("VALUE"));
            Assert.AreEqual("UNCLEAR", layout.GetPreviousWord("STATE"));
        }

        // Get words that are not assigned

    }
}
