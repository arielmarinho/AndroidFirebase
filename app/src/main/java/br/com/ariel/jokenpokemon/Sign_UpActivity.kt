package br.com.ariel.jokenpokemon

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.ariel.jokenpokemon.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign__up.*

class Sign_UpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign__up)
        mAuth = FirebaseAuth.getInstance()
        btCriar.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(
                    etEmail.text.toString(),
                    etSenha.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    salvaNoRealtimeDatabase()
                } else {
                    Toast.makeText(this@Sign_UpActivity,
                            it.exception?.message,
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun salvaNoRealtimeDatabase() {
        val user = Usuario(etNome.text.toString(),
                etEmail.text.toString(),
                etTelefone.text.toString())
        FirebaseDatabase.getInstance().getReference("Usuario")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@Sign_UpActivity,
                                "usuario cadastrado com sucesso",
                                Toast.LENGTH_LONG).show()
                        val intent = Intent()
                        intent.putExtra("email", etEmail.text.toString())
                        intent.putExtra("senha", etSenha.text.toString())
                        setResult(Activity.RESULT_OK,intent)
                        finish()
                    } else {
                        Toast.makeText(this@Sign_UpActivity,
                                it.exception?.message,
                                Toast.LENGTH_LONG).show()
                    }

                }
    }
}
