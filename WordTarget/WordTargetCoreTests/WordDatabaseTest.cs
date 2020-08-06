using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Buffers;
using System.Collections.Generic;
using System.Data.Common;
using System.IO;
using System.Linq;

namespace WordTargetCore
{
    [TestClass]
    public class WordDatabaseTest
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
        public void PopulatingDatabase()
        {
            WordDatabase db = new WordDatabase();
            db.AddWord("cat");
            db.AddWords(new List<string> { "bat", "baseball", "sport", "ports", "port" });
            HashSet<Word> words = db.GetAllWords();
            HashSet<string> texts = new HashSet<string>();
            Word bat = null;
            foreach (Word word in words)
            {
                texts.Add(word.Text);
                if (word.Text.Equals("bat"))
                {
                    bat = word;
                }
            }
            Assert.IsTrue(Enumerable.SequenceEqual(new HashSet<string> { "cat", "bat", "baseball", "sport", "ports", "port" }, texts));
            HashSet<Link> batLinks = db.GetLinksFor(bat);
            Assert.AreEqual(1, batLinks.Count);
            Assert.IsTrue(batLinks.Contains(new Link(new Word("bat"), new Word("cat"), LinkType.OneLetterChange)));
            HashSet<Link> portsLinks = db.GetLinksFor(new Word("ports"));
            Assert.AreEqual(2, portsLinks.Count);
            Assert.IsTrue(portsLinks.Contains(new Link(new Word("ports"), new Word("sport"), LinkType.Anagram)));
            Assert.IsTrue(portsLinks.Contains(new Link(new Word("ports"), new Word("port"), LinkType.OneLetterAddOrRemove)));
        }

        [TestMethod]
        public void SerializeDatabase()
        {
            string expected = @"Words:
baseball, bat, cat, port, ports, sport

Links:
bat cat OneLetterChange
cat bat OneLetterChange
port ports OneLetterAddOrRemove
port sport OneLetterAddOrRemove
ports port OneLetterAddOrRemove
ports sport Anagram
sport port OneLetterAddOrRemove
sport ports Anagram
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
            Chain chain = db.FindChain("cat", "port");
            Assert.AreEqual(5, chain.Count);
            Assert.AreEqual(new Link(new Word("cat"), new Word("bat"), LinkType.OneLetterChange), chain[0]);
            Assert.AreEqual(new Link(new Word("bat"), new Word("baseball"), LinkType.WordAssociation), chain[1]);
            Assert.AreEqual(new Link(new Word("baseball"), new Word("sport"), LinkType.WordAssociation), chain[2]);
            Assert.AreEqual(new Link(new Word("sport"), new Word("ports"), LinkType.Anagram), chain[3]);
            Assert.AreEqual(new Link(new Word("ports"), new Word("port"), LinkType.OneLetterAddOrRemove), chain[4]);
        }

    }
}
