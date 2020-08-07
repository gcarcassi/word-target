using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class LinkTest
    {

        static Word cat = new Word("cat");
        static Word car = new Word("car");
        static Word cart = new Word("cart");
        static Word bat = new Word("bat");
        static Word bit = new Word("bit");
        static Word brat = new Word("brat");
        static Word tab = new Word("tab");

        [TestMethod]
        public void LinkCreation()
        {
            
            Link link = new Link(cat, bat, LinkType.OneLetterChange);
            Assert.AreEqual(cat, link.WordA);
            Assert.AreEqual(bat, link.WordB);
            Assert.AreEqual(LinkType.OneLetterChange, link.Type);
        }

        [TestMethod]
        public void VerifyAnagram()
        {
            Link link = new Link(bat, tab, LinkType.Anagram);
            Assert.ThrowsException<System.Exception>(() => new Link(cat, bat, LinkType.Anagram));
        }


        [TestMethod]
        public void VerifyOneLetterChange()
        {
            Link link = new Link(cat, bat, LinkType.OneLetterChange);
            Assert.ThrowsException<System.Exception>(() => new Link(cat, brat, LinkType.OneLetterChange));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, bit, LinkType.OneLetterChange));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, cat, LinkType.OneLetterChange));
        }

        [TestMethod]
        public void VerifyOneLetterAdd()
        {
            Link link = new Link(cat, cart, LinkType.OneLetterAddOrRemove);
            Assert.ThrowsException<System.Exception>(() => new Link(cat, brat, LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, bit, LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, cat, LinkType.OneLetterAddOrRemove));
        }
        
        [TestMethod]
        public void VerifyOneLetterRemove()
        {
            Link link = new Link(cart, cat, LinkType.OneLetterAddOrRemove);
            Assert.ThrowsException<System.Exception>(() => new Link(cat, brat, LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, bit, LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, cat, LinkType.OneLetterAddOrRemove));
        }

        [TestMethod]
        public void VerifyOneLetterAddOrRemove()
        {
            Link link1 = new Link(cat, cart, LinkType.OneLetterAddOrRemove);
            Link link2 = new Link(cart, cat, LinkType.OneLetterAddOrRemove);
            Assert.ThrowsException<System.Exception>(() => new Link(cat, brat, LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, car, LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(cat, cat, LinkType.OneLetterAddOrRemove));

        }

        [TestMethod]
        public void LinkStingConversion()
        {
            Link link = new Link(cat, bat, LinkType.OneLetterChange);
            Assert.AreEqual("CAT-BAT", link.ToString());
        }

        [TestMethod]
        public void ValidLinkEquivalency()
        {
            Link link1 = new Link(cat, bat, LinkType.OneLetterChange);
            Link link2 = new Link(cat, bat, LinkType.OneLetterChange);
            Assert.AreEqual(link1, link2);
            Assert.AreEqual(link1.GetHashCode(), link2.GetHashCode());
        }

        [TestMethod]
        public void ValidLinkInversionForOneLetterChange()
        {
            Link link1 = new Link(cat, bat, LinkType.OneLetterChange);
            Link link2 = link1.Reverse();
            Assert.AreEqual(cat, link1.WordA);
            Assert.AreEqual(bat, link1.WordB);
            Assert.AreEqual(bat, link2.WordA);
            Assert.AreEqual(cat, link2.WordB);
        }
    }
}
