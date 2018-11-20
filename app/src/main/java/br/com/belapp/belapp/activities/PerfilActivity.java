package br.com.belapp.belapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.database.utils.FirebaseUtils;
import br.com.belapp.belapp.exceptions.ValidationException;
import br.com.belapp.belapp.model.Cliente;
import br.com.belapp.belapp.utils.StringUtils;

import static br.com.belapp.belapp.database.utils.FirebaseUtils.*;

public class PerfilActivity extends AppCompatActivity {

    private static final String TAG = "belapp.activities";
    private Cliente mClienteAtual, mClienteModificado;
    private EditText mEtNome, mEtEmail, mEtTelefone, mEtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Configuraçao do toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_perfil);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        mEtNome = findViewById(R.id.etNome);
        mEtEmail = findViewById(R.id.etEmail);
        mEtTelefone = findViewById(R.id.etTelefone);

        Button btnAlterarSenha = findViewById(R.id.btnAlterarSenha);
        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validarCampos();
                    povoarClienteModificado();
                    if(verificarSeHaAlteracoes()){
                        salvarAlteracoes();
                        mClienteAtual = mClienteModificado;
                    }
                } catch (ValidationException e) {
                    Toast.makeText(PerfilActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }
            }
        });

        final Query query = getmDatabaseReference().child("clientes");
        final String userId = getUsuarioAtual().getUid();

        final ValueEventListener mValueEventListenerClientes = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dadosObjeto : dataSnapshot.getChildren()) {
                    if(dadosObjeto.getKey().equals(userId)) {
                        mClienteAtual = dadosObjeto.getValue(Cliente.class);
                        mClienteModificado = dadosObjeto.getValue(Cliente.class);
                        preencherCampos();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        query.addValueEventListener(mValueEventListenerClientes);

    }

    /**
     * Realiza a mudanca de e-mail no Firebase Auth
     */
    private void realizarAlteracaoAutenticacao() {
        AuthCredential credencial = EmailAuthProvider
                .getCredential(Objects.requireNonNull(getUsuarioAtual().getEmail()), mClienteAtual.getmSenha());
        getUsuarioAtual()
                .reauthenticate(credencial)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getUsuarioAtual()
                        .updateEmail(mClienteModificado.getmEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "Alterou o email no firebase auth");
                                }
                                else{
                                    Log.d(TAG, "nao alterou o email no firebase auth");
                                }
                            }
                        });
                if(task.isSuccessful()){
                    Log.d(TAG, "autenticado com sucesso");
                }
                else{
                    Log.d(TAG, "erro ao autenticar");
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes
        onBackPressed();
        return true;
    }

    private void salvarAlteracoes() {
        getmDatabaseReference()
                .child("clientes")
                .child(getUsuarioAtual().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getmDatabaseReference()
                        .child("clientes")
                        .child(getUsuarioAtual().getUid())
                        .setValue(mClienteModificado)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(
                                            PerfilActivity.this,
                                            getText(R.string.sucess_alteracao_realizada),
                                            Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, getUsuarioAtual().getEmail());
                                    if(verificarSeHaAlteracaoEmail()) {
                                        realizarAlteracaoAutenticacao();
                                    }
                                }
                                else{
                                    Toast.makeText(
                                            PerfilActivity.this,
                                            getText(R.string.error_atualizar_perfil),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void povoarClienteModificado() {
        mClienteModificado.setmEmail(mEtEmail.getText().toString());
        mClienteModificado.setmNome(mEtNome.getText().toString());
        mClienteModificado.setmTelefone(mEtTelefone.getText().toString());
    }

    private boolean verificarSeHaAlteracoes() {
        return !mClienteAtual.equals(mClienteModificado);
    }

    /**
     * Verifica alteracoes de e-mail.
     * @return true se ha alteracao de e-mail
     */
    private boolean verificarSeHaAlteracaoEmail() {
        return !Objects.requireNonNull(getUsuarioAtual().getEmail()).equalsIgnoreCase(mClienteModificado.getmEmail());
    }

    private void preencherCampos() {
        if(mClienteAtual != null){
            mEtNome.setText(mClienteAtual.getmNome());
            mEtEmail.setText(mClienteAtual.getmEmail());
            mEtTelefone.setText(mClienteAtual.getmTelefone());
        }
    }

    private void validarCampos() throws ValidationException{
        if(TextUtils.isEmpty(mEtNome.getText().toString())){
            throw new ValidationException(getString(R.string.error_nome_nao_pode_ser_vazio));
        }
        if(TextUtils.isEmpty(mEtEmail.getText().toString())){
            throw new ValidationException(getString(R.string.error_email_nao_pode_ser_vazio));
        }
        if(!StringUtils.isEmailValido(mEtEmail.getText().toString())){
            throw new ValidationException(getString(R.string.error_email_invalido));
        }
    }



}