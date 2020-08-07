using Microsoft.VisualStudio.TestTools.UnitTesting;
using static WordTargetCore.CommonTestObjects;

namespace WordTargetCore
{
    [TestClass]
    public class ChainTest
    {

        [TestMethod]
        public void ValidChainCreation()
        {
            Chain chain = new Chain();
            chain.Add(catBat);
            chain.Add(batBaseball);
            Assert.AreEqual(2, chain.Count);
            Assert.AreEqual(catBat, chain[0]);
            Assert.AreEqual(batBaseball, chain[1]);
        }

        [TestMethod]
        public void InvalidChainCreation()
        {
            Chain chain = new Chain();
            chain.Add(batBaseball);
            Assert.ThrowsException<System.Exception>(() => chain.Add(catBat));
        }

        [TestMethod]
        public void ValidChainAdding()
        {
            Chain chain = new Chain();
            chain.Add(batBaseball);
            chain.Prepend(catBat);
            Assert.AreEqual(catBat, chain[0]);
        }

        [TestMethod]
        public void ValidChainAddingToEachOther()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            Chain chain2 = new Chain();
            chain2.Add(sportPorts);
            chain2.Add(portsParts);
            chain1.Add(chain2);
            Assert.AreEqual(5, chain1.Count);
            Assert.AreEqual(catBat, chain1[0]);
            Assert.AreEqual(batBaseball, chain1[1]);
            Assert.AreEqual(baseballSport, chain1[2]);
            Assert.AreEqual(sportPorts, chain1[3]);
            Assert.AreEqual(portsParts, chain1[4]);
        }

        [TestMethod]
        public void InvalidChainAddingToEachOther()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            Chain chain2 = new Chain();
            chain2.Add(sportPorts);
            chain2.Add(portsParts);
            Assert.ThrowsException<System.Exception>(() => chain2.Add(chain1));
        }

        [TestMethod]
        public void AddingEmptyChainToChain()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            Chain chain2 = new Chain();
            chain1.Add(chain2);
            Assert.AreEqual(3, chain1.Count);
            Assert.AreEqual(catBat, chain1[0]);
            Assert.AreEqual(batBaseball, chain1[1]);
            Assert.AreEqual(baseballSport, chain1[2]);
        }
        [TestMethod]
        public void AddingChainToEmptyChain()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            Chain chain2 = new Chain();
            chain2.Add(chain1);
            Assert.AreEqual(3, chain2.Count);
            Assert.AreEqual(catBat, chain2[0]);
            Assert.AreEqual(batBaseball, chain2[1]);
            Assert.AreEqual(baseballSport, chain2[2]);
        }

        [TestMethod]
        public void ValidChainAddingAtTheBeginning()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            Chain chain2 = new Chain();
            chain2.Add(sportPorts);
            chain2.Add(portsParts);
            chain2.Prepend(chain1);
            Assert.AreEqual(5, chain2.Count);
            Assert.AreEqual(catBat, chain2[0]);
            Assert.AreEqual(batBaseball, chain2[1]);
            Assert.AreEqual(baseballSport, chain2[2]);
            Assert.AreEqual(sportPorts, chain2[3]);
            Assert.AreEqual(portsParts, chain2[4]);
        }

        [TestMethod]
        public void InvalidChainAddingAtTheBeginning()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            Chain chain2 = new Chain();
            chain2.Add(sportPorts);
            chain2.Add(portsParts);
            Assert.ThrowsException<System.Exception>(() => chain1.Prepend(chain2));
        }

        [TestMethod]
        public void ValidChainInversion()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            chain1.Reverse();
            Assert.AreEqual(baseballSport, chain1[0]);
            Assert.AreEqual(batBaseball, chain1[1]);
            Assert.AreEqual(catBat, chain1[2]);
        }

        [TestMethod]
        public void EmptyChainInversion()
        {
            Chain chain2 = new Chain();
            chain2.Reverse();
            Assert.AreEqual("", chain2.ToString());
        }

        [TestMethod]
        public void ChainToStringConversion()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            Assert.AreEqual("CAT-BAT-BASEBALL-SPORT", chain1.ToString());
            Chain chain2 = new Chain();
            Assert.AreEqual("", chain2.ToString());
        }

        [TestMethod]
        public void ValidLinkRemovalFromChain()
        {
            Chain chain1 = new Chain();
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            chain1.Add(baseballSport);
            chain1.Add(sportPorts);
            chain1.RemoveFirst();
            Assert.AreEqual(batBaseball, chain1[0]);
            chain1.RemoveLast();
            Assert.AreEqual(batBaseball, chain1[0]);
            Assert.AreEqual(baseballSport, chain1[1]);
            Assert.AreEqual(2, chain1.Count);
        }

        [TestMethod]
        public void InValidLinkRemovalFromChain()
        {
            Chain chain1 = new Chain();
            Assert.ThrowsException<System.Exception>(() => chain1.RemoveFirst());
            Assert.ThrowsException<System.Exception>(() => chain1.RemoveLast());
        }

        [TestMethod]
        public void Contains()
        {
            Chain chain1 = new Chain();
            Assert.IsFalse(chain1.Contains(cat));
            chain1.Add(catBat);
            chain1.Add(batBaseball);
            Assert.IsTrue(chain1.Contains(cat));
            Assert.IsTrue(chain1.Contains(bat));
            Assert.IsTrue(chain1.Contains(baseball));
            Assert.IsFalse(chain1.Contains(sport));
            Assert.IsFalse(chain1.Contains(ports));
        }


    }
}
