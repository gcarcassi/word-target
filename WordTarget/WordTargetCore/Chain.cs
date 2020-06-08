using System;
using System.Collections.Generic;
using System.Text;

namespace WordTargetCore
{
    public class Chain
    {
        private List<Link> links = new List<Link>();
        public int Count { get { return links.Count; } }
        public Link this[int i]
        {
            get { return links[i]; }
        }

        public void add(Link newLink)
        {   
            if (links.Count != 0 && links[links.Count-1].WordB != newLink.WordA)
            {
                throw new Exception(links[links.Count - 1] + " is not a valid link with " + newLink);
            }
            links.Add(newLink);
        }
        
    }
}
