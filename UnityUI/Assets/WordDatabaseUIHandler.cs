﻿using System;
using System.Collections;
using System.Collections.Generic;
using System.Net.Http.Headers;
using TMPro;
using UnityEngine;
using UnityEngine.UI;
using WordTargetCore;
using System.Linq;
using UnityEditor;
using System.IO;

public class WordDatabaseUIHandler : MonoBehaviour
{
    public GameObject listItemTemplate;

    public GameObject selectedWordField;
    TMP_InputField selectedWordTMPField;

    public GameObject selectedLinkField;
    TMP_InputField selectedLinkTMPField;

    public GameObject wordListBoxContent;
    public GameObject linkListBoxContent;

    public GameObject addWordButtonObject;
    private Button addWordButton;

    public GameObject addAssociationButtonObject;
    private Button addAssociationButton;

    public GameObject addSynonymButtonObject;
    private Button addSynonymButton;

    public GameObject addAntonymButtonObject;
    private Button addAntonymButton;

    public GameObject removeWordButtonObject;
    private Button removeWordButton;

    public GameObject removeLinkButtonObject;
    private Button removeLinkButton;

    public GameObject saveButtonObject;
    private Button saveButton;

    private WordDatabase db = new WordDatabase();
    private string filename = null;

    void Start()
    {
        saveButton = saveButtonObject.GetComponent<Button>();
        SynchWords();

        selectedWordTMPField = selectedWordField.GetComponent<TMP_InputField>();
        selectedWordTMPField.onValueChanged.AddListener(text => onSelectedWordChange(text));

        selectedLinkTMPField = selectedLinkField.GetComponent<TMP_InputField>();
        selectedLinkTMPField.onValueChanged.AddListener(text => onSelectedLinkChange(selectedWordTMPField.text, text));

        addWordButton = addWordButtonObject.GetComponent<Button>();
        addAssociationButton = addAssociationButtonObject.GetComponent<Button>();
        addSynonymButton = addSynonymButtonObject.GetComponent<Button>();
        addAntonymButton = addAntonymButtonObject.GetComponent<Button>();

        removeWordButton = removeWordButtonObject.GetComponent<Button>();
        removeLinkButton = removeLinkButtonObject.GetComponent<Button>();
    }

    Word GetSelectedWordA()
    {
        return new Word(selectedWordTMPField.text.Trim());
    }

    Word GetSelectedWordB()
    {
        return new Word(selectedLinkTMPField.text.Trim());
    }

    /// <summary>
    /// Updates the UI when the selected word is changed.
    /// </summary>
    /// <param name="textA">the new word selected</param>
    void onSelectedWordChange(string textA)
    {
        // Determine whether the word can be added/removed
        bool canAdd = textA != "" && !db.ContainsWord(textA);
        bool canRemove = textA != "" && db.ContainsWord(textA);

        // Update the UI accordingly
        addWordButton.interactable = canAdd;
        removeWordButton.interactable = canRemove;

        // If the word is changed, the link is changed as well
        onSelectedLinkChange(textA, selectedLinkTMPField.text);
    }

    /// <summary>
    /// Updates the UI when the selected words are changed.
    /// </summary>
    /// <param name="textA">the new word selecte</param>
    /// <param name="textB">the new link destination</param>
    void onSelectedLinkChange(string textA, string textB)
    {
        // Determine whether a link between the selected word can be added/removed
        Link link = db.GetLinkBetween(textA, textB);
        bool canAdd = textA != "" && textB != "" && link == null;
        bool canRemove = textA != "" && textB != "" && link != null && !link.Type.IsAutomatic();

        // Update the UI accordingly
        addAssociationButton.interactable = canAdd;
        addSynonymButton.interactable = canAdd;
        addAntonymButton.interactable = canAdd;
        removeLinkButton.interactable = canRemove;
    }

    /// <summary>
    /// Synchronizes the words in the db with the ones displayed in the word ListBox
    /// </summary>
    void SynchWords()
    {
        ClearListBox(wordListBoxContent);
        foreach (Word word in db.GetAllWords().OrderBy(x => x.Text).ToList()) {
            var copy = Instantiate(listItemTemplate);
            copy.transform.SetParent(wordListBoxContent.transform);
            copy.transform.localScale = new Vector3(1, 1, 1);
            copy.GetComponentInChildren<TextMeshProUGUI>().SetText(word.Text);
            copy.GetComponentInChildren<Button>().onClick.AddListener(
                () =>
                {
                    onWordListBoxSelection(word);
                }
            );
        }

        saveButton.interactable = (filename != null);
    }

