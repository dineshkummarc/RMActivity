package com.tmobile.reallyme.core.job;

import java.util.Timer;
import java.util.TimerTask;

import com.tmobile.reallyme.core.api.remote.UpdatesManager;
import com.tmobile.reallyme.utils.Constants;
import com.tmobile.reallyme.utils.Log;

/**
 * User: Kolesnik Aleksey
 * Date: 02.07.2009
 * Time: 12:57:12
 */

/*Jobs define*/
public class JobsManager {
    private Log log = new Log(this.getClass());
    private static final JobsManager _instance = new JobsManager();

    public static JobsManager getInstance() {
        return _instance;
    }

    private Timer getUpdatesJob = new Timer();

//    private TimerTask task = new TimerTask() {
//          public void run() {
//              log.info("getUpdatesJob:Getting updates");
//              UpdatesManager.getInstance().getUpdates();
//          }
//      };

    public void start() {
        getUpdatesJob.purge();
        getUpdatesJob.schedule( new TimerTask() {
          public void run() {
              log.info("getUpdatesJob:Getting updates");
              UpdatesManager.getInstance().getUpdates();
          }
      }, 1, Constants.API_UPDATES_INTERVAL );
    }

}
