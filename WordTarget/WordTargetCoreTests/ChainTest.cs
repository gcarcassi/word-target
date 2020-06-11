using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class ChainTest
    {
        [TestMethod]
        public void ValidChainCreation()
        {
            Word cat = new Word("Cat");
            Word bat = new Word("Bat");
            Word baseball = new Word("Baseball");
            Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
            Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
            Chain chain = new Chain();
            chain.add(catBat);
            chain.add(batBaseball);
            Assert.AreEqual(2, chain.Count);
            Assert.AreEqual(catBat, chain[0]);
            Assert.AreEqual(batBaseball, chain[1]);
        }
        
        [TestMethod]
        public void InvalidChainCreation()
        {
            Word cat = new Word("Cat");
            Word bat = new Word("Bat");
            Word baseball = new Word("Baseball");
            Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
            Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
            Chain chain = new Chain();
            chain.add(batBaseball);
            Assert.ThrowsException<System.Exception>(() => chain.add(catBat));
        }
        
        [TestMethod]
        public void ValidChainAdding()
        {
            Word cat = new Word("Cat");
            Word bat = new Word("Bat");
            Word baseball = new Word("Baseball");
            Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
            Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
            Chain chain = new Chain();
            chain.add(batBaseball);
            chain.prepend(catBat);
            Assert.AreEqual(catBat, chain[0]);
        }
    }
}
