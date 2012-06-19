package com.tmobile.reallyme.application.managers;

import android.content.Intent;
import android.view.View;

import com.tmobile.reallyme.application.enums.PageEnum;

/**
 * User: Kolesnik Aleksey
 * Date: 30.06.2009
 * Time: 12:10:18
 */
public class PageManager {
    private static PageEnum currentPage = null;
    
    private PageManager _instance = new PageManager();

    private PageManager() {}

    public static void openPage(View view, PageEnum page) {
        openPage(view, page, null);
    }

    public static void openPage(View view, PageEnum page, Intent extras) {
        if (currentPage == null || !currentPage.equals(page)) {
            Intent intent = new Intent(view.getContext(), page.getClazz());
            if (extras != null) {
                intent.putExtras(extras);
            }
            view.getContext().startActivity(intent);
        }
    }

    public PageEnum getCurrentPage() {
        return this.currentPage;
    }


}
