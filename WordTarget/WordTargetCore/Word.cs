using System;
using System.Collections.Generic;

namespace WordTargetCore
{
    public class Word
    {
        public string Text { get; }

        public Word(string text)
        {
            Text = text.ToUpper();
        }

        public override string ToString()
        {
            return Text;
        }

        public override bool Equals(object obj)
        {
            if(obj is Word other)
            {
                return Text.Equals(other.Text);
            }
            else
            {
                return false;
            }
        }

        public override int GetHashCode()
        {
            return Text.GetHashCode();
        }
    }
}
