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
        }


    }
}
