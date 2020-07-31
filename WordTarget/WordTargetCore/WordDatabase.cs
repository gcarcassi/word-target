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

        // TODO: protect from adding the same word twice
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

        // TODO: add tests
        public void AddWordIfMissing(string word)
        {
            Word newWord = new Word(word);
            if (!words.Contains(newWord))
            {
                AddWord(word);
            }
        }

        // TODO: add tests
        public bool ContainsWord(string word)
        {
            return words.Contains(new Word(word.ToUpper()));
        }

        // TODO: add tests
        public bool ContainsLink(string wordA, string wordB)
        {
            Word theWordA = new Word(wordA.ToUpper());
            Word theWordB = new Word(wordB.ToUpper());
            return words.Contains(theWordA) && GetLinksFor(theWordA).FirstOrDefault(x => x.WordB.Equals(theWordB)) != null;
        }

        // TODO: add tests
        public Link GetLinkBetween(string wordA, string wordB)
        {
            Word theWordA = new Word(wordA.ToUpper());
            Word theWordB = new Word(wordB.ToUpper());
            return words.Contains(theWordA) ? GetLinksFor(theWordA).FirstOrDefault(x => x.WordB.Equals(theWordB)) : null;
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

        // Add tests
        public void RemoveLink(Link link)
        {
            // TODO: we are not checking whether the link is not there

            if (linksByWord.ContainsKey(link.WordA))
            {
                linksByWord[link.WordA].Remove(link);
            }
            else
            {
                throw new Exception("Word " + link.WordA + " not present");
            }

            if (linksByWord.ContainsKey(link.WordB))
            {
                linksByWord[link.WordB].Remove(link.Reverse());
            }
            else
            {
                throw new Exception("Word " + link.WordB + " not present");
            }
        }

        public void RemoveWord(Word word)
        {
            List<Link> links = new List<Link>(GetLinksFor(word));
            foreach (Link link in links)
            {
                RemoveLink(link);
            }
            words.Remove(word);
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

        public Chain FindChain(string start, string end)
        {
            Chain chain = new Chain();
            bool result = CompleteChain(chain, new Word(start), new Word(end));
            if (result)
            {
                return chain;
            }
            else
            {
                return null;
            }
        }

        private bool CompleteChain(Chain chain, Word lastWord, Word end)
        {
            HashSet<Link> newLinks = GetLinksFor(lastWord);
            foreach (Link link in newLinks)
            {
                Word newLastWord = link.WordB;

                // Check if the new word is the last
                if (newLastWord.Equals(end))
                {
                    chain.Add(link);
                    return true;
                }

                // Check the new word does not repeat
                if (chain.Contains(newLastWord))
                {
                    continue;
                }

                // Try completing the chain
                chain.Add(link);
                bool result = CompleteChain(chain, newLastWord, end);
                if (result)
                {
                    return true;
                }
                chain.RemoveLast();
            }

            return false;
        }
    }
}