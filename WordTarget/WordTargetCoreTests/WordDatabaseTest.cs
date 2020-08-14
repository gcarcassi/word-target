using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using System.IO;
using static WordTargetCore.CommonTestObjects;

namespace WordTargetCore
{
    [TestClass]
    public class WordDatabaseTest
    {


        [TestMethod]
        public void PopulatingDatabase()
        {
            WordDatabase db = new WordDatabase();
            db.AddWord(cat);
            db.AddWords(new List<Word> { bat, baseball, sport, ports, port });
            Assert.IsTrue(db.GetAllWords().SetEquals(new HashSet<Word> { cat, bat, baseball, sport, ports, port }));
            HashSet<Link> batLinks = db.GetLinksFor(bat);
            Assert.AreEqual(1, batLinks.Count);
            Assert.IsTrue(batLinks.Contains(catBat.Reverse()));
            HashSet<Link> portsLinks = db.GetLinksFor(ports);
            Assert.AreEqual(2, portsLinks.Count);
            Assert.IsTrue(portsLinks.Contains(new Link(ports, sport, LinkType.Anagram)));
            Assert.IsTrue(portsLinks.Contains(new Link(ports, port, LinkType.OneLetterAddOrRemove)));
        }

        [TestMethod]
        public void SerializeDatabase()
        {
            string expected = @"Words:
BASEBALL, BAT, CAT, PORT, PORTS, SPORT

Links:
BAT CAT OneLetterChange
CAT BAT OneLetterChange
PORT PORTS OneLetterAddOrRemove
PORT SPORT OneLetterAddOrRemove
PORTS PORT OneLetterAddOrRemove
PORTS SPORT Anagram
SPORT PORT OneLetterAddOrRemove
SPORT PORTS Anagram
";
            StringWriter writer = new StringWriter();
            WordDatabase db = new WordDatabase();
            db.AddWords(new List<Word> { cat, bat, baseball, sport, ports, port });
            db.Serialize(writer);
            Assert.AreEqual(expected, writer.GetStringBuilder().ToString());
        }

        [TestMethod]
        public void DeserializeDatabase()
        {
            string expected = @"Words:
BASEBALL, BAT, CAT, PORT, PORTS, SPORT

Links:
BASEBALL BAT WordAssociation
BAT BASEBALL WordAssociation
BAT CAT OneLetterChange
CAT BAT OneLetterChange
PORT PORTS OneLetterAddOrRemove
PORT SPORT OneLetterAddOrRemove
PORTS PORT OneLetterAddOrRemove
PORTS SPORT Anagram
SPORT PORT OneLetterAddOrRemove
SPORT PORTS Anagram
";
            StringReader reader = new StringReader(expected);
            WordDatabase db = WordDatabase.Deserialize(reader);
            Assert.IsTrue(db.GetAllWords().SetEquals(new HashSet<Word> { baseball, bat, cat, sport, ports, port }));
            Assert.IsTrue(db.ContainsLink("BASEBALL", "BAT"));
            Assert.IsTrue(db.ContainsLink("BAT", "BASEBALL"));
            Assert.IsTrue(db.ContainsLink("PORTS", "SPORT"));
        }

        [TestMethod]
        public void FindChain()
        {
            WordDatabase db = new WordDatabase();
            db.AddWords(new List<Word> { cat, bat, baseball, sport, ports, port });
            db.AddLink(new Link(bat, baseball, LinkType.WordAssociation));
            db.AddLink(new Link(sport, baseball, LinkType.WordAssociation));
            Chain chain = db.FindChain(cat, port);
            Assert.AreEqual(5, chain.Count);
            Assert.AreEqual(catBat, chain[0]);
            Assert.AreEqual(batBaseball, chain[1]);
            Assert.AreEqual(baseballSport, chain[2]);
            Assert.AreEqual(sportPorts, chain[3]);
            Assert.AreEqual(portsPort, chain[4]);
        }

        [TestMethod]
        public void ImproperWordAdd()
        {
            WordDatabase db = new WordDatabase();
            db.AddWord(cat);
            Assert.IsTrue(db.GetAllWords().SetEquals(new HashSet<Word> { cat }));
            db.AddWord(cat);
            Assert.AreEqual(1, db.GetAllWords().Count);
            Assert.IsTrue(db.GetAllWords().SetEquals(new HashSet<Word> { cat }));
        }

        [TestMethod]
        public void ContainsWord()
        {
            WordDatabase db = new WordDatabase();
            db.AddWords(new List<Word> { cat, bat, baseball });
            Assert.IsTrue(db.ContainsWord(cat));
            Assert.IsTrue(db.ContainsWord("BAT"));
            Assert.IsTrue(db.ContainsWord(baseball));
            Assert.IsFalse(db.ContainsWord(ports));
            Assert.IsFalse(db.ContainsWord("SPORT"));
            Assert.IsFalse(db.ContainsWord(""));
        }

        [TestMethod]
        public void ContainsLink()
        {
            WordDatabase db = new WordDatabase();
            db.AddWords(new List<Word> { cat, bat, baseball, sport, ports, port });
            db.AddLink(new Link(bat, baseball, LinkType.WordAssociation));
            Assert.IsTrue(db.ContainsLink(cat, bat));
            Assert.IsTrue(db.ContainsLink(bat, cat));
            Assert.IsTrue(db.ContainsLink(bat, baseball));
            Assert.IsTrue(db.ContainsLink("SPORT", "PORTS"));
            Assert.IsFalse(db.ContainsLink(cat, sport));
            Assert.IsFalse(db.ContainsLink("CAT", "RAT"));
        }

        [TestMethod]
        public void GetLinkBetween()
        {
            WordDatabase db = new WordDatabase();
            db.AddWords(new List<Word> { cat, bat, baseball, sport, ports, port });
            db.AddLink(batBaseball);
            db.AddLink(baseballSport);
            Assert.AreEqual(catBat, db.GetLinkBetween(cat, bat));
            Assert.AreEqual(baseballSport, db.GetLinkBetween(baseball, sport));
            Assert.AreEqual(null, db.GetLinkBetween(cat, baseball));
            Assert.AreEqual(null, db.GetLinkBetween(car, cat));
            Assert.AreEqual(null, db.GetLinkBetween("", "CAT"));
        }

        [TestMethod]
        public void RemoveLink()
        {
            WordDatabase db = new WordDatabase();
            db.AddWords(new List<Word> { cat, bat, baseball, sport, ports, port });
            db.AddLink(batBaseball);
            db.AddLink(baseballSport);
            Assert.IsTrue(db.ContainsLink(sport, port));
            db.RemoveLink(sportPort);
            Assert.IsFalse(db.ContainsLink(sport, port));
            Assert.ThrowsException<System.Exception>(() => db.RemoveLink(portsParts));
        }
    }
}
