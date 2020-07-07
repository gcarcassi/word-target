using System;
using System.Collections.Generic;
using System.Text;

namespace WordTargetCore
{
    public class Link
    {
        public Word WordA { get; }
        public Word WordB { get; }
        public LinkType Type { get; }

        

        public Link(Word wordA, Word wordB, LinkType type)
        {
            this.WordA = wordA;
            this.WordB = wordB;
            this.Type = type;

            if (type == LinkType.Anagram && !LinkTypes.IsAnagram(WordA.Text, WordB.Text))
            {
                throw new Exception(WordA.Text + " is not an anagram of " + WordB.Text);
            }
            if (type == LinkType.OneLetterChange && !LinkTypes.IsOneLetterChange(WordA.Text, WordB.Text))
            {
                throw new Exception(WordA.Text + " is not a letter change from " + WordB.Text);
            }
            if (type == LinkType.OneLetterAddOrRemove && !LinkTypes.IsOneLetterAddOrRemove(WordA.Text, WordB.Text))
            {
                throw new Exception(WordA.Text + " is not a letter additon or subtraction to " + WordB.Text);
            }
            

        }
        public override string ToString()
        {
            return WordA + "-" + WordB;
        }

        public override bool Equals(object obj)
        {
            if(obj is Link other)
            {
                return WordA.Equals(other.WordA) && WordB.Equals(other.WordB) && Type.Equals(other.Type);
            }
            else
            {
                return false;
            }
        }

        public override int GetHashCode()
        {
            return WordA.GetHashCode() + WordB.GetHashCode() + Type.GetHashCode();
        }

        public Link Reverse()
        {
            return new Link(this.WordB, this.WordA, this.Type);
        }
    }
}
