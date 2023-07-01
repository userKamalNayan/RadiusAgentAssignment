package com.radiusagent.commons.timber

import timber.log.Timber

/** @Author: Kamal Nayan
 * @since: 22/12/22 at 6:43 PM */

class CrashlyticsLogTree(): Timber.Tree() {

    /**
     * log error or record exception using crashlytics or any other serive
     */
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (t != null) {
            //recordException(CrashlyticsNonFatalError("$tag : $message", t))
        } else {
            //log("$priority/$tag: $message")
        }
    }
}

