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
        private List<List<string>> wordsInCircle = new List<List<string>>() { null, null, new List<string>(), new List<string>(), new List<string>(), new List<string>() };
        private List<List<double>> fracsInCircle = new List<List<double>>() { null, null, new List<double>(), new List<double>(), new List<double>(), new List<double>() };
        private List<List<int>> anglesOfSeparators = new List<List<int>>() { null, null, new List<int>(), new List<int>(), new List<int>(), new List<int>() };
        private List<List<int>> anglesOfWords = new List<List<int>>() { null, null, new List<int>(), new List<int>(), new List<int>(), new List<int>() };
        private List<int> startingAngles = new List<int>() {0, 0, 0, 0, 0, 0 };

        public ReadOnlyCollection<string> Words => words.AsReadOnly();

        public ReadOnlyCollection<string> GetWordsInCircle(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new Exception("Circle must be between 2 and 5");
            }
            return wordsInCircle[circle].AsReadOnly();
        }

        public ReadOnlyCollection<double> GetFracsInCircle(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new Exception("Circle must be between 2 and 5");
            }
            return fracsInCircle[circle].AsReadOnly();
        }

        public int GetStartingAngle(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new Exception("Circle must be between 2 and 5");
            }
            return startingAngles[circle];
        }

        public void SetStartingAngle(int circle, int startingAngle)
        {
            if (circle < 2 || circle > 4)
            {
                throw new Exception("Circle must be between 2 and 4");
            }
            startingAngles[circle] = startingAngle;
        }

        public ReadOnlyCollection<int> GetAnglesOfSeparators(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new Exception("Circle must be between 2 and 5");
            }
            return anglesOfSeparators[circle].AsReadOnly();
        }
        public ReadOnlyCollection<int> GetAnglesOfWords(int circle)
        {
            if (circle < 2 || circle > 5)
            {
                throw new Exception("Circle must be between 2 and 5");
            }
            return anglesOfWords[circle].AsReadOnly();
        }

        public void CalculateAnglesInCircle()
        {
            for (int circle = 2; circle < 6; circle++)
            {
                double spaceBetweenWords = (1 - GetFracsInCircle(circle).Sum()) / GetFracsInCircle(circle).Count;

                if (spaceBetweenWords < minFracBetweenWords[circle])
                {
                    throw new Exception("Words do not fit in the circle");
                }

                int startingAngleDegrees;
                if (circle == 5)
                {
                    startingAngleDegrees = 45 - (int)(360 * (GetFracsInCircle(circle)[0] + spaceBetweenWords) / 2);
                }
                else
                {
                    startingAngleDegrees = GetStartingAngle(circle);
                }

                double circleSoFar = 0.0;
                for (int i = 0; i < GetWordsInCircle(circle).Count; i++)
                {
                    anglesOfSeparators[circle].Add(startingAngleDegrees + (int)(circleSoFar * 360));
                    circleSoFar += spaceBetweenWords / 2;
                    anglesOfWords[circle].Add(startingAngleDegrees + (int)(circleSoFar * 360));
                    circleSoFar += GetFracsInCircle(circle)[i];
                    circleSoFar += spaceBetweenWords / 2;
                }
            }
        }

        public string WordInCenter => words[words.Count - 1];

        private static readonly List<double> minFracBetweenWords = new List<double> { 0.0, 0.0, 20.0 / 360.0, 12.0 / 360.0, 10.0 / 360.0, 6.0 / 360.0 };

        public WordTargetLayout(List<string> words)
        {
            this.words = words;
            wordsInCircle[5].Add(words[0]);
            fracsInCircle[5].Add(Renderer.FracForWord(words[0], 5));
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
                if(this.GetWordsInCircle(i).Contains(word))
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

        public double GetRescaledEmptySpace(int circle)
        {
            double scaleFactor = minFracBetweenWords[circle] / minFracBetweenWords[5];
            return GetEmptySpace(circle) * scaleFactor;
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
            List<string> unassigned = words.Except(wordsInCircle[2]).Except(wordsInCircle[3]).Except(wordsInCircle[4]).Except(wordsInCircle[5]).ToList();
            unassigned.Remove(words[words.Count - 1]);
            return unassigned;
        }

        private int[] weights = new int[] { 3, 5, 7, 10 };

        private int pickCircle(Random rand, int circleToAvoid)
        {
            int weightSum = weights.Sum();
            while (true) {
                int i = rand.Next(weightSum);
                int circle = 0;
                while (i >= weights[circle])
                {
                    i -= weights[circle];
                    circle++;
                }
                circle += 2;
                if (circle != circleToAvoid)
                {
                    return circle;
                }
            }
        }

        public void DistributeRandomly(Random rand)
        {
            List<string> words = GetUnassignedWords();

            int previousCircle = -1;
            foreach (string word in words)
            {
                int circle = pickCircle(rand, previousCircle);
                AssignWord(word, circle);
                Console.WriteLine(word + " " + circle);
                previousCircle = circle;
            }
        }

        public void EquilibrateOnce(Random rand)
        {
            int originCircle = 0;
            int destinationCircle = 0;
            for (int circle = 2; circle < 6; circle++)
            {
                if (GetRescaledEmptySpace(circle) < 0)
                {
                    if (originCircle == 0 || GetRescaledEmptySpace(circle) < GetRescaledEmptySpace(originCircle))
                    {
                        originCircle = circle;
                    }
                }
                if (GetRescaledEmptySpace(circle) > 0)
                {
                    if (destinationCircle == 0 || GetRescaledEmptySpace(circle) > GetRescaledEmptySpace(originCircle))
                    {
                        destinationCircle = circle;
                    }
                }
            }
            if (originCircle != 0 && destinationCircle != 0)
            {
                int min = originCircle == 5 ? 1 : 0;
                string word = GetWordsInCircle(originCircle)[rand.Next(min, GetWordsInCircle(originCircle).Count)];
                RemoveWord(word, originCircle);
                AssignWord(word, destinationCircle);
            }
        }
    }
}
