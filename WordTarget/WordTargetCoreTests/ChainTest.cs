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

        [TestMethod]
        public void ValidChainAddingToEachOther()
        {
            Word cat = new Word("cat");
            Word bat = new Word("bat");
            Word baseball = new Word("baseball");
            Word sport = new Word("sport");
            Word ports = new Word("ports");
            Word parts = new Word("parts");
            Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
            Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
            Link baseballSports = new Link(baseball, sport, LinkType.WordAssociation);
            Link sportPorts = new Link(sport, ports, LinkType.Anagram);
            Link portsParts = new Link(ports, parts, LinkType.OneLetterChange);
            Chain chain1 = new Chain();
            chain1.add(catBat);
            chain1.add(batBaseball);
            chain1.add(baseballSports);
            Chain chain2 = new Chain();
            chain2.add(sportPorts);
            chain2.add(portsParts);
            Chain chain3 = new Chain();
            chain1.add(chain2);
            Assert.AreEqual(5, chain1.Count);
            Assert.AreEqual(catBat, chain1[0]);
            Assert.AreEqual(batBaseball, chain1[1]);
            Assert.AreEqual(baseballSports, chain1[2]);
            Assert.AreEqual(sportPorts, chain1[3]);
            Assert.AreEqual(portsParts, chain1[4]);
            chain1.add(chain3);
            Assert.AreEqual(5, chain1.Count);
            Assert.AreEqual(catBat, chain1[0]);
            Assert.AreEqual(batBaseball, chain1[1]);
            Assert.AreEqual(baseballSports, chain1[2]);
            Assert.AreEqual(sportPorts, chain1[3]);
            Assert.AreEqual(portsParts, chain1[4]);
            chain3.add(chain1);
            Assert.AreEqual(5, chain3.Count);
            Assert.AreEqual(catBat, chain3[0]);
            Assert.AreEqual(batBaseball, chain3[1]);
            Assert.AreEqual(baseballSports, chain3[2]);
            Assert.AreEqual(sportPorts, chain3[3]);
            Assert.AreEqual(portsParts, chain3[4]);


        }

        [TestMethod]
        public void ValidChainAddingAtTheBeginning()
        {
            Word cat = new Word("cat");
            Word bat = new Word("bat");
            Word baseball = new Word("baseball");
            Word sport = new Word("sport");
            Word ports = new Word("ports");
            Word parts = new Word("parts");
            Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
            Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
            Link baseballSports = new Link(baseball, sport, LinkType.WordAssociation);
            Link sportPorts = new Link(sport, ports, LinkType.Anagram);
            Link portsParts = new Link(ports, parts, LinkType.OneLetterChange);
            Chain chain1 = new Chain();
            chain1.add(catBat);
            chain1.add(batBaseball);
            chain1.add(baseballSports);
            Chain chain2 = new Chain();
            chain2.add(sportPorts);
            chain2.add(portsParts);
            chain2.prepend(chain1);
            Assert.AreEqual(5, chain2.Count);
            Assert.AreEqual(catBat, chain2[0]);
            Assert.AreEqual(batBaseball, chain2[1]);
            Assert.AreEqual(baseballSports, chain2[2]);
            Assert.AreEqual(sportPorts, chain2[3]);
            Assert.AreEqual(portsParts, chain2[4]);
        }

        [TestMethod]
        public void InvalidChainAddingAtTheBeginning()
        {
            Word cat = new Word("cat");
            Word bat = new Word("bat");
            Word baseball = new Word("baseball");
            Word sport = new Word("sport");
            Word ports = new Word("ports");
            Word parts = new Word("parts");
            Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
            Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
            Link baseballSports = new Link(baseball, sport, LinkType.WordAssociation);
            Link sportPorts = new Link(sport, ports, LinkType.Anagram);
            Link portsParts = new Link(ports, parts, LinkType.OneLetterChange);
            Chain chain1 = new Chain();
            chain1.add(catBat);
            chain1.add(batBaseball);
            chain1.add(baseballSports);
            Chain chain2 = new Chain();
            chain2.add(sportPorts);
            chain2.add(portsParts);
            Assert.ThrowsException<System.Exception>(() => chain1.prepend(chain2));
        }
    }
}
