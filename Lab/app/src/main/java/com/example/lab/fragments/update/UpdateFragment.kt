package com.example.lab.fragments.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lab.R

import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab.data.entities.Note
import com.example.lab.data.vm.NoteViewModel

class UpdateFragment : Fragment() {
    private  val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        view.findViewById<TextView>(R.id.updateNote).text = args.currentNote.note

        val updateButton = view.findViewById<Button>(R.id.update)
        updateButton.setOnClickListener {
            updateNote()
        }

        val deleteButton = view.findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            deleteNote()
        }

        val backButton = view.findViewById<Button>(R.id.backToListFromUpdate)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return  view
    }

    private  fun updateNote(){
        val noteText = view?.findViewById<EditText>(R.id.updateNote)?.text.toString()

        if(noteText.isEmpty()) {
            makeText(context , "Não pode uma nota vazia!", Toast.LENGTH_LONG).show()
        }
        else {
            val note = Note(args.currentNote.id, noteText)

            mNoteViewModel.updateNote(note)

            makeText(requireContext(), "Nota atualizada com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            makeText(
                requireContext(),
                "Nota apagada com sucesso!",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não") { _, _ -> }
        builder.setTitle("Apagar")
        builder.setMessage("Tem a certeza que pretende apagar a Nota?")
        builder.create().show()
    }
}