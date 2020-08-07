using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace WordTargetCore
{
    [TestClass]
    public class CommonTestObjects
    {
        public static Word cat = new Word("cat");
        public static Word bat = new Word("bat");
        public static Word baseball = new Word("baseball");
        public static Word sport = new Word("sport");
        public static Word ports = new Word("ports");
        public static Word parts = new Word("parts");
        public static Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
        public static Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
        public static Link baseballSport = new Link(baseball, sport, LinkType.WordAssociation);
        public static Link sportPorts = new Link(sport, ports, LinkType.Anagram);
        public static Link portsParts = new Link(ports, parts, LinkType.OneLetterChange);

    }
}
