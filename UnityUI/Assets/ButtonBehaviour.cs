using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using WordTargetCore;
using TMPro;
using UnityEngine.UIElements;

public class ButtonBehaviour : MonoBehaviour
{
    public TextMeshProUGUI text;
    public GameObject content;

    int n;
    public void OnButtonPress()
    {
        n++;
        Debug.Log("New link: " + new Word("cat"));
        Debug.Log("New element: " + text);
        text.SetText("Changing " + n + " Content " + content);
    }
}
