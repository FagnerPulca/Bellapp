package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Estabelecimento {

    //Aqui atributos e metodos da classe
    String mEId;
    String mNome;
    String mTelefone;
    String mDescricao;
    String mMediaAvaliacoes;
    String mGaleria;
    String mEndereco_ID;
    String mLocalizacao;
    ArrayList<Servico> mServicos;
    ArrayList<Agenda> mAgenda;
    ArrayList<Promocoes> mPromocoes;
    ArrayList<Profissional> mProfissionais;
    ArrayList<String> mAvaliacoes;
    private DatabaseReference mDataBase;

    public Estabelecimento() {
    }

    public Estabelecimento(String mNome, String mTelefone, String mDescricao, String mMediaAvaliacoes,
                           String mGaleria, String mEndereco, String mLocalizacao, ArrayList<Servico> mServicos, ArrayList<Agenda> mAgenda,
                           ArrayList<Promocoes> mPromocoes, ArrayList<Profissional> mProfissionais, ArrayList<String> mAvaliacoes) {
        //this.mEId = mEId;
        this.mNome = mNome;
        this.mTelefone = mTelefone;
        this.mDescricao = mDescricao;
        this.mMediaAvaliacoes = mMediaAvaliacoes;
        this.mGaleria = mGaleria;
        this.mEndereco_ID = mEndereco;
        this.mLocalizacao = mLocalizacao;
        this.mServicos = mServicos;
        this.mAgenda = mAgenda;
        this.mPromocoes = mPromocoes;
        this.mProfissionais = mProfissionais;
        this.mAvaliacoes = mAvaliacoes;
    }

    public void addEstabelecimento(String nome, String telefone, String descricao, String mediaAvaliacoes,
                                   String galeria, String endereco, String localizacao, ArrayList<Servico> servicos_arl, ArrayList<Agenda> agenda_arl,
                                   ArrayList<Promocoes> promocoes_arl, ArrayList<Profissional> profissionais_arl, ArrayList<String> avaliacoes_arl) {

        Estabelecimento estabelecimento = new Estabelecimento(nome, telefone, descricao, mediaAvaliacoes,
                galeria, endereco, localizacao, servicos_arl, agenda_arl, promocoes_arl, profissionais_arl, avaliacoes_arl);

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mDataBase.child("estabelecimentos").push().setValue(estabelecimento);
    }

    public String getmEId() {
        return mEId;
    }

    public void setmEId(String mEId) {
        this.mEId = mEId;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public String getmTelefone() {
        return mTelefone;
    }

    public void setmTelefone(String mTelefone) {
        this.mTelefone = mTelefone;
    }

    public String getmDescricao() {
        return mDescricao;
    }

    public void setmDescricao(String mDescricao) {
        this.mDescricao = mDescricao;
    }

    public String getmMediaAvaliacoes() {
        return mMediaAvaliacoes;
    }

    public void setmMediaAvaliacoes(String mMediaAvaliacoes) {
        this.mMediaAvaliacoes = mMediaAvaliacoes;
    }

    public String getmGaleria() {
        return mGaleria;
    }

    public void setmGaleria(String mGaleria) {
        this.mGaleria = mGaleria;
    }

    public String getmEndereco_ID() {
        return mEndereco_ID;
    }

    public void setmEndereco_ID(String mEndereco_ID) {
        this.mEndereco_ID = mEndereco_ID;
    }

    public String getmLocalizacao() {
        return mLocalizacao;
    }

    public void setmLocalizacao(String mLocalizacao) {
        this.mLocalizacao = mLocalizacao;
    }

    public ArrayList<Servico> getmServicos() {
        return mServicos;
    }

    public void setmServicos(ArrayList<Servico> mServicos) {
        this.mServicos = mServicos;
    }

    public ArrayList<Agenda> getmAgenda() {
        return mAgenda;
    }

    public void setmAgenda(ArrayList<Agenda> mAgenda) {
        this.mAgenda = mAgenda;
    }

    public ArrayList<Promocoes> getmPromocoes() {
        return mPromocoes;
    }

    public void setmPromocoes(ArrayList<Promocoes> mPromocoes) {
        this.mPromocoes = mPromocoes;
    }

    public ArrayList<Profissional> getmProfissionais() {
        return mProfissionais;
    }

    public void setmProfissionais(ArrayList<Profissional> mProfissionais) {
        this.mProfissionais = mProfissionais;
    }

    public ArrayList<String> getmAvaliacoes() {
        return mAvaliacoes;
    }

    public void setmAvaliacoes(ArrayList<String> mAvaliacoes) {
        this.mAvaliacoes = mAvaliacoes;
    }

    public void setmDataBase(DatabaseReference mDataBase) {
        this.mDataBase = mDataBase;
    }
}
