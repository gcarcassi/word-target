using System;
using System.Collections.Generic;

namespace WordTargetCore
{
    public class WordDatabase
    {

        private HashSet<Word> words = new HashSet<Word>();
        private HashSet<Link> links = new HashSet<Link>();

        public void AddWord(string word)
        {
            words.Add(new Word(word));
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
    }
}