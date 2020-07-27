using System;
using System.Collections;
using System.Collections.Generic;
using System.Net.Http.Headers;
using TMPro;
using UnityEngine;
using UnityEngine.UI;
using WordTargetCore;
using System.Linq;

public class WordDatabaseUIHandler : MonoBehaviour
{
    public GameObject listItemTemplate;

    public GameObject selectedWordField;
    public GameObject selectedLinkField;

    public GameObject wordListBoxContent;
    public GameObject linkListBoxContent;

    private WordDatabase db = new WordDatabase();

    void Start()
    {
        db.AddWords(new List<string>() { "BAT", "CAT", "BASEBALL", "SPORT", "PORTS", "PORT" });
        db.AddLink(new Link(new Word("BAT"), new Word("BASEBALL"), LinkType.WordAssociation));
        db.AddLink(new Link(new Word("SPORT"), new Word("BASEBALL"), LinkType.WordAssociation));
        SynchWords();
    }

    void SynchWords()
    {
        ClearListBox(wordListBoxContent);
        foreach (Word word in db.GetAllWords().OrderBy(x => x.Text).ToList()) {
            var copy = Instantiate(listItemTemplate);
            copy.transform.SetParent(wordListBoxContent.transform);
            copy.GetComponentInChildren<TextMeshProUGUI>().SetText(word.Text);
            copy.GetComponentInChildren<Button>().onClick.AddListener(
                () =>
                {
                    ChangeSelectedWord(word);
                }
            );
        }
    }

    private Dictionary<LinkType, Color32> colorForLinkType = new Dictionary<LinkType, Color32>() { { LinkType.OneLetterChange, new Color32(0, 128, 128, 255) },
        { LinkType.OneLetterAddOrRemove, new Color32(0, 0, 255, 255) },
        { LinkType.Anagram, new Color32(128, 0, 128, 255) },
        { LinkType.Synonym, new Color32(0, 128, 0, 255) },
        { LinkType.Antonym, new Color32(192, 0, 0, 255) },
        { LinkType.WordAssociation, new Color32(0, 0, 0, 255) } };

    void ChangeSelectedWord(Word selectedWord)
    {
        TMP_InputField field = selectedWordField.GetComponent<TMP_InputField>();
        field.text = selectedWord.Text;

        // Should check whether the current linked word matches
        string currentLink = selectedLinkField.GetComponent<TMP_InputField>().text;
        bool keepLink = false;

        ClearListBox(linkListBoxContent);
        foreach (Link link in db.GetLinksFor(selectedWord).OrderBy(x => x.WordB.Text).ToList())
        {
            var copy = Instantiate(listItemTemplate);
            copy.transform.SetParent(linkListBoxContent.transform);
            copy.GetComponentInChildren<TextMeshProUGUI>().color = colorForLinkType[link.Type];
            copy.GetComponentInChildren<TextMeshProUGUI>().SetText(link.WordB.Text);
            if (link.WordB.Text.Equals(currentLink)) keepLink = true;
            copy.GetComponentInChildren<Button>().onClick.AddListener(
                () =>
                {
                    ChangeSelectedLink(link);
                }
            );
        }

        if (!keepLink)
        {
            selectedLinkField.GetComponent<TMP_InputField>().text = "";
        }
    }
    public void OnAddWordButton()
    {
        TMP_InputField field = selectedWordField.GetComponent<TMP_InputField>();
        string newWord = field.text;

        db.AddWord(newWord);
        SynchWords();
        ChangeSelectedWord(new Word(newWord));
    }

    void ChangeSelectedLink(Link selectedLink)
    {
        TMP_InputField field = selectedLinkField.GetComponent<TMP_InputField>();
        field.text = selectedLink.WordB.Text;
    }

    void ClearListBox(GameObject listBoxContent)
    {
        while (listBoxContent.transform.childCount > 0)
        {
            Transform obj = listBoxContent.transform.GetChild(0);
            obj.SetParent(null);
            Destroy(obj.gameObject);
        }
    }

    void Update()
    {
        
    }
}
