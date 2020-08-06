using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class LinkTest
    {
        [TestMethod]
        public void LinkCreation()
        {
            Link link = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
            Assert.AreEqual("cat", link.WordA.Text);
            Assert.AreEqual("bat", link.WordB.Text);
            Assert.AreEqual(LinkType.OneLetterChange, link.Type);
        }

        [TestMethod]
        public void VerifyAnagram()
        {
            Link link = new Link(new Word("bat"), new Word("tab"), LinkType.Anagram);
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("bat"), LinkType.Anagram));
        }


        [TestMethod]
        public void VerifyOneLetterChange()
        {
            Link link = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("brat"), LinkType.OneLetterChange));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("bit"), LinkType.OneLetterChange));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("cat"), LinkType.OneLetterChange));
        }

        [TestMethod]
        public void VerifyOneLetterAdd()
        {
            Link link = new Link(new Word("cat"), new Word("cart"), LinkType.OneLetterAddOrRemove);
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("brat"), LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("bit"), LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("cat"), LinkType.OneLetterAddOrRemove));
        }
        
        [TestMethod]
        public void VerifyOneLetterRemove()
        {
            Link link = new Link(new Word("cart"), new Word("cat"), LinkType.OneLetterAddOrRemove);
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("brat"), LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("bit"), LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("cat"), LinkType.OneLetterAddOrRemove));
        }

        [TestMethod]
        public void VerifyOneLetterAddOrRemove()
        {
            Link link1 = new Link(new Word("cat"), new Word("cart"), LinkType.OneLetterAddOrRemove);
            Link link2 = new Link(new Word("cart"), new Word("cat"), LinkType.OneLetterAddOrRemove);
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("brat"), LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("car"), LinkType.OneLetterAddOrRemove));
            Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("cat"), LinkType.OneLetterAddOrRemove));

        }

        [TestMethod]
        public void LinkStingConversion()
        {
            Link link = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
            Assert.AreEqual("cat-bat", link.ToString());
        }

        [TestMethod]
        public void ValidLinkEquivalency()
        {
            Link link1 = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
            Link link2 = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
            Assert.AreEqual(link1, link2);
            Assert.AreEqual(link1.GetHashCode(), link2.GetHashCode());
        }

        [TestMethod]
        public void ValidLinkInversionForOneLetterChange()
        {
            Link link1 = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
            Link link2 = link1.Reverse();
            Assert.AreEqual("cat", link1.WordA.ToString());
            Assert.AreEqual("bat", link1.WordB.ToString());
            Assert.AreEqual("bat", link2.WordA.ToString());
            Assert.AreEqual("cat", link2.WordB.ToString());
        }
    }
}
