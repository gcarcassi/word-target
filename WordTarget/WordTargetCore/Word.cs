using System;

namespace WordTargetCore
{
    public class Word
    {
        public string Text { get; }

        public Word(string text)
        {
            Text = text;
        }

        public override string ToString()
        {
            return Text;
        }
    }
}