    private Dictionary<LinkType, Color32> colorForLinkType = new Dictionary<LinkType, Color32>() { { LinkType.OneLetterChange, new Color32(0, 128, 128, 255) },
        { LinkType.OneLetterAddOrRemove, new Color32(0, 0, 255, 255) },
        { LinkType.Anagram, new Color32(128, 0, 128, 255) },
        { LinkType.Synonym, new Color32(0, 128, 0, 255) },
        { LinkType.Antonym, new Color32(192, 0, 0, 255) },
        { LinkType.WordAssociation, new Color32(0, 0, 0, 255) } };


    /// <summary>
    /// Responds to a selection in the word ListBox
    /// </summary>
    /// <param name="selectedWord">the selected word</param>
    void onWordListBoxSelection(Word selectedWord)
    {
        selectedWordTMPField.text = selectedWord.Text;

        // Should check whether the current linked word matches
        string currentLink = selectedLinkTMPField.text;
        bool keepLink = false;

        ClearListBox(linkListBoxContent);
        foreach (Link link in db.GetLinksFor(selectedWord).OrderBy(x => x.WordB.Text).ToList())
        {
            var copy = Instantiate(listItemTemplate);
            copy.transform.SetParent(linkListBoxContent.transform);
            copy.transform.localScale = new Vector3(1, 1, 1);
            copy.GetComponentInChildren<TextMeshProUGUI>().color = colorForLinkType[link.Type];
            copy.GetComponentInChildren<TextMeshProUGUI>().SetText(link.WordB.Text);
            if (link.WordB.Text.Equals(currentLink)) keepLink = true;
            copy.GetComponentInChildren<Button>().onClick.AddListener(
                () =>
                {
                    onLinkListBoxSelection(link);
                }
            );
        }

        if (!keepLink)
        {
            selectedLinkTMPField.text = "";
        }
    }

    public void OnAddWordButton()
    {
        db.AddWord(GetSelectedWordA());
        SynchWords();
        onWordListBoxSelection(GetSelectedWordA());
    }

    public void OnAddSynonymButton()
    {
        AddLink(GetSelectedWordA(),
            GetSelectedWordB(),
            LinkType.Synonym);
    }

    public void OnAddAntonymButton()
    {
        AddLink(GetSelectedWordA(),
            GetSelectedWordB(),
            LinkType.Antonym);
    }

    public void OnAddAssociationButton()
    {
        AddLink(GetSelectedWordA(),
            GetSelectedWordB(),
            LinkType.WordAssociation);
    }

    public void OnDeleteWordButton()
    {
        db.RemoveWord(new Word(selectedWordTMPField.text.ToUpper()));
        SynchWords();
        selectedWordField.GetComponent<TMP_InputField>().text = "";
        ClearListBox(linkListBoxContent);
    }

    public void OnDeleteLinkButton()
    {
        Word wordA = new Word(selectedWordTMPField.text.ToUpper());
        Word wordB = new Word(selectedLinkTMPField.text.ToUpper());
        Link link = db.GetLinksFor(wordA).First(x => x.WordB.Equals(wordB));
        if (link != null)
        {
            db.RemoveLink(link);
        }
        onWordListBoxSelection(wordA);
    }

    void AddLink(Word wordA, Word wordB, LinkType type)
    {
        db.AddWord(wordA);
        db.AddWord(wordB);
        Link link = new Link(wordA, wordB, type);
        db.AddLink(link);
        SynchWords();
        onWordListBoxSelection(wordA);
        onLinkListBoxSelection(link);
    }

    void onLinkListBoxSelection(Link selectedLink)
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

    public void Load()
    {
        string[] paths = SFB.StandaloneFileBrowser.OpenFilePanel("Open Word Target Database", "", "wtdb", false);
        if (paths.Length == 1)
        {
            StreamReader file = System.IO.File.OpenText(paths[0]);
            db = WordDatabase.Deserialize(file);
            SynchWords();
            filename = paths[0];
        }
    }

    public void SaveAs()
    {
        string path = SFB.StandaloneFileBrowser.SaveFilePanel("Save Word Target Database", "", "", "wtdb");
        if (path != "")
        {
            filename = path;
            Save();
        }
    }

    public void Save()
    {
        if (filename != null)
        {
            StreamWriter file = System.IO.File.CreateText(filename);
            db.Serialize(file);
            file.Close();
        }
    }

    public void Quit()
    {
#if UNITY_EDITOR
        UnityEditor.EditorApplication.isPlaying = false;
#else
        Application.Quit();
#endif
    }

}
