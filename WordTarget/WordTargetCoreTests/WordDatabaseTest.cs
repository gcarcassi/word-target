using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Buffers;
using System.Collections.Generic;
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
            db.AddWords(new List<string> { "bat", "baseball", "sports", "parts" });
            HashSet<Word> words = db.GetAllWords();
            HashSet<string> texts = new HashSet<string>();
            foreach (Word word in words)
            {
                texts.Add(word.Text);
            }
            Assert.IsTrue(Enumerable.SequenceEqual(new HashSet<string> { "cat", "bat", "baseball", "sports", "parts" }, texts));
        }
    }
}