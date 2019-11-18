package ar.edu.uade.scrumgame.presentation.view.component.infoTheory;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.widget.AppCompatTextView;

public class TitleTextView extends AppCompatTextView  {
    public TitleTextView(Context context) {
        super(context);
        this.setTextSize(16);
        setTypeface(getTypeface(), Typeface.BOLD);
    }

}
