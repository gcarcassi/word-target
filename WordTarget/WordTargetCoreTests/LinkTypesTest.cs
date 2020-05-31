using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class LinkTypesTest
    {
        [TestMethod]
        public void CountLetters()
        {
            LinkTypes.LetterCounter letterCount = LinkTypes.CountLetters("system");
            Assert.AreEqual(2, letterCount.CountFor('s'));
            Assert.AreEqual(1, letterCount.CountFor('y'));
            Assert.AreEqual(1, letterCount.CountFor('t'));
            Assert.AreEqual(1, letterCount.CountFor('e'));
            Assert.AreEqual(1, letterCount.CountFor('m'));
            Assert.AreEqual(0, letterCount.CountFor('a'));
            Assert.AreEqual(0, letterCount.CountFor('b'));
        }
        [TestMethod]
        public void CompareLetterCount()
        {
            LinkTypes.LetterCounter letterCount1 = LinkTypes.CountLetters("system");
            LinkTypes.LetterCounter letterCount2 = LinkTypes.CountLetters("system");
            System.Console.WriteLine(letterCount1.ToString());
            Assert.AreEqual(letterCount1, letterCount2);

            letterCount1 = LinkTypes.CountLetters("system");
            letterCount2 = LinkTypes.CountLetters("sytem");
            System.Console.WriteLine(letterCount1.ToString());
            Assert.AreNotEqual(letterCount1, letterCount2);

            letterCount1 = LinkTypes.CountLetters("system");
            letterCount2 = LinkTypes.CountLetters("yetmss");
            System.Console.WriteLine(letterCount1.ToString());
            Assert.AreEqual(letterCount1, letterCount2);

            letterCount1 = LinkTypes.CountLetters("abc");
            letterCount2 = LinkTypes.CountLetters("cde");
            System.Console.WriteLine(letterCount1.ToString());
            Assert.AreNotEqual(letterCount1, letterCount2);

            letterCount1 = LinkTypes.CountLetters("abcd");
            letterCount2 = LinkTypes.CountLetters("aaaa");
            System.Console.WriteLine(letterCount1.ToString());
            Assert.AreNotEqual(letterCount1, letterCount2);
        }

        [TestMethod]
        public void IsAnagram()
        {
            Assert.IsTrue(LinkTypes.IsAnagram("bat", "tab"));
            Assert.IsTrue(LinkTypes.IsAnagram("angel", "angle"));
            Assert.IsFalse(LinkTypes.IsAnagram("cat", "angle"));
        }


    }
}
