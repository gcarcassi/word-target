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


        /*        [TestMethod]
                public void VerifyOneLetterChange()
                {
                    Link link = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
                    Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("brat"), LinkType.OneLetterChange));
                    Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("bit"), LinkType.OneLetterChange));
                    Assert.ThrowsException<System.Exception>(() => new Link(new Word("cat"), new Word("cat"), LinkType.OneLetterChange));
                }
        */
    }
}
