using System;

namespace WordTargetCore
{
    public enum LinkType
    {
        OneLetterChange,
        OneLetterAddOrRemove,
        Anagram,
        Synonym,
        Antonym,
        WordAssociation
    }

    public static class LinkTypeExtensions
    {
        public static bool IsAutomatic(this LinkType type)
        {
            switch (type)
            {
                case LinkType.OneLetterChange:
                case LinkType.OneLetterAddOrRemove:
                case LinkType.Anagram:
                    return true;
                case LinkType.Synonym:
                case LinkType.Antonym:
                case LinkType.WordAssociation:
                default:
                    return false;
            }
        }
    }
}