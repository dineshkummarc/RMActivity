package com.tmobile.reallyme.application.pages.profile.blocks;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.utils.ImageManager;
import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 19.07.2009
 * Time: 18:21:55
 */
public class Context extends AbstractBlock{

    public View createLayout() {
        View view = (View)View.inflate(this.activity, R.layout.profile_block_context, null);

        final TextView location = (TextView)view.findViewById(R.id.location);
        final TextView timeZone = (TextView)view.findViewById(R.id.time_zone);
        final TextView statusMsg = (TextView)view.findViewById(R.id.statemsg);

        final ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
        final ImageView state = (ImageView)view.findViewById(R.id.state);
        //temporary
        if (Utils.isNotBlank(identity.getAvatar())) {
            imageManager.fetchDrawableLazy(identity.getAvatar(), avatar);
        }
        state.setImageResource(State.getById(identity.getContext().state.id).getUnselectedImageResourceLarge());

        location.setText(identity.getContext().location.name);
        timeZone.setText(Utils.isNotBlank(identity.getContext().location.timeZone) ? identity.getContext().location.timeZone : "");
        statusMsg.setText(identity.getContext().state.desc);
        return view;
    }
}
