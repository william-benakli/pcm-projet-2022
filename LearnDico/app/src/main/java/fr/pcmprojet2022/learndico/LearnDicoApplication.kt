package fr.pcmprojet2022.learndico

import android.app.Application;
import fr.pcmprojet2022.learndico.data.LearnDicoBD

class LearnDicoApplication : Application() {

    val getDataBase by lazy {
        LearnDicoBD.getInstanceBD(this);
    }

}
