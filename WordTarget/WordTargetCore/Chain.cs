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

        public void Add(Link newLink)
        {   
            if (links.Count != 0 && !(links[links.Count-1].WordB.Equals(newLink.WordA)))
            {
                throw new Exception(links[links.Count - 1] + " is not a valid link with " + newLink);
            }
            links.Add(newLink);
        }

        public void Add(Chain newChain)
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

        public void Prepend(Link newLink)
        {
            links.Insert(0, newLink);
        }

        public void Prepend(Chain newChain)
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

        public void Reverse()
        {
            this.links.Reverse();
        }

        public override string ToString()
        {
            if(this.links.Count == 0)
            {
                return "";
            }
            string chainString = "";
            int i = 0;
            for(; i < this.links.Count; i++)
            {
                chainString = chainString + this.links[i].WordA.ToString() + "-";
                
            }
            chainString = chainString + this.links[i-1].WordB.ToString();
            return chainString;
        }

        public void RemoveFirst()
        {
            if (this.links.Count == 0)
            {
                throw new Exception("Cannot remove elements from an empty chain");
            }
            this.links.RemoveAt(0);
            
        }

        public void RemoveLast()
        {
            if (this.links.Count == 0)
            {
                throw new Exception("Cannot remove elements from an empty chain");
            }
            this.links.RemoveAt(links.Count - 1);
        }

        public bool Contains(Word word)
        {
            if (Count == 0)
            {
                return false;
            }

            foreach (Link link in links)
            {
                if (word.Equals(link.WordA))
                {
                    return true;
                }
            }

            return word.Equals(this[Count - 1].WordB);
        }
    }
}
