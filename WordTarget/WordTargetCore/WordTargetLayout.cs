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

        public WordTargetLayout(List<string> words)
        {
            this.words = words;
            wordsInCircle5.Add(words[0]);
        }

    }
}
