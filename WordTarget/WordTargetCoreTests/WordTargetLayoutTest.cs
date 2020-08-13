using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;

namespace WordTargetCore
{
    [TestClass]
    public class WordTargetLayoutTest
    {
        [TestMethod]
        public void LinkCreation()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE" });
            Assert.AreEqual(1, layout.GetWordsInCircle(5).Count);
            Assert.AreEqual("VARIABLE", layout.GetWordsInCircle(5)[0]);
            Assert.AreEqual(0, layout.GetWordsInCircle(4).Count);
            Assert.AreEqual(0, layout.GetWordsInCircle(3).Count);
            Assert.AreEqual(0, layout.GetWordsInCircle(2).Count);
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
            Assert.AreEqual(1, layout.GetWordsInCircle(3).Count);
            Assert.AreEqual(1, layout.GetWordsInCircle(4).Count);
            Assert.AreEqual("VALUE", layout.GetWordsInCircle(3)[0]);
            Assert.AreEqual("UNCLEAR", layout.GetWordsInCircle(4)[0]);


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
            Assert.AreEqual(0, layout.GetWordsInCircle(3).Count);
            Assert.AreEqual(1, layout.GetWordsInCircle(4).Count);
            Assert.AreEqual("UNCLEAR", layout.GetWordsInCircle(4)[0]);
        }

        // add to the target circle at the end of the list; calcualte the fraction and add it to the list of fractions for the circle
        [TestMethod]
        public void FracsWhenAssigningWords()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "UNCLEAR", "STATE" });
            Assert.AreEqual(0, layout.GetFracsInCircle(3).Count);
            layout.AssignWord("VALUE", 3);
            Assert.AreEqual(1, layout.GetFracsInCircle(3).Count);
            Assert.AreEqual(0.111, layout.GetFracsInCircle(3)[0], 0.001);
            layout.AssignWord("UNCLEAR", 3);
            Assert.AreEqual(2, layout.GetFracsInCircle(3).Count);
            Assert.AreEqual(0.162, layout.GetFracsInCircle(3)[1], 0.001);
        }

        [TestMethod]
        public void FracsWhenRemovingWords()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            layout.AssignWord("VALUE", 3);
            layout.AssignWord("UNCLEAR", 3);
            Assert.AreEqual(2, layout.GetFracsInCircle(3).Count);
            layout.RemoveWord("UNCLEAR", 3);
            Assert.AreEqual(1, layout.GetFracsInCircle(3).Count);
            layout.RemoveWord("VALUE", 3);
            Assert.AreEqual(0, layout.GetFracsInCircle(3).Count);
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
            Assert.ThrowsException<System.Exception>(() => layout.GetEmptySpace(6));
            Assert.ThrowsException<System.Exception>(() => layout.GetEmptySpace(1));

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
            Assert.ThrowsException<System.Exception>(() => layout.IsCircleFull(1));
            Assert.ThrowsException<System.Exception>(() => layout.IsCircleFull(6));
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
            Assert.ThrowsException<System.Exception>(() => layout.GetNextWord("NOTTHERE"));
        }

        [TestMethod]
        public void GetPreviousWord()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "STATE" });
            Assert.AreEqual(null, layout.GetPreviousWord("VARIABLE"));
            Assert.AreEqual("VARIABLE", layout.GetPreviousWord("VALUE"));
            Assert.AreEqual("UNCLEAR", layout.GetPreviousWord("STATE"));
            Assert.ThrowsException<System.Exception>(() => layout.GetNextWord("NOTTHERE"));
        }

        // Get words that are not assigned
        [TestMethod]
        public void GetUnassignedWords()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "STATE" });
            layout.AssignWord("VALUE", 2);
            layout.AssignWord("VAGUE", 2);
            List<string> unassigned = new List<string> { "UNCLEAR", "NUCLEAR" };
            Assert.IsTrue(Enumerable.SequenceEqual(unassigned, layout.GetUnassignedWords()));
        }

        [TestMethod]
        public void StartingAngles()
        {
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "STATE" });
            layout.SetStartingAngle(2, 80);
            layout.SetStartingAngle(3, 0);
            layout.SetStartingAngle(4, 5);
            Assert.AreEqual(80, layout.GetStartingAngle(2));
            Assert.AreEqual(0, layout.GetStartingAngle(3));
            Assert.AreEqual(5, layout.GetStartingAngle(4));
        }

        [TestMethod]
        public void DistributeRandomly()
        {
            Random rand = new Random(235);
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE" });
            layout.DistributeRandomly(rand);
            // TODO complete method
        }

        [TestMethod]
        public void EquilibrateOnce()
        {
            Random rand = new Random(236);
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE" });
            layout.DistributeRandomly(rand);
            layout.EquilibrateOnce(rand);
            // TODO complete method
        }

        [TestMethod]
        public void DoLayout()
        {
            Random rand = new Random(245);
            WordTargetLayout layout = new WordTargetLayout(new List<string> { "VARIABLE", "VALUE", "VAGUE", "UNCLEAR", "NUCLEAR", "CORE", "CORN", "COIN", "JOIN", "JOINT", "ANKLE", "ANGLE", "RIGHT", "SIGHT", "VITAMIN A", "CAROTENOIDS", "COORDINATES", "POLAR", "POLARS", "POLARIS", "STAR", "STARE", "SPARE", "SPACE", "STATION", "STATIN", "STATEN", "STATE" });
            layout.DoLayout(rand);
            // TODO complete method
            string wordTarget = Renderer.RenderWordTarget(layout);
            System.IO.File.WriteAllText(Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + @"\WordTargetOutput.svg", wordTarget);
        }
    }
}
