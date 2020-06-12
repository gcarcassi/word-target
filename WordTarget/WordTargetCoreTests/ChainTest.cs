using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class ChainTest
    {
        static Word cat = new Word("cat");
        static Word bat = new Word("bat");
        static Word baseball = new Word("baseball");
        static Word sport = new Word("sport");
        static Word ports = new Word("ports");
        static Word parts = new Word("parts");
        static Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
        static Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
        static Link baseballSports = new Link(baseball, sport, LinkType.WordAssociation);
        static Link sportPorts = new Link(sport, ports, LinkType.Anagram);
        static Link portsParts = new Link(ports, parts, LinkType.OneLetterChange);
        
        [TestMethod]
        public void ValidChainCreation()
        {
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
            Chain chain = new Chain();
            chain.add(batBaseball);
            Assert.ThrowsException<System.Exception>(() => chain.add(catBat));
        }

        [TestMethod]
        public void ValidChainAdding()
        {
            Chain chain = new Chain();
            chain.add(batBaseball);
            chain.prepend(catBat);
            Assert.AreEqual(catBat, chain[0]);
        }

        [TestMethod]
        public void ValidChainAddingToEachOther()
        {
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
        public void InvalidChainAddingToEachOther()
        {
            Chain chain1 = new Chain();
            chain1.add(catBat);
            chain1.add(batBaseball);
            chain1.add(baseballSports);
            Chain chain2 = new Chain();
            chain2.add(sportPorts);
            chain2.add(portsParts);
            Chain chain3 = new Chain();
            Assert.ThrowsException<System.Exception>(()=>chain2.add(chain1));
        }

        [TestMethod]
        public void ValidChainAddingAtTheBeginning()
        {
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
