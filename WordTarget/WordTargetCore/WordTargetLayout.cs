using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;
using System.Linq;


namespace WordTargetCore
{
    public class WordTargetLayout
    {
        private readonly List<string> words;
        private readonly List<string> wordsInCircle5 = new List<string>();
        private readonly List<string> wordsInCircle4 = new List<string>();
        private readonly List<string> wordsInCircle3 = new List<string>();
        private readonly List<string> wordsInCircle2 = new List<string>();

        private readonly List<double> fracsInCircle5 = new List<double>();
        private readonly List<double> fracsInCircle4 = new List<double>();
        private readonly List<double> fracsInCircle3 = new List<double>();
        private readonly List<double> fracsInCircle2 = new List<double>();
        public ReadOnlyCollection<string> Words => words.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle5 => wordsInCircle5.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle4 => wordsInCircle4.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle3 => wordsInCircle3.AsReadOnly();
        public ReadOnlyCollection<string> WordsInCircle2 => wordsInCircle2.AsReadOnly();
        public string WordInCenter => words[words.Count - 1];

        public ReadOnlyCollection<double> FracsInCircle5 => fracsInCircle5.AsReadOnly();
        public ReadOnlyCollection<double> FracsInCircle4 => fracsInCircle4.AsReadOnly();
        public ReadOnlyCollection<double> FracsInCircle3 => fracsInCircle3.AsReadOnly();
        public ReadOnlyCollection<double> FracsInCircle2 => fracsInCircle2.AsReadOnly();

        public int startingAngle2 { get; set; }
        public int startingAngle3 { get; set; }
        public int startingAngle4 { get; set; }


        public readonly List<List<string>> wordsInCircle;
        public readonly List<List<double>> fracsInCircle;

        private static readonly List<double> minFracBetweenWords = new List<double> { 0.0, 0.0, 20.0 / 360.0, 12.0 / 360.0, 10.0 / 360.0, 6.0 / 360.0 };

        public WordTargetLayout(List<string> words)
        {
            this.words = words;
            wordsInCircle5.Add(words[0]);
            fracsInCircle5.Add(Renderer.FracForWord(words[0], 5));
            wordsInCircle = new List<List<string>>() { null, null, wordsInCircle2, wordsInCircle3, wordsInCircle4, wordsInCircle5 };
            fracsInCircle = new List<List<double>>() { null, null, fracsInCircle2, fracsInCircle3, fracsInCircle4, fracsInCircle5 };
            startingAngle2 = 80;
            startingAngle3 = 0;
            startingAngle4 = 5;
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
            for(int i = 2; i <= 5; i++)
            {
                if(this.wordsInCircle[i].Contains(word))
                {
                    throw new Exception("This word has already been assigned to a circle");
                }
            }
            wordsInCircle[circle].Add(word);
            fracsInCircle[circle].Add(Renderer.FracForWord(word, circle));
        }

        public void RemoveWord(string word, int circle)
        {
            if (word.Equals(this.words[0]) || word.Equals(this.WordInCenter))
            {
                throw new Exception("The first and last word cannot be removed");
            }
            if (circle <= 1 || circle > 5)
            {
                throw new Exception("Words can only be removed from circles 2-5");
            }
            if (!this.wordsInCircle[circle].Contains(word))
            {
                throw new Exception("This word is not found in this circle");
            }
            int wordPosition = wordsInCircle[circle].IndexOf(word);
            wordsInCircle[circle].RemoveAt(wordPosition);
            fracsInCircle[circle].RemoveAt(wordPosition);
        }

        public double GetEmptySpace(int circle)
        {
            if (circle <= 1 || circle > 5)
            {
                throw new Exception("Invalid input");
            }
            return 1 - fracsInCircle[circle].Sum() - (minFracBetweenWords[circle] * wordsInCircle[circle].Count);
        }

        public bool IsCircleFull(int circle)
        {
            if(GetEmptySpace(circle) < minFracBetweenWords[circle])
            {
                return true;
            }

            return false;
        }

        public int? FindWord(string word)
        {
            if(word.Equals(this.WordInCenter))
            {
                return 1;
            }
            for(int i = 2; i <= 5; i++)
            {
                if (this.wordsInCircle[i].Contains(word))
                {
                    return i;
                }
            }
            return null;
        }


        public string GetNextWord(string word)
        {
            if(this.words.Contains(word) == false)
            {
                throw new Exception("Word is not in the list of words");
            }
            int position = this.words.IndexOf(word);
            if(position == words.Count - 1)
            {
                return null;
            }
            position++;
            return words[position];
        }
        public string GetPreviousWord(string word)
        {
            if (this.words.Contains(word) == false)
            {
                throw new Exception("Word is not in the list of words");
            }
            int position = this.words.IndexOf(word);
            if (position == 0)
            {
                return null;
            }
            position--;
            return words[position];
        }

        public List<string> GetUnassignedWords()
        {
            List<string> unassigned = words.Except(wordsInCircle2).Except(wordsInCircle3).Except(wordsInCircle4).Except(wordsInCircle5).ToList();
            unassigned.Remove(words[words.Count - 1]);
            return unassigned;
        }
    }
}
