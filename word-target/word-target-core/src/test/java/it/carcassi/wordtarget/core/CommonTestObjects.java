/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.carcassi.wordtarget.core;

/**
 *
 * @author Matteo
 */
public class CommonTestObjects {
    public static Word cat = new Word("cat");
        public static Word bat = new Word("bat");
        public static Word baseball = new Word("baseball");
        public static Word sport = new Word("sport");
        public static Word ports = new Word("ports");
        public static Word port = new Word("port");
        public static Word parts = new Word("parts");
        public static Word bit = new Word("bit");
        public static Word brat = new Word("brat");
        public static Word tab = new Word("tab");
        public static Word car = new Word("car");
        public static Word cart = new Word("cart");
        public static Link catBat = new Link(cat, bat, LinkType.OneLetterChange);
        public static Link batBaseball = new Link(bat, baseball, LinkType.WordAssociation);
        public static Link baseballSport = new Link(baseball, sport, LinkType.WordAssociation);
        public static Link sportPorts = new Link(sport, ports, LinkType.Anagram);
        public static Link sportPort = new Link(sport, port, LinkType.OneLetterAddOrRemove);
        public static Link portsPort = new Link(ports, port, LinkType.OneLetterAddOrRemove);
        public static Link portsParts = new Link(ports, parts, LinkType.OneLetterChange);
}
