package ru.agladkov.questgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.agladkov.questgo.screens.questList.QuestListFragment

class MainActivity : AppCompatActivity(), PushListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onValueChanged(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}