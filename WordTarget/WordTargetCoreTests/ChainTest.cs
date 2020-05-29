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
        }
    }
}
