using System;
using System.Collections;
using System.Collections.Generic;
using System.Net.Http.Headers;
using TMPro;
using UnityEngine;
using UnityEngine.UI;
using WordTargetCore;

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
        foreach (Word word in db.GetAllWords()) {
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

    void ChangeSelectedWord(Word selectedWord)
    {
        TMP_InputField field = selectedWordField.GetComponent<TMP_InputField>();
        field.text = selectedWord.Text;

        // Should check whether the current linked word matches
        string currentLink = selectedLinkField.GetComponent<TMP_InputField>().text;
        bool keepLink = false;

        ClearListBox(linkListBoxContent);
        foreach (Link link in db.GetLinksFor(selectedWord))
        {
            var copy = Instantiate(listItemTemplate);
            copy.transform.SetParent(linkListBoxContent.transform);
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

    void ChangeSelectedLink(Link selectedLink)
    {
        TMP_InputField field = selectedLinkField.GetComponent<TMP_InputField>();
        Debug.Log("Field " + field);
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
