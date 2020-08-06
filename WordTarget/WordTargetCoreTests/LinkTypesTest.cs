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
            Assert.IsFalse(LinkTypes.IsAnagram("cat", "cat"));
        }

        [TestMethod]
        public void IsOneLetterChange()
        {
            Assert.IsTrue(LinkTypes.IsOneLetterChange("moon", "moan"));
            Assert.IsTrue(LinkTypes.IsOneLetterChange("cart", "care"));
            Assert.IsFalse(LinkTypes.IsOneLetterChange("cat", "bit"));
            Assert.IsFalse(LinkTypes.IsOneLetterChange("cat", "angle"));
            Assert.IsFalse(LinkTypes.IsOneLetterChange("cat", "cat"));
        }

        [TestMethod]
        public void IsOneLetterAdd()
        {
            Assert.IsTrue(LinkTypes.IsOneLetterAdd("pan", "pain"));
            Assert.IsTrue(LinkTypes.IsOneLetterAdd("car", "cart"));
            Assert.IsFalse(LinkTypes.IsOneLetterAdd("cat", "bite"));
            Assert.IsFalse(LinkTypes.IsOneLetterAdd("raw", "straw"));
            Assert.IsFalse(LinkTypes.IsOneLetterAdd("cat", "cat"));
        }
        
        [TestMethod]
        public void IsOneLetterRemove()
        {
            Assert.IsTrue(LinkTypes.IsOneLetterRemove("pain", "pan"));
            Assert.IsTrue(LinkTypes.IsOneLetterRemove("cart", "car"));
            Assert.IsFalse(LinkTypes.IsOneLetterRemove("cat", "bite"));
            Assert.IsFalse(LinkTypes.IsOneLetterRemove("straw", "raw"));
            Assert.IsFalse(LinkTypes.IsOneLetterRemove("cat", "cat"));
        }


        [TestMethod]
        public void ToString1()
        {
            Assert.AreEqual("OneLetterChange", LinkType.OneLetterChange.ToString());
            Assert.AreEqual("OneLetterAddOrRemove", LinkType.OneLetterAddOrRemove.ToString());
            Assert.AreEqual("Anagram", LinkType.Anagram.ToString());
            Assert.AreEqual("Synonym", LinkType.Synonym.ToString());
            Assert.AreEqual("Antonym", LinkType.Antonym.ToString());
            Assert.AreEqual("WordAssociation", LinkType.WordAssociation.ToString());
        }

        [TestMethod]
        public void FromString1()
        {
            Assert.AreEqual(LinkType.OneLetterChange, LinkTypes.FromString("OneLetterChange"));
            Assert.AreEqual(LinkType.OneLetterAddOrRemove, LinkTypes.FromString("OneLetterAddOrRemove"));
            Assert.AreEqual(LinkType.Anagram, LinkTypes.FromString("Anagram"));
            Assert.AreEqual(LinkType.Synonym, LinkTypes.FromString("Synonym"));
            Assert.AreEqual(LinkType.Antonym, LinkTypes.FromString("Antonym"));
            Assert.AreEqual(LinkType.WordAssociation, LinkTypes.FromString("WordAssociation"));
        }

    }
}
