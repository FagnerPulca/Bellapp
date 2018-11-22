package br.com.belapp.belapp.test;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class DefaultTest {

    private Activity activity;

    /**
     * Pausa a execuçao pelo tempo informado
     * @param tempo em milisegundos
     */
    public void esperar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Preenche as informaçoes no campo
     * @param idEditText a ser preenchido
     * @param msg  msg a ser escrita no campo
     */
    public void preencherCampoEditText(int idEditText, String msg){
        limparCampoEditText(idEditText);
        onView(withId(idEditText)).perform(typeText(msg), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
    }

    /**
     * Limpa o componente EditText
     * @param idEditText a ser limpo
     */
    public void limparCampoEditText(int idEditText){
        onView(withId(idEditText))
                .perform(replaceText(""));
        Espresso.closeSoftKeyboard();
    }

    /**
     *
     * @return activity retornada de activityTestRule
     */
    public Activity getActivity() {
        return activity;
    }

    public void verificarMensagemToast(String mensagem){
        // Espera 2 segundos
        esperar(1000);
        onView(withText(mensagem))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     *
     * @param idBotao do botao a ser apertado
     */
    public void apertarBotao(int idBotao){
        onView(withId(idBotao))
                .perform(click());
    }

//    public  void isActivityAtual(String activityNome) {
//        intended(hasComponent(activityNome));
//    }

    /**
     *
     * @return referência da activity que está sendo exibida na tela
     */
    public Activity getAtualActivity() {
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                activity[0] = (Activity) view.getContext();
            }
        });
        return activity[0];
    }
}