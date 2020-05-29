using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class LinkTest
    {
        /*        [TestMethod]
                public void LinkCreation()
                {
                    Link link = new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange);
                    Assert.AreEqual("cat", link.WordA);
                    Assert.AreEqual("bat", link.WordB);
                    Assert.AreEqual(LinkType.OneLetterChange, link.Type);
                }
        */

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
