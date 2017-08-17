package id.co.imastudio.bakingapp3.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by idn on 8/17/2017.
 */

public class RecipeService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeDataProvider(this, intent) ;
    }
}
