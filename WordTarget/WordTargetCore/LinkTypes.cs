using System;
using System.Collections.Generic;
using System.Text;

namespace WordTargetCore
{
    public class LinkTypes
    {
        public class LetterCounter
        {
            private Dictionary<char, int> counter = new Dictionary<char, int>();

            public void AddLetter(char letter)
            {
                if (counter.ContainsKey(letter))
                {
                    counter[letter]++;
                } else
                {
                    counter[letter] = 1;
                }
            }

            public int CountFor(char letter)
            {
                if (counter.ContainsKey(letter))
                {
                    return counter[letter];
                } else
                {
                    return 0;
                }
            }

            public override bool Equals(object obj)
            {
                if (obj is LetterCounter other) {
                    if (counter.Keys.Count != other.counter.Keys.Count)
                    {
                        return false;
                    }
                    foreach (KeyValuePair<char, int> kvp in counter)
                    {
                        if (!other.counter.ContainsKey(kvp.Key) || other.counter[kvp.Key] != kvp.Value) return false;
                    }
                }
                else
                {
                    return false;
                }

                return true;
            }

            public override int GetHashCode()
            {
                return -837799269 + EqualityComparer<Dictionary<char, int>>.Default.GetHashCode(counter);
            }

            public override string ToString()
            {
                string output = "[";
                foreach (KeyValuePair<char, int> kvp in counter)
                {
                    output += string.Format("{0}:{1}", kvp.Key, kvp.Value);
                    output += ";";
                }
                if (output.Length > 1) {
                    output = output.Remove(output.Length - 1, 1);
                }
                output += "]";
                return output;
            }
        }

        public static LetterCounter CountLetters(string text)
        {
            LetterCounter counter = new LetterCounter();
            foreach (char c in text)
            {
                counter.AddLetter(c);
            }
            return counter;
        }

        public static bool IsAnagram(string textA, string textB)
        {
            return CountLetters(textA).Equals(CountLetters(textB));
        }

        public static bool IsOneLetterChange(string textA, string textB)
        {
            if (textA.Length != textB.Length)
            {
                return false;
            }

            bool firstLetterChange = false;
            for (int i = 0; i < textA.Length; i++)
            {
                if (textA[i] != textB[i])
                {
                    if (firstLetterChange)
                    {
                        return false;
                    } 
                    else
                    {
                        firstLetterChange = true;
                    }
                
                }
            }
            return firstLetterChange;
        }

        public static bool IsOneLetterAdd(string textA, string textB)
        {
            if (textA.Length - textB.Length != -1)
            {
                return false;
            }
            
            bool firstLetterAdded = false;
            for (int i = 0; i < textA.Length; i++)
            {
                int offset = firstLetterAdded ? 1 : 0;
                if (textA[i] != textB[i + offset])
                {
                    if (firstLetterAdded)
                    {
                        return false;
                    }
                    else
                    {
                        firstLetterAdded = true;
                        i--;
                    }
                }
            }
            return true;
        }

        public static bool IsOneLetterRemove(string textA, string textB)
        {
            return IsOneLetterAdd(textB, textA);
        }
    }

}
