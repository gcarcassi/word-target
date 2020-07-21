using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using WordTargetCore;
using TMPro;

public class ButtonBehaviour : MonoBehaviour
{
    public TextMeshProUGUI text;

    int n;
    public void OnButtonPress()
    {
        n++;
        Debug.Log("New link: " + new Word("cat"));
        Debug.Log("New element: " + text);
        text.SetText("Changing " + n);
    }
}
