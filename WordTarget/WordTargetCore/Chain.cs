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

        public void add(Chain newChain)
        { 
            if (newChain.Count == 0)
            {
                return;
            }
            
            if (this.Count != 0 && this.links[links.Count - 1].WordB != newChain.links[0].WordA)
            {
                throw new Exception(this.links[links.Count - 1] + " is not a valid link with " + newChain.links[0]);
            }
            
            links.AddRange(newChain.links);
        }

        public void prepend(Link newLink)
        {
            links.Insert(0, newLink);
        }

        public void prepend(Chain newChain)
        {
            if (newChain.Count == 0)
            {
                return;
            }
            if (this.Count != 0 && this.links[0].WordA != newChain.links[newChain.links.Count - 1].WordB)
            {
                throw new Exception(this.links[0] + " is not a valid link with " + newChain.links[newChain.links.Count - 1]);
            }
            links.InsertRange(0, newChain.links);
        }

        public void invert()
        {
            this.links.Reverse();
        }
    }
}
