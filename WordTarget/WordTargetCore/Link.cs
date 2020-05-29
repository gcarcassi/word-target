using System;
using System.Collections.Generic;
using System.Text;

namespace WordTargetCore
{
    public class Link
    {
        private Word wordA;
        private Word wordB;
        private LinkType type;

        public Link(Word wordA, Word wordB, LinkType type)
        {
            this.wordA = wordA;
            this.wordB = wordB;
            this.type = type;
        }
    }
}
