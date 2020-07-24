using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class AddObjectToList : MonoBehaviour
{
    public GameObject itemTemplate;

    public GameObject content;

    public void OnButtonClick()
    {
        var copy = Instantiate(itemTemplate);
        copy.transform.SetParent(content.transform);
        copy.GetComponentInChildren<TextMeshProUGUI>().SetText("One");
    }
}
