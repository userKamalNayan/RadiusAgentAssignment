package com.radiusagent.commons.timber

/** @Author: Kamal Nayan
 * @since: 22/12/22 at 7:28 PM */

class CrashlyticsNonFatalError constructor(message: String, cause: Throwable) :
    RuntimeException(message, cause)