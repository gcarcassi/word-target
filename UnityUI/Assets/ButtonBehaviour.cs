using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using WordTargetCore;

public class ButtonBehaviour : MonoBehaviour
{
    int n;
    public void OnButtonPress()
    {
        n++;
        Debug.Log("New link: " + new Word("cat"));
    }
}
