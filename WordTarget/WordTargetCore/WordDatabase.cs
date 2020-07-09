using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace WordTargetCore
{
    public class WordDatabase
    {

        private HashSet<Word> words = new HashSet<Word>();
        private HashSet<Link> links = new HashSet<Link>();
        private Dictionary<Word, HashSet<Link>> linksByWord = new Dictionary<Word, HashSet<Link>>();

        public void AddWord(string word)
        {
            Word newWord = new Word(word);
            words.Add(newWord);

            // Check if word can be linked to others
            foreach (Word other in words)
            {
                if (LinkTypes.IsOneLetterChange(word, other.Text))
                {
                    Link link = new Link(newWord, other, LinkType.OneLetterChange);
                    AddLink(link);
                }
                if (LinkTypes.IsOneLetterAddOrRemove(word, other.Text))
                {
                    Link link = new Link(newWord, other, LinkType.OneLetterAddOrRemove);
                    AddLink(link);
                }
                if (LinkTypes.IsAnagram(word, other.Text))
                {
                    Link link = new Link(newWord, other, LinkType.Anagram);
                    AddLink(link);
                }
            }
        }

        public void AddLink(Link link)
        {
            links.Add(link);
            AddLinkForWord(link.WordA, link);
            AddLinkForWord(link.WordB, link.Reverse());
        }

        private void AddLinkForWord(Word word, Link link)
        {
            if (linksByWord.ContainsKey(word))
            {
                linksByWord[word].Add(link);
            }
            else
            {
                HashSet<Link> newLinks = new HashSet<Link>();
                newLinks.Add(link);
                linksByWord[word] = newLinks;
            }
        }

        public void AddWords(List<string> words)
        {
            foreach (string word in words)
            {
                AddWord(word);
            }
        }

        public HashSet<Word> GetAllWords()
        {
            return words;
        }

        public HashSet<Link> GetLinksFor(Word word)
        {
            if (linksByWord.ContainsKey(word))
            {
                return linksByWord[word];
            } else
            {
                return new HashSet<Link>();
            }
        }

        public void Serialize(TextWriter writer)
        {
            writer.WriteLine("Words:");
            var sortedWords = words.OrderBy(x => x.Text).ToList();
            Boolean addComma = false;
            foreach (Word word in sortedWords)
            {
                if (addComma)
                {
                    writer.Write(", ");
                }

                writer.Write(word.Text);
                addComma = true;
            }
            writer.WriteLine();
            writer.WriteLine();
            writer.WriteLine("Links:");

            foreach (Word word in sortedWords)
            {
                var wordLinks = GetLinksFor(word);
                var sortedLinks = wordLinks.OrderBy(x => x.WordB.Text).ToList();
                foreach (Link link in sortedLinks)
                {
                    writer.Write(link.WordA);
                    writer.Write(" ");
                    writer.Write(link.WordB);
                    writer.Write(" ");
                    writer.WriteLine(link.Type.ToString());
                }
            }
        }
    }
}