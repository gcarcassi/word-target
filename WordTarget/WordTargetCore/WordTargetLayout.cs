using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace WordTargetCore
{
    public class WordTargetLayout
    {
        private readonly List<string> words;
        private readonly List<string> wordsInCircle5 = new List<string>();
        private readonly List<string> wordsInCircle4 = new List<string>();
        private readonly List<string> wordsInCircle3 = new List<string>();
        private readonly List<string> wordsInCircle2 = new List<string>();

        public ReadOnlyCollection<string> Words => words.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle5 => wordsInCircle5.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle4 => wordsInCircle4.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle3 => wordsInCircle3.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle2 => wordsInCircle2.AsReadOnly();
        public string WordInCenter => words[words.Count - 1];

        private List<List<string>> wordsInCircle;

        public WordTargetLayout(List<string> words)
        {
            this.words = words;
            wordsInCircle5.Add(words[0]);
            wordsInCircle = new List<List<string>>() { null, null, wordsInCircle2, wordsInCircle3, wordsInCircle4, wordsInCircle5 };

        }

        public void AssignWord(string word, int circle)
        {
            if (word.Equals(this.words[0]) || word.Equals(this.WordInCenter))
            {
                throw new Exception("The first and last word cannot be assigned to a different circle");
            }
            if (circle <= 1 || circle > 5)
            {
                throw new Exception("Words can only be assigned to circles 2-5");
            }
            if (!this.words.Contains(word))
            {
                throw new Exception("This word is not in the list of words");
            }
            
            wordsInCircle[circle].Add(word);
        }

        public void RemoveWord(string word, int circle)
        {
            if (word.Equals(this.words[0]) || word.Equals(this.WordInCenter))
            {
                throw new Exception("The first and last word cannot be removed");
            }
            if (!this.wordsInCircle[circle].Contains(word))
            {
                throw new Exception("This word is not found in this circle");
            }
            wordsInCircle[circle].Remove(word);
        }
    }
}
