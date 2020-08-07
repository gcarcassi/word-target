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
            db.AddWord("cat");
            db.AddWords(new List<string> { "bat", "baseball", "sport", "ports", "port" });
            Assert.IsTrue(db.GetAllWords().SetEquals(new HashSet<Word> { cat, bat, baseball, sport, ports, port }));
            HashSet<Link> batLinks = db.GetLinksFor(bat);
            Assert.AreEqual(1, batLinks.Count);
            Assert.IsTrue(batLinks.Contains(catBat.Reverse()));
            HashSet<Link> portsLinks = db.GetLinksFor(ports);
            Assert.AreEqual(2, portsLinks.Count);
            Assert.IsTrue(portsLinks.Contains(new Link(new Word("ports"), new Word("sport"), LinkType.Anagram)));
            Assert.IsTrue(portsLinks.Contains(new Link(new Word("ports"), new Word("port"), LinkType.OneLetterAddOrRemove)));
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
            db.AddWords(new List<string> { "cat", "bat", "baseball", "sport", "ports", "port" });
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
            Assert.IsTrue(db.GetAllWords().SetEquals(new HashSet<Word> { new Word("BASEBALL"), new Word("BAT"), new Word("CAT"), new Word("SPORT"), new Word("PORTS"), new Word("PORT") }));
            Assert.IsTrue(db.ContainsLink("BASEBALL", "BAT"));
            Assert.IsTrue(db.ContainsLink("BAT", "BASEBALL"));
            Assert.IsTrue(db.ContainsLink("PORTS", "SPORT"));
        }

        [TestMethod]
        public void FindChain()
        {
            WordDatabase db = new WordDatabase();
            db.AddWords(new List<string> { "cat", "bat", "baseball", "sport", "ports", "port" });
            db.AddLink(new Link(new Word("bat"), new Word("baseball"), LinkType.WordAssociation));
            db.AddLink(new Link(new Word("sport"), new Word("baseball"), LinkType.WordAssociation));
            Chain chain = db.FindChain(cat, port);
            Assert.AreEqual(5, chain.Count);
            Assert.AreEqual(new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange), chain[0]);
            Assert.AreEqual(new Link(new Word("bat"), new Word("baseball"), LinkType.WordAssociation), chain[1]);
            Assert.AreEqual(new Link(new Word("baseball"), new Word("sport"), LinkType.WordAssociation), chain[2]);
            Assert.AreEqual(new Link(new Word("sport"), new Word("ports"), LinkType.Anagram), chain[3]);
            Assert.AreEqual(new Link(new Word("ports"), new Word("port"), LinkType.OneLetterAddOrRemove), chain[4]);
        }

    }
}
